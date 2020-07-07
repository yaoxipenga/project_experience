package com.medcaptain.productservice.entity.mybatis;

public class Rolefuncrelat {
    private Integer id;

    private Integer roleId;

    private Integer tableFuncPrvlgId;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getTableFuncPrvlgId() {
        return tableFuncPrvlgId;
    }

    public void setTableFuncPrvlgId(Integer tableFuncPrvlgId) {
        this.tableFuncPrvlgId = tableFuncPrvlgId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}