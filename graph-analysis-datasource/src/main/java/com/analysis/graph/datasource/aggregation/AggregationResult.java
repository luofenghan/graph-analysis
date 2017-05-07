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

    public static class ColumnIndex {
        private int index;
        private String name;
        private Metric.Function function;

        public ColumnIndex(int index, String name) {
            this.index = index;
            this.name = name;
        }

        public ColumnIndex() {
        }

        public static ColumnIndex fromMeasure(Metric aggregation) {
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

        public Metric.Function getFunction() {
            return function;
        }

        public void setFunction(Metric.Function function) {
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
}
