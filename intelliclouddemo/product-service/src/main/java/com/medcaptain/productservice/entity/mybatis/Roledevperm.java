package com.medcaptain.productservice.entity.mybatis;

public class Roledevperm {
    private Integer roledevpermId;

    private Integer roleId;

    private Integer deviceTripleId;

    private String desc;

    public Integer getRoledevpermId() {
        return roledevpermId;
    }

    public void setRoledevpermId(Integer roledevpermId) {
        this.roledevpermId = roledevpermId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getDeviceTripleId() {
        return deviceTripleId;
    }

    public void setDeviceTripleId(Integer deviceTripleId) {
        this.deviceTripleId = deviceTripleId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }
}