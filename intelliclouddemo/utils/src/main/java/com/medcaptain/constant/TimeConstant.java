package com.medcaptain.constant;

/**
 * 时间常量
 *
 * @author bingwen.ai
 */
public class TimeConstant {
    /**
     * 一秒钟的毫秒数
     */
    public static final int MILLISECONDS_ONE_SECOND = 1000;

    /**
     * 一分钟的秒数
     */
    public static final int SECONDS_ONE_MINUTE = 60;

    /**
     * 一分钟的毫秒数
     */
    public static final int MILLISECONDS_ONE_MINUTE = SECONDS_ONE_MINUTE * MILLISECONDS_ONE_SECOND;

    /**
     * 一小时的分钟数
     */
    public static final int MINUTES_ONE_HOUR = 60;

    /**
     * 一小时的秒数
     */
    public static final int SECONDS_ONE_HOUR = MINUTES_ONE_HOUR * MINUTES_ONE_HOUR;

    /**
     * 一小时的毫秒数
     */
    public static final long MILLISECONDS_ONE_HOUR = SECONDS_ONE_HOUR * MILLISECONDS_ONE_SECOND;

    /**
     * 一天的小时数
     */
    public static final int HOURS_ONE_DAY = 24;

    /**
     * 一天的分钟数
     */
    public static final int MINUTES_ONE_DAY = HOURS_ONE_DAY * MINUTES_ONE_HOUR;

    /**
     * 一天的秒数
     */
    public static final long SECONDS_ONE_DAY = HOURS_ONE_DAY * SECONDS_ONE_HOUR;

    /**
     * 一天的毫秒数
     */
    public static final long MILLISECONDS_ONE_DAY = SECONDS_ONE_DAY * MILLISECONDS_ONE_SECOND;

    /**
     * 一年的月份数
     */
    public static final int MONTHS_ONE_YEAR = 12;

}
