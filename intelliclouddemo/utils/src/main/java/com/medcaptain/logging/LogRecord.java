package com.medcaptain.logging;

import com.alibaba.fastjson.JSON;

/**
 * 文件日志记录
 *
 * @author bingwen.ai
 */
public class LogRecord {
    protected String logClass;

    protected String className;

    protected String methodName;

    public String getLogClass() {
        return logClass;
    }

    public void setLogClass(String logClass) {
        this.logClass = logClass;
    }

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

    /**
     * 使用json格式化日志记录，便于后期统计处理
     *
     * @return json格式化日志记录
     */
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
