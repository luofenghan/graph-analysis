package com.analysis.graph.web.library.domain.dto;

import java.util.Map;

/**
 * Created by cwc on 2017/4/23 0023.
 */
public class DataSetDTO {
    private Long id;

    private Integer clientId;

    private Integer dataSourceId;

    private String dataSetName;

    private String categoryName;

    private Map<String, String> query;

    private FilterGroup[] filterGroups;

    private Expression[] expressions;

    private Long interval;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(Integer dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    public String getDataSetName() {
        return dataSetName;
    }

    public void setDataSetName(String dataSetName) {
        this.dataSetName = dataSetName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Map<String, String> getQuery() {
        return query;
    }

    public void setQuery(Map<String, String> query) {
        this.query = query;
    }

    public FilterGroup[] getFilterGroups() {
        return filterGroups;
    }

    public void setFilterGroups(FilterGroup[] filterGroups) {
        this.filterGroups = filterGroups;
    }

    public Expression[] getExpressions() {
        return expressions;
    }

    public void setExpressions(Expression[] expressions) {
        this.expressions = expressions;
    }

    public Long getInterval() {
        return interval;
    }

    public void setInterval(Long interval) {
        this.interval = interval;
    }

    /**
     * FilterGroup
     */
    static class FilterGroup {
        private Filter[] filters;
        private String name;

        public Filter[] getFilters() {
            return filters;
        }

        public void setFilters(Filter[] filters) {
            this.filters = filters;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        static class Filter {
            private String type;
            private String[] values;
            private String colName;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String[] getValues() {
                return values;
            }

            public void setValues(String[] values) {
                this.values = values;
            }

            public String getColName() {
                return colName;
            }

            public void setColName(String colName) {
                this.colName = colName;
            }
        }
    }

    static class Expression {
        private String alias;
        private String type;
        private String exp;

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getExp() {
            return exp;
        }

        public void setExp(String exp) {
            this.exp = exp;
        }
    }
}
