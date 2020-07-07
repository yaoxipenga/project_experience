package com.medcaptain.productservice.entity.mybatis;

import java.util.Date;

public class Deviceuploadfile {
    private Integer fileId;

    private String fileDesc;

    private Integer deviceTripleId;

    private String localPath;

    private String fileName;

    private String fileMd5;

    private Long fileSize;

    private Integer userId;

    private Date creatTime;

    private Integer isDel;

    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getFileDesc() {
        return fileDesc;
    }

    public void setFileDesc(String fileDesc) {
        this.fileDesc = fileDesc == null ? null : fileDesc.trim();
    }

    public Integer getDeviceTripleId() {
        return deviceTripleId;
    }

    public void setDeviceTripleId(Integer deviceTripleId) {
        this.deviceTripleId = deviceTripleId;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath == null ? null : localPath.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getFileMd5() {
        return fileMd5;
    }

    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5 == null ? null : fileMd5.trim();
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}