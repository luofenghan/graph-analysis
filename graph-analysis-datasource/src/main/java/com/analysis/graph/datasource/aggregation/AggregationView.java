package com.analysis.graph.datasource.aggregation;

import com.analysis.graph.datasource.aggregation.AggregationFilter.Type;

import java.util.List;

/**
 * Created by cwc on 2017/4/27 0027.
 */
public class AggregationView {
    private List<DimensionView> rows;
    private List<DimensionView> columns;
    private List<DimensionView> filters;
    private List<ValueView> values;

    public List<DimensionView> getRows() {
        return rows;
    }

    public void setRows(List<DimensionView> rows) {
        this.rows = rows;
    }

    public List<DimensionView> getColumns() {
        return columns;
    }

    public void setColumns(List<DimensionView> columns) {
        this.columns = columns;
    }

    public List<DimensionView> getFilters() {
        return filters;
    }

    public void setFilters(List<DimensionView> filters) {
        this.filters = filters;
    }

    public List<ValueView> getValues() {
        return values;
    }

    public void setValues(List<ValueView> values) {
        this.values = values;
    }

    public static class DimensionView {
        private String name;
        private Type filter;
        private List<String> values;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Type getFilter() {
            return filter;
        }

        public void setFilter(Type filter) {
            this.filter = filter;
        }

        public List<String> getValues() {
            return values;
        }

        public void setValues(List<String> values) {
            this.values = values;
        }
    }

    public static class ValueView {
        private String column;
        private AggregationMethod method;

        public String getColumn() {
            return column;
        }

        public void setColumn(String column) {
            this.column = column;
        }

        public AggregationMethod getMethod() {
            return method;
        }

        public void setMethod(AggregationMethod method) {
            this.method = method;
        }
    }
}
