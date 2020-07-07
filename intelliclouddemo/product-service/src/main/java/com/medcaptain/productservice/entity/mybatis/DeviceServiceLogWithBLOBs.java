package com.medcaptain.productservice.entity.mybatis;

public class DeviceServiceLogWithBLOBs extends DeviceServiceLog {
    private String returnLogContent;

    private String logContent;

    public String getReturnLogContent() {
        return returnLogContent;
    }

    public void setReturnLogContent(String returnLogContent) {
        this.returnLogContent = returnLogContent == null ? null : returnLogContent.trim();
    }

    public String getLogContent() {
        return logContent;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent == null ? null : logContent.trim();
    }
}