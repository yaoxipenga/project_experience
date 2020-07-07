package com.medcaptain.utils.encrypt;

import com.medcaptain.utils.CharsetUtil;

/**
 * Base64的编码和解码
 */
public final class Base64 {

    /**
     * Base64编码
     *
     * @param content 需编码的内容
     * @return 编码后的内容
     */
    public static String encodeToString(String content) {
        byte[] contentBytes = content.getBytes(CharsetUtil.UTF8);
        return java.util.Base64.getEncoder().encodeToString(contentBytes);
    }

    /**
     * Base64解码
     *
     * @param content 需解码的内容
     * @return 解码后的内容
     */
    public static String decodeToString(String content) {
        byte[] contentBytes = java.util.Base64.getDecoder().decode(content);
        return new String(contentBytes, CharsetUtil.UTF8);
    }


    /**
     * Base64编码地址
     *
     * @param content 需编码的URL地址
     * @return 编码后的内容
     */
    public static String encodeURLToString(String content) {
        byte[] contentBytes = content.getBytes(CharsetUtil.UTF8);
        return java.util.Base64.getUrlEncoder().encodeToString(contentBytes);
    }

    /**
     * Base64解码URL地址
     *
     * @param content 需解码的内容
     * @return 解码后的URL地址
     */
    public static String decodeURLToString(String content) {
        byte[] contentBytes = java.util.Base64.getUrlDecoder().decode(content);
        return new String(contentBytes, CharsetUtil.UTF8);
    }

}
