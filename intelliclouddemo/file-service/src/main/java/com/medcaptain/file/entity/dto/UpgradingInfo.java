package com.medcaptain.file.entity.dto;

import java.util.Date;

public class UpgradingInfo {
    private String deviceName;
    private String currentVersion;
    private Date statusUpdataAt;
    private int status;
    private int progress;

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public Date getStatusUpdataAt() {
        return statusUpdataAt;
    }

    public String getCurrentVersion() {
        return currentVersion;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setCurrentVersion(String currentVersion) {
        this.currentVersion = currentVersion;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public void setStatusUpdataAt(Date statusUpdataAt) {
        this.statusUpdataAt = statusUpdataAt;
    }
}
