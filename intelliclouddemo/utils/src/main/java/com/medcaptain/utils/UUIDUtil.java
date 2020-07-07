package com.medcaptain.utils;


import java.util.UUID;

/**
 * @author : yangzhixiong
 * @description : 生成UUID的工具类
 * @date : 2018/9/18
 */
public class UUIDUtil {

    /**
     * 获得指定数目的UUID
     * @param number int 需要获得的UUID数量
     * @return String[] UUID数组
     */
    public static String[] getUUIDList(int number){
        if(number < 1){
            return null;
        }
        String[] retArray = new String[number];
        for(int i=0;i<number;i++){
            retArray[i] = getUUID();
        }
        return retArray;
    }

    /**
     * 获得一个UUID
     * @return String UUID
     */
    public static String getUUID(){
        String uuid = UUID.randomUUID().toString();
        //去掉“-”符号
        return uuid.replaceAll("-", "");
    }
}
