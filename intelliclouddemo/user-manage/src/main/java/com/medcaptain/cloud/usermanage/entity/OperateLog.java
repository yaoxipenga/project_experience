package com.medcaptain.cloud.usermanage.entity;

/**
 * 用户操作日志实体
 *
 * @author bingwen.ai
 */
public class OperateLog {
    private Integer logId;

    private Long logTime;

    private String clientIp;

    private Integer userId;

    private String logType;

    private Byte logLevel;

    private String logContent;

    public OperateLog(long logTime, String clientIp, int userId, String logType, String logContent, byte logLevel) {
        this.logTime = logTime;
        this.clientIp = clientIp;
        this.userId = userId;
        this.logType = logType;
        this.logContent = logContent;
        this.logLevel = logLevel;
    }

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public Long getLogTime() {
        return logTime;
    }

    public void setLogTime(Long logTime) {
        this.logTime = logTime;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp == null ? null : clientIp.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType == null ? null : logType.trim();
    }

    public Byte getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(Byte logLevel) {
        this.logLevel = logLevel;
    }

    public String getLogContent() {
        return logContent;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent == null ? null : logContent.trim();
    }
}