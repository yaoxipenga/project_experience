package com.medcaptain.file.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * 返回前端的固件单条详细信息
 * @author Adam.Chen
 */
public class FirmwareInfoOneEntity {
    private String firmwareVersion;
    private String firmwareDesc;
    private String md5;
    private String firmwareUrl;
    private String firmwareName;
    private Date createAt;
    private int targetDeviceCount;
    private int upgradeSuccessCount;
    private int upgradeFailCount;

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public void setFirmwareName(String firmwareName) {
        this.firmwareName = firmwareName;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public String getFirmwareName() {
        return firmwareName;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public int getTargetDeviceCount() {
        return targetDeviceCount;
    }

    public int getUpgradeFailCount() {
        return upgradeFailCount;
    }

    public int getUpgradeSuccessCount() {
        return upgradeSuccessCount;
    }

    public String getFirmwareDesc() {
        return firmwareDesc;
    }

    public String getFirmwareUrl() {
        return firmwareUrl;
    }

    public void setFirmwareDesc(String firmwareDesc) {
        this.firmwareDesc = firmwareDesc;
    }

    public void setFirmwareUrl(String firmwareUrl) {
        this.firmwareUrl = firmwareUrl;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    @JsonProperty("MD5")
    public String getMd5() {
        return md5;
    }

    public void setTargetDeviceCount(int targetDeviceCount) {
        this.targetDeviceCount = targetDeviceCount;
    }

    public void setUpgradeFailCount(int upgradeFailCount) {
        this.upgradeFailCount = upgradeFailCount;
    }

    public void setUpgradeSuccessCount(int upgradeSuccessCount) {
        this.upgradeSuccessCount = upgradeSuccessCount;
    }
}
