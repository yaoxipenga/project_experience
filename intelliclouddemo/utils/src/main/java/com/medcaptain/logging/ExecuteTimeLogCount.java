package com.medcaptain.logging;

/**
 * 方法执行时间统计记录
 *
 * @author bingwen.ai
 */
public class ExecuteTimeLogCount {
    /**
     * 执行类名称
     */
    private String className;

    /**
     * 执行方法名称
     */
    private String methodName;

    /**
     * 执行开始的次数
     */
    private long startCount;

    /**
     * 执行完成的次数
     */
    private long finishCount;

    /**
     * 最小执行时间
     */
    private long minimumExecuteTime;

    /**
     * 最大执行时间
     */
    private long maximumExecuteTime;

    /**
     * 执行总耗时
     */
    private long totalExecuteTime;

    /**
     * 首次执行时间
     */
    private long firstExecuteTime;

    /**
     * 最后一次执行时间
     */
    private long lastExecuteTime;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public long getStartCount() {
        return startCount;
    }

    public void setStartCount(long startCount) {
        this.startCount = startCount;
    }

    public long getFinishCount() {
        return finishCount;
    }

    public void setFinishCount(long finishCount) {
        this.finishCount = finishCount;
    }

    public long getMinimumExecuteTime() {
        return minimumExecuteTime;
    }

    public void setMinimumExecuteTime(long minimumExecuteTime) {
        this.minimumExecuteTime = minimumExecuteTime;
    }

    public long getMaximumExecuteTime() {
        return maximumExecuteTime;
    }

    public void setMaximumExecuteTime(long maximumExecuteTime) {
        this.maximumExecuteTime = maximumExecuteTime;
    }

    public long getTotalExecuteTime() {
        return totalExecuteTime;
    }

    public void setTotalExecuteTime(long totalExecuteTime) {
        this.totalExecuteTime = totalExecuteTime;
    }

    public long getFirstExecuteTime() {
        return firstExecuteTime;
    }

    public void setFirstExecuteTime(long firstExecuteTime) {
        this.firstExecuteTime = firstExecuteTime;
    }

    public long getLastExecuteTime() {
        return lastExecuteTime;
    }

    public void setLastExecuteTime(long lastExecuteTime) {
        this.lastExecuteTime = lastExecuteTime;
    }

    public double getAverageEexcuteTime() {
        if (finishCount > 0) {
            return (double) totalExecuteTime / finishCount;
        }
        return 0;
    }
}
