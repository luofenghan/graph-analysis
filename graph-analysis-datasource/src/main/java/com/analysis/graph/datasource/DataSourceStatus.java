package com.analysis.graph.datasource;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cwc on 2017/4/27 0027.
 */
public class DataSourceStatus {

    //private URI uri; // 例如："jdbc://root:123@127.0.0.1:3306/chinaregion?db=mysql&pooled=true&aggregatable=false"
    private Configuration conf;
    private boolean aggregatable;
    private DataSourceType type;
    private String owner;
    private String group;
    private long createTime;
    private long accessTime;

    public DataSourceStatus(URI uri) {
        this(uri, null, null);
    }

    public DataSourceStatus(URI uri, String owner, String group) {
        this.type = DataSourceType.fromType(uri.getScheme());
        this.conf = new Configuration();
        initParams(uri);
        this.aggregatable = conf.getBoolean("data.aggregate.enable", false);
        this.accessTime = this.createTime = new Date().getTime();
        this.owner = owner;
        this.group = group;
    }

    public Configuration getConf() {
        return conf;
    }

    private void initParams(URI uri) {
        Map<String, String> params = parseQuery(uri.getQuery());
        // 初始化jdbc类型数据库的信息
        if ("jdbc".equals(uri.getScheme())) {
            if (!params.containsKey("db")) {
                throw new IllegalArgumentException("type of jdbc uri must contains db");
            }
            this.conf.put("jdbc.pooled.enable", params.remove("pooled"));
            this.conf.put("data.aggregate.enable", params.remove("aggregatable"));

            DataSourceType.DB db = DataSourceType.verifyDB(uri.getScheme(), params.remove("db"));
            this.conf.put("jdbc.driver", "com." + db.name().toLowerCase() + ".jdbc.Driver");
            switch (db) {
                case MYSQL:
                    this.conf.put("jdbc.url", String.format("jdbc:mysql://%s:%d%s", uri.getHost(), uri.getPort(), uri.getPath()));
                    break;
                case ORACLE:
                    this.conf.put("jdbc.url", String.format("jdbc:oracle:thin:@//%s:%d%s", uri.getHost(), uri.getPort(), uri.getPath()));
                    break;
            }
            String userInfo = uri.getUserInfo();
            if (userInfo.contains(":")) {
                String[] authority = userInfo.split(":");
                this.conf.put("jdbc.username", authority[0]);
                this.conf.put("jdbc.password", authority[1]);
            }
        }
    }

    private Map<String, String> parseQuery(String query) {
        Map<String, String> params = new HashMap<>();
        for (String param : query.split("&")) {
            String[] keyValue = param.split("=");
            params.put(keyValue[0], keyValue[1]);
        }
        return params;
    }

    public boolean isAggregatable() {
        return aggregatable;
    }

    public DataSourceType getType() {
        return type;
    }

    public long getAccessTime() {
        return accessTime;
    }

    public long getCreateTime() {
        return createTime;
    }


    public String getOwner() {
        return owner;
    }

    public String getGroup() {
        return group;
    }


    public void setGroup(String group) {
        this.group = group;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setAccessTime(long accessTime) {
        this.accessTime = accessTime;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataSourceStatus that = (DataSourceStatus) o;

        if (aggregatable != that.aggregatable) return false;
        if (createTime != that.createTime) return false;
        if (accessTime != that.accessTime) return false;
        if (conf != null ? !conf.equals(that.conf) : that.conf != null) return false;
        if (type != that.type) return false;
        if (owner != null ? !owner.equals(that.owner) : that.owner != null) return false;
        return group != null ? group.equals(that.group) : that.group == null;
    }

    @Override
    public int hashCode() {
        int result = conf != null ? conf.hashCode() : 0;
        result = 31 * result + (aggregatable ? 1 : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (group != null ? group.hashCode() : 0);
        result = 31 * result + (int) (createTime ^ (createTime >>> 32));
        result = 31 * result + (int) (accessTime ^ (accessTime >>> 32));
        return result;
    }
}
