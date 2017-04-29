package com.analysis.graph.datasource.jdbc;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.analysis.graph.datasource.*;
import com.analysis.graph.datasource.aggregation.AggregationResult;
import com.analysis.graph.datasource.aggregation.AggregationView;
import org.apache.commons.collections.map.HashedMap;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return new JdbcDataProvider(getAsSubQuery(query.get("sql")));
    }

    @Override
    public DataAggregator getDataAggregator() {
        return new JdbcDataAggregator();
    }

    private String getAsSubQuery(String rawQueryText) {
        String deletedBlankLine = rawQueryText.replaceAll("(?m)^[\\s\t]*\r?\n", "").trim();
        return deletedBlankLine.endsWith(";") ? deletedBlankLine.substring(0, deletedBlankLine.length() - 1) : deletedBlankLine;
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
        private String sql;
        private Connection connection;
        private Statement stat;
        private ResultSet result;

        public JdbcDataProvider(String sql) throws SQLException {
            this.connection = getConnection();
            this.stat = connection.createStatement();
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
                 ResultSet rs = stat.executeQuery(String.format("\nSELECT * FROM (\n%s\n) __view__ WHERE 1=0", sql))) {
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
                 ResultSet rs = stat.executeQuery(String.format("\nSELECT * FROM (\n%s\n) __view__ WHERE 1=0", sql))) {
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

    private class JdbcDataAggregator extends AbstractDataAggregator {

        @Override
        public void aggregate(String columnName, AggregationView aggregationView) throws Exception {

        }

        @Override
        public void aggregate(AggregationView aggregationView) throws Exception {

        }

        @Override
        public AggregationResult getAggregateResult() {
            return null;
        }
    }

}
