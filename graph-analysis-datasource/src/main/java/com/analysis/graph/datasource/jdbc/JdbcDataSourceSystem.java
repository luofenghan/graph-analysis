package com.analysis.graph.datasource.jdbc;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.analysis.graph.datasource.*;
import com.analysis.graph.datasource.aggregation.*;
import com.analysis.graph.datasource.aggregation.AggregationResult.ColumnIndex;
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
@DataSource(type = DsType.JDBC)
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
    public Aggregator getDataAggregator(DataProvider dataProvider) {
        return new JdbcDataAggregator(dataProvider);
    }


    private Connection getConnection() {
        try {
            Configuration conf = getDsStatus().getConf();
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
        //TODO 考虑将ResultSet缓存，用于读取重复数据
        //private WeakHashMap<String, ResultSet> resultSetMap = new WeakHashMap<>();

        JdbcDataProvider(Map<String, String> query) throws SQLException {
            super(query);
            connect();
        }

        private void connect() throws SQLException {
            this.connection = getConnection();
            this.stat = connection.createStatement();
            //this.result = stat.executeQuery(getQuery("sql"));
            //resultSetMap.put(getQuery("sql"), result);
        }

        @Override
        public void resetResultSetIfExisted(String sql) throws SQLException {
            if (result != null && !result.isClosed()) {
                batchClose(result);
                result = null;
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
        public String[] getColumnLabels() throws SQLException {
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

    private static class JdbcDataAggregator extends AbstractAggregator {
        JdbcDataAggregator(DataProvider dataProvider) {
            super(dataProvider);
        }

        @Override
        public AggregationResult doAggregation(DimensionView av) throws SQLException {
            String sql = getAggregationSql(av);
            dataProvider.resetResultSetIfExisted(sql);

            List<ColumnIndex> columnIndices = getColumnIndexList(av);
            if (columnIndices.isEmpty()) {
                String[] labels = dataProvider.getColumnLabels();
                IntStream.range(0, labels.length).forEach(i -> columnIndices.add(new ColumnIndex(i, labels[i])));
            }

            return new AggregationResult(columnIndices, dataProvider.readFully());
        }

        private List<ColumnIndex> getColumnIndexList(DimensionView av) {
            List<ColumnIndex> columnIndexList = Stream.concat(av.columnStream(), av.rowStream())
                    .map(ColumnIndex::fromDimension)
                    .collect(Collectors.toList());
            columnIndexList.addAll(av.metricStream().map(ColumnIndex::fromMeasure).collect(Collectors.toList()));
            IntStream.range(0, columnIndexList.size()).forEach(j -> columnIndexList.get(j).setIndex(j));
            return columnIndexList;
        }


        @Override
        public String getAggregationSql(DimensionView av) throws SQLException {
            String columnNames = getDimensionColumns(av.rowStream(), av.columnStream());

            String groupBy = "";
            StringJoiner selectColsStr = new StringJoiner(",").setEmptyValue("*");
            if (!StringUtils.isBlank(columnNames)) {
                groupBy = "GROUP BY " + columnNames;
                selectColsStr.add(columnNames);
            }
            String metricColumns = getMetricColumns(av.metricStream());
            if (!StringUtils.isBlank(metricColumns)) {
                selectColsStr.add(metricColumns);
            }

            String subQuerySql = ((JdbcDataProvider) dataProvider).getQuery("sql");
            String sqlViewTemplate = "\nSELECT %s \n FROM (\n%s\n) __view__ \n %s \n %s";

            String whereCondition = getWhereCondition(av.rowStream(), av.columnStream(), av.filterStream());
            return String.format(sqlViewTemplate, selectColsStr, subQuerySql, whereCondition, groupBy);

        }

        private String getWhereCondition(Stream<Field> rowStream, Stream<Field> columnStream, Stream<Field> filterStream) {
            StringJoiner where = new StringJoiner("\nAND ", "WHERE" + " ", "").setEmptyValue("");
            Stream.concat(Stream.concat(rowStream, columnStream), filterStream)
                    .map(FILTER_PARSER)
                    .filter(Objects::nonNull)
                    .forEach(where::add);
            return where.toString();
        }

        private String getDimensionColumns(Stream<Field> rowStream, Stream<Field> columnStream) {
            StringJoiner columns = new StringJoiner(", ", "", " ").setEmptyValue("");
            Stream.concat(rowStream, columnStream)
                    .map(Field::getName)
                    .distinct()
                    .filter(Objects::nonNull)
                    .forEach(columns::add);
            return columns.toString();
        }

        private String getMetricColumns(Stream<Metric> metricStream) throws SQLException {
            String[] labels = dataProvider.getColumnLabels();
            StringJoiner columns = new StringJoiner(", ", "", " ").setEmptyValue("");
            metricStream.map(m -> METRIC_PARSER.apply(m, labels))
                    .filter(Objects::nonNull)
                    .forEach(columns::add);
            return columns.toString();
        }

    }


}
