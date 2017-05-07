package com.analysis.graph.common.domain.dbo;

import java.util.Date;

public class Dashboard {
    private Long id;

    private Integer clientId;

    private String category;

    private String name;

    private Date createdTime;

    private Date updatedTime;

    private String layoutConfig;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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

    public String getLayoutConfig() {
        return layoutConfig;
    }

    public void setLayoutConfig(String layoutConfig) {
        this.layoutConfig = layoutConfig == null ? null : layoutConfig.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", clientId=").append(clientId);
        sb.append(", category=").append(category);
        sb.append(", name=").append(name);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", updatedTime=").append(updatedTime);
        sb.append(", layoutConfig=").append(layoutConfig);
        sb.append("]");
        return sb.toString();
    }
}