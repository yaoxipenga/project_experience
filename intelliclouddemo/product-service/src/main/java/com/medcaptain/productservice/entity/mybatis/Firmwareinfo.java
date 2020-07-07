package com.medcaptain.productservice.entity.mybatis;

import java.util.Date;

public class Firmwareinfo {
    private Integer firmwareId;

    private String productKey;

    private String dpsitPath;

    private String md5;

    private String firmwareName;

    private Integer firmwareSize;

    private String firmwareVersion;

    private Integer isVerified;

    private Date createTime;

    private String remark;

    private Integer isDel;

    public Integer getFirmwareId() {
        return firmwareId;
    }

    public void setFirmwareId(Integer firmwareId) {
        this.firmwareId = firmwareId;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey == null ? null : productKey.trim();
    }

    public String getDpsitPath() {
        return dpsitPath;
    }

    public void setDpsitPath(String dpsitPath) {
        this.dpsitPath = dpsitPath == null ? null : dpsitPath.trim();
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5 == null ? null : md5.trim();
    }

    public String getFirmwareName() {
        return firmwareName;
    }

    public void setFirmwareName(String firmwareName) {
        this.firmwareName = firmwareName == null ? null : firmwareName.trim();
    }

    public Integer getFirmwareSize() {
        return firmwareSize;
    }

    public void setFirmwareSize(Integer firmwareSize) {
        this.firmwareSize = firmwareSize;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion == null ? null : firmwareVersion.trim();
    }

    public Integer getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Integer isVerified) {
        this.isVerified = isVerified;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}