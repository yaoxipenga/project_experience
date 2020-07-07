package com.medcaptain.annotation;

import java.lang.annotation.*;

/**
 * <h1>不需要验证权限注解</h1>
 * <p>无此注解的方法需要校验用户访问权限</p>
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface AuthorizationFree {

}
