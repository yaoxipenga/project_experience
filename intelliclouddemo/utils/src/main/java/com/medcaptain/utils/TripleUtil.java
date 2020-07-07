package com.medcaptain.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 三元组
 * @author yangzhixiong
 */
public class TripleUtil {

    /**
     * 获取三元组
     *
     * @param tripLeng 三元组长度
     * @return 规定长度的三元组
     */
    public static String getTriple(Integer tripLeng) {
        return UUIDUtil.getUUID().substring(0, tripLeng);
    }

    /**
     * 获取三元组
     *
     * @param tripLeng 长度
     * @param num      数量
     * @return 结果
     */
    public static List<String> getTripleList(Integer tripLeng, Integer num) {
        List<String> tripleList = new ArrayList<>();
        int i = 0;
        for (; i < num; i++) {
            tripleList.add(getTriple(tripLeng));
        }
        return tripleList;
    }
}
