package com.analysis.graph.datasource.aggregation;


/**
 * Created by cwc on 2017/4/22 0022.
 */
public class ColumnIndex {
    private int index;
    private String name;
    private AggregationMethod method;

    public static ColumnIndex fromValueConfig(AggregationView.ValueView valueView) {
        ColumnIndex columnIndex = new ColumnIndex();
        columnIndex.setName(valueView.getColumn());
        columnIndex.setMethod(valueView.getMethod());
        return columnIndex;
    }

    public static ColumnIndex fromDimensionConfig(AggregationView.DimensionView dimensionView) {
        ColumnIndex columnIndex = new ColumnIndex();
        columnIndex.setName(dimensionView.getName());
        return columnIndex;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public AggregationMethod getMethod() {
        return method;
    }

    public void setMethod(AggregationMethod method) {
        this.method = method;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
