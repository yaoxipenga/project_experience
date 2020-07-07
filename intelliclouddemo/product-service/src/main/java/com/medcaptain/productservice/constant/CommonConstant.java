package com.medcaptain.productservice.constant;

public class CommonConstant {
    /**
     * 第几页
     */
    public static final String PAGE = "page";

    /**
     * 第几条
     */
    public static final String PERPAGE = "perPage";

    /**
     * 最大查询长度
     */
    public static final Integer PERPAGE_SIZE = 100;

    /**
     * 变量分割符
     */
    public static final String VARIABLE_SEGMENTATION = ",";

    /**
     * 设备下行id
     */
    public static final String DEVICE_MESSAGE_ID = "message.id:device.";

    /**
     * 消息id重置的标准线
     */
    public static final Integer DEVICE_MESSAGE_ID_MAX = 0xfafb;

    /**
     * ip地址
     */
    public static final String HOST = "medcaptain.iok.la";

    /**
     * 端口
     */
    public static final String PORT = "80";

    /**
     * 域名
     */
    public static final String HOSTNAME = "http://" + HOST;

    /**
     * 总数
     */
    public static final String TOTAL = "total";

    /**
     * 总数
     */
    public static final Integer DEVICE_LENGTH = 255;

    /**
     * 搜索条件长度
     */
    public static final Integer SEARCH_CONDITION_LENGTH = 10;

}
