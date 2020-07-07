package com.medcaptain.utils.encrypt;

import com.medcaptain.utils.ByteUtils;
import com.medcaptain.utils.CharsetUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 计算MD5
 */
public class MD5Util {

    private static final String MD5_DigestAlgorithm = "MD5";

    /**
     * 计算字节数组的MD5
     *
     * @param bytes 需计算的字节数组
     * @return MD5值
     */
    public static String md5(byte[] bytes) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(MD5_DigestAlgorithm);
            byte[] digestedBytes = messageDigest.digest(bytes);
            return ByteUtils.asHex(digestedBytes);
        } catch (NoSuchAlgorithmException ex) {
            return null;
        }
    }

    /**
     * 计算字字符串的MD5
     *
     * @param content 需计算的字符串
     * @return MD5值
     */
    public static String md5(String content) {
        byte[] digestedBytes = content.getBytes(CharsetUtil.UTF8);
        return md5(digestedBytes);
    }
}
