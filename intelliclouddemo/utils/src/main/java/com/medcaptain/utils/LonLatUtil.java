package com.medcaptain.utils;


import java.math.BigDecimal;
import java.util.Random;

/***
 * 随机经纬度
 * @author yangzhixiong
 */
public class LonLatUtil {

    /**
     * @param LonLat：最新经度 MaxLon： 最大经度   MinLat：最新纬度   MaxLat：最大纬度    type：设置返回经度还是纬度
     * @param type: 真 lon 经度    假 Lat 纬度
     * @return
     * @throws
     * @Title: randomLonLat
     * @Description: 在矩形内随机生成经纬度
     */
    public static String randomLonLat(double[] LonLat, Boolean type) {
        double MinLon = LonLat[0];
        double MaxLon = LonLat[1];
        double MinLat = LonLat[2];
        double MaxLat = LonLat[3];
        Random random = new Random();
        BigDecimal db = new BigDecimal(Math.random() * (MaxLon - MinLon) + MinLon);
        // 小数后6位
        String lon = db.setScale(6, BigDecimal.ROUND_HALF_UP).toString();
        db = new BigDecimal(Math.random() * (MaxLat - MinLat) + MinLat);
        String lat = db.setScale(6, BigDecimal.ROUND_HALF_UP).toString();
        if (type) {
            return lon;
        } else {
            return lat;
        }
    }

    /**
     * 41.4156821692,101.0742187500
     * 41.4156821692,122.0866324253
     *
     * 18.0623123045,101.0742187500
     * 18.0623123045,122.0866324253
     */
    public static String getChinaLonLat(Boolean type){
//        110.2436771476, 112.5162347294, 35.9862064059, 30.0122328171
        double[] LonLat = new double[]{
                108.2436771476, 117.5162347294, 40.9862064059, 25.0122328171
        };
        return randomLonLat(LonLat, type);
    }
}
