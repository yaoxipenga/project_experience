package com.medcaptain.productservice.entity.dto.mongo;

import com.alibaba.fastjson.JSONObject;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "device_log")
public class DeviceLogEntity {
    @Id
    private ObjectId id;

    @Field("product_key")
    private String productKey = null;

    @Field("device_name")
    private String deviceName = null;

    @Field("create_time")
    private Long createTime = null;

    @Field("log_content")
    private JSONObject logContent = null;

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

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public JSONObject getLogContent() {
        return logContent;
    }

    public void setLogContent(JSONObject logContent) {
        this.logContent = logContent;
    }
}
