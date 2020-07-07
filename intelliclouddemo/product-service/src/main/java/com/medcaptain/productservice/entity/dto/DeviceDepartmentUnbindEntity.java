package com.medcaptain.productservice.entity.dto;

public class DeviceDepartmentUnbindEntity {


    /**
     * productKey : 1
     * deviceName : 2
     */

    private String productKey;
    private String deviceName;

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
}
