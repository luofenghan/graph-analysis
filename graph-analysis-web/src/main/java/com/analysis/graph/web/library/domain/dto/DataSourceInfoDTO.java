package com.analysis.graph.web.library.domain.dto;

import com.alibaba.fastjson.JSONObject;
import com.analysis.graph.common.domain.dbo.DataSourceInfo;

import java.util.Date;
import java.util.Map;

/**
 * Created by cwc on 2017/4/22 0022.
 */
public class DataSourceInfoDTO {
    private Integer id;

    private Integer clientId;

    private String type;

    private String name;

    private Map<String, Object> config;

    private Date createdTime;

    private Date updatedTime;

    public DataSourceInfoDTO() {
    }

    public DataSourceInfoDTO(DataSourceInfo dataSourceInfo) {
        this.id = dataSourceInfo.getId();
        this.clientId = dataSourceInfo.getClientId();
        this.type = dataSourceInfo.getType();
        this.name = dataSourceInfo.getName();
        this.config = JSONObject.parseObject(dataSourceInfo.getConfig());
        this.createdTime = dataSourceInfo.getCreatedTime();
        this.updatedTime = dataSourceInfo.getUpdatedTime();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> getConfig() {
        return config;
    }

    public void setConfig(Map<String, Object> config) {
        this.config = config;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }
}
