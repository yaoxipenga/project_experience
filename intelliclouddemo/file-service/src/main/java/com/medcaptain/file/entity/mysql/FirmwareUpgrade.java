package com.medcaptain.file.entity.mysql;

import com.medcaptain.enums.UpgradeStatus;

import java.util.Date;

public class FirmwareUpgrade extends FirmwareUpgradeKey {
    private String deviceName;

    private String deviceFirmwareVersion;

    private UpgradeStatus upgradeStatus;

    private Integer upgradeProgress;

    private String desc;

    private String remark;

    private Date postTime;

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName == null ? null : deviceName.trim();
    }

    public String getDeviceFirmwareVersion() {
        return deviceFirmwareVersion;
    }

    public void setDeviceFirmwareVersion(String deviceFirmwareVersion) {
        this.deviceFirmwareVersion = deviceFirmwareVersion == null ? null : deviceFirmwareVersion.trim();
    }

    public UpgradeStatus getUpgradeStatus() {
        return upgradeStatus;
    }

    public void setUpgradeStatus(UpgradeStatus upgradeStatus) {
        this.upgradeStatus = upgradeStatus;
    }

    public Integer getUpgradeProgress() {
        return upgradeProgress;
    }

    public void setUpgradeProgress(Integer upgradeProgress) {
        this.upgradeProgress = upgradeProgress;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }
}