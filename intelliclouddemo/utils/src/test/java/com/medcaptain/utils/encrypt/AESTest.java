package com.medcaptain.utils.encrypt;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AESTest {

    private String original = "$#hello&你好";
    private String key = "sjal#9,f3@f密码";
    private String encrypted = "YsOlqibIRtL0njY76Bc0pQ==";


    @Test
    public void testEncrypt() {
        try {
            assertEquals(AESUtil.encrypt(original, key),encrypted);
        } catch (Exception ex) {
        }
    }

    @Test
    public void testDecrypt() {
        try {
            assertEquals(AESUtil.decrypt(encrypted, key),original);
        } catch (Exception ex) {
        }
    }
}
