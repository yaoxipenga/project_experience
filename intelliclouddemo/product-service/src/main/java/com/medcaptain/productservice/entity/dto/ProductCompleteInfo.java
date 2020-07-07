package com.medcaptain.productservice.entity.dto;

import java.sql.Timestamp;

public class ProductCompleteInfo {
    private String productName;
    private String productModel;
    private String productSecret;
    private String productKey;
    private String productDesc;
    private Timestamp createAt;
    private int deviceCount;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    public String getProductSecret() {
        return productSecret;
    }

    public void setProductSecret(String productSecret) {
        this.productSecret = productSecret;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public Timestamp getCreatAt() {
        return createAt;
    }

    public void setCreatAt(Timestamp creatAt) {
        this.createAt = creatAt;
    }

    public int getDeviceCount() {
        return deviceCount;
    }

    public void setDeviceCount(int deviceCount) {
        this.deviceCount = deviceCount;
    }
}
