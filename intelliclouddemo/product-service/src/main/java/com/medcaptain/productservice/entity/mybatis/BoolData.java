package com.medcaptain.productservice.entity.mybatis;

public class BoolData {
    private Integer indexId;

    private String trueDesc;

    private String falseDesc;

    private Integer userId;

    public Integer getIndexId() {
        return indexId;
    }

    public void setIndexId(Integer indexId) {
        this.indexId = indexId;
    }

    public String getTrueDesc() {
        return trueDesc;
    }

    public void setTrueDesc(String trueDesc) {
        this.trueDesc = trueDesc == null ? null : trueDesc.trim();
    }

    public String getFalseDesc() {
        return falseDesc;
    }

    public void setFalseDesc(String falseDesc) {
        this.falseDesc = falseDesc == null ? null : falseDesc.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}