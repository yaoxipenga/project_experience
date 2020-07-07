package com.medcaptain.file.entity.mysql;

import java.util.Date;

public class DeviceUploadStatus extends DeviceUploadStatusKey {
    private Integer productKey;

    private String deviceName;

    private String uploadStatus;

    private String uploadProg;

    private Integer userId;

    private Date creattime;

    public Integer getProductKey() {
        return productKey;
    }

    public void setProductKey(Integer productKey) {
        this.productKey = productKey;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName == null ? null : deviceName.trim();
    }

    public String getUploadStatus() {
        return uploadStatus;
    }

    public void setUploadStatus(String uploadStatus) {
        this.uploadStatus = uploadStatus == null ? null : uploadStatus.trim();
    }

    public String getUploadProg() {
        return uploadProg;
    }

    public void setUploadProg(String uploadProg) {
        this.uploadProg = uploadProg == null ? null : uploadProg.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreattime() {
        return creattime;
    }

    public void setCreattime(Date creattime) {
        this.creattime = creattime;
    }
}