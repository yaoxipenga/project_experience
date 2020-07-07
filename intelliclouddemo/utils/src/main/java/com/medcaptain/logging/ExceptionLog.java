package com.medcaptain.logging;

import com.medcaptain.utils.ClassUtil;

import java.util.Map;

/**
 * 发生异常时的本地日志记录，结构化便于后续处理
 *
 * @author bingwen.ai
 */
public class ExceptionLog extends LogRecord {
    private int codeLineNumber;

    private long logTime;

    private String exceptionClass;

    private String exceptionMessage;

    private String businessDescription;

    private Map<String, Object> parameters;

    public int getCodeLineNumber() {
        return codeLineNumber;
    }

    public void setCodeLineNumber(int codeLineNumber) {
        this.codeLineNumber = codeLineNumber;
    }

    public long getLogTime() {
        return logTime;
    }

    public void setLogTime(long logTime) {
        this.logTime = logTime;
    }

    public String getExceptionClass() {
        return exceptionClass;
    }

    public void setExceptionClass(String exceptionClass) {
        this.exceptionClass = exceptionClass;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public String getBusinessDescription() {
        return businessDescription;
    }

    public void setBusinessDescription(String businessDescription) {
        this.businessDescription = businessDescription;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public ExceptionLog(Exception exception, String businessDescription) {
        this.logClass = this.getClass().getSimpleName();
        this.logTime = System.currentTimeMillis();
        this.businessDescription = businessDescription;
        if (exception != null) {
            this.exceptionClass = exception.getClass().getName();
            this.exceptionMessage = exception.getMessage();
            StackTraceElement[] stackTraceElements = exception.getStackTrace();
            if (stackTraceElements != null && stackTraceElements.length > 0) {
                this.className = ClassUtil.getBriefClassName(stackTraceElements[0].getClassName());
                this.methodName = stackTraceElements[0].getMethodName();
                this.codeLineNumber = stackTraceElements[0].getLineNumber();
            }
        }
    }

    public ExceptionLog(Exception exception, String businessDescription, Map<String, Object> parameters) {
        this(exception, businessDescription);
        this.parameters = parameters;
    }
}
