package com.analysis.graph.web.library.constant;

/**
 * Created by cwc on 2017/4/14 0014.
 */
public enum ProfileEnum {
    DEFAULT,
    DEV,
    PROD;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
