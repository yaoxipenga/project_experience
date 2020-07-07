package com.medcaptain.productservice.entity.mybatis;

import java.util.Date;

public class ProductTransactionIdentifiers {
    private Integer id;

    private String productKey;

    private String identifiers;

    private byte transactionType;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey == null ? null : productKey.trim();
    }

    public String getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(String identifiers) {
        this.identifiers = identifiers == null ? null : identifiers.trim();
    }

    public byte getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(byte transactionType) {
        this.transactionType = transactionType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}