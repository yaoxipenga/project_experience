package com.medcaptain.productservice.entity.dto.mongo;

import net.sf.json.JSONObject;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "property_log")
public class PropertyLog {
    @Id
    private ObjectId id;

    @Field("product_key")
    private String productKey;

    @Field("device_name")
    private String deviceName;

    @Field("create_time")
    private String createTime;

    @Field("log_content")
    private JSONObject logContent;

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
