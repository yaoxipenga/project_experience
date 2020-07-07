package com.medcaptain.productservice.entity.mybatis;

public class Opttype {
    private Integer optType;

    private String optDesc;

    public Integer getOptType() {
        return optType;
    }

    public void setOptType(Integer optType) {
        this.optType = optType;
    }

    public String getOptDesc() {
        return optDesc;
    }

    public void setOptDesc(String optDesc) {
        this.optDesc = optDesc == null ? null : optDesc.trim();
    }
}