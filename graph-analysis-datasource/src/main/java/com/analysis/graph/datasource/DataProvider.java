package com.analysis.graph.datasource;

import java.io.Closeable;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by cwc on 2017/4/27 0027.
 */
public interface DataProvider extends Closeable {

    /**
     * 从数据库汇中读取一行记录，如果没有记录，则返回空
     *
     * @return
     */
    Object[] readLine() throws SQLException;

    /**
     * @return 返回读取数据库列名
     */
    String[] readColumnLabels() throws SQLException;

    Map<String, Integer> readColumnLabelTypeMap() throws SQLException;

    /**
     * @return 所有数据
     */
    Object[][] readFully() throws SQLException;

}
