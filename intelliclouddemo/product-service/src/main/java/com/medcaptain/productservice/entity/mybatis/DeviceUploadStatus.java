package com.medcaptain.productservice.entity.mybatis;

import java.util.Date;

public class DeviceUploadStatus {
    private Integer deviceUploadStatusId;

    private Integer deviceTripleId;

    private String fileName;

    private String uploadStatus;

    private Integer uploadProg;

    private Integer userId;

    private Date creattime;

    public Integer getDeviceUploadStatusId() {
        return deviceUploadStatusId;
    }

    public void setDeviceUploadStatusId(Integer deviceUploadStatusId) {
        this.deviceUploadStatusId = deviceUploadStatusId;
    }

    public Integer getDeviceTripleId() {
        return deviceTripleId;
    }

    public void setDeviceTripleId(Integer deviceTripleId) {
        this.deviceTripleId = deviceTripleId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getUploadStatus() {
        return uploadStatus;
    }

    public void setUploadStatus(String uploadStatus) {
        this.uploadStatus = uploadStatus == null ? null : uploadStatus.trim();
    }

    public Integer getUploadProg() {
        return uploadProg;
    }

    public void setUploadProg(Integer uploadProg) {
        this.uploadProg = uploadProg;
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