package com.medcaptain.utils;


import com.medcaptain.utils.encrypt.Base64;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Base64UtilTest {
    private String originalContent = "MedCaptain麦科田";
    private String encodeContent = "TWVkQ2FwdGFpbum6puenkeeUsA==";

    private String originalURL ="https://cloud.medcaptain.com/api_v2/query?name=奥吉弗&age=33&keyword=暗杀计划";
    private String encodeURL = "aHR0cHM6Ly9jbG91ZC5tZWRjYXB0YWluLmNvbS9hcGlfdjIvcXVlcnk_bmFtZT3lpaXlkInlvJcmYWdlPTMzJmtleXdvcmQ95pqX5p2A6K6h5YiS";

    @Test
    public void testEncodeToString() {
        assertEquals(encodeContent, Base64.encodeToString(originalContent));
    }

    @Test
    public void testDecodeToString() {
        assertEquals(originalContent, Base64.decodeToString(encodeContent));
    }

    @Test
    public void testEncodeURLToString() {
        assertEquals(encodeURL, Base64.encodeURLToString(originalURL));
    }

    @Test
    public void testDecodeURLToString() {
        assertEquals(originalURL, Base64.decodeURLToString(encodeURL));
    }

}