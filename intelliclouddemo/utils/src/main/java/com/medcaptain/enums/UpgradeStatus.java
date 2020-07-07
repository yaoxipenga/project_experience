package com.medcaptain.enums;

public enum UpgradeStatus {
    WAIT_TO_UPGRADE(0, "待升级"),
    UPGRADING(1, "升级中"),
    SUCCESS(2, "升级成功"),
    DEFEAT(3, "升级失败");
    private int code;
    private String msg;

    UpgradeStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() { return msg; }

    public static UpgradeStatus getEnumById(int code){
        for(UpgradeStatus idEnum : UpgradeStatus.values()){
            if (idEnum.getCode() == code){
                return idEnum;
            }
        }
        return null;
    }
}
