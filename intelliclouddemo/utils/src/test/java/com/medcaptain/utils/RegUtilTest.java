package com.medcaptain.utils;

import io.jsonwebtoken.lang.Assert;
import org.junit.Test;

public class RegUtilTest {
    public static final String LEGAL_PASSWORD_PATTERN = "^\\S[^\u4e00-\u9fa5]{6,12}$";

    /**
     * 合法的用户名规则：长度在4到15位，以字母或数字开头，可包含下划线
     */
    public static final String LEGAL_USERNAME_PATTERN = "^[a-zA-Z0-9]\\w{4,15}$";

    @Test
    public void testIsMatch() {
        String password = "1234";
        Assert.isTrue(!RegUtil.isMatch(password, LEGAL_PASSWORD_PATTERN));
        password = "1234566";
        Assert.isTrue(RegUtil.isMatch(password, LEGAL_PASSWORD_PATTERN));
        password = "12345";
        Assert.isTrue(RegUtil.isMatch(password, LEGAL_USERNAME_PATTERN));
    }
}
