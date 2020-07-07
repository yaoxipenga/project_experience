package com.medcaptain.enums;

public enum OtaTopic {
    DEVICE(2),
    FUNCTION(3),
    PRODUCT_KEY(4),
    DEVICE_NAME(5);
    private int code;

    OtaTopic(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }


}
