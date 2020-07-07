package com.medcaptain.cloud.usermanage.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 前端资源查询实例
 *
 * @author bingwen.ai
 */
@SuppressWarnings("ALL")
public class FrontendResourceExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public FrontendResourceExample() {
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

        public Criteria andResourceNameIsNull() {
            addCriterion("resource_name is null");
            return (Criteria) this;
        }

        public Criteria andResourceNameIsNotNull() {
            addCriterion("resource_name is not null");
            return (Criteria) this;
        }

        public Criteria andResourceNameEqualTo(String value) {
            addCriterion("resource_name =", value, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameNotEqualTo(String value) {
            addCriterion("resource_name <>", value, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameGreaterThan(String value) {
            addCriterion("resource_name >", value, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameGreaterThanOrEqualTo(String value) {
            addCriterion("resource_name >=", value, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameLessThan(String value) {
            addCriterion("resource_name <", value, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameLessThanOrEqualTo(String value) {
            addCriterion("resource_name <=", value, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameLike(String value) {
            addCriterion("resource_name like", value, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameNotLike(String value) {
            addCriterion("resource_name not like", value, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameIn(List<String> values) {
            addCriterion("resource_name in", values, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameNotIn(List<String> values) {
            addCriterion("resource_name not in", values, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameBetween(String value1, String value2) {
            addCriterion("resource_name between", value1, value2, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameNotBetween(String value1, String value2) {
            addCriterion("resource_name not between", value1, value2, "resourceName");
            return (Criteria) this;
        }

        public Criteria andElementIdIsNull() {
            addCriterion("element_id is null");
            return (Criteria) this;
        }

        public Criteria andElementIdIsNotNull() {
            addCriterion("element_id is not null");
            return (Criteria) this;
        }

        public Criteria andElementIdEqualTo(String value) {
            addCriterion("element_id =", value, "elementId");
            return (Criteria) this;
        }

        public Criteria andElementIdNotEqualTo(String value) {
            addCriterion("element_id <>", value, "elementId");
            return (Criteria) this;
        }

        public Criteria andElementIdGreaterThan(String value) {
            addCriterion("element_id >", value, "elementId");
            return (Criteria) this;
        }

        public Criteria andElementIdGreaterThanOrEqualTo(String value) {
            addCriterion("element_id >=", value, "elementId");
            return (Criteria) this;
        }

        public Criteria andElementIdLessThan(String value) {
            addCriterion("element_id <", value, "elementId");
            return (Criteria) this;
        }

        public Criteria andElementIdLessThanOrEqualTo(String value) {
            addCriterion("element_id <=", value, "elementId");
            return (Criteria) this;
        }

        public Criteria andElementIdLike(String value) {
            addCriterion("element_id like", value, "elementId");
            return (Criteria) this;
        }

        public Criteria andElementIdNotLike(String value) {
            addCriterion("element_id not like", value, "elementId");
            return (Criteria) this;
        }

        public Criteria andElementIdIn(List<String> values) {
            addCriterion("element_id in", values, "elementId");
            return (Criteria) this;
        }

        public Criteria andElementIdNotIn(List<String> values) {
            addCriterion("element_id not in", values, "elementId");
            return (Criteria) this;
        }

        public Criteria andElementIdBetween(String value1, String value2) {
            addCriterion("element_id between", value1, value2, "elementId");
            return (Criteria) this;
        }

        public Criteria andElementIdNotBetween(String value1, String value2) {
            addCriterion("element_id not between", value1, value2, "elementId");
            return (Criteria) this;
        }

        public Criteria andResourceTypeIsNull() {
            addCriterion("resource_type is null");
            return (Criteria) this;
        }

        public Criteria andResourceTypeIsNotNull() {
            addCriterion("resource_type is not null");
            return (Criteria) this;
        }

        public Criteria andResourceTypeEqualTo(Byte value) {
            addCriterion("resource_type =", value, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeNotEqualTo(Byte value) {
            addCriterion("resource_type <>", value, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeGreaterThan(Byte value) {
            addCriterion("resource_type >", value, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("resource_type >=", value, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeLessThan(Byte value) {
            addCriterion("resource_type <", value, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeLessThanOrEqualTo(Byte value) {
            addCriterion("resource_type <=", value, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeIn(List<Byte> values) {
            addCriterion("resource_type in", values, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeNotIn(List<Byte> values) {
            addCriterion("resource_type not in", values, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeBetween(Byte value1, Byte value2) {
            addCriterion("resource_type between", value1, value2, "resourceType");
            return (Criteria) this;
        }

        public Criteria andResourceTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("resource_type not between", value1, value2, "resourceType");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andParentResourceIdIsNull() {
            addCriterion("parent_resource_id is null");
            return (Criteria) this;
        }

        public Criteria andParentResourceIdIsNotNull() {
            addCriterion("parent_resource_id is not null");
            return (Criteria) this;
        }

        public Criteria andParentResourceIdEqualTo(Integer value) {
            addCriterion("parent_resource_id =", value, "parentResourceId");
            return (Criteria) this;
        }

        public Criteria andParentResourceIdNotEqualTo(Integer value) {
            addCriterion("parent_resource_id <>", value, "parentResourceId");
            return (Criteria) this;
        }

        public Criteria andParentResourceIdGreaterThan(Integer value) {
            addCriterion("parent_resource_id >", value, "parentResourceId");
            return (Criteria) this;
        }

        public Criteria andParentResourceIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("parent_resource_id >=", value, "parentResourceId");
            return (Criteria) this;
        }

        public Criteria andParentResourceIdLessThan(Integer value) {
            addCriterion("parent_resource_id <", value, "parentResourceId");
            return (Criteria) this;
        }

        public Criteria andParentResourceIdLessThanOrEqualTo(Integer value) {
            addCriterion("parent_resource_id <=", value, "parentResourceId");
            return (Criteria) this;
        }

        public Criteria andParentResourceIdIn(List<Integer> values) {
            addCriterion("parent_resource_id in", values, "parentResourceId");
            return (Criteria) this;
        }

        public Criteria andParentResourceIdNotIn(List<Integer> values) {
            addCriterion("parent_resource_id not in", values, "parentResourceId");
            return (Criteria) this;
        }

        public Criteria andParentResourceIdBetween(Integer value1, Integer value2) {
            addCriterion("parent_resource_id between", value1, value2, "parentResourceId");
            return (Criteria) this;
        }

        public Criteria andParentResourceIdNotBetween(Integer value1, Integer value2) {
            addCriterion("parent_resource_id not between", value1, value2, "parentResourceId");
            return (Criteria) this;
        }

        public Criteria andIsEnableIsNull() {
            addCriterion("is_enable is null");
            return (Criteria) this;
        }

        public Criteria andIsEnableIsNotNull() {
            addCriterion("is_enable is not null");
            return (Criteria) this;
        }

        public Criteria andIsEnableEqualTo(Byte value) {
            addCriterion("is_enable =", value, "isEnable");
            return (Criteria) this;
        }

        public Criteria andIsEnableNotEqualTo(Byte value) {
            addCriterion("is_enable <>", value, "isEnable");
            return (Criteria) this;
        }

        public Criteria andIsEnableGreaterThan(Byte value) {
            addCriterion("is_enable >", value, "isEnable");
            return (Criteria) this;
        }

        public Criteria andIsEnableGreaterThanOrEqualTo(Byte value) {
            addCriterion("is_enable >=", value, "isEnable");
            return (Criteria) this;
        }

        public Criteria andIsEnableLessThan(Byte value) {
            addCriterion("is_enable <", value, "isEnable");
            return (Criteria) this;
        }

        public Criteria andIsEnableLessThanOrEqualTo(Byte value) {
            addCriterion("is_enable <=", value, "isEnable");
            return (Criteria) this;
        }

        public Criteria andIsEnableIn(List<Byte> values) {
            addCriterion("is_enable in", values, "isEnable");
            return (Criteria) this;
        }

        public Criteria andIsEnableNotIn(List<Byte> values) {
            addCriterion("is_enable not in", values, "isEnable");
            return (Criteria) this;
        }

        public Criteria andIsEnableBetween(Byte value1, Byte value2) {
            addCriterion("is_enable between", value1, value2, "isEnable");
            return (Criteria) this;
        }

        public Criteria andIsEnableNotBetween(Byte value1, Byte value2) {
            addCriterion("is_enable not between", value1, value2, "isEnable");
            return (Criteria) this;
        }

        public Criteria andMenuUrlIsNull() {
            addCriterion("menu_url is null");
            return (Criteria) this;
        }

        public Criteria andMenuUrlIsNotNull() {
            addCriterion("menu_url is not null");
            return (Criteria) this;
        }

        public Criteria andMenuUrlEqualTo(String value) {
            addCriterion("menu_url =", value, "menuUrl");
            return (Criteria) this;
        }

        public Criteria andMenuUrlNotEqualTo(String value) {
            addCriterion("menu_url <>", value, "menuUrl");
            return (Criteria) this;
        }

        public Criteria andMenuUrlGreaterThan(String value) {
            addCriterion("menu_url >", value, "menuUrl");
            return (Criteria) this;
        }

        public Criteria andMenuUrlGreaterThanOrEqualTo(String value) {
            addCriterion("menu_url >=", value, "menuUrl");
            return (Criteria) this;
        }

        public Criteria andMenuUrlLessThan(String value) {
            addCriterion("menu_url <", value, "menuUrl");
            return (Criteria) this;
        }

        public Criteria andMenuUrlLessThanOrEqualTo(String value) {
            addCriterion("menu_url <=", value, "menuUrl");
            return (Criteria) this;
        }

        public Criteria andMenuUrlLike(String value) {
            addCriterion("menu_url like", value, "menuUrl");
            return (Criteria) this;
        }

        public Criteria andMenuUrlNotLike(String value) {
            addCriterion("menu_url not like", value, "menuUrl");
            return (Criteria) this;
        }

        public Criteria andMenuUrlIn(List<String> values) {
            addCriterion("menu_url in", values, "menuUrl");
            return (Criteria) this;
        }

        public Criteria andMenuUrlNotIn(List<String> values) {
            addCriterion("menu_url not in", values, "menuUrl");
            return (Criteria) this;
        }

        public Criteria andMenuUrlBetween(String value1, String value2) {
            addCriterion("menu_url between", value1, value2, "menuUrl");
            return (Criteria) this;
        }

        public Criteria andMenuUrlNotBetween(String value1, String value2) {
            addCriterion("menu_url not between", value1, value2, "menuUrl");
            return (Criteria) this;
        }

        public Criteria andMenuIconIsNull() {
            addCriterion("menu_icon is null");
            return (Criteria) this;
        }

        public Criteria andMenuIconIsNotNull() {
            addCriterion("menu_icon is not null");
            return (Criteria) this;
        }

        public Criteria andMenuIconEqualTo(String value) {
            addCriterion("menu_icon =", value, "menuIcon");
            return (Criteria) this;
        }

        public Criteria andMenuIconNotEqualTo(String value) {
            addCriterion("menu_icon <>", value, "menuIcon");
            return (Criteria) this;
        }

        public Criteria andMenuIconGreaterThan(String value) {
            addCriterion("menu_icon >", value, "menuIcon");
            return (Criteria) this;
        }

        public Criteria andMenuIconGreaterThanOrEqualTo(String value) {
            addCriterion("menu_icon >=", value, "menuIcon");
            return (Criteria) this;
        }

        public Criteria andMenuIconLessThan(String value) {
            addCriterion("menu_icon <", value, "menuIcon");
            return (Criteria) this;
        }

        public Criteria andMenuIconLessThanOrEqualTo(String value) {
            addCriterion("menu_icon <=", value, "menuIcon");
            return (Criteria) this;
        }

        public Criteria andMenuIconLike(String value) {
            addCriterion("menu_icon like", value, "menuIcon");
            return (Criteria) this;
        }

        public Criteria andMenuIconNotLike(String value) {
            addCriterion("menu_icon not like", value, "menuIcon");
            return (Criteria) this;
        }

        public Criteria andMenuIconIn(List<String> values) {
            addCriterion("menu_icon in", values, "menuIcon");
            return (Criteria) this;
        }

        public Criteria andMenuIconNotIn(List<String> values) {
            addCriterion("menu_icon not in", values, "menuIcon");
            return (Criteria) this;
        }

        public Criteria andMenuIconBetween(String value1, String value2) {
            addCriterion("menu_icon between", value1, value2, "menuIcon");
            return (Criteria) this;
        }

        public Criteria andMenuIconNotBetween(String value1, String value2) {
            addCriterion("menu_icon not between", value1, value2, "menuIcon");
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