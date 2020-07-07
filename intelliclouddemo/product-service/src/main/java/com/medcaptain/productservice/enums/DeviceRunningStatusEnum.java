package com.medcaptain.productservice.enums;

public enum DeviceRunningStatusEnum {
    NOT_ACTIVE(0, "未激活"),
    ONLINE(1, "在线"),
    NORMAL_OFFLINE(2,"正常"),
    ABNORMAL_OFFLINE(3,"异常下线");

    private int code;
    private String status;

    DeviceRunningStatusEnum(int code, String status) {
        this.code = code;
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public static DeviceRunningStatusEnum getEnumById(int code){
        for(DeviceRunningStatusEnum idEnum : DeviceRunningStatusEnum.values()){
            if (idEnum.getCode() == code){
                return idEnum;
            }
        }
        return null;
    }
}
