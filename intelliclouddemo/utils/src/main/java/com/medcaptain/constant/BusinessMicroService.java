package com.medcaptain.constant;

/**
 * 云平台业务微服务常量
 *
 * @author bingwen.ai
 */
public class BusinessMicroService {
    /**
     * 数据统计服务
     * <p>用于前端统计数据获取</p>
     */
    public static final String DATA_STATISTIC = "data-statistics";

    /**
     * 定时统计服务
     * <p>定时调用其它业务服务的统计接口获取统计中间结果</p>
     */
    public static final String SCHEDULE_STATISTICS = "schedule-statistics";

    /**
     * 文件存储服务
     */
    public static final String FAST_DFS = "fast-dfs";

    /**
     * 文件信息管理服务
     * <p>管理设备固件、日志文件信息</p>
     */
    public static final String FILE_MANAGE = "file-service";

    /**
     * MQTT客户端鉴权服务
     * <p>MQTT客户端登录及topic鉴权</p>
     */
    public static final String MQTT_AUTHENTICATE = "mqtt-device-authenticate";

    /**
     * MQTT消息转发
     */
    public static final String MQTT_FORWARDER = "mqtt-forwarder";

    /**
     * 设备上报消息处理服务
     * <p>存储和处理设备上报及下载的消息</p>
     */
    public static final String PARSE_DATE = "parse-data-service";

    /**
     * 产品设备信息管理服务
     */
    public static final String PRODUCT_MANAGE = "product-service";

    /**
     * 产品消息模板解析服务
     * <p>校验及解析设备上报消息，管理影子设备数据</p>
     */
    public static final String PRODUCT_TEMPLATE = "product-template";

    /**
     * 消息推送服务
     * <p>前端页面、用户短信及电子邮件、站内消息的推送管理</p>
     */
    public static final String MESSAGE_PUSH = "push-socket-service";

    /**
     * 用户信息管理服务
     */
    public static final String USER_MANAGE = "user-manage";
}
