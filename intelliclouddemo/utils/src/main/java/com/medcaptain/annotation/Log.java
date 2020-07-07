package com.medcaptain.annotation;

import java.lang.annotation.*;

/**
 * 日志记录注解，加在需要记录日志的方法上。
 * 使用aop记录日志
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Log {

    /**
     * 记录开始结束时间
     */
    boolean logTime() default true;

    /**
     * 需要记录的传入参数列表，用逗号分隔
     */
    String logParameterNames() default "";

    /**
     * 记录方法结果
     */
    boolean logResult() default false;
}
