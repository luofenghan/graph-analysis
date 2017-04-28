package com.analysis.graph.common.component.cache;

/**
 * Created by cwc on 2017/4/19 0019.
 */
public interface CacheProvider {

    void save(String cacheName, String key, Object value);


    void saveWithExpire(String cacheName, String key, Object value, long expire);


    boolean remove(String cacheName, String key);


    Object get(String cacheName, String key);


    <T> T get(String cacheName, String key, Class<T> valueTypeClass);

    boolean existed(String cacheName, String key);
}
