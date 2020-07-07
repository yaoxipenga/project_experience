package com.medcaptain.utils;

public class LongUtil {
    public static long tryParse(String longValue, long defaultValue) {
        try {
            return Long.valueOf(longValue);
        } catch (Exception ex) {
            return defaultValue;
        }
    }
}
