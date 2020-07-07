package com.medcaptain.enums;

public enum  FileTopic {
    DEVICE(2),
    FUNCTION(3),
    PRODUCT_KEY(4),
    DEVICE_NAME(5);
    private int code;

    FileTopic(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
