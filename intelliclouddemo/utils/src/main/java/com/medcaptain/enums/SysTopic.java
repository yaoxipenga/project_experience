package com.medcaptain.enums;

public enum SysTopic {
    NAME(1),
    PRODUCT_KEY(2),
    DEVICE_NAME(3),
    ID(6);
    private int code;

    SysTopic(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
