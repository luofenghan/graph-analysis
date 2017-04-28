package com.analysis.graph.common.component.cache;

import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by cwc on 2017/4/19 0019.
 */
@Component
@Profile("prod")
public class RedisCacheProvider implements CacheProvider {
    @Resource(name = "redisTemplate")
    private ValueOperations<String, Object> objectValueOperations;

    @Override
    public void save(String cacheName, String key, Object value) {
        objectValueOperations.set(key, value);
    }

    @Override
    public void saveWithExpire(String cacheName, String key, Object value, long expire) {
        objectValueOperations.set(key, value, expire);
    }

    @Override
    public boolean remove(String cacheName, String key) {
        objectValueOperations.getOperations().delete(key);
        return true;
    }

    @Override
    public Object get(String cacheName, String key) {
        return objectValueOperations.get(key);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(String cacheName, String key, Class<T> valueTypeClass) {
        return (T) get(cacheName, key);
    }

    @Override
    public boolean existed(String cacheName, String key) {
        return objectValueOperations.getOperations().hasKey(key);
    }
}
