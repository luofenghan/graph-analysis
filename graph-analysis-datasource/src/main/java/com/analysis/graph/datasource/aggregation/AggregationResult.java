package com.analysis.graph.datasource.aggregation;

import java.util.List;

/**
 * Created by cwc on 2017/4/22 0022.
 */
public class AggregationResult {
    private List<ColumnIndex> columns;
    private Object[][] data;

    public AggregationResult() {
    }

    public AggregationResult(List<ColumnIndex> columnList, Object[][] data) {
        this.columns = columnList;
        this.data = data;
    }

    public List<ColumnIndex> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnIndex> columns) {
        this.columns = columns;
    }

    public Object[][] getData() {
        return data;
    }

    public void setData(Object[][] data) {
        this.data = data;
    }
}
