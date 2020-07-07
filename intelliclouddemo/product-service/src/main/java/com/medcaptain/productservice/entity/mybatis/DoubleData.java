package com.medcaptain.productservice.entity.mybatis;

import java.util.Date;

public class DoubleData {
    private Integer indexId;

    private Double maxVal;

    private Double minVal;

    private Double step;

    private Integer unitId;

    private Date creatTime;

    private Integer userId;

    public Integer getIndexId() {
        return indexId;
    }

    public void setIndexId(Integer indexId) {
        this.indexId = indexId;
    }

    public Double getMaxVal() {
        return maxVal;
    }

    public void setMaxVal(Double maxVal) {
        this.maxVal = maxVal;
    }

    public Double getMinVal() {
        return minVal;
    }

    public void setMinVal(Double minVal) {
        this.minVal = minVal;
    }

    public Double getStep() {
        return step;
    }

    public void setStep(Double step) {
        this.step = step;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}