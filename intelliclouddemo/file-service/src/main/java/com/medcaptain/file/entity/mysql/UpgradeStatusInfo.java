package com.medcaptain.file.entity.mysql;

public class UpgradeStatusInfo {
    private Integer firmwareId;

    private String veriStatus;

    public Integer getFirmwareId() {
        return firmwareId;
    }

    public void setFirmwareId(Integer firmwareId) {
        this.firmwareId = firmwareId;
    }

    public String getVeriStatus() {
        return veriStatus;
    }

    public void setVeriStatus(String veriStatus) {
        this.veriStatus = veriStatus == null ? null : veriStatus.trim();
    }
}