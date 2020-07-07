package com.medcaptain.file.entity.mysql;

public class FirmwareUpgradeKey {
    private Integer firmwareId;

    private Integer deviceTripleId;

    public Integer getFirmwareId() {
        return firmwareId;
    }

    public void setFirmwareId(Integer firmwareId) {
        this.firmwareId = firmwareId;
    }

    public Integer getDeviceTripleId() {
        return deviceTripleId;
    }

    public void setDeviceTripleId(Integer deviceTripleId) {
        this.deviceTripleId = deviceTripleId;
    }
}