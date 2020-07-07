package com.medcaptain.parsedata.entity.mongodb;

import com.alibaba.fastjson.JSONObject;
import com.medcaptain.parsedata.entity.device.JsonData;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;

@Document(collection = "property_log")//集合名
public class PropertyLog implements Serializable {
    @Id
    private ObjectId id;

    @Field("product_key")
    private String productKey = null;

    @Field("device_name")
    private String deviceName = null;

    @Field("create_time")
    private Date createTime = null;

    @Field("log_content")
    private JSONObject logContent;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getProductKey() {return productKey;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public JSONObject getLogContent() {
        return logContent;
    }

    public void setLogContent(JSONObject logContent) {
        this.logContent = logContent;
    }
}

