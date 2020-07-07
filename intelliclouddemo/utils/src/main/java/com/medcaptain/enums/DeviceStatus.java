package com.medcaptain.enums;

public enum DeviceStatus {
    NOT_ACTIVE(0, "设备未激活"),
    ONLINE(1, "设备在线"),
    OFFLINE(2, "设备离线"),
    ABNORMAL_OFFLINE(3, "设备异常下线");
    private int code;
    private String msg;

    DeviceStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() { return msg; }
}
