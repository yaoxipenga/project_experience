package com.medcaptain.cloud.usermanage.entity;

import java.util.Date;

/**
 * 机构信息
 *
 * @author bingwen.ai
 */
public class Organization {
    private Integer organizationId;

    private String organizationName;

    private Integer organizationLevel;

    /**
     * 机构类型： 0 = 医院 ； 1 = 厂商 ; 2 = 平台
     */
    private Integer organizationType;

    private Integer createUserId;

    private Date createTime;

    private String remark;

    private Integer regionId;

    private Byte isEnable;

    private Byte isDeleted;

    private String regionName;

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName == null ? null : organizationName.trim();
    }

    public Integer getOrganizationLevel() {
        return organizationLevel;
    }

    public void setOrganizationLevel(Integer organizationLevel) {
        this.organizationLevel = organizationLevel;
    }

    public Integer getOrganizationType() {
        return organizationType;
    }

    public void setOrganizationType(Integer organizationType) {
        this.organizationType = organizationType;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
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

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public Byte getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Byte isEnable) {
        this.isEnable = isEnable;
    }

    public Byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
}
