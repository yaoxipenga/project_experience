package com.medcaptain.utils;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class FontUtilTest {
    @Test
    public void testGetRandomFont() {
        assertNotNull(FontUtil.getRandomFont());
    }
}
