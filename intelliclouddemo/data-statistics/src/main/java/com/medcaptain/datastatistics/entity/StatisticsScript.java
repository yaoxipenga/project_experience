package com.medcaptain.datastatistics.entity;

/**
 * 统计脚本实体
 *
 * @author bingwen.ai
 */
public class StatisticsScript {
    private String scriptName;

    private String databaseType;

    private String scriptContent;

    private String remark;

    private Byte isEnable;

    private Byte isDeleted;

    public String getScriptName() {
        return scriptName;
    }

    public void setScriptName(String scriptName) {
        this.scriptName = scriptName == null ? null : scriptName.trim();
    }

    public String getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(String databaseType) {
        this.databaseType = databaseType == null ? null : databaseType.trim();
    }

    public String getScriptContent() {
        return scriptContent;
    }

    public void setScriptContent(String scriptContent) {
        this.scriptContent = scriptContent == null ? null : scriptContent.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Byte getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Byte isEnable) {
        this.isEnable = isEnable;
    }

    public Byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }
}