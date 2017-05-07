package com.analysis.graph.datasource.aggregation;

import java.util.List;
import java.util.Objects;

/**
 * Created by cwc on 2017/5/4 0004.
 */
public class Field {
    private String name;
    private String alias;
    private List<String> values;
    private Filter filter;
    private Sort sort;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public Sort getSort() {
        return sort;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public void setSort(String sort) {
        if (!Objects.isNull(sort)) {
            this.sort = Sort.valueOf(sort.toUpperCase());
        } else {
            this.sort = Sort.DEFAULT;
        }
    }

    public enum Sort {
        DEFAULT, ASC, DESC;
    }

}
