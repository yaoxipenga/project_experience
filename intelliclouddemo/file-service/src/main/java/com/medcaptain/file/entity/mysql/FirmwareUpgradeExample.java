package com.medcaptain.file.entity.mysql;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FirmwareUpgradeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public FirmwareUpgradeExample() {
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

        public Criteria andFirmwareIdIsNull() {
            addCriterion("firmware_id is null");
            return (Criteria) this;
        }

        public Criteria andFirmwareIdIsNotNull() {
            addCriterion("firmware_id is not null");
            return (Criteria) this;
        }

        public Criteria andFirmwareIdEqualTo(Integer value) {
            addCriterion("firmware_id =", value, "firmwareId");
            return (Criteria) this;
        }

        public Criteria andFirmwareIdNotEqualTo(Integer value) {
            addCriterion("firmware_id <>", value, "firmwareId");
            return (Criteria) this;
        }

        public Criteria andFirmwareIdGreaterThan(Integer value) {
            addCriterion("firmware_id >", value, "firmwareId");
            return (Criteria) this;
        }

        public Criteria andFirmwareIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("firmware_id >=", value, "firmwareId");
            return (Criteria) this;
        }

        public Criteria andFirmwareIdLessThan(Integer value) {
            addCriterion("firmware_id <", value, "firmwareId");
            return (Criteria) this;
        }

        public Criteria andFirmwareIdLessThanOrEqualTo(Integer value) {
            addCriterion("firmware_id <=", value, "firmwareId");
            return (Criteria) this;
        }

        public Criteria andFirmwareIdIn(List<Integer> values) {
            addCriterion("firmware_id in", values, "firmwareId");
            return (Criteria) this;
        }

        public Criteria andFirmwareIdNotIn(List<Integer> values) {
            addCriterion("firmware_id not in", values, "firmwareId");
            return (Criteria) this;
        }

        public Criteria andFirmwareIdBetween(Integer value1, Integer value2) {
            addCriterion("firmware_id between", value1, value2, "firmwareId");
            return (Criteria) this;
        }

        public Criteria andFirmwareIdNotBetween(Integer value1, Integer value2) {
            addCriterion("firmware_id not between", value1, value2, "firmwareId");
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

        public Criteria andDeviceFirmwareVersionIsNull() {
            addCriterion("device_firmware_version is null");
            return (Criteria) this;
        }

        public Criteria andDeviceFirmwareVersionIsNotNull() {
            addCriterion("device_firmware_version is not null");
            return (Criteria) this;
        }

        public Criteria andDeviceFirmwareVersionEqualTo(String value) {
            addCriterion("device_firmware_version =", value, "deviceFirmwareVersion");
            return (Criteria) this;
        }

        public Criteria andDeviceFirmwareVersionNotEqualTo(String value) {
            addCriterion("device_firmware_version <>", value, "deviceFirmwareVersion");
            return (Criteria) this;
        }

        public Criteria andDeviceFirmwareVersionGreaterThan(String value) {
            addCriterion("device_firmware_version >", value, "deviceFirmwareVersion");
            return (Criteria) this;
        }

        public Criteria andDeviceFirmwareVersionGreaterThanOrEqualTo(String value) {
            addCriterion("device_firmware_version >=", value, "deviceFirmwareVersion");
            return (Criteria) this;
        }

        public Criteria andDeviceFirmwareVersionLessThan(String value) {
            addCriterion("device_firmware_version <", value, "deviceFirmwareVersion");
            return (Criteria) this;
        }

        public Criteria andDeviceFirmwareVersionLessThanOrEqualTo(String value) {
            addCriterion("device_firmware_version <=", value, "deviceFirmwareVersion");
            return (Criteria) this;
        }

        public Criteria andDeviceFirmwareVersionLike(String value) {
            addCriterion("device_firmware_version like", value, "deviceFirmwareVersion");
            return (Criteria) this;
        }

        public Criteria andDeviceFirmwareVersionNotLike(String value) {
            addCriterion("device_firmware_version not like", value, "deviceFirmwareVersion");
            return (Criteria) this;
        }

        public Criteria andDeviceFirmwareVersionIn(List<String> values) {
            addCriterion("device_firmware_version in", values, "deviceFirmwareVersion");
            return (Criteria) this;
        }

        public Criteria andDeviceFirmwareVersionNotIn(List<String> values) {
            addCriterion("device_firmware_version not in", values, "deviceFirmwareVersion");
            return (Criteria) this;
        }

        public Criteria andDeviceFirmwareVersionBetween(String value1, String value2) {
            addCriterion("device_firmware_version between", value1, value2, "deviceFirmwareVersion");
            return (Criteria) this;
        }

        public Criteria andDeviceFirmwareVersionNotBetween(String value1, String value2) {
            addCriterion("device_firmware_version not between", value1, value2, "deviceFirmwareVersion");
            return (Criteria) this;
        }

        public Criteria andUpgradeStatusIsNull() {
            addCriterion("upgrade_status is null");
            return (Criteria) this;
        }

        public Criteria andUpgradeStatusIsNotNull() {
            addCriterion("upgrade_status is not null");
            return (Criteria) this;
        }

        public Criteria andUpgradeStatusEqualTo(Integer value) {
            addCriterion("upgrade_status =", value, "upgradeStatus");
            return (Criteria) this;
        }

        public Criteria andUpgradeStatusNotEqualTo(Integer value) {
            addCriterion("upgrade_status <>", value, "upgradeStatus");
            return (Criteria) this;
        }

        public Criteria andUpgradeStatusGreaterThan(Integer value) {
            addCriterion("upgrade_status >", value, "upgradeStatus");
            return (Criteria) this;
        }

        public Criteria andUpgradeStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("upgrade_status >=", value, "upgradeStatus");
            return (Criteria) this;
        }

        public Criteria andUpgradeStatusLessThan(Integer value) {
            addCriterion("upgrade_status <", value, "upgradeStatus");
            return (Criteria) this;
        }

        public Criteria andUpgradeStatusLessThanOrEqualTo(Integer value) {
            addCriterion("upgrade_status <=", value, "upgradeStatus");
            return (Criteria) this;
        }

        public Criteria andUpgradeStatusIn(List<Integer> values) {
            addCriterion("upgrade_status in", values, "upgradeStatus");
            return (Criteria) this;
        }

        public Criteria andUpgradeStatusNotIn(List<Integer> values) {
            addCriterion("upgrade_status not in", values, "upgradeStatus");
            return (Criteria) this;
        }

        public Criteria andUpgradeStatusBetween(Integer value1, Integer value2) {
            addCriterion("upgrade_status between", value1, value2, "upgradeStatus");
            return (Criteria) this;
        }

        public Criteria andUpgradeStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("upgrade_status not between", value1, value2, "upgradeStatus");
            return (Criteria) this;
        }

        public Criteria andUpgradeProgressIsNull() {
            addCriterion("upgrade_progress is null");
            return (Criteria) this;
        }

        public Criteria andUpgradeProgressIsNotNull() {
            addCriterion("upgrade_progress is not null");
            return (Criteria) this;
        }

        public Criteria andUpgradeProgressEqualTo(Integer value) {
            addCriterion("upgrade_progress =", value, "upgradeProgress");
            return (Criteria) this;
        }

        public Criteria andUpgradeProgressNotEqualTo(Integer value) {
            addCriterion("upgrade_progress <>", value, "upgradeProgress");
            return (Criteria) this;
        }

        public Criteria andUpgradeProgressGreaterThan(Integer value) {
            addCriterion("upgrade_progress >", value, "upgradeProgress");
            return (Criteria) this;
        }

        public Criteria andUpgradeProgressGreaterThanOrEqualTo(Integer value) {
            addCriterion("upgrade_progress >=", value, "upgradeProgress");
            return (Criteria) this;
        }

        public Criteria andUpgradeProgressLessThan(Integer value) {
            addCriterion("upgrade_progress <", value, "upgradeProgress");
            return (Criteria) this;
        }

        public Criteria andUpgradeProgressLessThanOrEqualTo(Integer value) {
            addCriterion("upgrade_progress <=", value, "upgradeProgress");
            return (Criteria) this;
        }

        public Criteria andUpgradeProgressIn(List<Integer> values) {
            addCriterion("upgrade_progress in", values, "upgradeProgress");
            return (Criteria) this;
        }

        public Criteria andUpgradeProgressNotIn(List<Integer> values) {
            addCriterion("upgrade_progress not in", values, "upgradeProgress");
            return (Criteria) this;
        }

        public Criteria andUpgradeProgressBetween(Integer value1, Integer value2) {
            addCriterion("upgrade_progress between", value1, value2, "upgradeProgress");
            return (Criteria) this;
        }

        public Criteria andUpgradeProgressNotBetween(Integer value1, Integer value2) {
            addCriterion("upgrade_progress not between", value1, value2, "upgradeProgress");
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

        public Criteria andPostTimeIsNull() {
            addCriterion("post_time is null");
            return (Criteria) this;
        }

        public Criteria andPostTimeIsNotNull() {
            addCriterion("post_time is not null");
            return (Criteria) this;
        }

        public Criteria andPostTimeEqualTo(Date value) {
            addCriterion("post_time =", value, "postTime");
            return (Criteria) this;
        }

        public Criteria andPostTimeNotEqualTo(Date value) {
            addCriterion("post_time <>", value, "postTime");
            return (Criteria) this;
        }

        public Criteria andPostTimeGreaterThan(Date value) {
            addCriterion("post_time >", value, "postTime");
            return (Criteria) this;
        }

        public Criteria andPostTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("post_time >=", value, "postTime");
            return (Criteria) this;
        }

        public Criteria andPostTimeLessThan(Date value) {
            addCriterion("post_time <", value, "postTime");
            return (Criteria) this;
        }

        public Criteria andPostTimeLessThanOrEqualTo(Date value) {
            addCriterion("post_time <=", value, "postTime");
            return (Criteria) this;
        }

        public Criteria andPostTimeIn(List<Date> values) {
            addCriterion("post_time in", values, "postTime");
            return (Criteria) this;
        }

        public Criteria andPostTimeNotIn(List<Date> values) {
            addCriterion("post_time not in", values, "postTime");
            return (Criteria) this;
        }

        public Criteria andPostTimeBetween(Date value1, Date value2) {
            addCriterion("post_time between", value1, value2, "postTime");
            return (Criteria) this;
        }

        public Criteria andPostTimeNotBetween(Date value1, Date value2) {
            addCriterion("post_time not between", value1, value2, "postTime");
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