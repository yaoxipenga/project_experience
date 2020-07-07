package com.medcaptain.entity;

import java.util.Date;

/**
 * 统计服务使用的
 *
 * @author yang
 */
public class ProductinfoEntity {
    /*
     * 机构位置表主键
     */
    private Integer organizationRegionId;

    /*
     * 产品Key
     */
    private String productKey;

    /*
     * 在这个时间段，这个医院，这个产品，新增的设备数量
     */
    private int deviceCount;

    /*
     * 数据类型（0新增   1删除数量）
     */
    private byte countType;

    public Integer getOrganizationRegionId() {
        return organizationRegionId;
    }

    public void setOrganizationRegionId(Integer organizationRegionId) {
        this.organizationRegionId = organizationRegionId;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public int getDeviceCount() {
        return deviceCount;
    }

    public void setDeviceCount(int deviceCount) {
        this.deviceCount = deviceCount;
    }

    public byte getCountType() {
        return countType;
    }

    public void setCountType(byte countType) {
        this.countType = countType;
    }

    public void setCountType(Integer countType) {
        this.countType = countType.byteValue();
    }
}
