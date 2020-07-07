package com.medcaptain.cloud.producttemplate.entity;

import java.util.List;


/**
 * 影子设备
 *
 * @author bingwen.ai
 */
public class ShadowDevice {

    private String deviceTripleID;

    private List<ShadowProperty> propertyList;

    public String getDeviceTripleID() {
        return deviceTripleID;
    }

    public void setDeviceTripleID(String deviceTripleID) {
        this.deviceTripleID = deviceTripleID;
    }

    public List<ShadowProperty> getPropertyList() {
        return propertyList;
    }

    public void setPropertyList(List<ShadowProperty> propertyList) {
        this.propertyList = propertyList;
    }
}
