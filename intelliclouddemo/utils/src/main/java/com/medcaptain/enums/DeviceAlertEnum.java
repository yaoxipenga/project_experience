package com.medcaptain.enums;

/**
 * @author zhixiong
 * @date 2019-04-17
 */
public enum DeviceAlertEnum {
    /**
     *
     */
    SUCCESS(1, "成功"),
    ALARM(2, "告警"),
    MESSAGE(3, "消息"),
    ERROR(4, "错误");

    DeviceAlertEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    int code;
    String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}