package com.medcaptain.file.entity.dto;

import java.util.Date;

/**
 * 返回前端升级信息
 * @author Adam.Chen
 */
public class UpgradeInfo {
    private String deviceName;
    private String currentVersion;
    private Date statusUpdataAt;
    private int status;

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
