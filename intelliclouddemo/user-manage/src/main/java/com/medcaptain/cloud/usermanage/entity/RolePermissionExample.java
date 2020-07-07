package com.medcaptain.cloud.usermanage.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色权限查询示例
 *
 * @author bingwen.ai
 */
@SuppressWarnings("ALL")
public class RolePermissionExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RolePermissionExample() {
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

        public Criteria andRoleIdIsNull() {
            addCriterion("role_id is null");
            return (Criteria) this;
        }

        public Criteria andRoleIdIsNotNull() {
            addCriterion("role_id is not null");
            return (Criteria) this;
        }

        public Criteria andRoleIdEqualTo(Integer value) {
            addCriterion("role_id =", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdNotEqualTo(Integer value) {
            addCriterion("role_id <>", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdGreaterThan(Integer value) {
            addCriterion("role_id >", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("role_id >=", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdLessThan(Integer value) {
            addCriterion("role_id <", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdLessThanOrEqualTo(Integer value) {
            addCriterion("role_id <=", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdIn(List<Integer> values) {
            addCriterion("role_id in", values, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdNotIn(List<Integer> values) {
            addCriterion("role_id not in", values, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdBetween(Integer value1, Integer value2) {
            addCriterion("role_id between", value1, value2, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdNotBetween(Integer value1, Integer value2) {
            addCriterion("role_id not between", value1, value2, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleNameIsNull() {
            addCriterion("role_name is null");
            return (Criteria) this;
        }

        public Criteria andRoleNameIsNotNull() {
            addCriterion("role_name is not null");
            return (Criteria) this;
        }

        public Criteria andRoleNameEqualTo(String value) {
            addCriterion("role_name =", value, "roleName");
            return (Criteria) this;
        }

        public Criteria andRoleNameNotEqualTo(String value) {
            addCriterion("role_name <>", value, "roleName");
            return (Criteria) this;
        }

        public Criteria andRoleNameGreaterThan(String value) {
            addCriterion("role_name >", value, "roleName");
            return (Criteria) this;
        }

        public Criteria andRoleNameGreaterThanOrEqualTo(String value) {
            addCriterion("role_name >=", value, "roleName");
            return (Criteria) this;
        }

        public Criteria andRoleNameLessThan(String value) {
            addCriterion("role_name <", value, "roleName");
            return (Criteria) this;
        }

        public Criteria andRoleNameLessThanOrEqualTo(String value) {
            addCriterion("role_name <=", value, "roleName");
            return (Criteria) this;
        }

        public Criteria andRoleNameLike(String value) {
            addCriterion("role_name like", value, "roleName");
            return (Criteria) this;
        }

        public Criteria andRoleNameNotLike(String value) {
            addCriterion("role_name not like", value, "roleName");
            return (Criteria) this;
        }

        public Criteria andRoleNameIn(List<String> values) {
            addCriterion("role_name in", values, "roleName");
            return (Criteria) this;
        }

        public Criteria andRoleNameNotIn(List<String> values) {
            addCriterion("role_name not in", values, "roleName");
            return (Criteria) this;
        }

        public Criteria andRoleNameBetween(String value1, String value2) {
            addCriterion("role_name between", value1, value2, "roleName");
            return (Criteria) this;
        }

        public Criteria andRoleNameNotBetween(String value1, String value2) {
            addCriterion("role_name not between", value1, value2, "roleName");
            return (Criteria) this;
        }

        public Criteria andRoleEnableIsNull() {
            addCriterion("role_enable is null");
            return (Criteria) this;
        }

        public Criteria andRoleEnableIsNotNull() {
            addCriterion("role_enable is not null");
            return (Criteria) this;
        }

        public Criteria andRoleEnableEqualTo(Byte value) {
            addCriterion("role_enable =", value, "roleEnable");
            return (Criteria) this;
        }

        public Criteria andRoleEnableNotEqualTo(Byte value) {
            addCriterion("role_enable <>", value, "roleEnable");
            return (Criteria) this;
        }

        public Criteria andRoleEnableGreaterThan(Byte value) {
            addCriterion("role_enable >", value, "roleEnable");
            return (Criteria) this;
        }

        public Criteria andRoleEnableGreaterThanOrEqualTo(Byte value) {
            addCriterion("role_enable >=", value, "roleEnable");
            return (Criteria) this;
        }

        public Criteria andRoleEnableLessThan(Byte value) {
            addCriterion("role_enable <", value, "roleEnable");
            return (Criteria) this;
        }

        public Criteria andRoleEnableLessThanOrEqualTo(Byte value) {
            addCriterion("role_enable <=", value, "roleEnable");
            return (Criteria) this;
        }

        public Criteria andRoleEnableIn(List<Byte> values) {
            addCriterion("role_enable in", values, "roleEnable");
            return (Criteria) this;
        }

        public Criteria andRoleEnableNotIn(List<Byte> values) {
            addCriterion("role_enable not in", values, "roleEnable");
            return (Criteria) this;
        }

        public Criteria andRoleEnableBetween(Byte value1, Byte value2) {
            addCriterion("role_enable between", value1, value2, "roleEnable");
            return (Criteria) this;
        }

        public Criteria andRoleEnableNotBetween(Byte value1, Byte value2) {
            addCriterion("role_enable not between", value1, value2, "roleEnable");
            return (Criteria) this;
        }

        public Criteria andRoleDeletedIsNull() {
            addCriterion("role_deleted is null");
            return (Criteria) this;
        }

        public Criteria andRoleDeletedIsNotNull() {
            addCriterion("role_deleted is not null");
            return (Criteria) this;
        }

        public Criteria andRoleDeletedEqualTo(Byte value) {
            addCriterion("role_deleted =", value, "roleDeleted");
            return (Criteria) this;
        }

        public Criteria andRoleDeletedNotEqualTo(Byte value) {
            addCriterion("role_deleted <>", value, "roleDeleted");
            return (Criteria) this;
        }

        public Criteria andRoleDeletedGreaterThan(Byte value) {
            addCriterion("role_deleted >", value, "roleDeleted");
            return (Criteria) this;
        }

        public Criteria andRoleDeletedGreaterThanOrEqualTo(Byte value) {
            addCriterion("role_deleted >=", value, "roleDeleted");
            return (Criteria) this;
        }

        public Criteria andRoleDeletedLessThan(Byte value) {
            addCriterion("role_deleted <", value, "roleDeleted");
            return (Criteria) this;
        }

        public Criteria andRoleDeletedLessThanOrEqualTo(Byte value) {
            addCriterion("role_deleted <=", value, "roleDeleted");
            return (Criteria) this;
        }

        public Criteria andRoleDeletedIn(List<Byte> values) {
            addCriterion("role_deleted in", values, "roleDeleted");
            return (Criteria) this;
        }

        public Criteria andRoleDeletedNotIn(List<Byte> values) {
            addCriterion("role_deleted not in", values, "roleDeleted");
            return (Criteria) this;
        }

        public Criteria andRoleDeletedBetween(Byte value1, Byte value2) {
            addCriterion("role_deleted between", value1, value2, "roleDeleted");
            return (Criteria) this;
        }

        public Criteria andRoleDeletedNotBetween(Byte value1, Byte value2) {
            addCriterion("role_deleted not between", value1, value2, "roleDeleted");
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

        public Criteria andFrontendResourceNameIsNull() {
            addCriterion("frontend_resource_name is null");
            return (Criteria) this;
        }

        public Criteria andFrontendResourceNameIsNotNull() {
            addCriterion("frontend_resource_name is not null");
            return (Criteria) this;
        }

        public Criteria andFrontendResourceNameEqualTo(String value) {
            addCriterion("frontend_resource_name =", value, "frontendResourceName");
            return (Criteria) this;
        }

        public Criteria andFrontendResourceNameNotEqualTo(String value) {
            addCriterion("frontend_resource_name <>", value, "frontendResourceName");
            return (Criteria) this;
        }

        public Criteria andFrontendResourceNameGreaterThan(String value) {
            addCriterion("frontend_resource_name >", value, "frontendResourceName");
            return (Criteria) this;
        }

        public Criteria andFrontendResourceNameGreaterThanOrEqualTo(String value) {
            addCriterion("frontend_resource_name >=", value, "frontendResourceName");
            return (Criteria) this;
        }

        public Criteria andFrontendResourceNameLessThan(String value) {
            addCriterion("frontend_resource_name <", value, "frontendResourceName");
            return (Criteria) this;
        }

        public Criteria andFrontendResourceNameLessThanOrEqualTo(String value) {
            addCriterion("frontend_resource_name <=", value, "frontendResourceName");
            return (Criteria) this;
        }

        public Criteria andFrontendResourceNameLike(String value) {
            addCriterion("frontend_resource_name like", value, "frontendResourceName");
            return (Criteria) this;
        }

        public Criteria andFrontendResourceNameNotLike(String value) {
            addCriterion("frontend_resource_name not like", value, "frontendResourceName");
            return (Criteria) this;
        }

        public Criteria andFrontendResourceNameIn(List<String> values) {
            addCriterion("frontend_resource_name in", values, "frontendResourceName");
            return (Criteria) this;
        }

        public Criteria andFrontendResourceNameNotIn(List<String> values) {
            addCriterion("frontend_resource_name not in", values, "frontendResourceName");
            return (Criteria) this;
        }

        public Criteria andFrontendResourceNameBetween(String value1, String value2) {
            addCriterion("frontend_resource_name between", value1, value2, "frontendResourceName");
            return (Criteria) this;
        }

        public Criteria andFrontendResourceNameNotBetween(String value1, String value2) {
            addCriterion("frontend_resource_name not between", value1, value2, "frontendResourceName");
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

        public Criteria andFrontendRemarkIsNull() {
            addCriterion("frontend_remark is null");
            return (Criteria) this;
        }

        public Criteria andFrontendRemarkIsNotNull() {
            addCriterion("frontend_remark is not null");
            return (Criteria) this;
        }

        public Criteria andFrontendRemarkEqualTo(String value) {
            addCriterion("frontend_remark =", value, "frontendRemark");
            return (Criteria) this;
        }

        public Criteria andFrontendRemarkNotEqualTo(String value) {
            addCriterion("frontend_remark <>", value, "frontendRemark");
            return (Criteria) this;
        }

        public Criteria andFrontendRemarkGreaterThan(String value) {
            addCriterion("frontend_remark >", value, "frontendRemark");
            return (Criteria) this;
        }

        public Criteria andFrontendRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("frontend_remark >=", value, "frontendRemark");
            return (Criteria) this;
        }

        public Criteria andFrontendRemarkLessThan(String value) {
            addCriterion("frontend_remark <", value, "frontendRemark");
            return (Criteria) this;
        }

        public Criteria andFrontendRemarkLessThanOrEqualTo(String value) {
            addCriterion("frontend_remark <=", value, "frontendRemark");
            return (Criteria) this;
        }

        public Criteria andFrontendRemarkLike(String value) {
            addCriterion("frontend_remark like", value, "frontendRemark");
            return (Criteria) this;
        }

        public Criteria andFrontendRemarkNotLike(String value) {
            addCriterion("frontend_remark not like", value, "frontendRemark");
            return (Criteria) this;
        }

        public Criteria andFrontendRemarkIn(List<String> values) {
            addCriterion("frontend_remark in", values, "frontendRemark");
            return (Criteria) this;
        }

        public Criteria andFrontendRemarkNotIn(List<String> values) {
            addCriterion("frontend_remark not in", values, "frontendRemark");
            return (Criteria) this;
        }

        public Criteria andFrontendRemarkBetween(String value1, String value2) {
            addCriterion("frontend_remark between", value1, value2, "frontendRemark");
            return (Criteria) this;
        }

        public Criteria andFrontendRemarkNotBetween(String value1, String value2) {
            addCriterion("frontend_remark not between", value1, value2, "frontendRemark");
            return (Criteria) this;
        }

        public Criteria andFrontendEnableIsNull() {
            addCriterion("frontend_enable is null");
            return (Criteria) this;
        }

        public Criteria andFrontendEnableIsNotNull() {
            addCriterion("frontend_enable is not null");
            return (Criteria) this;
        }

        public Criteria andFrontendEnableEqualTo(Byte value) {
            addCriterion("frontend_enable =", value, "frontendEnable");
            return (Criteria) this;
        }

        public Criteria andFrontendEnableNotEqualTo(Byte value) {
            addCriterion("frontend_enable <>", value, "frontendEnable");
            return (Criteria) this;
        }

        public Criteria andFrontendEnableGreaterThan(Byte value) {
            addCriterion("frontend_enable >", value, "frontendEnable");
            return (Criteria) this;
        }

        public Criteria andFrontendEnableGreaterThanOrEqualTo(Byte value) {
            addCriterion("frontend_enable >=", value, "frontendEnable");
            return (Criteria) this;
        }

        public Criteria andFrontendEnableLessThan(Byte value) {
            addCriterion("frontend_enable <", value, "frontendEnable");
            return (Criteria) this;
        }

        public Criteria andFrontendEnableLessThanOrEqualTo(Byte value) {
            addCriterion("frontend_enable <=", value, "frontendEnable");
            return (Criteria) this;
        }

        public Criteria andFrontendEnableIn(List<Byte> values) {
            addCriterion("frontend_enable in", values, "frontendEnable");
            return (Criteria) this;
        }

        public Criteria andFrontendEnableNotIn(List<Byte> values) {
            addCriterion("frontend_enable not in", values, "frontendEnable");
            return (Criteria) this;
        }

        public Criteria andFrontendEnableBetween(Byte value1, Byte value2) {
            addCriterion("frontend_enable between", value1, value2, "frontendEnable");
            return (Criteria) this;
        }

        public Criteria andFrontendEnableNotBetween(Byte value1, Byte value2) {
            addCriterion("frontend_enable not between", value1, value2, "frontendEnable");
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

        public Criteria andBackendResourceNameIsNull() {
            addCriterion("backend_resource_name is null");
            return (Criteria) this;
        }

        public Criteria andBackendResourceNameIsNotNull() {
            addCriterion("backend_resource_name is not null");
            return (Criteria) this;
        }

        public Criteria andBackendResourceNameEqualTo(String value) {
            addCriterion("backend_resource_name =", value, "backendResourceName");
            return (Criteria) this;
        }

        public Criteria andBackendResourceNameNotEqualTo(String value) {
            addCriterion("backend_resource_name <>", value, "backendResourceName");
            return (Criteria) this;
        }

        public Criteria andBackendResourceNameGreaterThan(String value) {
            addCriterion("backend_resource_name >", value, "backendResourceName");
            return (Criteria) this;
        }

        public Criteria andBackendResourceNameGreaterThanOrEqualTo(String value) {
            addCriterion("backend_resource_name >=", value, "backendResourceName");
            return (Criteria) this;
        }

        public Criteria andBackendResourceNameLessThan(String value) {
            addCriterion("backend_resource_name <", value, "backendResourceName");
            return (Criteria) this;
        }

        public Criteria andBackendResourceNameLessThanOrEqualTo(String value) {
            addCriterion("backend_resource_name <=", value, "backendResourceName");
            return (Criteria) this;
        }

        public Criteria andBackendResourceNameLike(String value) {
            addCriterion("backend_resource_name like", value, "backendResourceName");
            return (Criteria) this;
        }

        public Criteria andBackendResourceNameNotLike(String value) {
            addCriterion("backend_resource_name not like", value, "backendResourceName");
            return (Criteria) this;
        }

        public Criteria andBackendResourceNameIn(List<String> values) {
            addCriterion("backend_resource_name in", values, "backendResourceName");
            return (Criteria) this;
        }

        public Criteria andBackendResourceNameNotIn(List<String> values) {
            addCriterion("backend_resource_name not in", values, "backendResourceName");
            return (Criteria) this;
        }

        public Criteria andBackendResourceNameBetween(String value1, String value2) {
            addCriterion("backend_resource_name between", value1, value2, "backendResourceName");
            return (Criteria) this;
        }

        public Criteria andBackendResourceNameNotBetween(String value1, String value2) {
            addCriterion("backend_resource_name not between", value1, value2, "backendResourceName");
            return (Criteria) this;
        }

        public Criteria andResourceUrlIsNull() {
            addCriterion("resource_url is null");
            return (Criteria) this;
        }

        public Criteria andResourceUrlIsNotNull() {
            addCriterion("resource_url is not null");
            return (Criteria) this;
        }

        public Criteria andResourceUrlEqualTo(String value) {
            addCriterion("resource_url =", value, "resourceUrl");
            return (Criteria) this;
        }

        public Criteria andResourceUrlNotEqualTo(String value) {
            addCriterion("resource_url <>", value, "resourceUrl");
            return (Criteria) this;
        }

        public Criteria andResourceUrlGreaterThan(String value) {
            addCriterion("resource_url >", value, "resourceUrl");
            return (Criteria) this;
        }

        public Criteria andResourceUrlGreaterThanOrEqualTo(String value) {
            addCriterion("resource_url >=", value, "resourceUrl");
            return (Criteria) this;
        }

        public Criteria andResourceUrlLessThan(String value) {
            addCriterion("resource_url <", value, "resourceUrl");
            return (Criteria) this;
        }

        public Criteria andResourceUrlLessThanOrEqualTo(String value) {
            addCriterion("resource_url <=", value, "resourceUrl");
            return (Criteria) this;
        }

        public Criteria andResourceUrlLike(String value) {
            addCriterion("resource_url like", value, "resourceUrl");
            return (Criteria) this;
        }

        public Criteria andResourceUrlNotLike(String value) {
            addCriterion("resource_url not like", value, "resourceUrl");
            return (Criteria) this;
        }

        public Criteria andResourceUrlIn(List<String> values) {
            addCriterion("resource_url in", values, "resourceUrl");
            return (Criteria) this;
        }

        public Criteria andResourceUrlNotIn(List<String> values) {
            addCriterion("resource_url not in", values, "resourceUrl");
            return (Criteria) this;
        }

        public Criteria andResourceUrlBetween(String value1, String value2) {
            addCriterion("resource_url between", value1, value2, "resourceUrl");
            return (Criteria) this;
        }

        public Criteria andResourceUrlNotBetween(String value1, String value2) {
            addCriterion("resource_url not between", value1, value2, "resourceUrl");
            return (Criteria) this;
        }

        public Criteria andRequestTypeIsNull() {
            addCriterion("request_type is null");
            return (Criteria) this;
        }

        public Criteria andRequestTypeIsNotNull() {
            addCriterion("request_type is not null");
            return (Criteria) this;
        }

        public Criteria andRequestTypeEqualTo(Byte value) {
            addCriterion("request_type =", value, "requestType");
            return (Criteria) this;
        }

        public Criteria andRequestTypeNotEqualTo(Byte value) {
            addCriterion("request_type <>", value, "requestType");
            return (Criteria) this;
        }

        public Criteria andRequestTypeGreaterThan(Byte value) {
            addCriterion("request_type >", value, "requestType");
            return (Criteria) this;
        }

        public Criteria andRequestTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("request_type >=", value, "requestType");
            return (Criteria) this;
        }

        public Criteria andRequestTypeLessThan(Byte value) {
            addCriterion("request_type <", value, "requestType");
            return (Criteria) this;
        }

        public Criteria andRequestTypeLessThanOrEqualTo(Byte value) {
            addCriterion("request_type <=", value, "requestType");
            return (Criteria) this;
        }

        public Criteria andRequestTypeIn(List<Byte> values) {
            addCriterion("request_type in", values, "requestType");
            return (Criteria) this;
        }

        public Criteria andRequestTypeNotIn(List<Byte> values) {
            addCriterion("request_type not in", values, "requestType");
            return (Criteria) this;
        }

        public Criteria andRequestTypeBetween(Byte value1, Byte value2) {
            addCriterion("request_type between", value1, value2, "requestType");
            return (Criteria) this;
        }

        public Criteria andRequestTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("request_type not between", value1, value2, "requestType");
            return (Criteria) this;
        }

        public Criteria andBackendRemarkIsNull() {
            addCriterion("backend_remark is null");
            return (Criteria) this;
        }

        public Criteria andBackendRemarkIsNotNull() {
            addCriterion("backend_remark is not null");
            return (Criteria) this;
        }

        public Criteria andBackendRemarkEqualTo(String value) {
            addCriterion("backend_remark =", value, "backendRemark");
            return (Criteria) this;
        }

        public Criteria andBackendRemarkNotEqualTo(String value) {
            addCriterion("backend_remark <>", value, "backendRemark");
            return (Criteria) this;
        }

        public Criteria andBackendRemarkGreaterThan(String value) {
            addCriterion("backend_remark >", value, "backendRemark");
            return (Criteria) this;
        }

        public Criteria andBackendRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("backend_remark >=", value, "backendRemark");
            return (Criteria) this;
        }

        public Criteria andBackendRemarkLessThan(String value) {
            addCriterion("backend_remark <", value, "backendRemark");
            return (Criteria) this;
        }

        public Criteria andBackendRemarkLessThanOrEqualTo(String value) {
            addCriterion("backend_remark <=", value, "backendRemark");
            return (Criteria) this;
        }

        public Criteria andBackendRemarkLike(String value) {
            addCriterion("backend_remark like", value, "backendRemark");
            return (Criteria) this;
        }

        public Criteria andBackendRemarkNotLike(String value) {
            addCriterion("backend_remark not like", value, "backendRemark");
            return (Criteria) this;
        }

        public Criteria andBackendRemarkIn(List<String> values) {
            addCriterion("backend_remark in", values, "backendRemark");
            return (Criteria) this;
        }

        public Criteria andBackendRemarkNotIn(List<String> values) {
            addCriterion("backend_remark not in", values, "backendRemark");
            return (Criteria) this;
        }

        public Criteria andBackendRemarkBetween(String value1, String value2) {
            addCriterion("backend_remark between", value1, value2, "backendRemark");
            return (Criteria) this;
        }

        public Criteria andBackendRemarkNotBetween(String value1, String value2) {
            addCriterion("backend_remark not between", value1, value2, "backendRemark");
            return (Criteria) this;
        }

        public Criteria andBackendEnableIsNull() {
            addCriterion("backend_enable is null");
            return (Criteria) this;
        }

        public Criteria andBackendEnableIsNotNull() {
            addCriterion("backend_enable is not null");
            return (Criteria) this;
        }

        public Criteria andBackendEnableEqualTo(Byte value) {
            addCriterion("backend_enable =", value, "backendEnable");
            return (Criteria) this;
        }

        public Criteria andBackendEnableNotEqualTo(Byte value) {
            addCriterion("backend_enable <>", value, "backendEnable");
            return (Criteria) this;
        }

        public Criteria andBackendEnableGreaterThan(Byte value) {
            addCriterion("backend_enable >", value, "backendEnable");
            return (Criteria) this;
        }

        public Criteria andBackendEnableGreaterThanOrEqualTo(Byte value) {
            addCriterion("backend_enable >=", value, "backendEnable");
            return (Criteria) this;
        }

        public Criteria andBackendEnableLessThan(Byte value) {
            addCriterion("backend_enable <", value, "backendEnable");
            return (Criteria) this;
        }

        public Criteria andBackendEnableLessThanOrEqualTo(Byte value) {
            addCriterion("backend_enable <=", value, "backendEnable");
            return (Criteria) this;
        }

        public Criteria andBackendEnableIn(List<Byte> values) {
            addCriterion("backend_enable in", values, "backendEnable");
            return (Criteria) this;
        }

        public Criteria andBackendEnableNotIn(List<Byte> values) {
            addCriterion("backend_enable not in", values, "backendEnable");
            return (Criteria) this;
        }

        public Criteria andBackendEnableBetween(Byte value1, Byte value2) {
            addCriterion("backend_enable between", value1, value2, "backendEnable");
            return (Criteria) this;
        }

        public Criteria andBackendEnableNotBetween(Byte value1, Byte value2) {
            addCriterion("backend_enable not between", value1, value2, "backendEnable");
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
