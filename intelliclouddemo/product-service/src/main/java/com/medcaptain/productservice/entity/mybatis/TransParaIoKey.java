package com.medcaptain.productservice.entity.mybatis;

public class TransParaIoKey {
    private String productKey;

    private String productTransIdenti;

    private Integer ioType;

    private String paraIdentifier;

    private Integer templetId;

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

    public Integer getIoType() {
        return ioType;
    }

    public void setIoType(Integer ioType) {
        this.ioType = ioType;
    }

    public String getParaIdentifier() {
        return paraIdentifier;
    }

    public void setParaIdentifier(String paraIdentifier) {
        this.paraIdentifier = paraIdentifier == null ? null : paraIdentifier.trim();
    }

    public Integer getTempletId() {
        return templetId;
    }

    public void setTempletId(Integer templetId) {
        this.templetId = templetId;
    }
}