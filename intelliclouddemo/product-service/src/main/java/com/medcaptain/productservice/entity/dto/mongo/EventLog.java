package com.medcaptain.productservice.entity.dto.mongo;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "event_log")
public class EventLog {
    @Id
    private ObjectId id;

    @Field("product_key")
    private String productKey;

    @Field("device_name")
    private String deviceName;

    @Field("log_content")
    private EventLogContent logContent;

    @Field("create_time")
    private String createTime;

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

    public EventLogContent getLogContent() {
        return logContent;
    }

    public void setLogContent(EventLogContent logContent) {
        this.logContent = logContent;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
