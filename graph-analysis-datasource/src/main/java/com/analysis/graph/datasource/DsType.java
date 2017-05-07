package com.analysis.graph.datasource;

/**
 * Created by cwc on 2017/4/27 0027.
 */
public enum DsType {
    JDBC(DB.MYSQL, DB.ORACLE, DB.H2),
    SAIKU;
    private DB[] dbs;

    DsType(DB... dbs) {
        this.dbs = dbs;
    }


    public static DsType.DB verifyDB(String type, String db) {
        for (DsType.DB supportedDB : getDsType(type).dbs) {
            if (supportedDB.name().equalsIgnoreCase(db)) {
                return supportedDB;
            }
        }
        throw new IllegalArgumentException(String.format("%s doesn't support %s", type, db));
    }

    public static DsType getDsType(String type) {
        return valueOf(type.toUpperCase());
    }

    public enum DB {
        MYSQL,
        ORACLE,
        H2;
    }
}
