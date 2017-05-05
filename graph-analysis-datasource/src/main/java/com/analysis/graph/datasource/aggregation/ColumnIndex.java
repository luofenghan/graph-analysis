package com.analysis.graph.datasource.aggregation;


/**
 * Created by cwc on 2017/4/22 0022.
 */
public class ColumnIndex {
    private int index;
    private String name;
    private Aggregation.Function function;

    public static ColumnIndex fromValueConfig(Aggregation aggregation) {
        ColumnIndex columnIndex = new ColumnIndex();
        columnIndex.setName(aggregation.getColumn());
        columnIndex.setFunction(aggregation.getFunction());
        return columnIndex;
    }

    public static ColumnIndex fromDimensionConfig(Dimension dimension) {
        ColumnIndex columnIndex = new ColumnIndex();
        columnIndex.setName(dimension.getName());
        return columnIndex;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Aggregation.Function getFunction() {
        return function;
    }

    public void setFunction(Aggregation.Function function) {
        this.function = function;
    }
}
