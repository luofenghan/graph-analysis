package com.analysis.graph.datasource;

/**
 * Created by cwc on 2017/4/28 0028.
 */
public abstract class AbstractDataProvider implements DataProvider {
    public static void batchClose(AutoCloseable... closeables) {
        for (AutoCloseable c : closeables) {
            if (c != null) {
                try {
                    c.close();
                } catch (Exception e) {
                }
            }
        }
    }
}
