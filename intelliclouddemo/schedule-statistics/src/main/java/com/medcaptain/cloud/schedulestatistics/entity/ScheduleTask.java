package com.medcaptain.cloud.schedulestatistics.entity;

/**
 * 周期性统计任务
 *
 * @author bingwen.ai
 */
public class ScheduleTask {
    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 业务服务名称
     */
    private String businessServiceName;

    /**
     * 统计脚本名称
     */
    private String scriptName;

    /**
     * 最后执行时间
     */
    private long lastExecuteTime;

    /**
     * 结果存放集合名
     */
    private String resultCollection;

    /**
     * 是否用新结果替换以前的结果集
     * <p>同步基本资料表信息，mongoDB关联查询(lookup)</p>
     */
    private boolean isReplaceOldResult;

    /**
     * 执行周期(分钟)
     */
    private long executePeriod;

    /**
     * 备注说明
     */
    private String remark;

    /**
     * 是否启用
     */
    private boolean isEnable;

    /**
     * 是否已删除
     */
    private boolean isDeleted;

    /**
     * 每日任务是否在凌晨2点执行
     */
    private boolean isMorningExecute;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getBusinessServiceName() {
        return businessServiceName;
    }

    public void setBusinessServiceName(String businessServiceName) {
        this.businessServiceName = businessServiceName;
    }

    public String getScriptName() {
        return scriptName;
    }

    public void setScriptName(String scriptName) {
        this.scriptName = scriptName;
    }

    public long getLastExecuteTime() {
        return lastExecuteTime;
    }

    public void setLastExecuteTime(long lastExecuteTime) {
        this.lastExecuteTime = lastExecuteTime;
    }

    public String getResultCollection() {
        return resultCollection;
    }

    public void setResultCollection(String resultCollection) {
        this.resultCollection = resultCollection;
    }

    public boolean isReplaceOldResult() {
        return isReplaceOldResult;
    }

    public void setReplaceOldResult(boolean replaceOldResult) {
        isReplaceOldResult = replaceOldResult;
    }

    public long getExecutePeriod() {
        return executePeriod;
    }

    public void setExecutePeriod(long executePeriod) {
        this.executePeriod = executePeriod;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public boolean isMorningExecute() {
        return isMorningExecute;
    }

    public void setMorningExecute(boolean morningExecute) {
        isMorningExecute = morningExecute;
    }
}
