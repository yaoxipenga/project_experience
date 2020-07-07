package com.medcaptain.productservice.entity.mybatis;

public class Roleprodperm {
    private Integer roleprodpermId;

    private Integer roleId;

    private String productKey;

    private String desc;

    public Integer getRoleprodpermId() {
        return roleprodpermId;
    }

    public void setRoleprodpermId(Integer roleprodpermId) {
        this.roleprodpermId = roleprodpermId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey == null ? null : productKey.trim();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }
}