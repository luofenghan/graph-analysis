package com.analysis.graph.common.component.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Created by cwc on 2017/4/19 0019.
 */
@Component
@Profile("dev")
public class EhCacheProvider implements CacheProvider {
    @Autowired
    private CacheManager cacheManager;

    @Override
    public void save(String cacheName, String key, Object value) {

        getCache(cacheName).put(new Element(key, value));
    }

    @Override
    public void saveWithExpire(String cacheName, String key, Object value, long expire) {

        getCache(cacheName).put(new Element(key, value, (int) expire, (int) expire));

    }

    @Override
    public boolean remove(String cacheName, String key) {

        return getCache(cacheName).remove(key);
    }

    @Override
    public Object get(String cacheName, String key) {
        return get(cacheName, key, Object.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(String cacheName, String key, Class<T> valueTypeClass) {
        return (T) getCache(cacheName).get(key);
    }

    @Override
    public boolean existed(String cacheName, String key) {
        return getCache(cacheName).get(key) != null;
    }

    private Cache getCache(String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            throw new IllegalArgumentException();
        }
        return cache;
    }
}
