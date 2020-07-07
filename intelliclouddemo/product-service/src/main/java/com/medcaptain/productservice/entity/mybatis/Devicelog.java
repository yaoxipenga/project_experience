package com.medcaptain.productservice.entity.mybatis;

import java.util.Date;

public class Devicelog {
    private Integer deviceLogId;

    private String productKey;

    private String deviceName;

    private Integer deviceTripleId;

    private String deviceIp;

    private Date creatTime;

    private String Topic;

    private String desc;

    private String logContent;

    public Integer getDeviceLogId() {
        return deviceLogId;
    }

    public void setDeviceLogId(Integer deviceLogId) {
        this.deviceLogId = deviceLogId;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName == null ? null : deviceName.trim();
    }

    public Integer getDeviceTripleId() {
        return deviceTripleId;
    }

    public void setDeviceTripleId(Integer deviceTripleId) {
        this.deviceTripleId = deviceTripleId;
    }

    public String getDeviceIp() {
        return deviceIp;
    }

    public void setDeviceIp(String deviceIp) {
        this.deviceIp = deviceIp == null ? null : deviceIp.trim();
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public String getTopic() {
        return Topic;
    }

    public void setTopic(String Topic) {
        this.Topic = Topic == null ? null : Topic.trim();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }

    public String getLogContent() {
        return logContent;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent == null ? null : logContent.trim();
    }
}