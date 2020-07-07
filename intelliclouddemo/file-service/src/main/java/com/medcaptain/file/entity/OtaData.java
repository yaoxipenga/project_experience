package com.medcaptain.file.entity;

/**
 * ota数据格式
 * @author Adam.Chen
 */
public class OtaData {
    private Integer size;
    private String sign;
    private String version;
    private String url;
    private String signMethod;
    private String md5;
    private Integer type;

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getSize() {
        return size;
    }

    public String getSign() {
        return sign;
    }

    public String getSignMethod() {
        return signMethod;
    }

    public String getVersion() {
        return version;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public void setSignMethod(String signMethod) {
        this.signMethod = signMethod;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}
