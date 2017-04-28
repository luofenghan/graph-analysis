package com.analysis.graph.datasource;

/**
 * Created by cwc on 2017/4/27 0027.
 */
public enum DataSourceType {
    JDBC(DB.MYSQL, DB.ORACLE),
    SAIKU;
    private DB[] dbs;

    DataSourceType(DB... dbs) {
        this.dbs = dbs;
    }


    public static DataSourceType.DB verifyDB(String type, String db) {
        for (DataSourceType.DB supportedDB : fromType(type).dbs) {
            if (supportedDB.name().equalsIgnoreCase(db)) {
                return supportedDB;
            }
        }
        throw new IllegalArgumentException(String.format("%s doesn't support %s", type, db));
    }

    public static DataSourceType fromType(String type) {
        return valueOf(type.toUpperCase());
    }

    public enum DB {
        MYSQL,
        ORACLE;
    }
}
