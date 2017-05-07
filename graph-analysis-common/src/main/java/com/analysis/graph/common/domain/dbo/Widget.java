package com.analysis.graph.common.domain.dbo;

import java.util.Date;

public class Widget {
    private Long id;

    private Integer clientId;

    private Long datasetId;

    private String category;

    private String name;

    private String graphType;

    private String optionalField;

    private String rowField;

    private String columnField;

    private String filterField;

    private String metricField;

    private Date createdTime;

    private Date updatedTime;

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

    public Long getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(Long datasetId) {
        this.datasetId = datasetId;
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

    public String getGraphType() {
        return graphType;
    }

    public void setGraphType(String graphType) {
        this.graphType = graphType == null ? null : graphType.trim();
    }

    public String getOptionalField() {
        return optionalField;
    }

    public void setOptionalField(String optionalField) {
        this.optionalField = optionalField == null ? null : optionalField.trim();
    }

    public String getRowField() {
        return rowField;
    }

    public void setRowField(String rowField) {
        this.rowField = rowField == null ? null : rowField.trim();
    }

    public String getColumnField() {
        return columnField;
    }

    public void setColumnField(String columnField) {
        this.columnField = columnField == null ? null : columnField.trim();
    }

    public String getFilterField() {
        return filterField;
    }

    public void setFilterField(String filterField) {
        this.filterField = filterField == null ? null : filterField.trim();
    }

    public String getMetricField() {
        return metricField;
    }

    public void setMetricField(String metricField) {
        this.metricField = metricField == null ? null : metricField.trim();
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", clientId=").append(clientId);
        sb.append(", datasetId=").append(datasetId);
        sb.append(", category=").append(category);
        sb.append(", name=").append(name);
        sb.append(", graphType=").append(graphType);
        sb.append(", optionalField=").append(optionalField);
        sb.append(", rowField=").append(rowField);
        sb.append(", columnField=").append(columnField);
        sb.append(", filterField=").append(filterField);
        sb.append(", metricField=").append(metricField);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", updatedTime=").append(updatedTime);
        sb.append("]");
        return sb.toString();
    }
}