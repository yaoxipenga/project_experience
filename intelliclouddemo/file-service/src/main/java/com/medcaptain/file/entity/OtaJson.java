package com.medcaptain.file.entity;

/**
 * ota设备基本格式
 * @author Adam.Chen
 */
public class OtaJson {
    private OtaData params;
    private Integer id;
    private String version;
    private String timestamp;


    public Integer getId() {
        return id;
    }

    public OtaData getParams() {
        return params;
    }



    public void setParams(OtaData params) {
        this.params = params;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
