package com.medcaptain.productservice.entity.mybatis;

import java.util.Date;

public class ProductTransTemplate extends ProductTransTemplateKey {
    private Integer templetId;

    private String productTransName;

    private Integer productTransType;

    private Integer eventType;

    private Integer isDel;

    private Integer rwStatus;

    private Date creatTime;

    private Date modifTime;

    private Integer userId;

    private String remark;

    public Integer getTempletId() {
        return templetId;
    }

    public void setTempletId(Integer templetId) {
        this.templetId = templetId;
    }

    public String getProductTransName() {
        return productTransName;
    }

    public void setProductTransName(String productTransName) {
        this.productTransName = productTransName == null ? null : productTransName.trim();
    }

    public Integer getProductTransType() {
        return productTransType;
    }

    public void setProductTransType(Integer productTransType) {
        this.productTransType = productTransType;
    }

    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Integer getRwStatus() {
        return rwStatus;
    }

    public void setRwStatus(Integer rwStatus) {
        this.rwStatus = rwStatus;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public Date getModifTime() {
        return modifTime;
    }

    public void setModifTime(Date modifTime) {
        this.modifTime = modifTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}