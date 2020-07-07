package com.medcaptain.enums;

/**
 * 产品和设备库中，删除字段的枚举
 * @author yang
 */
public enum DelEnum {

    /**
     * 产品和设备库中，删除字段的枚举
     */
    NOT_DEL(0, "未删除"),

    DEL(1, "标识删除");

    private int code;
    private String msg;

    DelEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() { return msg; }
}
