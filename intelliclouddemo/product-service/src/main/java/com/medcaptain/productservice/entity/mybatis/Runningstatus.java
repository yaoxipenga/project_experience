package com.medcaptain.productservice.entity.mybatis;

import com.medcaptain.productservice.enums.DeviceRunningStatusEnum;

import java.sql.Timestamp;
import java.util.Date;

public class Runningstatus {
    private Integer indexId;

    private Integer deviceTripleId;

    private DeviceRunningStatusEnum deviceStatus;

    private String firmwareVersion;

    private Timestamp lastOnlineTime;

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

    public DeviceRunningStatusEnum getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(DeviceRunningStatusEnum deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion == null ? null : firmwareVersion.trim();
    }

    public Timestamp getLastOnlineTime() {
        return lastOnlineTime;
    }

    public void setLastOnlineTime(Timestamp lastOnlineTime) {
        this.lastOnlineTime = lastOnlineTime;
    }

    public String getDeviceIp() {
        return deviceIp;
    }

    public void setDeviceIp(String deviceIp) {
        this.deviceIp = deviceIp == null ? null : deviceIp.trim();
    }
}