package com.medcaptain.file.entity.mysql;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FirmwareInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public FirmwareInfoExample() {
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

        public Criteria andProductKeyIsNull() {
            addCriterion("product_key is null");
            return (Criteria) this;
        }

        public Criteria andProductKeyIsNotNull() {
            addCriterion("product_key is not null");
            return (Criteria) this;
        }

        public Criteria andProductKeyEqualTo(String value) {
            addCriterion("product_key =", value, "productKey");
            return (Criteria) this;
        }

        public Criteria andProductKeyNotEqualTo(String value) {
            addCriterion("product_key <>", value, "productKey");
            return (Criteria) this;
        }

        public Criteria andProductKeyGreaterThan(String value) {
            addCriterion("product_key >", value, "productKey");
            return (Criteria) this;
        }

        public Criteria andProductKeyGreaterThanOrEqualTo(String value) {
            addCriterion("product_key >=", value, "productKey");
            return (Criteria) this;
        }

        public Criteria andProductKeyLessThan(String value) {
            addCriterion("product_key <", value, "productKey");
            return (Criteria) this;
        }

        public Criteria andProductKeyLessThanOrEqualTo(String value) {
            addCriterion("product_key <=", value, "productKey");
            return (Criteria) this;
        }

        public Criteria andProductKeyLike(String value) {
            addCriterion("product_key like", value, "productKey");
            return (Criteria) this;
        }

        public Criteria andProductKeyNotLike(String value) {
            addCriterion("product_key not like", value, "productKey");
            return (Criteria) this;
        }

        public Criteria andProductKeyIn(List<String> values) {
            addCriterion("product_key in", values, "productKey");
            return (Criteria) this;
        }

        public Criteria andProductKeyNotIn(List<String> values) {
            addCriterion("product_key not in", values, "productKey");
            return (Criteria) this;
        }

        public Criteria andProductKeyBetween(String value1, String value2) {
            addCriterion("product_key between", value1, value2, "productKey");
            return (Criteria) this;
        }

        public Criteria andProductKeyNotBetween(String value1, String value2) {
            addCriterion("product_key not between", value1, value2, "productKey");
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

        public Criteria andDpsitPathIsNull() {
            addCriterion("dpsit_path is null");
            return (Criteria) this;
        }

        public Criteria andDpsitPathIsNotNull() {
            addCriterion("dpsit_path is not null");
            return (Criteria) this;
        }

        public Criteria andDpsitPathEqualTo(String value) {
            addCriterion("dpsit_path =", value, "dpsitPath");
            return (Criteria) this;
        }

        public Criteria andDpsitPathNotEqualTo(String value) {
            addCriterion("dpsit_path <>", value, "dpsitPath");
            return (Criteria) this;
        }

        public Criteria andDpsitPathGreaterThan(String value) {
            addCriterion("dpsit_path >", value, "dpsitPath");
            return (Criteria) this;
        }

        public Criteria andDpsitPathGreaterThanOrEqualTo(String value) {
            addCriterion("dpsit_path >=", value, "dpsitPath");
            return (Criteria) this;
        }

        public Criteria andDpsitPathLessThan(String value) {
            addCriterion("dpsit_path <", value, "dpsitPath");
            return (Criteria) this;
        }

        public Criteria andDpsitPathLessThanOrEqualTo(String value) {
            addCriterion("dpsit_path <=", value, "dpsitPath");
            return (Criteria) this;
        }

        public Criteria andDpsitPathLike(String value) {
            addCriterion("dpsit_path like", value, "dpsitPath");
            return (Criteria) this;
        }

        public Criteria andDpsitPathNotLike(String value) {
            addCriterion("dpsit_path not like", value, "dpsitPath");
            return (Criteria) this;
        }

        public Criteria andDpsitPathIn(List<String> values) {
            addCriterion("dpsit_path in", values, "dpsitPath");
            return (Criteria) this;
        }

        public Criteria andDpsitPathNotIn(List<String> values) {
            addCriterion("dpsit_path not in", values, "dpsitPath");
            return (Criteria) this;
        }

        public Criteria andDpsitPathBetween(String value1, String value2) {
            addCriterion("dpsit_path between", value1, value2, "dpsitPath");
            return (Criteria) this;
        }

        public Criteria andDpsitPathNotBetween(String value1, String value2) {
            addCriterion("dpsit_path not between", value1, value2, "dpsitPath");
            return (Criteria) this;
        }

        public Criteria andMd5IsNull() {
            addCriterion("md5 is null");
            return (Criteria) this;
        }

        public Criteria andMd5IsNotNull() {
            addCriterion("md5 is not null");
            return (Criteria) this;
        }

        public Criteria andMd5EqualTo(String value) {
            addCriterion("md5 =", value, "md5");
            return (Criteria) this;
        }

        public Criteria andMd5NotEqualTo(String value) {
            addCriterion("md5 <>", value, "md5");
            return (Criteria) this;
        }

        public Criteria andMd5GreaterThan(String value) {
            addCriterion("md5 >", value, "md5");
            return (Criteria) this;
        }

        public Criteria andMd5GreaterThanOrEqualTo(String value) {
            addCriterion("md5 >=", value, "md5");
            return (Criteria) this;
        }

        public Criteria andMd5LessThan(String value) {
            addCriterion("md5 <", value, "md5");
            return (Criteria) this;
        }

        public Criteria andMd5LessThanOrEqualTo(String value) {
            addCriterion("md5 <=", value, "md5");
            return (Criteria) this;
        }

        public Criteria andMd5Like(String value) {
            addCriterion("md5 like", value, "md5");
            return (Criteria) this;
        }

        public Criteria andMd5NotLike(String value) {
            addCriterion("md5 not like", value, "md5");
            return (Criteria) this;
        }

        public Criteria andMd5In(List<String> values) {
            addCriterion("md5 in", values, "md5");
            return (Criteria) this;
        }

        public Criteria andMd5NotIn(List<String> values) {
            addCriterion("md5 not in", values, "md5");
            return (Criteria) this;
        }

        public Criteria andMd5Between(String value1, String value2) {
            addCriterion("md5 between", value1, value2, "md5");
            return (Criteria) this;
        }

        public Criteria andMd5NotBetween(String value1, String value2) {
            addCriterion("md5 not between", value1, value2, "md5");
            return (Criteria) this;
        }

        public Criteria andFirmwareNameIsNull() {
            addCriterion("firmware_name is null");
            return (Criteria) this;
        }

        public Criteria andFirmwareNameIsNotNull() {
            addCriterion("firmware_name is not null");
            return (Criteria) this;
        }

        public Criteria andFirmwareNameEqualTo(String value) {
            addCriterion("firmware_name =", value, "firmwareName");
            return (Criteria) this;
        }

        public Criteria andFirmwareNameNotEqualTo(String value) {
            addCriterion("firmware_name <>", value, "firmwareName");
            return (Criteria) this;
        }

        public Criteria andFirmwareNameGreaterThan(String value) {
            addCriterion("firmware_name >", value, "firmwareName");
            return (Criteria) this;
        }

        public Criteria andFirmwareNameGreaterThanOrEqualTo(String value) {
            addCriterion("firmware_name >=", value, "firmwareName");
            return (Criteria) this;
        }

        public Criteria andFirmwareNameLessThan(String value) {
            addCriterion("firmware_name <", value, "firmwareName");
            return (Criteria) this;
        }

        public Criteria andFirmwareNameLessThanOrEqualTo(String value) {
            addCriterion("firmware_name <=", value, "firmwareName");
            return (Criteria) this;
        }

        public Criteria andFirmwareNameLike(String value) {
            addCriterion("firmware_name like", value, "firmwareName");
            return (Criteria) this;
        }

        public Criteria andFirmwareNameNotLike(String value) {
            addCriterion("firmware_name not like", value, "firmwareName");
            return (Criteria) this;
        }

        public Criteria andFirmwareNameIn(List<String> values) {
            addCriterion("firmware_name in", values, "firmwareName");
            return (Criteria) this;
        }

        public Criteria andFirmwareNameNotIn(List<String> values) {
            addCriterion("firmware_name not in", values, "firmwareName");
            return (Criteria) this;
        }

        public Criteria andFirmwareNameBetween(String value1, String value2) {
            addCriterion("firmware_name between", value1, value2, "firmwareName");
            return (Criteria) this;
        }

        public Criteria andFirmwareNameNotBetween(String value1, String value2) {
            addCriterion("firmware_name not between", value1, value2, "firmwareName");
            return (Criteria) this;
        }

        public Criteria andFirmwareSizeIsNull() {
            addCriterion("firmware_size is null");
            return (Criteria) this;
        }

        public Criteria andFirmwareSizeIsNotNull() {
            addCriterion("firmware_size is not null");
            return (Criteria) this;
        }

        public Criteria andFirmwareSizeEqualTo(Integer value) {
            addCriterion("firmware_size =", value, "firmwareSize");
            return (Criteria) this;
        }

        public Criteria andFirmwareSizeNotEqualTo(Integer value) {
            addCriterion("firmware_size <>", value, "firmwareSize");
            return (Criteria) this;
        }

        public Criteria andFirmwareSizeGreaterThan(Integer value) {
            addCriterion("firmware_size >", value, "firmwareSize");
            return (Criteria) this;
        }

        public Criteria andFirmwareSizeGreaterThanOrEqualTo(Integer value) {
            addCriterion("firmware_size >=", value, "firmwareSize");
            return (Criteria) this;
        }

        public Criteria andFirmwareSizeLessThan(Integer value) {
            addCriterion("firmware_size <", value, "firmwareSize");
            return (Criteria) this;
        }

        public Criteria andFirmwareSizeLessThanOrEqualTo(Integer value) {
            addCriterion("firmware_size <=", value, "firmwareSize");
            return (Criteria) this;
        }

        public Criteria andFirmwareSizeIn(List<Integer> values) {
            addCriterion("firmware_size in", values, "firmwareSize");
            return (Criteria) this;
        }

        public Criteria andFirmwareSizeNotIn(List<Integer> values) {
            addCriterion("firmware_size not in", values, "firmwareSize");
            return (Criteria) this;
        }

        public Criteria andFirmwareSizeBetween(Integer value1, Integer value2) {
            addCriterion("firmware_size between", value1, value2, "firmwareSize");
            return (Criteria) this;
        }

        public Criteria andFirmwareSizeNotBetween(Integer value1, Integer value2) {
            addCriterion("firmware_size not between", value1, value2, "firmwareSize");
            return (Criteria) this;
        }

        public Criteria andFirmwareVersionIsNull() {
            addCriterion("firmware_version is null");
            return (Criteria) this;
        }

        public Criteria andFirmwareVersionIsNotNull() {
            addCriterion("firmware_version is not null");
            return (Criteria) this;
        }

        public Criteria andFirmwareVersionEqualTo(String value) {
            addCriterion("firmware_version =", value, "firmwareVersion");
            return (Criteria) this;
        }

        public Criteria andFirmwareVersionNotEqualTo(String value) {
            addCriterion("firmware_version <>", value, "firmwareVersion");
            return (Criteria) this;
        }

        public Criteria andFirmwareVersionGreaterThan(String value) {
            addCriterion("firmware_version >", value, "firmwareVersion");
            return (Criteria) this;
        }

        public Criteria andFirmwareVersionGreaterThanOrEqualTo(String value) {
            addCriterion("firmware_version >=", value, "firmwareVersion");
            return (Criteria) this;
        }

        public Criteria andFirmwareVersionLessThan(String value) {
            addCriterion("firmware_version <", value, "firmwareVersion");
            return (Criteria) this;
        }

        public Criteria andFirmwareVersionLessThanOrEqualTo(String value) {
            addCriterion("firmware_version <=", value, "firmwareVersion");
            return (Criteria) this;
        }

        public Criteria andFirmwareVersionLike(String value) {
            addCriterion("firmware_version like", value, "firmwareVersion");
            return (Criteria) this;
        }

        public Criteria andFirmwareVersionNotLike(String value) {
            addCriterion("firmware_version not like", value, "firmwareVersion");
            return (Criteria) this;
        }

        public Criteria andFirmwareVersionIn(List<String> values) {
            addCriterion("firmware_version in", values, "firmwareVersion");
            return (Criteria) this;
        }

        public Criteria andFirmwareVersionNotIn(List<String> values) {
            addCriterion("firmware_version not in", values, "firmwareVersion");
            return (Criteria) this;
        }

        public Criteria andFirmwareVersionBetween(String value1, String value2) {
            addCriterion("firmware_version between", value1, value2, "firmwareVersion");
            return (Criteria) this;
        }

        public Criteria andFirmwareVersionNotBetween(String value1, String value2) {
            addCriterion("firmware_version not between", value1, value2, "firmwareVersion");
            return (Criteria) this;
        }

        public Criteria andIsVerifiedIsNull() {
            addCriterion("is_verified is null");
            return (Criteria) this;
        }

        public Criteria andIsVerifiedIsNotNull() {
            addCriterion("is_verified is not null");
            return (Criteria) this;
        }

        public Criteria andIsVerifiedEqualTo(Integer value) {
            addCriterion("is_verified =", value, "isVerified");
            return (Criteria) this;
        }

        public Criteria andIsVerifiedNotEqualTo(Integer value) {
            addCriterion("is_verified <>", value, "isVerified");
            return (Criteria) this;
        }

        public Criteria andIsVerifiedGreaterThan(Integer value) {
            addCriterion("is_verified >", value, "isVerified");
            return (Criteria) this;
        }

        public Criteria andIsVerifiedGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_verified >=", value, "isVerified");
            return (Criteria) this;
        }

        public Criteria andIsVerifiedLessThan(Integer value) {
            addCriterion("is_verified <", value, "isVerified");
            return (Criteria) this;
        }

        public Criteria andIsVerifiedLessThanOrEqualTo(Integer value) {
            addCriterion("is_verified <=", value, "isVerified");
            return (Criteria) this;
        }

        public Criteria andIsVerifiedIn(List<Integer> values) {
            addCriterion("is_verified in", values, "isVerified");
            return (Criteria) this;
        }

        public Criteria andIsVerifiedNotIn(List<Integer> values) {
            addCriterion("is_verified not in", values, "isVerified");
            return (Criteria) this;
        }

        public Criteria andIsVerifiedBetween(Integer value1, Integer value2) {
            addCriterion("is_verified between", value1, value2, "isVerified");
            return (Criteria) this;
        }

        public Criteria andIsVerifiedNotBetween(Integer value1, Integer value2) {
            addCriterion("is_verified not between", value1, value2, "isVerified");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
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

        public Criteria andIsDelIsNull() {
            addCriterion("is_del is null");
            return (Criteria) this;
        }

        public Criteria andIsDelIsNotNull() {
            addCriterion("is_del is not null");
            return (Criteria) this;
        }

        public Criteria andIsDelEqualTo(Integer value) {
            addCriterion("is_del =", value, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelNotEqualTo(Integer value) {
            addCriterion("is_del <>", value, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelGreaterThan(Integer value) {
            addCriterion("is_del >", value, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_del >=", value, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelLessThan(Integer value) {
            addCriterion("is_del <", value, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelLessThanOrEqualTo(Integer value) {
            addCriterion("is_del <=", value, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelIn(List<Integer> values) {
            addCriterion("is_del in", values, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelNotIn(List<Integer> values) {
            addCriterion("is_del not in", values, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelBetween(Integer value1, Integer value2) {
            addCriterion("is_del between", value1, value2, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelNotBetween(Integer value1, Integer value2) {
            addCriterion("is_del not between", value1, value2, "isDel");
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