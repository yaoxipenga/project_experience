package com.medcaptain.productservice.entity.mybatis;

public class ProductTransTemplateKey {
    private String productTempletId;

    private String productTransIdenti;

    public String getProductTempletId() {
        return productTempletId;
    }

    public void setProductTempletId(String productTempletId) {
        this.productTempletId = productTempletId == null ? null : productTempletId.trim();
    }

    public String getProductTransIdenti() {
        return productTransIdenti;
    }

    public void setProductTransIdenti(String productTransIdenti) {
        this.productTransIdenti = productTransIdenti == null ? null : productTransIdenti.trim();
    }
}