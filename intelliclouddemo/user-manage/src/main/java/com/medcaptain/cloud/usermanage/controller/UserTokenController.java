package com.medcaptain.cloud.usermanage.controller;

import com.medcaptain.annotation.AuthorizationFree;
import com.medcaptain.annotation.Log;
import com.medcaptain.cloud.usermanage.entity.UserToken;
import com.medcaptain.cloud.usermanage.redis.RedisService;
import com.medcaptain.dto.RestResult;
import com.medcaptain.enums.HttpResponseCode;
import com.medcaptain.helper.HttpServletHelper;
import com.medcaptain.logging.ExceptionLog;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * 用户token信息控制器
 *
 * @author bingwen.ai
 */
@RestController
public class UserTokenController {
    private Logger logger = LoggerFactory.getLogger(UserTokenController.class);

    @Autowired
    RedisService redisService;

    /**
     * 用于服务间调用，通过token获取已登录用户信息
     *
     * @param request HTTP请求
     * @return 返回用户JSON或相关异常信息
     */
    @Log
    @AuthorizationFree
    @GetMapping(value = "/token")
    public RestResult getUserToken(HttpServletRequest request) {
        String token = HttpServletHelper.getRequestToken(request);
        if (StringUtils.isBlank(token)) {
            return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }
        try {
            UserToken userToken = redisService.getCacheUser(token);
            if (userToken != null) {
                return RestResult.getInstance(HttpResponseCode.SUCCESS, userToken.getUserInfo());
            } else {
                return RestResult.getInstance(HttpResponseCode.RESOURCE_NOT_FOUND, null);
            }
        } catch (Exception ex) {
            HashMap<String, Object> parameters = new HashMap<>(1);
            parameters.put("token", token);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "通过token获取已登录用户信息", parameters);
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }
}
