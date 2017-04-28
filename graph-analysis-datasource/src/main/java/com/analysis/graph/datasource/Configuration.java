package com.analysis.graph.datasource;

import java.util.Properties;

/**
 * Created by cwc on 2017/4/28 0028.
 */
public class Configuration {
    private Properties properties;

    public Configuration(Properties properties) {
        this.properties = properties;
    }

    public Configuration() {
        this(new Properties());
    }

    public boolean containsKey(String key) {
        return properties.containsKey(key);
    }

    public void put(String key, Object value) {
        properties.put(key, value);
    }

    public String getString(String key, String defaultValue) {
        return String.valueOf(properties.getOrDefault(key, defaultValue));
    }

    public Integer getInteger(String key, Integer defaultValue) {
        return Integer.parseInt(String.valueOf(properties.getOrDefault(key, defaultValue)));
    }

    public Boolean getBoolean(String key, Boolean defaultValue) {
        return Boolean.valueOf(String.valueOf(properties.getOrDefault(key, defaultValue)));
    }
}
