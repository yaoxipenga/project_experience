package com.medcaptain.entity;

public class ProductinfoCountEntity {
    /*
     * 机构位置表主键
     */
    private Integer organizationId;

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
    private Byte countType;

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
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

    public Byte getCountType() {
        return countType;
    }

    public void setCountType(Byte countType) {
        this.countType = countType;
    }
}
