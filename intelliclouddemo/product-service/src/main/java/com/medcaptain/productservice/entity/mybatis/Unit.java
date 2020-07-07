package com.medcaptain.productservice.entity.mybatis;

public class Unit {
    private Integer unitId;

    private String unitDesc;

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public String getUnitDesc() {
        return unitDesc;
    }

    public void setUnitDesc(String unitDesc) {
        this.unitDesc = unitDesc == null ? null : unitDesc.trim();
    }
}