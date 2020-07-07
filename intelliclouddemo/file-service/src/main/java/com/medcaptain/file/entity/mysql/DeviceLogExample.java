package com.medcaptain.file.entity.mysql;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DeviceLogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DeviceLogExample() {
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

        public Criteria andDeviceLogIdIsNull() {
            addCriterion("device_log_id is null");
            return (Criteria) this;
        }

        public Criteria andDeviceLogIdIsNotNull() {
            addCriterion("device_log_id is not null");
            return (Criteria) this;
        }

        public Criteria andDeviceLogIdEqualTo(Integer value) {
            addCriterion("device_log_id =", value, "deviceLogId");
            return (Criteria) this;
        }

        public Criteria andDeviceLogIdNotEqualTo(Integer value) {
            addCriterion("device_log_id <>", value, "deviceLogId");
            return (Criteria) this;
        }

        public Criteria andDeviceLogIdGreaterThan(Integer value) {
            addCriterion("device_log_id >", value, "deviceLogId");
            return (Criteria) this;
        }

        public Criteria andDeviceLogIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("device_log_id >=", value, "deviceLogId");
            return (Criteria) this;
        }

        public Criteria andDeviceLogIdLessThan(Integer value) {
            addCriterion("device_log_id <", value, "deviceLogId");
            return (Criteria) this;
        }

        public Criteria andDeviceLogIdLessThanOrEqualTo(Integer value) {
            addCriterion("device_log_id <=", value, "deviceLogId");
            return (Criteria) this;
        }

        public Criteria andDeviceLogIdIn(List<Integer> values) {
            addCriterion("device_log_id in", values, "deviceLogId");
            return (Criteria) this;
        }

        public Criteria andDeviceLogIdNotIn(List<Integer> values) {
            addCriterion("device_log_id not in", values, "deviceLogId");
            return (Criteria) this;
        }

        public Criteria andDeviceLogIdBetween(Integer value1, Integer value2) {
            addCriterion("device_log_id between", value1, value2, "deviceLogId");
            return (Criteria) this;
        }

        public Criteria andDeviceLogIdNotBetween(Integer value1, Integer value2) {
            addCriterion("device_log_id not between", value1, value2, "deviceLogId");
            return (Criteria) this;
        }

        public Criteria andProductKeyIsNull() {
            addCriterion("product_key is null");
            return (Criteria) this;
        }

        public Criteria andProductKeyIsNotNull() {
            addCriterion("product_key is not null");
            return (Criteria) this;
        }

        public Criteria andProductKeyEqualTo(Integer value) {
            addCriterion("product_key =", value, "productKey");
            return (Criteria) this;
        }

        public Criteria andProductKeyNotEqualTo(Integer value) {
            addCriterion("product_key <>", value, "productKey");
            return (Criteria) this;
        }

        public Criteria andProductKeyGreaterThan(Integer value) {
            addCriterion("product_key >", value, "productKey");
            return (Criteria) this;
        }

        public Criteria andProductKeyGreaterThanOrEqualTo(Integer value) {
            addCriterion("product_key >=", value, "productKey");
            return (Criteria) this;
        }

        public Criteria andProductKeyLessThan(Integer value) {
            addCriterion("product_key <", value, "productKey");
            return (Criteria) this;
        }

        public Criteria andProductKeyLessThanOrEqualTo(Integer value) {
            addCriterion("product_key <=", value, "productKey");
            return (Criteria) this;
        }

        public Criteria andProductKeyIn(List<Integer> values) {
            addCriterion("product_key in", values, "productKey");
            return (Criteria) this;
        }

        public Criteria andProductKeyNotIn(List<Integer> values) {
            addCriterion("product_key not in", values, "productKey");
            return (Criteria) this;
        }

        public Criteria andProductKeyBetween(Integer value1, Integer value2) {
            addCriterion("product_key between", value1, value2, "productKey");
            return (Criteria) this;
        }

        public Criteria andProductKeyNotBetween(Integer value1, Integer value2) {
            addCriterion("product_key not between", value1, value2, "productKey");
            return (Criteria) this;
        }

        public Criteria andDeviceNameIsNull() {
            addCriterion("device_name is null");
            return (Criteria) this;
        }

        public Criteria andDeviceNameIsNotNull() {
            addCriterion("device_name is not null");
            return (Criteria) this;
        }

        public Criteria andDeviceNameEqualTo(String value) {
            addCriterion("device_name =", value, "deviceName");
            return (Criteria) this;
        }

        public Criteria andDeviceNameNotEqualTo(String value) {
            addCriterion("device_name <>", value, "deviceName");
            return (Criteria) this;
        }

        public Criteria andDeviceNameGreaterThan(String value) {
            addCriterion("device_name >", value, "deviceName");
            return (Criteria) this;
        }

        public Criteria andDeviceNameGreaterThanOrEqualTo(String value) {
            addCriterion("device_name >=", value, "deviceName");
            return (Criteria) this;
        }

        public Criteria andDeviceNameLessThan(String value) {
            addCriterion("device_name <", value, "deviceName");
            return (Criteria) this;
        }

        public Criteria andDeviceNameLessThanOrEqualTo(String value) {
            addCriterion("device_name <=", value, "deviceName");
            return (Criteria) this;
        }

        public Criteria andDeviceNameLike(String value) {
            addCriterion("device_name like", value, "deviceName");
            return (Criteria) this;
        }

        public Criteria andDeviceNameNotLike(String value) {
            addCriterion("device_name not like", value, "deviceName");
            return (Criteria) this;
        }

        public Criteria andDeviceNameIn(List<String> values) {
            addCriterion("device_name in", values, "deviceName");
            return (Criteria) this;
        }

        public Criteria andDeviceNameNotIn(List<String> values) {
            addCriterion("device_name not in", values, "deviceName");
            return (Criteria) this;
        }

        public Criteria andDeviceNameBetween(String value1, String value2) {
            addCriterion("device_name between", value1, value2, "deviceName");
            return (Criteria) this;
        }

        public Criteria andDeviceNameNotBetween(String value1, String value2) {
            addCriterion("device_name not between", value1, value2, "deviceName");
            return (Criteria) this;
        }

        public Criteria andDeviceTripleIdIsNull() {
            addCriterion("device_triple_id is null");
            return (Criteria) this;
        }

        public Criteria andDeviceTripleIdIsNotNull() {
            addCriterion("device_triple_id is not null");
            return (Criteria) this;
        }

        public Criteria andDeviceTripleIdEqualTo(Integer value) {
            addCriterion("device_triple_id =", value, "deviceTripleId");
            return (Criteria) this;
        }

        public Criteria andDeviceTripleIdNotEqualTo(Integer value) {
            addCriterion("device_triple_id <>", value, "deviceTripleId");
            return (Criteria) this;
        }

        public Criteria andDeviceTripleIdGreaterThan(Integer value) {
            addCriterion("device_triple_id >", value, "deviceTripleId");
            return (Criteria) this;
        }

        public Criteria andDeviceTripleIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("device_triple_id >=", value, "deviceTripleId");
            return (Criteria) this;
        }

        public Criteria andDeviceTripleIdLessThan(Integer value) {
            addCriterion("device_triple_id <", value, "deviceTripleId");
            return (Criteria) this;
        }

        public Criteria andDeviceTripleIdLessThanOrEqualTo(Integer value) {
            addCriterion("device_triple_id <=", value, "deviceTripleId");
            return (Criteria) this;
        }

        public Criteria andDeviceTripleIdIn(List<Integer> values) {
            addCriterion("device_triple_id in", values, "deviceTripleId");
            return (Criteria) this;
        }

        public Criteria andDeviceTripleIdNotIn(List<Integer> values) {
            addCriterion("device_triple_id not in", values, "deviceTripleId");
            return (Criteria) this;
        }

        public Criteria andDeviceTripleIdBetween(Integer value1, Integer value2) {
            addCriterion("device_triple_id between", value1, value2, "deviceTripleId");
            return (Criteria) this;
        }

        public Criteria andDeviceTripleIdNotBetween(Integer value1, Integer value2) {
            addCriterion("device_triple_id not between", value1, value2, "deviceTripleId");
            return (Criteria) this;
        }

        public Criteria andDeviceIpIsNull() {
            addCriterion("device_ip is null");
            return (Criteria) this;
        }

        public Criteria andDeviceIpIsNotNull() {
            addCriterion("device_ip is not null");
            return (Criteria) this;
        }

        public Criteria andDeviceIpEqualTo(String value) {
            addCriterion("device_ip =", value, "deviceIp");
            return (Criteria) this;
        }

        public Criteria andDeviceIpNotEqualTo(String value) {
            addCriterion("device_ip <>", value, "deviceIp");
            return (Criteria) this;
        }

        public Criteria andDeviceIpGreaterThan(String value) {
            addCriterion("device_ip >", value, "deviceIp");
            return (Criteria) this;
        }

        public Criteria andDeviceIpGreaterThanOrEqualTo(String value) {
            addCriterion("device_ip >=", value, "deviceIp");
            return (Criteria) this;
        }

        public Criteria andDeviceIpLessThan(String value) {
            addCriterion("device_ip <", value, "deviceIp");
            return (Criteria) this;
        }

        public Criteria andDeviceIpLessThanOrEqualTo(String value) {
            addCriterion("device_ip <=", value, "deviceIp");
            return (Criteria) this;
        }

        public Criteria andDeviceIpLike(String value) {
            addCriterion("device_ip like", value, "deviceIp");
            return (Criteria) this;
        }

        public Criteria andDeviceIpNotLike(String value) {
            addCriterion("device_ip not like", value, "deviceIp");
            return (Criteria) this;
        }

        public Criteria andDeviceIpIn(List<String> values) {
            addCriterion("device_ip in", values, "deviceIp");
            return (Criteria) this;
        }

        public Criteria andDeviceIpNotIn(List<String> values) {
            addCriterion("device_ip not in", values, "deviceIp");
            return (Criteria) this;
        }

        public Criteria andDeviceIpBetween(String value1, String value2) {
            addCriterion("device_ip between", value1, value2, "deviceIp");
            return (Criteria) this;
        }

        public Criteria andDeviceIpNotBetween(String value1, String value2) {
            addCriterion("device_ip not between", value1, value2, "deviceIp");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Integer value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Integer value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Integer value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Integer value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Integer> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Integer> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Integer value1, Integer value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andCreatTimeIsNull() {
            addCriterion("creat_time is null");
            return (Criteria) this;
        }

        public Criteria andCreatTimeIsNotNull() {
            addCriterion("creat_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreatTimeEqualTo(Date value) {
            addCriterion("creat_time =", value, "creatTime");
            return (Criteria) this;
        }

        public Criteria andCreatTimeNotEqualTo(Date value) {
            addCriterion("creat_time <>", value, "creatTime");
            return (Criteria) this;
        }

        public Criteria andCreatTimeGreaterThan(Date value) {
            addCriterion("creat_time >", value, "creatTime");
            return (Criteria) this;
        }

        public Criteria andCreatTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("creat_time >=", value, "creatTime");
            return (Criteria) this;
        }

        public Criteria andCreatTimeLessThan(Date value) {
            addCriterion("creat_time <", value, "creatTime");
            return (Criteria) this;
        }

        public Criteria andCreatTimeLessThanOrEqualTo(Date value) {
            addCriterion("creat_time <=", value, "creatTime");
            return (Criteria) this;
        }

        public Criteria andCreatTimeIn(List<Date> values) {
            addCriterion("creat_time in", values, "creatTime");
            return (Criteria) this;
        }

        public Criteria andCreatTimeNotIn(List<Date> values) {
            addCriterion("creat_time not in", values, "creatTime");
            return (Criteria) this;
        }

        public Criteria andCreatTimeBetween(Date value1, Date value2) {
            addCriterion("creat_time between", value1, value2, "creatTime");
            return (Criteria) this;
        }

        public Criteria andCreatTimeNotBetween(Date value1, Date value2) {
            addCriterion("creat_time not between", value1, value2, "creatTime");
            return (Criteria) this;
        }

        public Criteria andPostTopicIsNull() {
            addCriterion("post_topic is null");
            return (Criteria) this;
        }

        public Criteria andPostTopicIsNotNull() {
            addCriterion("post_topic is not null");
            return (Criteria) this;
        }

        public Criteria andPostTopicEqualTo(String value) {
            addCriterion("post_topic =", value, "postTopic");
            return (Criteria) this;
        }

        public Criteria andPostTopicNotEqualTo(String value) {
            addCriterion("post_topic <>", value, "postTopic");
            return (Criteria) this;
        }

        public Criteria andPostTopicGreaterThan(String value) {
            addCriterion("post_topic >", value, "postTopic");
            return (Criteria) this;
        }

        public Criteria andPostTopicGreaterThanOrEqualTo(String value) {
            addCriterion("post_topic >=", value, "postTopic");
            return (Criteria) this;
        }

        public Criteria andPostTopicLessThan(String value) {
            addCriterion("post_topic <", value, "postTopic");
            return (Criteria) this;
        }

        public Criteria andPostTopicLessThanOrEqualTo(String value) {
            addCriterion("post_topic <=", value, "postTopic");
            return (Criteria) this;
        }

        public Criteria andPostTopicLike(String value) {
            addCriterion("post_topic like", value, "postTopic");
            return (Criteria) this;
        }

        public Criteria andPostTopicNotLike(String value) {
            addCriterion("post_topic not like", value, "postTopic");
            return (Criteria) this;
        }

        public Criteria andPostTopicIn(List<String> values) {
            addCriterion("post_topic in", values, "postTopic");
            return (Criteria) this;
        }

        public Criteria andPostTopicNotIn(List<String> values) {
            addCriterion("post_topic not in", values, "postTopic");
            return (Criteria) this;
        }

        public Criteria andPostTopicBetween(String value1, String value2) {
            addCriterion("post_topic between", value1, value2, "postTopic");
            return (Criteria) this;
        }

        public Criteria andPostTopicNotBetween(String value1, String value2) {
            addCriterion("post_topic not between", value1, value2, "postTopic");
            return (Criteria) this;
        }

        public Criteria andDescIsNull() {
            addCriterion("desc is null");
            return (Criteria) this;
        }

        public Criteria andDescIsNotNull() {
            addCriterion("desc is not null");
            return (Criteria) this;
        }

        public Criteria andDescEqualTo(String value) {
            addCriterion("desc =", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescNotEqualTo(String value) {
            addCriterion("desc <>", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescGreaterThan(String value) {
            addCriterion("desc >", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescGreaterThanOrEqualTo(String value) {
            addCriterion("desc >=", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescLessThan(String value) {
            addCriterion("desc <", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescLessThanOrEqualTo(String value) {
            addCriterion("desc <=", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescLike(String value) {
            addCriterion("desc like", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescNotLike(String value) {
            addCriterion("desc not like", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescIn(List<String> values) {
            addCriterion("desc in", values, "desc");
            return (Criteria) this;
        }

        public Criteria andDescNotIn(List<String> values) {
            addCriterion("desc not in", values, "desc");
            return (Criteria) this;
        }

        public Criteria andDescBetween(String value1, String value2) {
            addCriterion("desc between", value1, value2, "desc");
            return (Criteria) this;
        }

        public Criteria andDescNotBetween(String value1, String value2) {
            addCriterion("desc not between", value1, value2, "desc");
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