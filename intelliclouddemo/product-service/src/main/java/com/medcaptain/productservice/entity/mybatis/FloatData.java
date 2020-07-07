package com.medcaptain.productservice.entity.mybatis;

import java.util.Date;

public class FloatData {
    private Integer indexId;

    private Float maxVal;

    private Float minVal;

    private Float step;

    private Integer unitId;

    private Date creatTime;

    private Integer userId;

    public Integer getIndexId() {
        return indexId;
    }

    public void setIndexId(Integer indexId) {
        this.indexId = indexId;
    }

    public Float getMaxVal() {
        return maxVal;
    }

    public void setMaxVal(Float maxVal) {
        this.maxVal = maxVal;
    }

    public Float getMinVal() {
        return minVal;
    }

    public void setMinVal(Float minVal) {
        this.minVal = minVal;
    }

    public Float getStep() {
        return step;
    }

    public void setStep(Float step) {
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