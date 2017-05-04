package com.analysis.graph.datasource;

import java.util.Map;

/**
 * Created by cwc on 2017/4/28 0028.
 */
public abstract class AbstractDataProvider implements DataProvider {
    private Map<String, String> query;

    public AbstractDataProvider(Map<String, String> query) {
        this.query = query;
    }

    public AbstractDataProvider() {
    }

    private String getAsSubQuery(String rawQueryText) {
        String deletedBlankLine = rawQueryText.replaceAll("(?m)^[\\s\t]*\r?\n", "").trim();
        return deletedBlankLine.endsWith(";") ? deletedBlankLine.substring(0, deletedBlankLine.length() - 1) : deletedBlankLine;
    }

    public void setQuery(Map<String, String> query) {
        this.query = query;
    }

    public String getQuery(String key) {
        if (query != null && query.containsKey(key)) {
            return getAsSubQuery(query.get(key));
        }
        return null;
    }


}
