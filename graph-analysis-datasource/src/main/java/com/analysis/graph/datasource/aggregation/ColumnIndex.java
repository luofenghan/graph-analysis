package com.analysis.graph.datasource.aggregation;


/**
 * Created by cwc on 2017/4/22 0022.
 */
public class ColumnIndex {
    private int index;
    private String name;
    private Measure.Function function;

    public ColumnIndex(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public ColumnIndex() {
    }

    public static ColumnIndex fromMeasure(Measure aggregation) {
        ColumnIndex columnIndex = new ColumnIndex();
        columnIndex.setName(aggregation.getColumn());
        columnIndex.setFunction(aggregation.getFunction());
        return columnIndex;
    }

    public static ColumnIndex fromDimension(Dimension dimension) {
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

    public Measure.Function getFunction() {
        return function;
    }

    public void setFunction(Measure.Function function) {
        this.function = function;
    }

    @Override
    public String toString() {
        return "ColumnIndex{" +
                "index=" + index +
                ", name='" + name + '\'' +
                ", function=" + function +
                '}';
    }
}
