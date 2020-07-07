package com.medcaptain.utils;

public class ByteUtils {
    public static String asHex(byte[] bytes) {
        StringBuffer hexBuffer = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            String hexByte = Integer.toHexString(Byte.toUnsignedInt(bytes[i]));
            if (hexByte.length() == 1)
                hexBuffer.append("0");

            hexBuffer.append(hexByte);
        }
        return hexBuffer.toString();
    }

    public static byte tryParse(String s, byte defaultValue) {
        byte result = defaultValue;
        try {
            result = Byte.valueOf(s);
        } catch (Exception ex) {
        }
        return result;
    }
}
