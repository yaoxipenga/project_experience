package com.medcaptain.productservice.entity.mybatis;

import java.util.Date;

public class Producttriple {
    private String productKey;

    private String productName;

    private String productSecret;

    private Integer userId;

    private Date creatTime;

    private Integer isDel;

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey == null ? null : productKey.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getProductSecret() {
        return productSecret;
    }

    public void setProductSecret(String productSecret) {
        this.productSecret = productSecret == null ? null : productSecret.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}