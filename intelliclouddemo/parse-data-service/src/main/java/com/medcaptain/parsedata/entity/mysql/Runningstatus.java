package com.medcaptain.parsedata.entity.mysql;

import java.util.Date;

public class Runningstatus {
    private Integer indexId;

    private Integer deviceTripleId;

    private Integer deviceStatus;

    private String firmwareVersion;

    private Date lastOnlineTime;

    private String deviceIp;

    public Integer getIndexId() {
        return indexId;
    }

    public void setIndexId(Integer indexId) {
        this.indexId = indexId;
    }

    public Integer getDeviceTripleId() {
        return deviceTripleId;
    }

    public void setDeviceTripleId(Integer deviceTripleId) {
        this.deviceTripleId = deviceTripleId;
    }

    public Integer getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(Integer deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion == null ? null : firmwareVersion.trim();
    }

    public Date getLastOnlineTime() {
        return lastOnlineTime;
    }

    public void setLastOnlineTime(Date lastOnlineTime) {
        this.lastOnlineTime = lastOnlineTime;
    }

    public String getDeviceIp() {
        return deviceIp;
    }

    public void setDeviceIp(String deviceIp) {
        this.deviceIp = deviceIp == null ? null : deviceIp.trim();
    }
}