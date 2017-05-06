package com.analysis.graph.datasource.jdbc;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.analysis.graph.datasource.*;
import com.analysis.graph.datasource.aggregation.*;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


/**
 * Created by cwc on 2017/4/27 0027.
 */
@DataSource(type = DataSourceType.JDBC)
public class JdbcDataSourceSystem extends DataSourceSystem {
    private DruidDataSource druidDataSource;

    @Override
    public boolean isAggregatable() {
        return true;
    }

    @Override
    public DataProvider getDataProvider(Map<String, String> query) throws SQLException {
        return new JdbcDataProvider(query);
    }

    @Override
    public DataAggregator getDataAggregator(DataProvider dataProvider) {
        return new JdbcDataAggregator(dataProvider);
    }


    private Connection getConnection() {
        try {
            Configuration conf = getDataSourceStatus().getConf();
            if (!conf.getBoolean("jdbc.pooled.enable", false)) {
                Class.forName(conf.getString("jdbc.driver", null));
                return DriverManager.getConnection(conf.getString("jdbc.url", null),
                        conf.getString("jdbc.username", null), conf.getString("jdbc.password", null));
            }
            synchronized (this) {
                if (druidDataSource == null) {
                    initDataSource(conf);
                }
                return druidDataSource.getConnection();
            }
        } catch (Exception e) {
            throw new RuntimeException("getConnection failed , due to : " + e.getMessage(), e);
        }
    }

    private void initDataSource(Configuration config) throws Exception {
        if (druidDataSource == null) {
            HashedMap conf = new HashedMap();
            conf.put(DruidDataSourceFactory.PROP_DRIVERCLASSNAME, config.getString("jdbc.driver", null));
            conf.put(DruidDataSourceFactory.PROP_URL, config.getString("jdbc.url", null));
            conf.put(DruidDataSourceFactory.PROP_USERNAME, config.getString("jdbc.username", null));
            if (config.containsKey("jdbc.password")) {
                conf.put(DruidDataSourceFactory.PROP_PASSWORD, config.getString("jdbc.password", null));
            }
            conf.put(DruidDataSourceFactory.PROP_INITIALSIZE, "3");

            druidDataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(conf);
            druidDataSource.setBreakAfterAcquireFailure(true);
            druidDataSource.setConnectionErrorRetryAttempts(5);
        }
    }

    private class JdbcDataProvider extends AbstractDataProvider {
        private Connection connection;
        private Statement stat;
        private ResultSet result;

        JdbcDataProvider(Map<String, String> query) throws SQLException {
            super(query);
            connect();
        }

        private void connect() throws SQLException {
            this.connection = getConnection();
            this.stat = connection.createStatement();
            this.result = stat.executeQuery(getQuery("sql"));
        }

        @Override
        public void resetResultIfExisted(String sql) throws SQLException {
            if (result != null) {
                batchClose(result);
            }
            this.result = stat.executeQuery(sql);
        }


        @Override
        public Object[] readLine() throws SQLException {
            if (result.next()) {
                ResultSetMetaData metaData = result.getMetaData();
                Object[] row = new Object[metaData.getColumnCount()];
                for (int i = 0, len = metaData.getColumnCount(); i < len; i++) {
                    row[i] = result.getObject(i + 1);
                }
                return row;
            }
            return null;
        }

        @Override
        @SuppressWarnings("Duplicates")
        public String[] readColumnLabels() throws SQLException {
            try (Connection connection = getConnection();
                 Statement stat = connection.createStatement();
                 ResultSet rs = stat.executeQuery(String.format("\nSELECT * FROM (\n%s\n) __view__ WHERE 1=0", getQuery("sql")))) {
                ResultSetMetaData metaData = rs.getMetaData();
                String[] label = new String[metaData.getColumnCount()];
                for (int i = 0, len = metaData.getColumnCount(); i < len; i++) {
                    label[i] = metaData.getColumnLabel(i + 1);
                }
                return label;
            }
        }

        @Override
        @SuppressWarnings("Duplicates")
        public Map<String, Integer> readColumnLabelTypeMap() throws SQLException {
            try (Connection connection = getConnection();
                 Statement stat = connection.createStatement();
                 ResultSet rs = stat.executeQuery(String.format("\nSELECT * FROM (\n%s\n) __view__ WHERE 1=0", getQuery("sql")))) {
                ResultSetMetaData metaData = rs.getMetaData();
                Map<String, Integer> map = new HashMap<>(metaData.getColumnCount());
                for (int i = 0, len = metaData.getColumnCount(); i < len; i++) {
                    map.put(metaData.getColumnLabel(i + 1), metaData.getColumnType(i + 1));
                }
                return map;
            }
        }

