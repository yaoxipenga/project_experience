package com.medcaptain.productservice.entity.mybatis;

import java.util.Date;

public class Productbasicinfotemp {
    private Integer templetId;

    private String templetName;

    private Integer userId;

    private Integer prodCate;

    private String templetDesc;

    private String remark;

    private Date creatTime;

    public Integer getTempletId() {
        return templetId;
    }

    public void setTempletId(Integer templetId) {
        this.templetId = templetId;
    }

    public String getTempletName() {
        return templetName;
    }

    public void setTempletName(String templetName) {
        this.templetName = templetName == null ? null : templetName.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProdCate() {
        return prodCate;
    }

    public void setProdCate(Integer prodCate) {
        this.prodCate = prodCate;
    }

    public String getTempletDesc() {
        return templetDesc;
    }

    public void setTempletDesc(String templetDesc) {
        this.templetDesc = templetDesc == null ? null : templetDesc.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }
}