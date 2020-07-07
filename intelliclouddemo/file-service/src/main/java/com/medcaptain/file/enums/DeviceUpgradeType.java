package com.medcaptain.file.enums;

/**
 * 设备ota升级类型
 * @author Adam.Chen
 */
public enum DeviceUpgradeType {
    MASTER_CONTROL(0, "主控升级"),
    MODULE(1, "模组升级");
    private int code;

    private String message;

    DeviceUpgradeType(int code, String message) {
        this.code = code;this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
