package com.analysis.graph.datasource.aggregation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by cwc on 2017/5/4 0004.
 */
public class Dimension {
    private String name;
    private List<String> values;
    private FilterType filterType;
    private SortType sortType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public FilterType getFilterType() {
        return filterType;
    }

    public void setFilterType(FilterType filterType) {
        this.filterType = filterType;
    }

    public void setFilterType(String filter) {
        this.filterType = FilterType.valueOfSymbol(filter);
    }

    public SortType getSortType() {
        return sortType;
    }

    public void setSortType(SortType sortType) {
        this.sortType = sortType;
    }

    public void setSort(String sort) {
        if (!Objects.isNull(sort)) {
            this.sortType = SortType.valueOf(sort.toUpperCase());
        } else {
            this.sortType = SortType.DEFAULT;
        }
    }

    public enum SortType {
        DEFAULT, ASC, DESC;
    }

    public enum FilterType {
        EQUAL("=", "%s IN ( %s )"),
        NOT_EQUAL("≠", "%s NOT IN ( '%s' )"),
        GREATER_THAN(">", "%s > '%s'"),
        LESS_THAN("<", "%s < '%s'"),
        NOT_LESS_THAN("≥", "%s >= '%s'"),
        NOT_MORE_THAN("≤", "%s <= '%s'"),
        RANGE_A_B("(a,b)", "( %s > '%s' AND %s < '%s'"),
        RANGE_A_$B("(a,b]", "( %s > '%s' AND %s <= '%s'"),
        RANGE_$A_B("[a,b)", "( %s >= '%s' AND %s < '%s'"),
        RANGE_$A_$B("[a,b]", "( %s >= '%s' AND %s <= '%s'");
        private String symbol;
        private String expFormat;

        FilterType(String symbol, String expFormat) {
            this.symbol = symbol;
            this.expFormat = expFormat;
        }

        private static final Map<String, FilterType> MAP = new HashMap<>(values().length);

        static {
            for (FilterType filter : values()) {
                MAP.put(filter.symbol, filter);
            }
        }

        public String getSymbol() {
            return symbol;
        }

        public static FilterType valueOfSymbol(String type) {
            return MAP.get(type.toLowerCase());
        }

        public String getFilterExp(String first, String second) {
            return String.format(expFormat, first, second);
        }

        public String getFilterExp(String first, String second, String thirty, String fourth) {
            return String.format(expFormat, first, second, thirty, fourth);
        }
    }
}
