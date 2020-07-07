package com.medcaptain.utils.encrypt;

import org.junit.Test;

import static org.junit.Assert.*;

public class MD5UtilTest {
    private String originalContent = "MedCaptain麦科田@2018";

    private String md5 = "65ec1f106ab6b51b3d92183ae8fe0d0c";

    @Test
    public void md5() {
        assertEquals(md5, MD5Util.md5(originalContent));
    }

}