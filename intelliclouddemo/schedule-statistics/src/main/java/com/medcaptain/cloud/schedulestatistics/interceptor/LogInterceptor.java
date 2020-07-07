package com.medcaptain.cloud.schedulestatistics.interceptor;

import com.medcaptain.annotation.Log;
import com.medcaptain.logging.LoggerHelper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

/**
 * 处理@Log注解：记录方法开始、结束时间及耗时；记录传入方法参数；记录方法返回结果
 *
 * @author bingwen.ai
 * @see Log
 */

@Aspect
@Component
public class LogInterceptor {
    private ThreadLocal<Long> beginTime = new ThreadLocal<>();

    private Logger logger = null;

    /**
     * 定义一个注解切入点
     */
    @Pointcut("@annotation(logAnnotation)")
    public void methodLog(Log logAnnotation) {
    }

    /**
     * 声明切入点的方法执行前
     *
     * @param joinPoint     切入点
     * @param logAnnotation log注解
     */
    @Before("methodLog(logAnnotation)")
    public void beforeLog(JoinPoint joinPoint, Log logAnnotation) {
        beginTime.set(System.currentTimeMillis());
        logger = LoggerHelper.getLogger(joinPoint);
        LoggerHelper.beforeLog(joinPoint, logAnnotation, logger, beginTime.get());
    }

    /**
     * 声明切入点的方法执行后
     */
    @After("methodLog(logAnnotation)")
    public void afterLog(JoinPoint joinPoint, Log logAnnotation) {
        LoggerHelper.afterLog(joinPoint, logAnnotation, logger, beginTime.get());
        beginTime.remove();
    }

    /**
     * 记录返回值
     *
     * @param joinPoint     切入点
     * @param result        方法返回的结果
     * @param logAnnotation log注解
     */
    @AfterReturning(value = "methodLog(logAnnotation)", returning = "result")
    public void returnLog(JoinPoint joinPoint, Object result, Log logAnnotation) {
        LoggerHelper.returnLog(joinPoint, result, logAnnotation, logger);
    }
}
