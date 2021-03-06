package com.medcaptain.cloud.usermanage.interceptor;

import com.medcaptain.annotation.AuthorizationFree;
import com.medcaptain.authorization.AuthorizationHelper;
import com.medcaptain.authorization.MethodRequest;
import com.medcaptain.cloud.usermanage.service.AuthorizationService;
import com.medcaptain.enums.HttpResponseCode;
import com.medcaptain.helper.HttpServletHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <h1>访问授权拦截器</h1>
 * <p>前端直接访问没有 @AuthorizationFree 注解的后端资源，需验证是否有访问权限</p>
 *
 * @see AuthorizationFree
 */
public class AuthorizationInterceptor implements HandlerInterceptor {
    private static Logger logger = LoggerFactory.getLogger(AuthorizationInterceptor.class);

    @Autowired
    AuthorizationService authorizationService;

    /**
     * 请求进入 Controller 之前校验用户权限
     *
     * @param httpServletRequest  http请求
     * @param httpServletResponse http响应
     * @param handler             需要处理的handler
     * @return true-不拦截 ; false 拦截请求
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                             Object handler) {
        HttpServletHelper.addClientIDToNewRequest(httpServletRequest, httpServletResponse);
        MethodRequest methodRequest = AuthorizationHelper.getMethodRequest(httpServletRequest, handler);
        boolean hasAuthority = authorizationService.checkAuthorization(methodRequest);
        if (hasAuthority) {
            return true;
        } else {
            return HttpServletHelper.handlerValidateException(httpServletResponse, HttpResponseCode.ERROR_UNAUTHRORIZED_ACCESS);
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object o, ModelAndView modelAndView) {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Object o, Exception e) {
    }
}
