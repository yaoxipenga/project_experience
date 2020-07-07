package com.medcaptain.cloud.usermanage.entity;

/**
 * 统计脚本实体
 *
 * @author bingwen.ai
 */
public class StatisticsScript {
    /**
     * 脚本名称
     * <p>使用该字段查找对应的查询语句</p>
     */
    private String scriptName;

    /**
     * 数据库类型:SQL/MONGODB/HBASE/REDIS
     */
    private String databaseType;

    /**
     * 具体的查询语句
     */
    private String scriptContent;

    /**
     * 脚本说明
     */
    private String remark;

    /**
     * 启用标记：1 = 已启用; 0 = 已禁用
     */
    private Byte isEnable;

    /**
     * 删除标记: 1 = 已删除 ; 0 = 未删除
     */
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