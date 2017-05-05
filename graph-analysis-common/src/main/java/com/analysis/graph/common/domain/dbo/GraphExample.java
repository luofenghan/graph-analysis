package com.analysis.graph.common.domain.dbo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GraphExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public GraphExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andClientIdIsNull() {
            addCriterion("client_id is null");
            return (Criteria) this;
        }

        public Criteria andClientIdIsNotNull() {
            addCriterion("client_id is not null");
            return (Criteria) this;
        }

        public Criteria andClientIdEqualTo(Integer value) {
            addCriterion("client_id =", value, "clientId");
            return (Criteria) this;
        }

        public Criteria andClientIdNotEqualTo(Integer value) {
            addCriterion("client_id <>", value, "clientId");
            return (Criteria) this;
        }

        public Criteria andClientIdGreaterThan(Integer value) {
            addCriterion("client_id >", value, "clientId");
            return (Criteria) this;
        }

        public Criteria andClientIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("client_id >=", value, "clientId");
            return (Criteria) this;
        }

        public Criteria andClientIdLessThan(Integer value) {
            addCriterion("client_id <", value, "clientId");
            return (Criteria) this;
        }

        public Criteria andClientIdLessThanOrEqualTo(Integer value) {
            addCriterion("client_id <=", value, "clientId");
            return (Criteria) this;
        }

        public Criteria andClientIdIn(List<Integer> values) {
            addCriterion("client_id in", values, "clientId");
            return (Criteria) this;
        }

        public Criteria andClientIdNotIn(List<Integer> values) {
            addCriterion("client_id not in", values, "clientId");
            return (Criteria) this;
        }

        public Criteria andClientIdBetween(Integer value1, Integer value2) {
            addCriterion("client_id between", value1, value2, "clientId");
            return (Criteria) this;
        }

        public Criteria andClientIdNotBetween(Integer value1, Integer value2) {
            addCriterion("client_id not between", value1, value2, "clientId");
            return (Criteria) this;
        }

        public Criteria andCategoryIsNull() {
            addCriterion("category is null");
            return (Criteria) this;
        }

        public Criteria andCategoryIsNotNull() {
            addCriterion("category is not null");
            return (Criteria) this;
        }

        public Criteria andCategoryEqualTo(String value) {
            addCriterion("category =", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotEqualTo(String value) {
            addCriterion("category <>", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryGreaterThan(String value) {
            addCriterion("category >", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryGreaterThanOrEqualTo(String value) {
            addCriterion("category >=", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryLessThan(String value) {
            addCriterion("category <", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryLessThanOrEqualTo(String value) {
            addCriterion("category <=", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryLike(String value) {
            addCriterion("category like", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotLike(String value) {
            addCriterion("category not like", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryIn(List<String> values) {
            addCriterion("category in", values, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotIn(List<String> values) {
            addCriterion("category not in", values, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryBetween(String value1, String value2) {
            addCriterion("category between", value1, value2, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotBetween(String value1, String value2) {
            addCriterion("category not between", value1, value2, "category");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andDatasourceIdIsNull() {
            addCriterion("datasource_id is null");
            return (Criteria) this;
        }

        public Criteria andDatasourceIdIsNotNull() {
            addCriterion("datasource_id is not null");
            return (Criteria) this;
        }

        public Criteria andDatasourceIdEqualTo(Integer value) {
            addCriterion("datasource_id =", value, "datasourceId");
            return (Criteria) this;
        }

        public Criteria andDatasourceIdNotEqualTo(Integer value) {
            addCriterion("datasource_id <>", value, "datasourceId");
            return (Criteria) this;
        }

        public Criteria andDatasourceIdGreaterThan(Integer value) {
            addCriterion("datasource_id >", value, "datasourceId");
            return (Criteria) this;
        }

        public Criteria andDatasourceIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("datasource_id >=", value, "datasourceId");
            return (Criteria) this;
        }

        public Criteria andDatasourceIdLessThan(Integer value) {
            addCriterion("datasource_id <", value, "datasourceId");
            return (Criteria) this;
        }

        public Criteria andDatasourceIdLessThanOrEqualTo(Integer value) {
            addCriterion("datasource_id <=", value, "datasourceId");
            return (Criteria) this;
        }

        public Criteria andDatasourceIdIn(List<Integer> values) {
            addCriterion("datasource_id in", values, "datasourceId");
            return (Criteria) this;
        }

        public Criteria andDatasourceIdNotIn(List<Integer> values) {
            addCriterion("datasource_id not in", values, "datasourceId");
            return (Criteria) this;
        }

        public Criteria andDatasourceIdBetween(Integer value1, Integer value2) {
            addCriterion("datasource_id between", value1, value2, "datasourceId");
            return (Criteria) this;
        }

        public Criteria andDatasourceIdNotBetween(Integer value1, Integer value2) {
            addCriterion("datasource_id not between", value1, value2, "datasourceId");
            return (Criteria) this;
        }

        public Criteria andQueryIsNull() {
            addCriterion("query is null");
            return (Criteria) this;
        }

        public Criteria andQueryIsNotNull() {
            addCriterion("query is not null");
            return (Criteria) this;
        }

        public Criteria andQueryEqualTo(String value) {
            addCriterion("query =", value, "query");
            return (Criteria) this;
        }

        public Criteria andQueryNotEqualTo(String value) {
            addCriterion("query <>", value, "query");
            return (Criteria) this;
        }

        public Criteria andQueryGreaterThan(String value) {
            addCriterion("query >", value, "query");
            return (Criteria) this;
        }

        public Criteria andQueryGreaterThanOrEqualTo(String value) {
            addCriterion("query >=", value, "query");
            return (Criteria) this;
        }

        public Criteria andQueryLessThan(String value) {
            addCriterion("query <", value, "query");
            return (Criteria) this;
        }

        public Criteria andQueryLessThanOrEqualTo(String value) {
            addCriterion("query <=", value, "query");
            return (Criteria) this;
        }

        public Criteria andQueryLike(String value) {
            addCriterion("query like", value, "query");
            return (Criteria) this;
        }

        public Criteria andQueryNotLike(String value) {
            addCriterion("query not like", value, "query");
            return (Criteria) this;
        }

        public Criteria andQueryIn(List<String> values) {
            addCriterion("query in", values, "query");
            return (Criteria) this;
        }

        public Criteria andQueryNotIn(List<String> values) {
            addCriterion("query not in", values, "query");
            return (Criteria) this;
        }

        public Criteria andQueryBetween(String value1, String value2) {
            addCriterion("query between", value1, value2, "query");
            return (Criteria) this;
        }

        public Criteria andQueryNotBetween(String value1, String value2) {
            addCriterion("query not between", value1, value2, "query");
            return (Criteria) this;
        }

        public Criteria andDatasetIdIsNull() {
            addCriterion("dataset_id is null");
            return (Criteria) this;
        }

        public Criteria andDatasetIdIsNotNull() {
            addCriterion("dataset_id is not null");
            return (Criteria) this;
        }

        public Criteria andDatasetIdEqualTo(Long value) {
            addCriterion("dataset_id =", value, "datasetId");
            return (Criteria) this;
        }

        public Criteria andDatasetIdNotEqualTo(Long value) {
            addCriterion("dataset_id <>", value, "datasetId");
            return (Criteria) this;
        }

        public Criteria andDatasetIdGreaterThan(Long value) {
            addCriterion("dataset_id >", value, "datasetId");
            return (Criteria) this;
        }

        public Criteria andDatasetIdGreaterThanOrEqualTo(Long value) {
            addCriterion("dataset_id >=", value, "datasetId");
            return (Criteria) this;
        }

        public Criteria andDatasetIdLessThan(Long value) {
            addCriterion("dataset_id <", value, "datasetId");
            return (Criteria) this;
        }

        public Criteria andDatasetIdLessThanOrEqualTo(Long value) {
            addCriterion("dataset_id <=", value, "datasetId");
            return (Criteria) this;
        }

        public Criteria andDatasetIdIn(List<Long> values) {
            addCriterion("dataset_id in", values, "datasetId");
            return (Criteria) this;
        }

        public Criteria andDatasetIdNotIn(List<Long> values) {
            addCriterion("dataset_id not in", values, "datasetId");
            return (Criteria) this;
        }

        public Criteria andDatasetIdBetween(Long value1, Long value2) {
            addCriterion("dataset_id between", value1, value2, "datasetId");
            return (Criteria) this;
        }

        public Criteria andDatasetIdNotBetween(Long value1, Long value2) {
            addCriterion("dataset_id not between", value1, value2, "datasetId");
            return (Criteria) this;
        }

        public Criteria andGraphTypeIsNull() {
            addCriterion("graph_type is null");
            return (Criteria) this;
        }

        public Criteria andGraphTypeIsNotNull() {
            addCriterion("graph_type is not null");
            return (Criteria) this;
        }

        public Criteria andGraphTypeEqualTo(String value) {
            addCriterion("graph_type =", value, "graphType");
            return (Criteria) this;
        }

        public Criteria andGraphTypeNotEqualTo(String value) {
            addCriterion("graph_type <>", value, "graphType");
            return (Criteria) this;
        }

        public Criteria andGraphTypeGreaterThan(String value) {
            addCriterion("graph_type >", value, "graphType");
            return (Criteria) this;
        }

        public Criteria andGraphTypeGreaterThanOrEqualTo(String value) {
            addCriterion("graph_type >=", value, "graphType");
            return (Criteria) this;
        }

        public Criteria andGraphTypeLessThan(String value) {
            addCriterion("graph_type <", value, "graphType");
            return (Criteria) this;
        }

        public Criteria andGraphTypeLessThanOrEqualTo(String value) {
            addCriterion("graph_type <=", value, "graphType");
            return (Criteria) this;
        }

        public Criteria andGraphTypeLike(String value) {
            addCriterion("graph_type like", value, "graphType");
            return (Criteria) this;
        }

        public Criteria andGraphTypeNotLike(String value) {
            addCriterion("graph_type not like", value, "graphType");
            return (Criteria) this;
        }

        public Criteria andGraphTypeIn(List<String> values) {
            addCriterion("graph_type in", values, "graphType");
            return (Criteria) this;
        }

        public Criteria andGraphTypeNotIn(List<String> values) {
            addCriterion("graph_type not in", values, "graphType");
            return (Criteria) this;
        }

        public Criteria andGraphTypeBetween(String value1, String value2) {
            addCriterion("graph_type between", value1, value2, "graphType");
            return (Criteria) this;
        }

        public Criteria andGraphTypeNotBetween(String value1, String value2) {
            addCriterion("graph_type not between", value1, value2, "graphType");
            return (Criteria) this;
        }

        public Criteria andFillFieldIsNull() {
            addCriterion("fill_field is null");
            return (Criteria) this;
        }

        public Criteria andFillFieldIsNotNull() {
            addCriterion("fill_field is not null");
            return (Criteria) this;
        }

        public Criteria andFillFieldEqualTo(String value) {
            addCriterion("fill_field =", value, "fillField");
            return (Criteria) this;
        }

        public Criteria andFillFieldNotEqualTo(String value) {
            addCriterion("fill_field <>", value, "fillField");
            return (Criteria) this;
        }

        public Criteria andFillFieldGreaterThan(String value) {
            addCriterion("fill_field >", value, "fillField");
            return (Criteria) this;
        }

        public Criteria andFillFieldGreaterThanOrEqualTo(String value) {
            addCriterion("fill_field >=", value, "fillField");
            return (Criteria) this;
        }

        public Criteria andFillFieldLessThan(String value) {
            addCriterion("fill_field <", value, "fillField");
            return (Criteria) this;
        }

        public Criteria andFillFieldLessThanOrEqualTo(String value) {
            addCriterion("fill_field <=", value, "fillField");
            return (Criteria) this;
        }

        public Criteria andFillFieldLike(String value) {
            addCriterion("fill_field like", value, "fillField");
            return (Criteria) this;
        }

        public Criteria andFillFieldNotLike(String value) {
            addCriterion("fill_field not like", value, "fillField");
            return (Criteria) this;
        }

        public Criteria andFillFieldIn(List<String> values) {
            addCriterion("fill_field in", values, "fillField");
            return (Criteria) this;
        }

        public Criteria andFillFieldNotIn(List<String> values) {
            addCriterion("fill_field not in", values, "fillField");
            return (Criteria) this;
        }

        public Criteria andFillFieldBetween(String value1, String value2) {
            addCriterion("fill_field between", value1, value2, "fillField");
            return (Criteria) this;
        }

        public Criteria andFillFieldNotBetween(String value1, String value2) {
            addCriterion("fill_field not between", value1, value2, "fillField");
            return (Criteria) this;
        }

        public Criteria andRowIsNull() {
            addCriterion("row is null");
            return (Criteria) this;
        }

        public Criteria andRowIsNotNull() {
            addCriterion("row is not null");
            return (Criteria) this;
        }

        public Criteria andRowEqualTo(String value) {
            addCriterion("row =", value, "row");
            return (Criteria) this;
        }

        public Criteria andRowNotEqualTo(String value) {
            addCriterion("row <>", value, "row");
            return (Criteria) this;
        }

        public Criteria andRowGreaterThan(String value) {
            addCriterion("row >", value, "row");
            return (Criteria) this;
        }

        public Criteria andRowGreaterThanOrEqualTo(String value) {
            addCriterion("row >=", value, "row");
            return (Criteria) this;
        }

        public Criteria andRowLessThan(String value) {
            addCriterion("row <", value, "row");
            return (Criteria) this;
        }

        public Criteria andRowLessThanOrEqualTo(String value) {
            addCriterion("row <=", value, "row");
            return (Criteria) this;
        }

        public Criteria andRowLike(String value) {
            addCriterion("row like", value, "row");
            return (Criteria) this;
        }

        public Criteria andRowNotLike(String value) {
            addCriterion("row not like", value, "row");
            return (Criteria) this;
        }

        public Criteria andRowIn(List<String> values) {
            addCriterion("row in", values, "row");
            return (Criteria) this;
        }

        public Criteria andRowNotIn(List<String> values) {
            addCriterion("row not in", values, "row");
            return (Criteria) this;
        }

        public Criteria andRowBetween(String value1, String value2) {
            addCriterion("row between", value1, value2, "row");
            return (Criteria) this;
        }

        public Criteria andRowNotBetween(String value1, String value2) {
            addCriterion("row not between", value1, value2, "row");
            return (Criteria) this;
        }

        public Criteria andColumnIsNull() {
            addCriterion("column is null");
            return (Criteria) this;
        }

        public Criteria andColumnIsNotNull() {
            addCriterion("column is not null");
            return (Criteria) this;
        }

        public Criteria andColumnEqualTo(String value) {
            addCriterion("column =", value, "column");
            return (Criteria) this;
        }

        public Criteria andColumnNotEqualTo(String value) {
            addCriterion("column <>", value, "column");
            return (Criteria) this;
        }

        public Criteria andColumnGreaterThan(String value) {
            addCriterion("column >", value, "column");
            return (Criteria) this;
        }

        public Criteria andColumnGreaterThanOrEqualTo(String value) {
            addCriterion("column >=", value, "column");
            return (Criteria) this;
        }

        public Criteria andColumnLessThan(String value) {
            addCriterion("column <", value, "column");
            return (Criteria) this;
        }

        public Criteria andColumnLessThanOrEqualTo(String value) {
            addCriterion("column <=", value, "column");
            return (Criteria) this;
        }

        public Criteria andColumnLike(String value) {
            addCriterion("column like", value, "column");
            return (Criteria) this;
        }

        public Criteria andColumnNotLike(String value) {
            addCriterion("column not like", value, "column");
            return (Criteria) this;
        }

        public Criteria andColumnIn(List<String> values) {
            addCriterion("column in", values, "column");
            return (Criteria) this;
        }

        public Criteria andColumnNotIn(List<String> values) {
            addCriterion("column not in", values, "column");
            return (Criteria) this;
        }

        public Criteria andColumnBetween(String value1, String value2) {
            addCriterion("column between", value1, value2, "column");
            return (Criteria) this;
        }

        public Criteria andColumnNotBetween(String value1, String value2) {
            addCriterion("column not between", value1, value2, "column");
            return (Criteria) this;
        }

        public Criteria andFilterIsNull() {
            addCriterion("filter is null");
            return (Criteria) this;
        }

        public Criteria andFilterIsNotNull() {
            addCriterion("filter is not null");
            return (Criteria) this;
        }

        public Criteria andFilterEqualTo(String value) {
            addCriterion("filter =", value, "filter");
            return (Criteria) this;
        }

        public Criteria andFilterNotEqualTo(String value) {
            addCriterion("filter <>", value, "filter");
            return (Criteria) this;
        }

        public Criteria andFilterGreaterThan(String value) {
            addCriterion("filter >", value, "filter");
            return (Criteria) this;
        }

        public Criteria andFilterGreaterThanOrEqualTo(String value) {
            addCriterion("filter >=", value, "filter");
            return (Criteria) this;
        }

        public Criteria andFilterLessThan(String value) {
            addCriterion("filter <", value, "filter");
            return (Criteria) this;
        }

        public Criteria andFilterLessThanOrEqualTo(String value) {
            addCriterion("filter <=", value, "filter");
            return (Criteria) this;
        }

        public Criteria andFilterLike(String value) {
            addCriterion("filter like", value, "filter");
            return (Criteria) this;
        }

        public Criteria andFilterNotLike(String value) {
            addCriterion("filter not like", value, "filter");
            return (Criteria) this;
        }

        public Criteria andFilterIn(List<String> values) {
            addCriterion("filter in", values, "filter");
            return (Criteria) this;
        }

        public Criteria andFilterNotIn(List<String> values) {
            addCriterion("filter not in", values, "filter");
            return (Criteria) this;
        }

        public Criteria andFilterBetween(String value1, String value2) {
            addCriterion("filter between", value1, value2, "filter");
            return (Criteria) this;
        }

        public Criteria andFilterNotBetween(String value1, String value2) {
            addCriterion("filter not between", value1, value2, "filter");
            return (Criteria) this;
        }

        public Criteria andAggregationIsNull() {
            addCriterion("aggregation is null");
            return (Criteria) this;
        }

        public Criteria andAggregationIsNotNull() {
            addCriterion("aggregation is not null");
            return (Criteria) this;
        }

        public Criteria andAggregationEqualTo(String value) {
            addCriterion("aggregation =", value, "aggregation");
            return (Criteria) this;
        }

        public Criteria andAggregationNotEqualTo(String value) {
            addCriterion("aggregation <>", value, "aggregation");
            return (Criteria) this;
        }

        public Criteria andAggregationGreaterThan(String value) {
            addCriterion("aggregation >", value, "aggregation");
            return (Criteria) this;
        }

        public Criteria andAggregationGreaterThanOrEqualTo(String value) {
            addCriterion("aggregation >=", value, "aggregation");
            return (Criteria) this;
        }

        public Criteria andAggregationLessThan(String value) {
            addCriterion("aggregation <", value, "aggregation");
            return (Criteria) this;
        }

        public Criteria andAggregationLessThanOrEqualTo(String value) {
            addCriterion("aggregation <=", value, "aggregation");
            return (Criteria) this;
        }

        public Criteria andAggregationLike(String value) {
            addCriterion("aggregation like", value, "aggregation");
            return (Criteria) this;
        }

        public Criteria andAggregationNotLike(String value) {
            addCriterion("aggregation not like", value, "aggregation");
            return (Criteria) this;
        }

        public Criteria andAggregationIn(List<String> values) {
            addCriterion("aggregation in", values, "aggregation");
            return (Criteria) this;
        }

        public Criteria andAggregationNotIn(List<String> values) {
            addCriterion("aggregation not in", values, "aggregation");
            return (Criteria) this;
        }

        public Criteria andAggregationBetween(String value1, String value2) {
            addCriterion("aggregation between", value1, value2, "aggregation");
            return (Criteria) this;
        }

        public Criteria andAggregationNotBetween(String value1, String value2) {
            addCriterion("aggregation not between", value1, value2, "aggregation");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeIsNull() {
            addCriterion("created_time is null");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeIsNotNull() {
            addCriterion("created_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeEqualTo(Date value) {
            addCriterion("created_time =", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeNotEqualTo(Date value) {
            addCriterion("created_time <>", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeGreaterThan(Date value) {
            addCriterion("created_time >", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("created_time >=", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeLessThan(Date value) {
            addCriterion("created_time <", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeLessThanOrEqualTo(Date value) {
            addCriterion("created_time <=", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeIn(List<Date> values) {
            addCriterion("created_time in", values, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeNotIn(List<Date> values) {
            addCriterion("created_time not in", values, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeBetween(Date value1, Date value2) {
            addCriterion("created_time between", value1, value2, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeNotBetween(Date value1, Date value2) {
            addCriterion("created_time not between", value1, value2, "createdTime");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeIsNull() {
            addCriterion("updated_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeIsNotNull() {
            addCriterion("updated_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeEqualTo(Date value) {
            addCriterion("updated_time =", value, "updatedTime");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeNotEqualTo(Date value) {
            addCriterion("updated_time <>", value, "updatedTime");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeGreaterThan(Date value) {
            addCriterion("updated_time >", value, "updatedTime");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("updated_time >=", value, "updatedTime");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeLessThan(Date value) {
            addCriterion("updated_time <", value, "updatedTime");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeLessThanOrEqualTo(Date value) {
            addCriterion("updated_time <=", value, "updatedTime");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeIn(List<Date> values) {
            addCriterion("updated_time in", values, "updatedTime");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeNotIn(List<Date> values) {
            addCriterion("updated_time not in", values, "updatedTime");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeBetween(Date value1, Date value2) {
            addCriterion("updated_time between", value1, value2, "updatedTime");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimeNotBetween(Date value1, Date value2) {
            addCriterion("updated_time not between", value1, value2, "updatedTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}