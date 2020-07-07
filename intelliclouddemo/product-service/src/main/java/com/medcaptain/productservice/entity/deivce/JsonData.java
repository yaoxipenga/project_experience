package com.medcaptain.productservice.entity.deivce;

import com.alibaba.fastjson.JSONObject;

public class JsonData {
    private Integer id;
    private JSONObject params;
    private String timestamp;
    private String version;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public Integer getId() {
        return id;
    }

    public JSONObject getParams() {
        return params;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setParams(JSONObject params) {
        this.params = params;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
