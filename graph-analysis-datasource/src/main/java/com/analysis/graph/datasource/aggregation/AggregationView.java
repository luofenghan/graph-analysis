package com.analysis.graph.datasource.aggregation;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by cwc on 2017/4/27 0027.
 */
public class AggregationView {
    private List<Dimension> rows;
    private List<Dimension> columns;
    private List<Dimension> filters;
    private List<Measure> measures;

    public AggregationView() {
        this.rows = Collections.emptyList();
        this.columns = Collections.emptyList();
        this.filters = Collections.emptyList();
        this.measures = Collections.emptyList();
    }

    public Stream<Dimension> rowStream() {
        return rows.stream();
    }

    public Stream<Dimension> columnStream() {
        return columns.stream();
    }

    public Stream<Dimension> filterStream() {
        return filters.stream();
    }

    public Stream<Measure> getMeasureStream() {
        return measures.stream();
    }


    public List<Dimension> getRows() {
        return rows;
    }

    public void setRows(List<Dimension> rows) {
        this.rows = rows;
    }

    public List<Dimension> getColumns() {
        return columns;
    }

    public void setColumns(List<Dimension> columns) {
        this.columns = columns;
    }

    public List<Dimension> getFilters() {
        return filters;
    }

    public void setFilters(List<Dimension> filters) {
        this.filters = filters;
    }

    public List<Measure> getMeasures() {
        return measures;
    }

    public void setMeasures(List<Measure> measures) {
        this.measures = measures;
    }
}
