package com.medcaptain.productservice.entity.mybatis;

public class Productcategory {
    private Integer productCate;

    private String productTypeDesc;

    public Integer getProductCate() {
        return productCate;
    }

    public void setProductCate(Integer productCate) {
        this.productCate = productCate;
    }

    public String getProductTypeDesc() {
        return productTypeDesc;
    }

    public void setProductTypeDesc(String productDesc) {
        this.productTypeDesc = productDesc == null ? null : productDesc.trim();
    }
}