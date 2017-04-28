package com.analysis.graph.common.repository;

import com.analysis.graph.common.component.cache.CacheProvider;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by cwc on 2017/4/19 0019.
 */
@Repository
public class CacheRepository {
    @Autowired
    private CacheProvider cacheProvider;

    public void addOrUpdateCacheObject(String cacheName, String key, Object value) {
        cacheProvider.save(cacheName, key, value);
    }

    public void addOrUpdateCacheObject(String cacheName, String key, Object value, int timeToIdleSeconds, int
            timeToLiveSeconds) {

        cacheProvider.saveWithExpire(cacheName, key, value, timeToLiveSeconds);
    }

    public Object getCacheObject(String cacheName, String key) {
        return cacheProvider.get(cacheName, key);
    }

    public Element getCacheElement(String cacheName, String key) {

        return cacheProvider.get(cacheName, key, Element.class);
    }

    public boolean remove(String cacheName, String key) {

        return cacheProvider.remove(cacheName, key);
    }


    public boolean existed(String cacheName, String key) {
        return cacheProvider.existed(cacheName, key);
    }

}
