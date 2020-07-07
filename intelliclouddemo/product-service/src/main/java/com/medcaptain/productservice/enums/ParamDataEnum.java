package com.medcaptain.productservice.enums;

/**
 * 事件、服务、属性 通用接口返回数据的类型
 * @author yang
 */
public enum ParamDataEnum {

    /**
     * 事件、服务、属性 通用接口返回数据的类型
     */
    ORDINARY(0, "普通数据，默认"),

    SIMPLE(1, "简略数据");

    private int code;
    private String msg;

    ParamDataEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() { return msg; }
}
