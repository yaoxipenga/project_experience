package com.medcaptain.parsedata.entity.mongodb;

import com.alibaba.fastjson.JSONObject;
import com.medcaptain.parsedata.entity.device.JsonData;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Document(collection = "event_log")//集合名
public class EventLog implements Serializable {
    @Id
    private ObjectId id;

    @Field("product_key")
    private String productKey = null;

    @Field("device_name")
    private String deviceName = null;

    @Field("create_time")
    private String createTime = null;

    @Field("log_content")
    private JSONObject logContent;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public JSONObject getLogContent() {
        return logContent;
    }

    public void setLogContent(JSONObject logContent) {
        this.logContent = logContent;
    }
}
