package com.medcaptain.file.entity.dto;

import java.util.Date;

/**
 * 返回前端的操作日志格式
 * @author Adam.Chen
 */
public class OptInfoEntity {
    private String logContent;
    private String operationType;
    private Date createAt;

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getLogContent() {
        return logContent;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }
}
