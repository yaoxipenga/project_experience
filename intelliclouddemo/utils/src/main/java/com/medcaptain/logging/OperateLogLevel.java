package com.medcaptain.logging;

/**
 * 关键操作日志级别
 * <p>关键操作记录：长期保存</p>
 * <p>一般操作记录：可在一段时间后删除</p>
 *
 * @author bingwen.ai
 */
public enum OperateLogLevel {
    /**
     * 关键系统操作，需长期保存
     */
    critical,
    /**
     * 一般系统操作，可在一段时间后删除
     */
    normal
}
