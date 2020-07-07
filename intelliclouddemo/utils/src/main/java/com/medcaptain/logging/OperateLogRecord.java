package com.medcaptain.logging;

/**
 * 用户操作记录，用于记录用户操作到数据库
 *
 * @author bingwen.ai
 */
public class OperateLogRecord {
    /**
     * 日志分类
     */
    private String logType;

    /**
     * 日志内容
     */
    private String logContent;

    /**
     * 日志级别
     */
    private OperateLogLevel logLevel;

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getLogContent() {
        return logContent;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }

    public OperateLogLevel getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(OperateLogLevel logLevel) {
        this.logLevel = logLevel;
    }

    public OperateLogRecord(String logType, String logContent, OperateLogLevel logLevel) {
        this.logType = logType;
        this.logContent = logContent;
        this.logLevel = logLevel;
    }
}
