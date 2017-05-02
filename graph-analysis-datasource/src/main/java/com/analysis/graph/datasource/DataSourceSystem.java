package com.analysis.graph.datasource;

import com.analysis.graph.common.util.ReflectUtils;
import org.reflections.Reflections;

import java.net.URI;
import java.util.*;

/**
 * Created by cwc on 2017/4/27 0027.
 */
public abstract class DataSourceSystem {
    private static final Map<DataSourceType, Class<? extends DataSourceSystem>> SOURCE_TYPE_SYSTEM_MAP = new HashMap<>();
    private DataSourceStatus dataSourceStatus;
    private static final Cache CACHE = new Cache();
    private Cache.Key key;
    private URI uri;

    static {
        Reflections reflections = new Reflections("com.analysis.graph.datasource");
        for (Class dataSourceSystemClass : reflections.getTypesAnnotatedWith(DataSource.class)) {
            if (DataSourceSystem.class.isAssignableFrom(dataSourceSystemClass)) {
                //noinspection unchecked
                SOURCE_TYPE_SYSTEM_MAP.put(((DataSource) dataSourceSystemClass.getAnnotation(DataSource.class)).type(), dataSourceSystemClass);
            }
        }
    }

    public static DataSourceSystem get(Integer clientId, final URI uri) {
        return CACHE.get(uri, clientId);
    }


    public void initialize(URI uri) {
        this.uri = uri;
        this.dataSourceStatus = new DataSourceStatus(uri);
    }

    private static DataSourceSystem createDataSourceSystem(URI uri) throws Exception {
        DataSourceType type = DataSourceType.valueOf(uri.getScheme().toUpperCase());
        DataSourceSystem provider = ReflectUtils.on(SOURCE_TYPE_SYSTEM_MAP.get(type)).create().get();
        provider.initialize(uri);
        return provider;
    }

    protected void clear() {
        CACHE.remove(this.key, this);
    }

    public DataSourceStatus getDataSourceStatus() {
        return dataSourceStatus;
    }

    public String getKey() {
        return key.toString();
    }

    public abstract boolean isAggregatable();

    public abstract DataProvider getDataProvider(Map<String, String> query) throws Exception;

    public abstract DataAggregator getDataAggregator(DataProvider dataProvider);

    public URI getUri() {
        return uri;
    }

    static class Cache {
        private final Map<Key, DataSourceSystem> map = new HashMap<>();

        DataSourceSystem get(URI uri, Integer uniqueId) {
            Key key = new Key(uri, uniqueId);
            DataSourceSystem dp;
            synchronized (this) {
                dp = map.get(key);
            }
            if (dp != null) {
                return dp;
            }
            try {
                dp = createDataSourceSystem(uri);
            } catch (Exception e) {
                throw new IllegalArgumentException("Create DataSourceSystem Failed, due to : " + e.getMessage(), e);
            }
            synchronized (this) {
                DataSourceSystem oldDp = map.get(key);
                if (oldDp != null) {
                    oldDp.clear();
                    return oldDp;
                }
                dp.key = key;
                map.put(key, dp);
                return dp;
            }
        }

        synchronized void remove(Key key, DataSourceSystem dataProvider) {
            if (map.containsKey(key) && dataProvider == map.get(key)) {
                map.remove(key);
            }
        }

        synchronized void clearAll() {
            for (Map.Entry<Key, DataSourceSystem> entry : map.entrySet()) {
                final Key key = entry.getKey();
                final DataSourceSystem dp = entry.getValue();
                remove(key, dp);
                if (dp != null) {
                    dp.clear();
                }
            }
        }

        synchronized void clearAll(Integer uniqueId) {
            List<DataSourceSystem> targetProviders = new ArrayList<>();
            for (Map.Entry<Key, DataSourceSystem> entry : map.entrySet()) {
                final Key key = entry.getKey();
                final DataSourceSystem dp = entry.getValue();
                if (Objects.equals(key.uniqueId, uniqueId) && dp != null) {
                    targetProviders.add(dp);
                }
            }
            for (DataSourceSystem dp : targetProviders) {
                dp.clear();
            }

        }


        static class Key {
            //作为DataSourceSystem的唯一标识
            final String uri;// "jdbc://root:123@mysql:3306/chinaregion?pooled=true&aggregatable=false"
            final Integer uniqueId;// 唯一ID，通常为clientId

            Key(URI uri, Integer uniqueId) {
                this.uri = uri.toString();
                this.uniqueId = uniqueId;
            }

            @Override
            public int hashCode() {
                return uri.hashCode();
            }

            @Override
            public boolean equals(Object obj) {
                if (obj == this) {
                    return true;
                }
                if (obj != null && obj instanceof Key) {
                    Key that = (Key) obj;
                    return this.uri.equals(that.uri);
                }
                return false;
            }

            @Override
            public String toString() {
                return this.uri;
            }
        }
    }
}
