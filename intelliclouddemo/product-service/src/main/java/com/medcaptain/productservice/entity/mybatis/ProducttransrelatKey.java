package com.medcaptain.productservice.entity.mybatis;

public class ProducttransrelatKey {
    private String productKey;

    private String productTransIdenti;

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
}