package com.medcaptain.productservice.entity.mybatis;

public class TransType {
    private Integer productTransType;

    private String productTransTypeDesc;

    public Integer getProductTransType() {
        return productTransType;
    }

    public void setProductTransType(Integer productTransType) {
        this.productTransType = productTransType;
    }

    public String getProductTransTypeDesc() {
        return productTransTypeDesc;
    }

    public void setProductTransTypeDesc(String productTransTypeDesc) {
        this.productTransTypeDesc = productTransTypeDesc == null ? null : productTransTypeDesc.trim();
    }
}