        @Override
        public Object[][] readFully() throws SQLException {
            if (!result.isFirst()) {
                result.beforeFirst();
            }
            List<Object[]> resultList = new ArrayList<>();
            Object[] row;
            while ((row = readLine()) != null) {
                resultList.add(row);
            }
            return resultList.toArray(new Object[][]{});
        }


        @Override
        public void close() throws IOException {
            batchClose(connection, stat, result);
        }
    }

    private static class JdbcDataAggregator extends AbstractDataAggregator {
        private AggregationResult aggregationResult;

        JdbcDataAggregator(DataProvider dataProvider) {
            super(dataProvider);
        }

        @Override
        public void aggregate(String columnName, AggregationView aggregationView) throws SQLException {
        }

        @Override
        public void aggregate(AggregationView av) throws SQLException {
            String sql = getAggregationSql(av);
            dataProvider.resetResultIfExisted(sql);
            List<ColumnIndex> columnIndices = getColumnIndexList(av);
            if (columnIndices.isEmpty()) {
                String[] labels = dataProvider.readColumnLabels();
                IntStream.range(0, labels.length).forEach(i -> columnIndices.add(new ColumnIndex(i, labels[i])));
            }
            aggregationResult = new AggregationResult(columnIndices, dataProvider.readFully());
        }

        private List<ColumnIndex> getColumnIndexList(AggregationView av) {
            List<ColumnIndex> columnIndexList = Stream.concat(av.columnStream(), av.rowStream())
                    .map(ColumnIndex::fromDimension)
                    .collect(Collectors.toList());
            columnIndexList.addAll(av.getMeasureStream().map(ColumnIndex::fromMeasure).collect(Collectors.toList()));
            IntStream.range(0, columnIndexList.size()).forEach(j -> columnIndexList.get(j).setIndex(j));
            return columnIndexList;
        }

        @Override
        public AggregationResult getAggregateResult() {
            return aggregationResult;
        }

        @Override
        public String getAggregationSql(AggregationView av) throws SQLException {
            String columnNames = assembleDimensionColumns(av.rowStream(), av.columnStream());
            String measureColumns = assembleMeasureColumns(av.getMeasureStream());
            String whereCondition = assembleWhereCondition(av.rowStream(), av.columnStream(), av.filterStream());

            String groupBy = "";
            StringJoiner selectColsStr = new StringJoiner(",");
            if (!StringUtils.isBlank(columnNames)) {
                groupBy = "GROUP BY " + columnNames;
                selectColsStr.add(columnNames);
            }
            if (!StringUtils.isBlank(measureColumns)) {
                selectColsStr.add(measureColumns);
            }
            String subQuerySql = ((JdbcDataProvider) dataProvider).getQuery("sql");
            String sqlTemplate = "\nSELECT %s \n FROM (\n%s\n) __view__ \n %s \n %s";

            return String.format(sqlTemplate, selectColsStr.length() == 0 ? "*" : selectColsStr, subQuerySql, whereCondition, groupBy);

        }

        private String assembleWhereCondition(Stream<Dimension> rowStream, Stream<Dimension> columnStream, Stream<Dimension> filterStream) {
            StringJoiner where = new StringJoiner("\nAND ", "WHERE" + " ", "").setEmptyValue("");
            Stream.concat(Stream.concat(rowStream, columnStream), filterStream)
                    .map(CONDITION_PARSER)
                    .filter(Objects::nonNull)
                    .forEach(where::add);
            return where.toString();
        }

        private String assembleDimensionColumns(Stream<Dimension> rowStream, Stream<Dimension> columnStream) {
            StringJoiner columns = new StringJoiner(", ", "", " ").setEmptyValue("");
            Stream.concat(rowStream, columnStream)
                    .map(Dimension::getName)
                    .distinct()
                    .filter(Objects::nonNull)
                    .forEach(columns::add);
            return columns.toString();
        }

        private String assembleMeasureColumns(Stream<Measure> measureStream) throws SQLException {
            String[] labels = dataProvider.readColumnLabels();
            StringJoiner columns = new StringJoiner(", ", "", " ").setEmptyValue("");
            measureStream.map(m -> AGGREGATION_PARSER.apply(m, labels))
                    .filter(Objects::nonNull)
                    .forEach(columns::add);
            return columns.toString();
        }

    }


}
