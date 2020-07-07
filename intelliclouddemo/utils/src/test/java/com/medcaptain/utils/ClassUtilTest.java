package com.medcaptain.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ClassUtilTest {
    private static final String THIS_CLASS_BRIEF_NAME = "c.m.utils.ClassUtilTest";

    private static final String THIS_CLASS_NAME = "com.medcaptain.utils.ClassUtilTest";

    @Test
    public void testGetBriefClassName() {
        Class thisClass = ClassUtilTest.class;
        String briefClassName = ClassUtil.getBriefClassName(thisClass);
        assertEquals(briefClassName, THIS_CLASS_BRIEF_NAME);
    }

    @Test
    public void testBriefClassName() {
        String briefClassName = ClassUtil.getBriefClassName(THIS_CLASS_NAME);
        assertEquals(briefClassName, THIS_CLASS_BRIEF_NAME);
    }
}
