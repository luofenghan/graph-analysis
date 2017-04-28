package com.analysis.graph.common.domain.dbo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataSetExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DataSetExample() {
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

        public Criteria andDataSourceIdIsNull() {
            addCriterion("data_source_id is null");
            return (Criteria) this;
        }

        public Criteria andDataSourceIdIsNotNull() {
            addCriterion("data_source_id is not null");
            return (Criteria) this;
        }

        public Criteria andDataSourceIdEqualTo(Integer value) {
            addCriterion("data_source_id =", value, "dataSourceId");
            return (Criteria) this;
        }

        public Criteria andDataSourceIdNotEqualTo(Integer value) {
            addCriterion("data_source_id <>", value, "dataSourceId");
            return (Criteria) this;
        }

        public Criteria andDataSourceIdGreaterThan(Integer value) {
            addCriterion("data_source_id >", value, "dataSourceId");
            return (Criteria) this;
        }

        public Criteria andDataSourceIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("data_source_id >=", value, "dataSourceId");
            return (Criteria) this;
        }

        public Criteria andDataSourceIdLessThan(Integer value) {
            addCriterion("data_source_id <", value, "dataSourceId");
            return (Criteria) this;
        }

        public Criteria andDataSourceIdLessThanOrEqualTo(Integer value) {
            addCriterion("data_source_id <=", value, "dataSourceId");
            return (Criteria) this;
        }

        public Criteria andDataSourceIdIn(List<Integer> values) {
            addCriterion("data_source_id in", values, "dataSourceId");
            return (Criteria) this;
        }

        public Criteria andDataSourceIdNotIn(List<Integer> values) {
            addCriterion("data_source_id not in", values, "dataSourceId");
            return (Criteria) this;
        }

        public Criteria andDataSourceIdBetween(Integer value1, Integer value2) {
            addCriterion("data_source_id between", value1, value2, "dataSourceId");
            return (Criteria) this;
        }

        public Criteria andDataSourceIdNotBetween(Integer value1, Integer value2) {
            addCriterion("data_source_id not between", value1, value2, "dataSourceId");
            return (Criteria) this;
        }

        public Criteria andCategoryNameIsNull() {
            addCriterion("category_name is null");
            return (Criteria) this;
        }

        public Criteria andCategoryNameIsNotNull() {
            addCriterion("category_name is not null");
            return (Criteria) this;
        }

        public Criteria andCategoryNameEqualTo(String value) {
            addCriterion("category_name =", value, "categoryName");
            return (Criteria) this;
        }

        public Criteria andCategoryNameNotEqualTo(String value) {
            addCriterion("category_name <>", value, "categoryName");
            return (Criteria) this;
        }

        public Criteria andCategoryNameGreaterThan(String value) {
            addCriterion("category_name >", value, "categoryName");
            return (Criteria) this;
        }

        public Criteria andCategoryNameGreaterThanOrEqualTo(String value) {
            addCriterion("category_name >=", value, "categoryName");
            return (Criteria) this;
        }

        public Criteria andCategoryNameLessThan(String value) {
            addCriterion("category_name <", value, "categoryName");
            return (Criteria) this;
        }

        public Criteria andCategoryNameLessThanOrEqualTo(String value) {
            addCriterion("category_name <=", value, "categoryName");
            return (Criteria) this;
        }

        public Criteria andCategoryNameLike(String value) {
            addCriterion("category_name like", value, "categoryName");
            return (Criteria) this;
        }

        public Criteria andCategoryNameNotLike(String value) {
            addCriterion("category_name not like", value, "categoryName");
            return (Criteria) this;
        }

        public Criteria andCategoryNameIn(List<String> values) {
            addCriterion("category_name in", values, "categoryName");
            return (Criteria) this;
        }

        public Criteria andCategoryNameNotIn(List<String> values) {
            addCriterion("category_name not in", values, "categoryName");
            return (Criteria) this;
        }

        public Criteria andCategoryNameBetween(String value1, String value2) {
            addCriterion("category_name between", value1, value2, "categoryName");
            return (Criteria) this;
        }

        public Criteria andCategoryNameNotBetween(String value1, String value2) {
            addCriterion("category_name not between", value1, value2, "categoryName");
            return (Criteria) this;
        }

        public Criteria andDataSetNameIsNull() {
            addCriterion("data_set_name is null");
            return (Criteria) this;
        }

        public Criteria andDataSetNameIsNotNull() {
            addCriterion("data_set_name is not null");
            return (Criteria) this;
        }

        public Criteria andDataSetNameEqualTo(String value) {
            addCriterion("data_set_name =", value, "dataSetName");
            return (Criteria) this;
        }

        public Criteria andDataSetNameNotEqualTo(String value) {
            addCriterion("data_set_name <>", value, "dataSetName");
            return (Criteria) this;
        }

        public Criteria andDataSetNameGreaterThan(String value) {
            addCriterion("data_set_name >", value, "dataSetName");
            return (Criteria) this;
        }

        public Criteria andDataSetNameGreaterThanOrEqualTo(String value) {
            addCriterion("data_set_name >=", value, "dataSetName");
            return (Criteria) this;
        }

        public Criteria andDataSetNameLessThan(String value) {
            addCriterion("data_set_name <", value, "dataSetName");
            return (Criteria) this;
        }

        public Criteria andDataSetNameLessThanOrEqualTo(String value) {
            addCriterion("data_set_name <=", value, "dataSetName");
            return (Criteria) this;
        }

        public Criteria andDataSetNameLike(String value) {
            addCriterion("data_set_name like", value, "dataSetName");
            return (Criteria) this;
        }

        public Criteria andDataSetNameNotLike(String value) {
            addCriterion("data_set_name not like", value, "dataSetName");
            return (Criteria) this;
        }

        public Criteria andDataSetNameIn(List<String> values) {
            addCriterion("data_set_name in", values, "dataSetName");
            return (Criteria) this;
        }

        public Criteria andDataSetNameNotIn(List<String> values) {
            addCriterion("data_set_name not in", values, "dataSetName");
            return (Criteria) this;
        }

        public Criteria andDataSetNameBetween(String value1, String value2) {
            addCriterion("data_set_name between", value1, value2, "dataSetName");
            return (Criteria) this;
        }

        public Criteria andDataSetNameNotBetween(String value1, String value2) {
            addCriterion("data_set_name not between", value1, value2, "dataSetName");
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

        public Criteria andFilterGroupIsNull() {
            addCriterion("filter_group is null");
            return (Criteria) this;
        }

        public Criteria andFilterGroupIsNotNull() {
            addCriterion("filter_group is not null");
            return (Criteria) this;
        }

        public Criteria andFilterGroupEqualTo(String value) {
            addCriterion("filter_group =", value, "filterGroup");
            return (Criteria) this;
        }

        public Criteria andFilterGroupNotEqualTo(String value) {
            addCriterion("filter_group <>", value, "filterGroup");
            return (Criteria) this;
        }

        public Criteria andFilterGroupGreaterThan(String value) {
            addCriterion("filter_group >", value, "filterGroup");
            return (Criteria) this;
        }

        public Criteria andFilterGroupGreaterThanOrEqualTo(String value) {
            addCriterion("filter_group >=", value, "filterGroup");
            return (Criteria) this;
        }

        public Criteria andFilterGroupLessThan(String value) {
            addCriterion("filter_group <", value, "filterGroup");
            return (Criteria) this;
        }

        public Criteria andFilterGroupLessThanOrEqualTo(String value) {
            addCriterion("filter_group <=", value, "filterGroup");
            return (Criteria) this;
        }

        public Criteria andFilterGroupLike(String value) {
            addCriterion("filter_group like", value, "filterGroup");
            return (Criteria) this;
        }

        public Criteria andFilterGroupNotLike(String value) {
            addCriterion("filter_group not like", value, "filterGroup");
            return (Criteria) this;
        }

        public Criteria andFilterGroupIn(List<String> values) {
            addCriterion("filter_group in", values, "filterGroup");
            return (Criteria) this;
        }

        public Criteria andFilterGroupNotIn(List<String> values) {
            addCriterion("filter_group not in", values, "filterGroup");
            return (Criteria) this;
        }

        public Criteria andFilterGroupBetween(String value1, String value2) {
            addCriterion("filter_group between", value1, value2, "filterGroup");
            return (Criteria) this;
        }

        public Criteria andFilterGroupNotBetween(String value1, String value2) {
            addCriterion("filter_group not between", value1, value2, "filterGroup");
            return (Criteria) this;
        }

        public Criteria andExpressionsIsNull() {
            addCriterion("expressions is null");
            return (Criteria) this;
        }

        public Criteria andExpressionsIsNotNull() {
            addCriterion("expressions is not null");
            return (Criteria) this;
        }

        public Criteria andExpressionsEqualTo(String value) {
            addCriterion("expressions =", value, "expressions");
            return (Criteria) this;
        }

        public Criteria andExpressionsNotEqualTo(String value) {
            addCriterion("expressions <>", value, "expressions");
            return (Criteria) this;
        }

        public Criteria andExpressionsGreaterThan(String value) {
            addCriterion("expressions >", value, "expressions");
            return (Criteria) this;
        }

        public Criteria andExpressionsGreaterThanOrEqualTo(String value) {
            addCriterion("expressions >=", value, "expressions");
            return (Criteria) this;
        }

        public Criteria andExpressionsLessThan(String value) {
            addCriterion("expressions <", value, "expressions");
            return (Criteria) this;
        }

        public Criteria andExpressionsLessThanOrEqualTo(String value) {
            addCriterion("expressions <=", value, "expressions");
            return (Criteria) this;
        }

        public Criteria andExpressionsLike(String value) {
            addCriterion("expressions like", value, "expressions");
            return (Criteria) this;
        }

        public Criteria andExpressionsNotLike(String value) {
            addCriterion("expressions not like", value, "expressions");
            return (Criteria) this;
        }

        public Criteria andExpressionsIn(List<String> values) {
            addCriterion("expressions in", values, "expressions");
            return (Criteria) this;
        }

        public Criteria andExpressionsNotIn(List<String> values) {
            addCriterion("expressions not in", values, "expressions");
            return (Criteria) this;
        }

        public Criteria andExpressionsBetween(String value1, String value2) {
            addCriterion("expressions between", value1, value2, "expressions");
            return (Criteria) this;
        }

        public Criteria andExpressionsNotBetween(String value1, String value2) {
            addCriterion("expressions not between", value1, value2, "expressions");
            return (Criteria) this;
        }

        public Criteria andIntervalIsNull() {
            addCriterion("interval is null");
            return (Criteria) this;
        }

        public Criteria andIntervalIsNotNull() {
            addCriterion("interval is not null");
            return (Criteria) this;
        }

        public Criteria andIntervalEqualTo(Long value) {
            addCriterion("interval =", value, "interval");
            return (Criteria) this;
        }

        public Criteria andIntervalNotEqualTo(Long value) {
            addCriterion("interval <>", value, "interval");
            return (Criteria) this;
        }

        public Criteria andIntervalGreaterThan(Long value) {
            addCriterion("interval >", value, "interval");
            return (Criteria) this;
        }

        public Criteria andIntervalGreaterThanOrEqualTo(Long value) {
            addCriterion("interval >=", value, "interval");
            return (Criteria) this;
        }

        public Criteria andIntervalLessThan(Long value) {
            addCriterion("interval <", value, "interval");
            return (Criteria) this;
        }

        public Criteria andIntervalLessThanOrEqualTo(Long value) {
            addCriterion("interval <=", value, "interval");
            return (Criteria) this;
        }

        public Criteria andIntervalIn(List<Long> values) {
            addCriterion("interval in", values, "interval");
            return (Criteria) this;
        }

        public Criteria andIntervalNotIn(List<Long> values) {
            addCriterion("interval not in", values, "interval");
            return (Criteria) this;
        }

        public Criteria andIntervalBetween(Long value1, Long value2) {
            addCriterion("interval between", value1, value2, "interval");
            return (Criteria) this;
        }

        public Criteria andIntervalNotBetween(Long value1, Long value2) {
            addCriterion("interval not between", value1, value2, "interval");
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