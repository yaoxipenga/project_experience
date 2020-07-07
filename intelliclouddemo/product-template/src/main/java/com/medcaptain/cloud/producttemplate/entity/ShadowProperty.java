package com.medcaptain.cloud.producttemplate.entity;


/**
 * 影子设备属性
 *
 * @author bingwen.ai
 */
public class ShadowProperty<T> {
    private String deviceTripleId;

    private String identifier;

    private long deviceUploadTime;

    private T value;

    private long serverReceiveTime;

    private String version;

    public String getDeviceTripleId() {
        return deviceTripleId;
    }

    public void setDeviceTripleId(String deviceTripleId) {
        this.deviceTripleId = deviceTripleId;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public long getDeviceUploadTime() {
        return deviceUploadTime;
    }

    public void setDeviceUploadTime(long deviceUploadTime) {
        this.deviceUploadTime = deviceUploadTime;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public long getServerReceiveTime() {
        return serverReceiveTime;
    }

    public void setServerReceiveTime(long serverReceiveTime) {
        this.serverReceiveTime = serverReceiveTime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
