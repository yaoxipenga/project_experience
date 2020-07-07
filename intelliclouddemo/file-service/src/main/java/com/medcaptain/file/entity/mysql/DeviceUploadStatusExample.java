package com.medcaptain.file.entity.mysql;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DeviceUploadStatusExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DeviceUploadStatusExample() {
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

        public Criteria andFileMd5IsNull() {
            addCriterion("file_md5 is null");
            return (Criteria) this;
        }

        public Criteria andFileMd5IsNotNull() {
            addCriterion("file_md5 is not null");
            return (Criteria) this;
        }

        public Criteria andFileMd5EqualTo(String value) {
            addCriterion("file_md5 =", value, "fileMd5");
            return (Criteria) this;
        }

        public Criteria andFileMd5NotEqualTo(String value) {
            addCriterion("file_md5 <>", value, "fileMd5");
            return (Criteria) this;
        }

        public Criteria andFileMd5GreaterThan(String value) {
            addCriterion("file_md5 >", value, "fileMd5");
            return (Criteria) this;
        }

        public Criteria andFileMd5GreaterThanOrEqualTo(String value) {
            addCriterion("file_md5 >=", value, "fileMd5");
            return (Criteria) this;
        }

        public Criteria andFileMd5LessThan(String value) {
            addCriterion("file_md5 <", value, "fileMd5");
            return (Criteria) this;
        }

        public Criteria andFileMd5LessThanOrEqualTo(String value) {
            addCriterion("file_md5 <=", value, "fileMd5");
            return (Criteria) this;
        }

        public Criteria andFileMd5Like(String value) {
            addCriterion("file_md5 like", value, "fileMd5");
            return (Criteria) this;
        }

        public Criteria andFileMd5NotLike(String value) {
            addCriterion("file_md5 not like", value, "fileMd5");
            return (Criteria) this;
        }

        public Criteria andFileMd5In(List<String> values) {
            addCriterion("file_md5 in", values, "fileMd5");
            return (Criteria) this;
        }

        public Criteria andFileMd5NotIn(List<String> values) {
            addCriterion("file_md5 not in", values, "fileMd5");
            return (Criteria) this;
        }

        public Criteria andFileMd5Between(String value1, String value2) {
            addCriterion("file_md5 between", value1, value2, "fileMd5");
            return (Criteria) this;
        }

        public Criteria andFileMd5NotBetween(String value1, String value2) {
            addCriterion("file_md5 not between", value1, value2, "fileMd5");
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

        public Criteria andUploadStatusIsNull() {
            addCriterion("upload_status is null");
            return (Criteria) this;
        }

        public Criteria andUploadStatusIsNotNull() {
            addCriterion("upload_status is not null");
            return (Criteria) this;
        }

        public Criteria andUploadStatusEqualTo(String value) {
            addCriterion("upload_status =", value, "uploadStatus");
            return (Criteria) this;
        }

        public Criteria andUploadStatusNotEqualTo(String value) {
            addCriterion("upload_status <>", value, "uploadStatus");
            return (Criteria) this;
        }

        public Criteria andUploadStatusGreaterThan(String value) {
            addCriterion("upload_status >", value, "uploadStatus");
            return (Criteria) this;
        }

        public Criteria andUploadStatusGreaterThanOrEqualTo(String value) {
            addCriterion("upload_status >=", value, "uploadStatus");
            return (Criteria) this;
        }

        public Criteria andUploadStatusLessThan(String value) {
            addCriterion("upload_status <", value, "uploadStatus");
            return (Criteria) this;
        }

        public Criteria andUploadStatusLessThanOrEqualTo(String value) {
            addCriterion("upload_status <=", value, "uploadStatus");
            return (Criteria) this;
        }

        public Criteria andUploadStatusLike(String value) {
            addCriterion("upload_status like", value, "uploadStatus");
            return (Criteria) this;
        }

        public Criteria andUploadStatusNotLike(String value) {
            addCriterion("upload_status not like", value, "uploadStatus");
            return (Criteria) this;
        }

        public Criteria andUploadStatusIn(List<String> values) {
            addCriterion("upload_status in", values, "uploadStatus");
            return (Criteria) this;
        }

        public Criteria andUploadStatusNotIn(List<String> values) {
            addCriterion("upload_status not in", values, "uploadStatus");
            return (Criteria) this;
        }

        public Criteria andUploadStatusBetween(String value1, String value2) {
            addCriterion("upload_status between", value1, value2, "uploadStatus");
            return (Criteria) this;
        }

        public Criteria andUploadStatusNotBetween(String value1, String value2) {
            addCriterion("upload_status not between", value1, value2, "uploadStatus");
            return (Criteria) this;
        }

        public Criteria andUploadProgIsNull() {
            addCriterion("upload_prog is null");
            return (Criteria) this;
        }

        public Criteria andUploadProgIsNotNull() {
            addCriterion("upload_prog is not null");
            return (Criteria) this;
        }

        public Criteria andUploadProgEqualTo(String value) {
            addCriterion("upload_prog =", value, "uploadProg");
            return (Criteria) this;
        }

        public Criteria andUploadProgNotEqualTo(String value) {
            addCriterion("upload_prog <>", value, "uploadProg");
            return (Criteria) this;
        }

        public Criteria andUploadProgGreaterThan(String value) {
            addCriterion("upload_prog >", value, "uploadProg");
            return (Criteria) this;
        }

        public Criteria andUploadProgGreaterThanOrEqualTo(String value) {
            addCriterion("upload_prog >=", value, "uploadProg");
            return (Criteria) this;
        }

        public Criteria andUploadProgLessThan(String value) {
            addCriterion("upload_prog <", value, "uploadProg");
            return (Criteria) this;
        }

        public Criteria andUploadProgLessThanOrEqualTo(String value) {
            addCriterion("upload_prog <=", value, "uploadProg");
            return (Criteria) this;
        }

        public Criteria andUploadProgLike(String value) {
            addCriterion("upload_prog like", value, "uploadProg");
            return (Criteria) this;
        }

        public Criteria andUploadProgNotLike(String value) {
            addCriterion("upload_prog not like", value, "uploadProg");
            return (Criteria) this;
        }

        public Criteria andUploadProgIn(List<String> values) {
            addCriterion("upload_prog in", values, "uploadProg");
            return (Criteria) this;
        }

        public Criteria andUploadProgNotIn(List<String> values) {
            addCriterion("upload_prog not in", values, "uploadProg");
            return (Criteria) this;
        }

        public Criteria andUploadProgBetween(String value1, String value2) {
            addCriterion("upload_prog between", value1, value2, "uploadProg");
            return (Criteria) this;
        }

        public Criteria andUploadProgNotBetween(String value1, String value2) {
            addCriterion("upload_prog not between", value1, value2, "uploadProg");
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

        public Criteria andCreattimeIsNull() {
            addCriterion("creattime is null");
            return (Criteria) this;
        }

        public Criteria andCreattimeIsNotNull() {
            addCriterion("creattime is not null");
            return (Criteria) this;
        }

        public Criteria andCreattimeEqualTo(Date value) {
            addCriterion("creattime =", value, "creattime");
            return (Criteria) this;
        }

        public Criteria andCreattimeNotEqualTo(Date value) {
            addCriterion("creattime <>", value, "creattime");
            return (Criteria) this;
        }

        public Criteria andCreattimeGreaterThan(Date value) {
            addCriterion("creattime >", value, "creattime");
            return (Criteria) this;
        }

        public Criteria andCreattimeGreaterThanOrEqualTo(Date value) {
            addCriterion("creattime >=", value, "creattime");
            return (Criteria) this;
        }

        public Criteria andCreattimeLessThan(Date value) {
            addCriterion("creattime <", value, "creattime");
            return (Criteria) this;
        }

        public Criteria andCreattimeLessThanOrEqualTo(Date value) {
            addCriterion("creattime <=", value, "creattime");
            return (Criteria) this;
        }

        public Criteria andCreattimeIn(List<Date> values) {
            addCriterion("creattime in", values, "creattime");
            return (Criteria) this;
        }

        public Criteria andCreattimeNotIn(List<Date> values) {
            addCriterion("creattime not in", values, "creattime");
            return (Criteria) this;
        }

        public Criteria andCreattimeBetween(Date value1, Date value2) {
            addCriterion("creattime between", value1, value2, "creattime");
            return (Criteria) this;
        }

        public Criteria andCreattimeNotBetween(Date value1, Date value2) {
            addCriterion("creattime not between", value1, value2, "creattime");
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