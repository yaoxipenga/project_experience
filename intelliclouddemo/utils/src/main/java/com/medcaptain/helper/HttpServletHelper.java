package com.medcaptain.helper;

import com.alibaba.fastjson.JSON;
import com.medcaptain.dto.RestResult;
import com.medcaptain.enums.HttpResponseCode;
import com.medcaptain.utils.StringUtil;
import com.medcaptain.utils.UUIDUtil;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * HTTP请求辅助类
 *
 * @author bingwen.ai
 */
public class HttpServletHelper {

    private static final String REQUEST_HEADER_AUTHORIZATION = "Authorization";

    public static final String REQUEST_HEADER_INNER_INVOKE = "InnerInvoke";

    public static final String INNER_INVOKE_FLAG = "MECT";

    private static final String REQUEST_HEADER_BEARER_PREFIX = "Bearer";

    private static final String APPLICATION_JSON_UTF8_VALUE = "application/json;charset=UTF-8";

    private static final String CLIENT_ID = "MedCaptain.ClientID";


    /**
     * 获取HTTP请求中的用户token
     *
     * @param request HTTP请求
     * @return 用户token；未找到返回null
     */
    public static String getRequestToken(HttpServletRequest request) {
        if (request != null) {
            String userToken = request.getHeader(REQUEST_HEADER_AUTHORIZATION);
            return getRequestToken(userToken);
        }
        return null;
    }

    /**
     * <h1>获取HTTP客户端IP地址</h1>
     * (可能不准确)
     *
     * @param request HTTP请求
     * @return 客户端IP地址
     */
    public static String getHttpClientIPAddress(HttpServletRequest request) {
        String remoteIP = request.getHeader("x-forwarded-for");
        String separatorChar = ",";
        if (!isEmptyIPAddress(remoteIP)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (remoteIP.contains(separatorChar)) {
                remoteIP = remoteIP.split(separatorChar)[0];
            }
        }
        if (isEmptyIPAddress(remoteIP)) {
            remoteIP = request.getHeader("Proxy-Client-IP");
        }
        if (isEmptyIPAddress(remoteIP)) {
            remoteIP = request.getHeader("WL-Proxy-Client-IP");
        }
        if (isEmptyIPAddress(remoteIP)) {
            remoteIP = request.getHeader("HTTP_CLIENT_IP");
        }
        if (isEmptyIPAddress(remoteIP)) {
            remoteIP = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (isEmptyIPAddress(remoteIP)) {
            remoteIP = request.getHeader("X-Real-IP");
        }
        if (isEmptyIPAddress(remoteIP)) {
            remoteIP = request.getRemoteAddr();
        }
        return remoteIP;
    }

    /**
     * 回写鉴权拦截异常信息
     *
     * @param httpServletResponse 请求响应
     * @param httpResponseCode    异常代码
     * @return false
     */
    public static boolean handlerValidateException(HttpServletResponse httpServletResponse, HttpResponseCode httpResponseCode) {
        try {
            RestResult restResult = RestResult.getInstance(httpResponseCode, null);
            httpServletResponse.setContentType(APPLICATION_JSON_UTF8_VALUE);
            httpServletResponse.setStatus(restResult.getCode());
            String jsonResult = JSON.toJSONString(restResult);
            httpServletResponse.getWriter().print(jsonResult);
        } catch (Exception ex) {
        } finally {
            try {
                httpServletResponse.getWriter().close();
            } catch (Exception ex) {
            }
        }
        return false;
    }

    /**
     * 判断是否是服务间内部调用
     *
     * @param httpServletRequest HTTP请求头
     * @return true = 是内部调用 ; false = 不是内部调用
     */
    public static boolean isInnerInvoke(HttpServletRequest httpServletRequest) {
        if (httpServletRequest == null) {
            return false;
        } else {
            String invokeFlag = httpServletRequest.getHeader(REQUEST_HEADER_INNER_INVOKE);
            return INNER_INVOKE_FLAG.equals(invokeFlag);
        }
    }

    /**
     * 为新的http请求生成新的clientID
     * <p>新客户端未传clientID，则由服务端生成并返回给response的cookie和header中</p>
     *
     * @param httpServletRequest  http请求
     * @param httpServletResponse http响应
     * @return 客户端ID
     */
    public static void addClientIDToNewRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String clientID = getClientID(httpServletRequest, httpServletResponse);
        if (StringUtils.isEmpty(clientID)) {
            setClientID(httpServletResponse);
        }
    }

    /**
     * 从HTTP请求的cookie中获取clientID
     *
     * @param httpServletRequest HTTP请求
     * @return clientID值
     */
    public static String getClientIDFromCookie(HttpServletRequest httpServletRequest) {
        String clientID = null;
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (CLIENT_ID.equals(cookie.getName())) {
                    clientID = cookie.getValue();
                    break;
                }
            }
        }
        return clientID;
    }

    /**
     * 获取clientID
     * <p>新请求中不包含cookie，则从响应的header取</p>
     *
     * @param httpServletRequest  HTTP请求
     * @param httpServletResponse HTTP响应
     * @return clientID值
     */
    public static String getClientID(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String clientID = getClientIDFromCookie(httpServletRequest);
        if (StringUtil.isEmpty(clientID)) {
            clientID = httpServletResponse.getHeader(CLIENT_ID);
        }
        return clientID;
    }

    private static String setClientID(HttpServletResponse httpServletResponse) {
        String clientID = UUIDUtil.getUUID();
        httpServletResponse.setHeader(CLIENT_ID, clientID);
        Cookie clientIDCookie = new Cookie(CLIENT_ID, clientID);
        httpServletResponse.addCookie(clientIDCookie);
        return clientID;
    }

    private static boolean isEmptyIPAddress(String ipAddress) {
        return ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress);
    }

    /**
     * 获取HTTP请求中的用户token
     *
     * @param authorization Authorization值
     * @return 用户token；未找到返回null
     */
    private static String getRequestToken(String authorization) {
        if (!StringUtils.isEmpty(authorization)) {
            return authorization.replace(REQUEST_HEADER_BEARER_PREFIX, "").trim();
        }
        return null;
    }
}
