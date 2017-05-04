package com.analysis.graph.datasource.jdbc;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.analysis.graph.datasource.*;
import com.analysis.graph.datasource.aggregation.*;
import com.analysis.graph.datasource.aggregation.AggregationView.DimensionView;
import com.analysis.graph.datasource.aggregation.AggregationView.ValueView;
import com.analysis.graph.datasource.AbstractDataProvider;
import com.analysis.graph.datasource.DataProvider;
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
        private boolean lazyConnect = false;

        JdbcDataProvider(Map<String, String> query) throws SQLException {
            this(query, false);
        }

        JdbcDataProvider(Map<String, String> query, boolean lazyConnect) throws SQLException {
            super(query);
            this.lazyConnect = lazyConnect;
            if (!lazyConnect) {
                connect();
            }
        }

        private void connect() throws SQLException {
            this.connection = getConnection();
            this.stat = connection.createStatement();
            this.result = stat.executeQuery(getQuery("sql"));
        }

        @Override
        public void resetResultSet(String sql) throws SQLException {
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
            List<ColumnIndex> dimensionList = Stream.concat(av.columnStream(), av.rowStream())
                    .map(ColumnIndex::fromDimensionConfig)
                    .collect(Collectors.toList());
            dimensionList.addAll(av.valueStream().map(ColumnIndex::fromValueConfig).collect(Collectors.toList()));
            IntStream.range(0, dimensionList.size()).forEach(j -> dimensionList.get(j).setIndex(j));
            String sql = getAggregationSql(av);
            dataProvider.resetResultSet(sql);
            aggregationResult = new AggregationResult(dimensionList, dataProvider.readFully());
        }

        @Override
        public AggregationResult getAggregateResult() {
            return aggregationResult;
        }


        public String getAggregationSql(AggregationView av) throws SQLException {
            String columnNames = assembleDimensionColumns(Stream.concat(av.columnStream(), av.rowStream()));
            String aggregationColumnNames = assembleAggregationColumns(av.valueStream());
            String whereCondition = assembleWhereCondition(Stream.concat(Stream.concat(av.rowStream(), av.columnStream()), av.filterStream()));

            String groupBy = "";
            StringJoiner selectColsStr = new StringJoiner(",");
            if (!StringUtils.isBlank(columnNames)) {
                groupBy = "GROUP BY " + columnNames;
                selectColsStr.add(columnNames);
            }

            if (!StringUtils.isBlank(aggregationColumnNames)) {
                selectColsStr.add(aggregationColumnNames);
            }
            String subQuerySql = ((JdbcDataProvider) dataProvider).getQuery("sql");
            String sqlTemplate = "\nSELECT %s \n FROM (\n%s\n) __view__ \n %s \n %s";
            return String.format(sqlTemplate, selectColsStr.length() == 0 ? "*" : selectColsStr, subQuerySql, whereCondition, groupBy);

        }

        private String assembleWhereCondition(Stream<DimensionView> valueViewStream) {
            StringJoiner where = new StringJoiner("\nAND ", "WHERE" + " ", "").setEmptyValue("");
            valueViewStream.map(CONDITION_PARSER)
                    .filter(Objects::nonNull)
                    .forEach(where::add);
            return where.toString();
        }

        private String assembleDimensionColumns(Stream<DimensionView> dimensionViewStream) {
            StringJoiner columns = new StringJoiner(", ", "", " ").setEmptyValue("");
            dimensionViewStream.map(DimensionView::getName)
                    .distinct()
                    .filter(Objects::nonNull)
                    .forEach(columns::add);
            return columns.toString();
        }

        private String assembleAggregationColumns(Stream<ValueView> valueViewStream) throws SQLException {
            Map<String, Integer> labelTypeMap = dataProvider.readColumnLabelTypeMap();
            StringJoiner columns = new StringJoiner(", ", "", " ").setEmptyValue("");
            valueViewStream.map(m -> AGGREGATION_PARSER.apply(m, labelTypeMap))
                    .filter(Objects::nonNull)
                    .forEach(columns::add);
            return columns.toString();
        }

    }


}
