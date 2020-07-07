package com.medcaptain.productservice.entity.mybatis;

import java.util.Date;

public class DeviceServiceLog {
    private Integer deviceLogId;

    private String productKey;

    private String deviceName;

    private Integer deviceTripleId;

    private String deviceIp;

    private Integer returnCode;

    private Date createTime;

    private String topic;

    private String desc;

    private Integer checkResult;

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
        this.productKey = productKey == null ? null : productKey.trim();
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

    public Integer getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(Integer returnCode) {
        this.returnCode = returnCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic == null ? null : topic.trim();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }

    public Integer getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(Integer checkResult) {
        this.checkResult = checkResult;
    }
}