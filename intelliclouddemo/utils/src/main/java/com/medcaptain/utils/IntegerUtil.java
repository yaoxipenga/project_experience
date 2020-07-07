package com.medcaptain.utils;

import java.nio.ByteBuffer;

public class IntegerUtil {

    public static int tryParse(String intValue, int defaultValue) {
        int result = defaultValue;
        try {
            result = Integer.parseInt(intValue);
        } catch (Exception ex) {

        }
        return result;
    }

    public static byte[] toBytes(int intValue) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putInt(intValue);
        buffer.flip();
        return buffer.array();
    }

    public static int getInteger(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.put(bytes);
        buffer.flip();
        return buffer.getInt();
    }
}
