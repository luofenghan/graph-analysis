package com.analysis.graph.web.library.exception;

/**
 * Created by cwc on 2017/4/19 0019.
 */
public class DataSourceException extends RuntimeException {
    public DataSourceException(String message) {
        super(message);
    }

    public DataSourceException(String message, Throwable cause) {
        super(message, cause);
    }
}
