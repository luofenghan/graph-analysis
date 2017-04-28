package com.analysis.graph.datasource.aggregation;

import java.util.List;

/**
 * Created by cwc on 2017/4/22 0022.
 */
public class AggregationResult {
    private List<ColumnIndex> columnList;
    private String[][] data;

    public AggregationResult() {
    }

    public AggregationResult(List<ColumnIndex> columnList, String[][] data) {
        this.columnList = columnList;
        this.data = data;
    }

    public List<ColumnIndex> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<ColumnIndex> columnList) {
        this.columnList = columnList;
    }

    public String[][] getData() {
        return data;
    }

    public void setData(String[][] data) {
        this.data = data;
    }
}
