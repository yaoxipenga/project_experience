package com.medcaptain.productservice.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * 批量操作设备
 */
public class DeviceBatchEntity {


    /**
     * “type": 1
     *     “batchList” : [{"productKey":"123","deviceName":"456"},{"productKey":"1231","deviceName":"4561"},{"productKey":"1232","deviceName":"4562"},{"productKey":"1233","deviceName":"4563"}]
     */

    @JsonProperty("type")
    private int type;
    @JsonProperty("batchList")
    private List<batchListBean> batchList;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<batchListBean> getBatchList() {
        return batchList;
    }

    public void setBatchList(List<batchListBean> batchList) {
        this.batchList = batchList;
    }

    public static class batchListBean {
        /**
         * productKey : 123
         * deviceName : 456
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
}
