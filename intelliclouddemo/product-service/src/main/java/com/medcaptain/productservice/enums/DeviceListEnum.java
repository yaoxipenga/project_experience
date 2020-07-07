package com.medcaptain.productservice.enums;

import java.util.List;

/**
 * 获取设备列表的筛选条件参数
 */
public class DeviceListEnum {

    /*
     {
    "productKey": ["产品key"],
    "deviceName": ["设备name"],
    "provinceId": [44,55],
    "onlineStatus": 0,
    "filterParam" : 0
    "hospital": [
	    {
	        "organizationId": 1,
	        "department": {
	            "departmentId": 1
	        }
	    },
	    {
	        "organizationId": 1,
	        "department": {
	            "departmentId": 97
	        }
	    }
    ],
    "searchDevice": "a",
    "page": 2,
    "perPage": 15
}
     */

    /**
     * productKey : ["产品key"]
     * deviceName : ["设备name"]
     * provinceId : [44,55]
     * onlineStatus : 0
     * hospital : [{"organizationId":1,"department":{"departmentId":1}},{"organizationId":1,"department":{"departmentId":97}}]
     * searchDevice : a
     * page : 2
     * perPage : 15
     */

    private Integer onlineStatus;
    private String searchDevice;
    private Integer page;
    private Integer perPage;
    private Integer filterParam;
    private List<String> productKey;
    private List<String> deviceName;
    private List<Integer> provinceId;
    private List<HospitalBean> hospital;

    public Integer getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(Integer onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public String getSearchDevice() {
        return searchDevice;
    }

    public void setSearchDevice(String searchDevice) {
        this.searchDevice = searchDevice;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    public List<String> getProductKey() {
        return productKey;
    }

    public Integer getFilterParam() {
        return filterParam;
    }

    public void setFilterParam(Integer filterParam) {
        this.filterParam = filterParam;
    }

    public void setProductKey(List<String> productKey) {
        this.productKey = productKey;
    }

    public List<String> getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(List<String> deviceName) {
        this.deviceName = deviceName;
    }

    public List<Integer> getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(List<Integer> provinceId) {
        this.provinceId = provinceId;
    }

    public List<HospitalBean> getHospital() {
        return hospital;
    }

    public void setHospital(List<HospitalBean> hospital) {
        this.hospital = hospital;
    }

    public static class HospitalBean {
        /**
         * organizationId : 1
         * department : {"departmentId":1}
         */

        private Integer organizationId;
        private DepartmentBean department;

        public Integer getOrganizationId() {
            return organizationId;
        }

        public void setOrganizationId(Integer organizationId) {
            this.organizationId = organizationId;
        }

        public DepartmentBean getDepartment() {
            return department;
        }

        public void setDepartment(DepartmentBean department) {
            this.department = department;
        }

        public static class DepartmentBean {
            /**
             * departmentId : 1
             */

            private Integer departmentId;

            public Integer getDepartmentId() {
                return departmentId;
            }

            public void setDepartmentId(Integer departmentId) {
                this.departmentId = departmentId;
            }
        }
    }
}
