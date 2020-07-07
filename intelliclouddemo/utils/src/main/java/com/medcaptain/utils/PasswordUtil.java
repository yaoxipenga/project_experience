package com.medcaptain.utils;

/**
 * 提供密码相关的工具类
 */
public final class PasswordUtil {

    /**
     * 必选包含数字、大写字母、小写字母、特殊字符，长度在8到15位
     */
    private static final String SEC_PASSWORD =
            "^(?=.*?[0-9])(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[@!#$%^&*()_+\\.\\-\\?<>'\"|=]+).{8,15}$";


    /**
     * 判断一个密码是否健壮
     * 必选包含数字、大写字母、小写字母、特殊字符，长度在8到15位
     *
     * @param password
     * @return
     */
    public final static boolean isSec(String password) {
        return RegUtil.isMatch(password, SEC_PASSWORD);
    }
}
