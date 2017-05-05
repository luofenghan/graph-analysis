package com.analysis.graph.common.domain.dbo;

import java.util.Date;

public class Graph {
    private Long id;

    private Integer clientId;

    private String category;

    private String name;

    private Integer datasourceId;

    private String query;

    private Long datasetId;

    private String graphType;

    private String fillField;

    private String row;

    private String column;

    private String filter;

    private String aggregation;

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

    public Integer getDatasourceId() {
        return datasourceId;
    }

    public void setDatasourceId(Integer datasourceId) {
        this.datasourceId = datasourceId;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query == null ? null : query.trim();
    }

    public Long getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(Long datasetId) {
        this.datasetId = datasetId;
    }

    public String getGraphType() {
        return graphType;
    }

    public void setGraphType(String graphType) {
        this.graphType = graphType == null ? null : graphType.trim();
    }

    public String getFillField() {
        return fillField;
    }

    public void setFillField(String fillField) {
        this.fillField = fillField == null ? null : fillField.trim();
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row == null ? null : row.trim();
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column == null ? null : column.trim();
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter == null ? null : filter.trim();
    }

    public String getAggregation() {
        return aggregation;
    }

    public void setAggregation(String aggregation) {
        this.aggregation = aggregation == null ? null : aggregation.trim();
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
        sb.append(", category=").append(category);
        sb.append(", name=").append(name);
        sb.append(", datasourceId=").append(datasourceId);
        sb.append(", query=").append(query);
        sb.append(", datasetId=").append(datasetId);
        sb.append(", graphType=").append(graphType);
        sb.append(", fillField=").append(fillField);
        sb.append(", row=").append(row);
        sb.append(", column=").append(column);
        sb.append(", filter=").append(filter);
        sb.append(", aggregation=").append(aggregation);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", updatedTime=").append(updatedTime);
        sb.append("]");
        return sb.toString();
    }
}