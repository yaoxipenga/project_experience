package com.medcaptain.enums;

/**
 * 操作功能权限名称常量
 *
 * @author : yangzhixiong
 * @description : 用户权限的code码
 * @date : 2019/03/11
 */
public enum OperatePermissionEnum {
    /**
     * ！！msg 内容请勿更改，本内容和数据库的前端资源表中 resource_type = 3 的数据相同，返回给user服务查询数据库
     */
    ERROR(0, "错误，数据库没有本code"),
    PLATFORM_MANAGE(1, "平台管理员"),
    ORGANIZATION_MANAGE(2, "机构管理员"),
    OPERATE_DEVICE(3, "设备操作员");

    private int code;
    private String msg;

    OperatePermissionEnum(int code, String msg) {
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
