package com.medcaptain.productservice.entity.mybatis;

import java.util.Date;

public class Shadowdevice {
    private Integer deviceTripleId;

    private Date creatTime;

    private Integer userId;

    private String cmdsJson;

    public Integer getDeviceTripleId() {
        return deviceTripleId;
    }

    public void setDeviceTripleId(Integer deviceTripleId) {
        this.deviceTripleId = deviceTripleId;
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

    public String getCmdsJson() {
        return cmdsJson;
    }

    public void setCmdsJson(String cmdsJson) {
        this.cmdsJson = cmdsJson == null ? null : cmdsJson.trim();
    }
}