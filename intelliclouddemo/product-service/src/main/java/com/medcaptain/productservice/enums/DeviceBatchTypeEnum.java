package com.medcaptain.productservice.enums;


/**
 * 前端批量操作设备的type值
 * @author yang
 */
public enum  DeviceBatchTypeEnum {

    /**
     * 1、删除设备
     * 2、禁用
     * 3、启用
     * 4、分配
     */
    DEL(0, "删除设备"),
    DISABLE(1, "禁用"),
    ENABLED(2, "启用");

    private int code;
    private String msg;

    DeviceBatchTypeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() { return msg; }

}
