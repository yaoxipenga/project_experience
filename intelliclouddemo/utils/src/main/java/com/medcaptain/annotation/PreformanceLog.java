package com.medcaptain.annotation;

/*

  性能日志记录注解，分析方法执行耗时。
  使用aop记录日志

 */

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface PreformanceLog {

}
