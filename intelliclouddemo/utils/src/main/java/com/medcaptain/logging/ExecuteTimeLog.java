package com.medcaptain.logging;

import com.medcaptain.utils.ClassUtil;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Method;

/**
 * 方法执行时间日志记录
 * <p/>用于统计方法调用次数及执行时间
 *
 * @author bingwen.ai
 */
public class ExecuteTimeLog extends LogRecord {
    private long executeTimeInMs;

    private long executeStartTime;

    public ExecuteTimeLog(@NotNull Method executeMethod, long executeTimeInMs, long executeStartTime) {
        this.className = ClassUtil.getBriefClassName(executeMethod.getDeclaringClass());
        this.methodName = executeMethod.getName();
        this.executeTimeInMs = executeTimeInMs;
        this.executeStartTime = executeStartTime;
        this.logClass = this.getClass().getSimpleName();
    }

    public ExecuteTimeLog(String className, String methodName, long executeTimeInMs, long executeStartTime) {
        this.className = className;
        this.methodName = methodName;
        this.executeTimeInMs = executeTimeInMs;
        this.executeStartTime = executeStartTime;
        this.logClass = this.getClass().getSimpleName();
    }

    public long getExecuteTimeInMs() {
        return executeTimeInMs;
    }

    public void setExecuteTimeInMs(long executeTimeInMs) {
        this.executeTimeInMs = executeTimeInMs;
    }

    public long getExecuteStartTime() {
        return executeStartTime;
    }

    public void setExecuteStartTime(long executeStartTime) {
        this.executeStartTime = executeStartTime;
    }
}
