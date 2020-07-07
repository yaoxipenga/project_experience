package com.medcaptain.logging;

import com.alibaba.fastjson.JSON;
import com.medcaptain.annotation.Log;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * 日志记录公共类
 *
 * @author bingwen.ai
 */

public class LoggerHelper {
    public static void beforeLog(JoinPoint joinPoint, Log logAnnotation, Logger logger, long startTime) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        logTime(logger, method, logAnnotation, startTime, false);
        logParameter(logger, joinPoint, logAnnotation);
    }

    public static void afterLog(JoinPoint joinPoint, Log logAnnotation, Logger logger, long startTime) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        logTime(logger, method, logAnnotation, startTime, true);
    }

    public static void returnLog(JoinPoint joinPoint, Object result, Log logAnnotation, Logger logger) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        if (logAnnotation.logResult() && logger.isDebugEnabled()) {
            logger.debug("method {} return: {}", method.getName(), JSON.toJSONString(result));
        }
    }

    public static Logger getLogger(JoinPoint joinPoint) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Class clazz = method.getDeclaringClass();
        return LoggerFactory.getLogger(clazz);
    }

    public static void logOperate(Logger logger, int userID, String clientIP, String logContent, String logType, OperateLogLevel logLevel) {
        OperateLog operateLog = new OperateLog(userID, System.currentTimeMillis(), clientIP, logContent, logType, logLevel);
        logger.info(operateLog.toString());
    }

    private static void logTime(Logger logger, Method executeMethod, Log logAnnotation, long startTime, boolean afterExecute) {
        if (logger == null || executeMethod == null) {
            return;
        }
        if (logAnnotation == null || !logAnnotation.logTime()) {
            return;
        }

        if (afterExecute) {
            long endTime = System.currentTimeMillis();
            long interval = endTime - startTime;
            ExecuteTimeLog executeTimeLog = new ExecuteTimeLog(executeMethod, interval, startTime);
            logger.info(executeTimeLog.toString());
        } else {
            // 只记录开始时间，执行时间默认为-1
            ExecuteTimeLog executeTimeLog = new ExecuteTimeLog(executeMethod, -1, startTime);
            logger.info(executeTimeLog.toString());
        }
    }

    /**
     * 记录方法的传入参数，与最外层POM中的maven-compiler-plugin配合使用
     *
     * @param logger        日志记录器
     * @param joinPoint     日志切入点
     * @param logAnnotation 日志注解
     * @see Log#logParameterNames()
     */
    private static void logParameter(Logger logger, JoinPoint joinPoint, Log logAnnotation) {
        String logParameterNames = logAnnotation.logParameterNames();
        if (!StringUtils.isEmpty(logParameterNames) && logger.isDebugEnabled()) {
            String[] logParameters = StringUtils.split(logParameterNames, ",");
            Object[] parameterValues = joinPoint.getArgs();
            Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
            Parameter[] parameters = method.getParameters();
            for (int parameterIndex = 0; parameterIndex < parameters.length; parameterIndex++) {
                boolean needLog = false;
                String parameterName = parameters[parameterIndex].getName().toLowerCase();
                if ("all".equals(logParameterNames)) {
                    needLog = true;
                } else {
                    for (int logParameterIndex = 0; logParameterIndex < logParameters.length; logParameterIndex++) {
                        if (parameterName.equals(logParameters[logParameterIndex].toLowerCase())) {
                            needLog = true;
                            break;
                        }
                    }
                }
                if (needLog) {
                    logger.debug("{} 参数: {} > {}", method.getName(), parameterName, JSON.toJSONString(parameterValues[parameterIndex]));
                }
            }
        }
    }
}
