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

        public Criteria andOptionalFieldIsNull() {
            addCriterion("optional_field is null");
            return (Criteria) this;
        }

        public Criteria andOptionalFieldIsNotNull() {
            addCriterion("optional_field is not null");
            return (Criteria) this;
        }

        public Criteria andOptionalFieldEqualTo(String value) {
            addCriterion("optional_field =", value, "optionalField");
            return (Criteria) this;
        }

        public Criteria andOptionalFieldNotEqualTo(String value) {
            addCriterion("optional_field <>", value, "optionalField");
            return (Criteria) this;
        }

        public Criteria andOptionalFieldGreaterThan(String value) {
            addCriterion("optional_field >", value, "optionalField");
            return (Criteria) this;
        }

        public Criteria andOptionalFieldGreaterThanOrEqualTo(String value) {
            addCriterion("optional_field >=", value, "optionalField");
            return (Criteria) this;
        }

        public Criteria andOptionalFieldLessThan(String value) {
            addCriterion("optional_field <", value, "optionalField");
            return (Criteria) this;
        }

        public Criteria andOptionalFieldLessThanOrEqualTo(String value) {
            addCriterion("optional_field <=", value, "optionalField");
            return (Criteria) this;
        }

        public Criteria andOptionalFieldLike(String value) {
            addCriterion("optional_field like", value, "optionalField");
            return (Criteria) this;
        }

        public Criteria andOptionalFieldNotLike(String value) {
            addCriterion("optional_field not like", value, "optionalField");
            return (Criteria) this;
        }

        public Criteria andOptionalFieldIn(List<String> values) {
            addCriterion("optional_field in", values, "optionalField");
            return (Criteria) this;
        }

        public Criteria andOptionalFieldNotIn(List<String> values) {
            addCriterion("optional_field not in", values, "optionalField");
            return (Criteria) this;
        }

        public Criteria andOptionalFieldBetween(String value1, String value2) {
            addCriterion("optional_field between", value1, value2, "optionalField");
            return (Criteria) this;
        }

        public Criteria andOptionalFieldNotBetween(String value1, String value2) {
            addCriterion("optional_field not between", value1, value2, "optionalField");
            return (Criteria) this;
        }

        public Criteria andRowFieldIsNull() {
            addCriterion("row_field is null");
            return (Criteria) this;
        }

        public Criteria andRowFieldIsNotNull() {
            addCriterion("row_field is not null");
            return (Criteria) this;
        }

        public Criteria andRowFieldEqualTo(String value) {
            addCriterion("row_field =", value, "rowField");
            return (Criteria) this;
        }

        public Criteria andRowFieldNotEqualTo(String value) {
            addCriterion("row_field <>", value, "rowField");
            return (Criteria) this;
        }

        public Criteria andRowFieldGreaterThan(String value) {
            addCriterion("row_field >", value, "rowField");
            return (Criteria) this;
        }

        public Criteria andRowFieldGreaterThanOrEqualTo(String value) {
            addCriterion("row_field >=", value, "rowField");
            return (Criteria) this;
        }

        public Criteria andRowFieldLessThan(String value) {
            addCriterion("row_field <", value, "rowField");
            return (Criteria) this;
        }

        public Criteria andRowFieldLessThanOrEqualTo(String value) {
            addCriterion("row_field <=", value, "rowField");
            return (Criteria) this;
        }

        public Criteria andRowFieldLike(String value) {
            addCriterion("row_field like", value, "rowField");
            return (Criteria) this;
        }

        public Criteria andRowFieldNotLike(String value) {
            addCriterion("row_field not like", value, "rowField");
            return (Criteria) this;
        }

        public Criteria andRowFieldIn(List<String> values) {
            addCriterion("row_field in", values, "rowField");
            return (Criteria) this;
        }

        public Criteria andRowFieldNotIn(List<String> values) {
            addCriterion("row_field not in", values, "rowField");
            return (Criteria) this;
        }

        public Criteria andRowFieldBetween(String value1, String value2) {
            addCriterion("row_field between", value1, value2, "rowField");
            return (Criteria) this;
        }

        public Criteria andRowFieldNotBetween(String value1, String value2) {
            addCriterion("row_field not between", value1, value2, "rowField");
            return (Criteria) this;
        }

        public Criteria andColumnFieldIsNull() {
            addCriterion("column_field is null");
            return (Criteria) this;
        }

        public Criteria andColumnFieldIsNotNull() {
            addCriterion("column_field is not null");
            return (Criteria) this;
        }

        public Criteria andColumnFieldEqualTo(String value) {
            addCriterion("column_field =", value, "columnField");
            return (Criteria) this;
        }

        public Criteria andColumnFieldNotEqualTo(String value) {
            addCriterion("column_field <>", value, "columnField");
            return (Criteria) this;
        }

        public Criteria andColumnFieldGreaterThan(String value) {
            addCriterion("column_field >", value, "columnField");
            return (Criteria) this;
        }

        public Criteria andColumnFieldGreaterThanOrEqualTo(String value) {
            addCriterion("column_field >=", value, "columnField");
            return (Criteria) this;
        }

        public Criteria andColumnFieldLessThan(String value) {
            addCriterion("column_field <", value, "columnField");
            return (Criteria) this;
        }

        public Criteria andColumnFieldLessThanOrEqualTo(String value) {
            addCriterion("column_field <=", value, "columnField");
            return (Criteria) this;
        }

        public Criteria andColumnFieldLike(String value) {
            addCriterion("column_field like", value, "columnField");
            return (Criteria) this;
        }

        public Criteria andColumnFieldNotLike(String value) {
            addCriterion("column_field not like", value, "columnField");
            return (Criteria) this;
        }

        public Criteria andColumnFieldIn(List<String> values) {
            addCriterion("column_field in", values, "columnField");
            return (Criteria) this;
        }

        public Criteria andColumnFieldNotIn(List<String> values) {
            addCriterion("column_field not in", values, "columnField");
            return (Criteria) this;
        }

        public Criteria andColumnFieldBetween(String value1, String value2) {
            addCriterion("column_field between", value1, value2, "columnField");
            return (Criteria) this;
        }

        public Criteria andColumnFieldNotBetween(String value1, String value2) {
            addCriterion("column_field not between", value1, value2, "columnField");
            return (Criteria) this;
        }

        public Criteria andFilterFieldIsNull() {
            addCriterion("filter_field is null");
            return (Criteria) this;
        }

        public Criteria andFilterFieldIsNotNull() {
            addCriterion("filter_field is not null");
            return (Criteria) this;
        }

        public Criteria andFilterFieldEqualTo(String value) {
            addCriterion("filter_field =", value, "filterField");
            return (Criteria) this;
        }

        public Criteria andFilterFieldNotEqualTo(String value) {
            addCriterion("filter_field <>", value, "filterField");
            return (Criteria) this;
        }

        public Criteria andFilterFieldGreaterThan(String value) {
            addCriterion("filter_field >", value, "filterField");
            return (Criteria) this;
        }

        public Criteria andFilterFieldGreaterThanOrEqualTo(String value) {
            addCriterion("filter_field >=", value, "filterField");
            return (Criteria) this;
        }

        public Criteria andFilterFieldLessThan(String value) {
            addCriterion("filter_field <", value, "filterField");
            return (Criteria) this;
        }

        public Criteria andFilterFieldLessThanOrEqualTo(String value) {
            addCriterion("filter_field <=", value, "filterField");
            return (Criteria) this;
        }

        public Criteria andFilterFieldLike(String value) {
            addCriterion("filter_field like", value, "filterField");
            return (Criteria) this;
        }

        public Criteria andFilterFieldNotLike(String value) {
            addCriterion("filter_field not like", value, "filterField");
            return (Criteria) this;
        }

        public Criteria andFilterFieldIn(List<String> values) {
            addCriterion("filter_field in", values, "filterField");
            return (Criteria) this;
        }

        public Criteria andFilterFieldNotIn(List<String> values) {
            addCriterion("filter_field not in", values, "filterField");
            return (Criteria) this;
        }

        public Criteria andFilterFieldBetween(String value1, String value2) {
            addCriterion("filter_field between", value1, value2, "filterField");
            return (Criteria) this;
        }

        public Criteria andFilterFieldNotBetween(String value1, String value2) {
            addCriterion("filter_field not between", value1, value2, "filterField");
            return (Criteria) this;
        }

        public Criteria andMetricFieldIsNull() {
            addCriterion("metric_field is null");
            return (Criteria) this;
        }

        public Criteria andMetricFieldIsNotNull() {
            addCriterion("metric_field is not null");
            return (Criteria) this;
        }

        public Criteria andMetricFieldEqualTo(String value) {
            addCriterion("metric_field =", value, "metricField");
            return (Criteria) this;
        }

        public Criteria andMetricFieldNotEqualTo(String value) {
            addCriterion("metric_field <>", value, "metricField");
            return (Criteria) this;
        }

        public Criteria andMetricFieldGreaterThan(String value) {
            addCriterion("metric_field >", value, "metricField");
            return (Criteria) this;
        }

        public Criteria andMetricFieldGreaterThanOrEqualTo(String value) {
            addCriterion("metric_field >=", value, "metricField");
            return (Criteria) this;
        }

        public Criteria andMetricFieldLessThan(String value) {
            addCriterion("metric_field <", value, "metricField");
            return (Criteria) this;
        }

        public Criteria andMetricFieldLessThanOrEqualTo(String value) {
            addCriterion("metric_field <=", value, "metricField");
            return (Criteria) this;
        }

        public Criteria andMetricFieldLike(String value) {
            addCriterion("metric_field like", value, "metricField");
            return (Criteria) this;
        }

        public Criteria andMetricFieldNotLike(String value) {
            addCriterion("metric_field not like", value, "metricField");
            return (Criteria) this;
        }

        public Criteria andMetricFieldIn(List<String> values) {
            addCriterion("metric_field in", values, "metricField");
            return (Criteria) this;
        }

        public Criteria andMetricFieldNotIn(List<String> values) {
            addCriterion("metric_field not in", values, "metricField");
            return (Criteria) this;
        }

        public Criteria andMetricFieldBetween(String value1, String value2) {
            addCriterion("metric_field between", value1, value2, "metricField");
            return (Criteria) this;
        }

        public Criteria andMetricFieldNotBetween(String value1, String value2) {
            addCriterion("metric_field not between", value1, value2, "metricField");
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