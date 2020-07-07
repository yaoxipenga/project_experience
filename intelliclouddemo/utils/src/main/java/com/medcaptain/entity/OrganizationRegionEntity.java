package com.medcaptain.entity;


import java.util.Date;

/**
 * 统计服务使用的
 *
 * @author yang
 */
public class OrganizationRegionEntity {
    private int regionId;
    private int organizationId;
    private int organizationLevel;
    private String organizationName;
    private byte isDeleted;
    private Date createTime;

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public int getOrganizationLevel() {
        return organizationLevel;
    }

    public void setOrganizationLevel(int organizationLevel) {
        this.organizationLevel = organizationLevel;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
