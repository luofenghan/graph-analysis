package com.analysis.graph.datasource.aggregation;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by cwc on 2017/4/27 0027.
 */
public class DimensionView {
    private List<Field> rows;
    private List<Field> columns;
    private List<Field> filters;
    private List<Metric> metrics;

    public DimensionView() {
        this.rows = Collections.emptyList();
        this.columns = Collections.emptyList();
        this.filters = Collections.emptyList();
        this.metrics = Collections.emptyList();
    }

    public Stream<Field> rowStream() {
        return rows.stream();
    }

    public Stream<Field> columnStream() {
        return columns.stream();
    }

    public Stream<Field> filterStream() {
        return filters.stream();
    }

    public Stream<Metric> metricStream() {
        return metrics.stream();
    }


    public List<Field> getRows() {
        return rows;
    }

    public void setRows(List<Field> rows) {
        if (rows != null)
            this.rows = rows;
    }

    public List<Field> getColumns() {
        return columns;
    }

    public void setColumns(List<Field> columns) {
        if (columns != null)
            this.columns = columns;
    }

    public List<Field> getFilters() {
        return filters;
    }

    public void setFilters(List<Field> filters) {
        if (filters != null) {
            this.filters = filters;
        }
    }

    public List<Metric> getMetrics() {
        return metrics;
    }

    public void setMetrics(List<Metric> metrics) {
        if (metrics != null)
            this.metrics = metrics;
    }
}
