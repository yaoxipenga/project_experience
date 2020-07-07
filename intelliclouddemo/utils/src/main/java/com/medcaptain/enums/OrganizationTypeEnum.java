package com.medcaptain.enums;

public enum OrganizationTypeEnum {

    /**
     * t_organization 组织机构信息表中的  organization_type 字段
     * 内容信息
     */
    organization(1,"机构"),
    hospital(0,"医院");

    private int code;
    private String msg;

    OrganizationTypeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

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
