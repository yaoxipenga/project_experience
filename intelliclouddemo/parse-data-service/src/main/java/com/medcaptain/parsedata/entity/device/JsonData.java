package com.medcaptain.parsedata.entity.device;

import com.alibaba.fastjson.JSONObject;

public class JsonData {
    private Integer id;
    private JSONObject params;
    private Long timestamp;
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

    public void setParams(JSONObject params) {
        this.params = params;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
