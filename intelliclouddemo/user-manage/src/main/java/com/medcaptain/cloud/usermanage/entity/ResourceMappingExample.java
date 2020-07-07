package com.medcaptain.cloud.usermanage.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 前后端资源映射查询实例
 *
 * @author bingwen.ai
 */
@SuppressWarnings("ALL")
public class ResourceMappingExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ResourceMappingExample() {
        oredCriteria = new ArrayList<>();
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
        return new Criteria();
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
            criteria = new ArrayList<>();
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

        public Criteria andResourceMappingIdIsNull() {
            addCriterion("resource_mapping_id is null");
            return (Criteria) this;
        }

        public Criteria andResourceMappingIdIsNotNull() {
            addCriterion("resource_mapping_id is not null");
            return (Criteria) this;
        }

        public Criteria andResourceMappingIdEqualTo(Integer value) {
            addCriterion("resource_mapping_id =", value, "resourceMappingId");
            return (Criteria) this;
        }

        public Criteria andResourceMappingIdNotEqualTo(Integer value) {
            addCriterion("resource_mapping_id <>", value, "resourceMappingId");
            return (Criteria) this;
        }

        public Criteria andResourceMappingIdGreaterThan(Integer value) {
            addCriterion("resource_mapping_id >", value, "resourceMappingId");
            return (Criteria) this;
        }

        public Criteria andResourceMappingIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("resource_mapping_id >=", value, "resourceMappingId");
            return (Criteria) this;
        }

        public Criteria andResourceMappingIdLessThan(Integer value) {
            addCriterion("resource_mapping_id <", value, "resourceMappingId");
            return (Criteria) this;
        }

        public Criteria andResourceMappingIdLessThanOrEqualTo(Integer value) {
            addCriterion("resource_mapping_id <=", value, "resourceMappingId");
            return (Criteria) this;
        }

        public Criteria andResourceMappingIdIn(List<Integer> values) {
            addCriterion("resource_mapping_id in", values, "resourceMappingId");
            return (Criteria) this;
        }

        public Criteria andResourceMappingIdNotIn(List<Integer> values) {
            addCriterion("resource_mapping_id not in", values, "resourceMappingId");
            return (Criteria) this;
        }

        public Criteria andResourceMappingIdBetween(Integer value1, Integer value2) {
            addCriterion("resource_mapping_id between", value1, value2, "resourceMappingId");
            return (Criteria) this;
        }

        public Criteria andResourceMappingIdNotBetween(Integer value1, Integer value2) {
            addCriterion("resource_mapping_id not between", value1, value2, "resourceMappingId");
            return (Criteria) this;
        }

        public Criteria andBackendResourceIdIsNull() {
            addCriterion("backend_resource_id is null");
            return (Criteria) this;
        }

        public Criteria andBackendResourceIdIsNotNull() {
            addCriterion("backend_resource_id is not null");
            return (Criteria) this;
        }

        public Criteria andBackendResourceIdEqualTo(Integer value) {
            addCriterion("backend_resource_id =", value, "backendResourceId");
            return (Criteria) this;
        }

        public Criteria andBackendResourceIdNotEqualTo(Integer value) {
            addCriterion("backend_resource_id <>", value, "backendResourceId");
            return (Criteria) this;
        }

        public Criteria andBackendResourceIdGreaterThan(Integer value) {
            addCriterion("backend_resource_id >", value, "backendResourceId");
            return (Criteria) this;
        }

        public Criteria andBackendResourceIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("backend_resource_id >=", value, "backendResourceId");
            return (Criteria) this;
        }

        public Criteria andBackendResourceIdLessThan(Integer value) {
            addCriterion("backend_resource_id <", value, "backendResourceId");
            return (Criteria) this;
        }

        public Criteria andBackendResourceIdLessThanOrEqualTo(Integer value) {
            addCriterion("backend_resource_id <=", value, "backendResourceId");
            return (Criteria) this;
        }

        public Criteria andBackendResourceIdIn(List<Integer> values) {
            addCriterion("backend_resource_id in", values, "backendResourceId");
            return (Criteria) this;
        }

        public Criteria andBackendResourceIdNotIn(List<Integer> values) {
            addCriterion("backend_resource_id not in", values, "backendResourceId");
            return (Criteria) this;
        }

        public Criteria andBackendResourceIdBetween(Integer value1, Integer value2) {
            addCriterion("backend_resource_id between", value1, value2, "backendResourceId");
            return (Criteria) this;
        }

        public Criteria andBackendResourceIdNotBetween(Integer value1, Integer value2) {
            addCriterion("backend_resource_id not between", value1, value2, "backendResourceId");
            return (Criteria) this;
        }

        public Criteria andFrontendResourceIdIsNull() {
            addCriterion("frontend_resource_id is null");
            return (Criteria) this;
        }

        public Criteria andFrontendResourceIdIsNotNull() {
            addCriterion("frontend_resource_id is not null");
            return (Criteria) this;
        }

        public Criteria andFrontendResourceIdEqualTo(Integer value) {
            addCriterion("frontend_resource_id =", value, "frontendResourceId");
            return (Criteria) this;
        }

        public Criteria andFrontendResourceIdNotEqualTo(Integer value) {
            addCriterion("frontend_resource_id <>", value, "frontendResourceId");
            return (Criteria) this;
        }

        public Criteria andFrontendResourceIdGreaterThan(Integer value) {
            addCriterion("frontend_resource_id >", value, "frontendResourceId");
            return (Criteria) this;
        }

        public Criteria andFrontendResourceIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("frontend_resource_id >=", value, "frontendResourceId");
            return (Criteria) this;
        }

        public Criteria andFrontendResourceIdLessThan(Integer value) {
            addCriterion("frontend_resource_id <", value, "frontendResourceId");
            return (Criteria) this;
        }

        public Criteria andFrontendResourceIdLessThanOrEqualTo(Integer value) {
            addCriterion("frontend_resource_id <=", value, "frontendResourceId");
            return (Criteria) this;
        }

        public Criteria andFrontendResourceIdIn(List<Integer> values) {
            addCriterion("frontend_resource_id in", values, "frontendResourceId");
            return (Criteria) this;
        }

        public Criteria andFrontendResourceIdNotIn(List<Integer> values) {
            addCriterion("frontend_resource_id not in", values, "frontendResourceId");
            return (Criteria) this;
        }

        public Criteria andFrontendResourceIdBetween(Integer value1, Integer value2) {
            addCriterion("frontend_resource_id between", value1, value2, "frontendResourceId");
            return (Criteria) this;
        }

        public Criteria andFrontendResourceIdNotBetween(Integer value1, Integer value2) {
            addCriterion("frontend_resource_id not between", value1, value2, "frontendResourceId");
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
