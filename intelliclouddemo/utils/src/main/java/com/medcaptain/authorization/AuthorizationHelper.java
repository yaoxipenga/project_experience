package com.medcaptain.authorization;

import com.medcaptain.annotation.AuthorizationFree;
import com.medcaptain.helper.HttpServletHelper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 获取后端接口鉴权需要的请求信息
 *
 * @author bingwen.ai
 */
public class AuthorizationHelper {
    private static Logger logger = LoggerFactory.getLogger(AuthorizationHelper.class);

    public static MethodRequest getMethodRequest(HttpServletRequest httpServletRequest, Object handler) {
        try {
            logRequestInfo(httpServletRequest);
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            // 没有AuthorizationFree注解的方法需要验证权限
            boolean needValidate = method.getAnnotation(AuthorizationFree.class) == null;
            // 内部调用不鉴权
            if (HttpServletHelper.isInnerInvoke(httpServletRequest)) {
                needValidate = false;
            }
            String token = HttpServletHelper.getRequestToken(httpServletRequest);
            String remoteIP = HttpServletHelper.getHttpClientIPAddress(httpServletRequest);
            Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
            int requestType = 0;
            String backendResourceURL = null;
            for (Annotation annotation : declaredAnnotations) {
                if (annotation instanceof PostMapping) {
                    requestType = 1;
                    backendResourceURL = ((PostMapping) annotation).value()[0];
                    break;
                } else if (annotation instanceof DeleteMapping) {
                    requestType = 2;
                    backendResourceURL = ((DeleteMapping) annotation).value()[0];
                    break;
                } else if (annotation instanceof PutMapping) {
                    requestType = 3;
                    backendResourceURL = ((PutMapping) annotation).value()[0];
                    break;
                } else if (annotation instanceof GetMapping) {
                    requestType = 4;
                    backendResourceURL = ((GetMapping) annotation).value()[0];
                    break;
                }
            }
            if (requestType == 0) {
                return null;
            } else {
                return new MethodRequest(token, needValidate, requestType, backendResourceURL, remoteIP);
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public static void logMethodRequest(MethodRequest methodRequest, int userID) {
        if (methodRequest != null && logger.isDebugEnabled()) {
            String requestMethod = convertRequestMethod(methodRequest.getRequestType());
            logger.debug("来自 [{}] 的用户 [{}] {} -> {} ", methodRequest.getRemoteIP(), userID, requestMethod, methodRequest.getBackendResourceURL());
        }
    }

    private static void logRequestInfo(HttpServletRequest httpServletRequest) {
        String httpClientIP = HttpServletHelper.getHttpClientIPAddress(httpServletRequest);
        String requestMethod = httpServletRequest.getMethod();
        if (logger.isDebugEnabled()) {
            logger.debug(" [{}] {} -> {}", httpClientIP, requestMethod, httpServletRequest.getRequestURI());
        }
    }

    private static String convertRequestMethod(int requestType) {
        switch (requestType) {
            case 1:
                return "POST";
            case 2:
                return "DELETE";
            case 3:
                return "PUT";
            case 4:
                return "GET";
            default:
                return "";
        }
    }
}
