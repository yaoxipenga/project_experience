package com.medcaptain.cloud.usermanage.constant;

/**
 * 用户管理相关常量
 *
 * @author bingwen.ai
 */
public class UserConstant {
    public static final String ENCRYPT_KEY_PREFIX = "MEDCAPTAIN";

    public static final int MAX_LOGIN_FAILURE_TIMES = 3;

    public static final int MIN_REQUEST_TYPE = 1;

    public static final int MAX_REQUEST_TYPE = 4;

    public static final String ENABLE_INT = "1";

    public static final String DISABLE_INT = "0";

    /**
     * 合法的密码规则，长度在6到12位，允许数字、字母和特殊符号
     */
    public static final String LEGAL_PASSWORD_PATTERN = "^\\S[^\u4e00-\u9fa5]{5,11}$";

    /**
     * 合法的用户名规则：长度在6到12位，以字母或数字开头，可包含下划线
     */
    public static final String LEGAL_USERNAME_PATTERN = "^[a-zA-Z0-9]\\w{5,11}$";

    public static final String ILLEGAL_PASSWORD_NOTICE = "密码长度为6到12位，可包含数字、英文字母和特殊符号。";

    public static final String ILLEGAL_USERNAME_NOTICE = "用户名长度为6到12位，以数字、英文字母开头，可包含下划线。";
}
