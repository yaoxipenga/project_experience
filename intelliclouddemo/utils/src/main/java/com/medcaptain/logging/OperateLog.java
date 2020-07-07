package com.medcaptain.logging;

/**
 * 关键操作日志
 *
 * @author bingwen.ai
 */
public class OperateLog extends LogRecord {
    private int userID;

    private long logTime;

    private String clientIP;

    private String logContent;

    private String logType;

    private OperateLogLevel logLevel;

    public OperateLog(int userID, long logTime, String clientIP, String logContent, String logType, OperateLogLevel logLevel) {
        this.logClass = this.getClass().getSimpleName();
        this.userID = userID;
        this.logTime = logTime;
        this.clientIP = clientIP;
        this.logContent = logContent;
        this.logType = logType;
        this.logLevel = logLevel;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public long getLogTime() {
        return logTime;
    }

    public void setLogTime(long logTime) {
        this.logTime = logTime;
    }

    public String getClientIP() {
        return clientIP;
    }

    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    public String getLogContent() {
        return logContent;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public OperateLogLevel getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(OperateLogLevel logLevel) {
        this.logLevel = logLevel;
    }
}
