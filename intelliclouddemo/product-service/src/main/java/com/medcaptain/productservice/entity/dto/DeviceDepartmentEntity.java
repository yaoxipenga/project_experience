package com.medcaptain.productservice.entity.dto;

import java.util.List;

/**
 * @author yang
 */
public class DeviceDepartmentEntity {


    /*
    {"organizationId":1,"departmentId":1,"departmentCheckId":[97,98],"deviceList":[{"deviceName":"ba1384bf38f8481","productKey":"ffd7481b6e3"}]}
*/
    /**
     * organizationId : 1
     * departmentId : 1
     * departmentCheckId : [97,98]
     * deviceList : [{"deviceName":"ba1384bf38f8481","productKey":"ffd7481b6e3"}]
     */

    private Integer organizationId;
    private Integer departmentId;
    private List<Integer> departmentCheckId;
    private List<DeviceListBean> deviceList;

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public List<Integer> getDepartmentCheckId() {
        return departmentCheckId;
    }

    public void setDepartmentCheckId(List<Integer> departmentCheckId) {
        this.departmentCheckId = departmentCheckId;
    }

    public List<DeviceListBean> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<DeviceListBean> deviceList) {
        this.deviceList = deviceList;
    }

    public static class DeviceListBean {
        /**
         * deviceName : ba1384bf38f8481
         * productKey : ffd7481b6e3
         */

        private String deviceName;
        private String productKey;

        public String getDeviceName() {
            return deviceName;
        }

        public void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
        }

        public String getProductKey() {
            return productKey;
        }

        public void setProductKey(String productKey) {
            this.productKey = productKey;
        }
    }
}
