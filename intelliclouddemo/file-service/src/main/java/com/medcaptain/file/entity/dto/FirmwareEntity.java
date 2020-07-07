package com.medcaptain.file.entity.dto;

import java.util.Date;

/**
 * 返回前端的固件信息格式
 * @author Adam.Chen
 */
public class FirmwareEntity {
    private int firmwareId;
    private String firmwareName;
    private String firmwareVersion;
    private Date createAt;
    private int status;

    public Date getCreateAt() {
        return createAt;
    }

    public int getFirmwareId() {
        return firmwareId;
    }

    public int getStatus() {
        return status;
    }

    public String getFirmwareName() {
        return firmwareName;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public void setFirmwareId(int firmwareId) {
        this.firmwareId = firmwareId;
    }

    public void setFirmwareName(String firmwareName) {
        this.firmwareName = firmwareName;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
