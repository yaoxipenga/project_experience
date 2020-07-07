package com.medcaptain.cloud.usermanage.entity;

/**
 * 机构列表
 *
 * @author bingwen.ai
 */
public class OrganizationListEntity {
    private int hospitalId;

    private String hospitalName;

    private int productCount;

    private int deviceCount;

    private String hospitalLocation;

    private int hospitalStatus;

    private String hospitalLevel;

    public String getHospitalName() {
        return hospitalName;
    }

    public String getHospitalLocation() {
        return hospitalLocation;
    }

    public int getHospitalStatus() {
        return hospitalStatus;
    }

    public int getDeviceCount() {
        return deviceCount;
    }

    public int getHospitalId() {
        return hospitalId;
    }

    public int getProductCount() {
        return productCount;
    }

    public String getHospitalLevel() {
        return hospitalLevel;
    }

    public void setDeviceCount(int deviceCount) {
        this.deviceCount = deviceCount;
    }

    public void setHospitalId(int hospitalId) {
        this.hospitalId = hospitalId;
    }

    public void setHospitalLevel(String hospitalLevel) {
        this.hospitalLevel = hospitalLevel;
    }

    public void setHospitalLocation(String hospitalLocation) {
        this.hospitalLocation = hospitalLocation;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public void setHospitalStatus(int hospitalStatus) {
        this.hospitalStatus = hospitalStatus;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

}
