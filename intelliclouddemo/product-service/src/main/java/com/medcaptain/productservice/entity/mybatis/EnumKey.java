package com.medcaptain.productservice.entity.mybatis;

public class EnumKey {
    private String productKey;

    private String productTransIdenti;

    private Integer enumVal;

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey == null ? null : productKey.trim();
    }

    public String getProductTransIdenti() {
        return productTransIdenti;
    }

    public void setProductTransIdenti(String productTransIdenti) {
        this.productTransIdenti = productTransIdenti == null ? null : productTransIdenti.trim();
    }

    public Integer getEnumVal() {
        return enumVal;
    }

    public void setEnumVal(Integer enumVal) {
        this.enumVal = enumVal;
    }
}