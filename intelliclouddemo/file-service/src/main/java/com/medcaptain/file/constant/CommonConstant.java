package com.medcaptain.file.constant;

import net.sf.json.JSONObject;

public class CommonConstant {
    /**
     * 告警信息生存时间
     */
    public static final Integer ALERT_TTL = 300;

    /**
     * 消息id重置的标准线
     */
    public static final Integer DEVICE_MESSAGE_ID_MAX = 0xfafb;

    /**
     * topic设备段字符
     */
    public static final String DEVICE = "device";

    /**
     * 不计入的产品类型
     */
    public static final String MY_PRODUCT_KEY = "medcaptain0";

    /**
     * 设备下行id
     */
    public static final String DEVICE_MESSAGE_ID = "message.id:device.";

    public static final String DEVICE_POST_MESSAGE = "message:device";

    public static final Long FLUSH_TASK_SLEEP_TIME = 30 * 60 * 1000L;

    public static final Long DEVICE_MESSAGE_FLUSH_TIME = 10 * 1000L;

    public static final Double DEVICE_MESSAGE_FLUSH_TIME_START = 0.0;

    public static final String JSON_VERSION = "1.0.0";

    public static final String URL_FOR_FILE = "http://medcaptain.iok.la/api/file/file?token=";

    public static final String OTA_UPGRADE_TOPIC = "/ota/device/upgrade/${productKey}/${deviceName}";

    public static final String ONLINE_PRODUCT_DEVICE = "online:${productKey}";

    public static JSONObject getData(String jsonData){
        return JSONObject.fromObject(jsonData);
    }

}
