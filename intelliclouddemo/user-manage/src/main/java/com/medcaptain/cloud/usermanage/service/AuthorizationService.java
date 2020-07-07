package com.medcaptain.cloud.usermanage.service;

import com.medcaptain.authorization.AuthorizationHelper;
import com.medcaptain.authorization.MethodRequest;
import com.medcaptain.cloud.usermanage.entity.UserToken;
import com.medcaptain.cloud.usermanage.redis.RedisService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 鉴权服务
 *
 * @author bingwen.ai
 */
@Component
public class AuthorizationService {
    private static Logger logger = LoggerFactory.getLogger(AuthorizationService.class);

    @Autowired
    RedisService redisService;

    /**
     * 检查是否有后端接口访问权限
     *
     * @param methodRequest 接口请求信息
     * @return true = 有访问权限 ; false = 无访问权限
     */
    public boolean checkAuthorization(MethodRequest methodRequest) {
        if (methodRequest == null) {
            return false;
        }

        boolean needValidate = methodRequest.isNeedValidate();
        String token = methodRequest.getUserToken();
        if (StringUtils.isNotBlank(token)) {
            redisService.extendWebUserTokenExpire(token);
            if (needValidate && redisService.isTokenExpired(token)) {
                return false;
            }
        } else if (needValidate) {
            return false;
        }

        if (needValidate) {
            int requestType = methodRequest.getRequestType();
            String backendResourceURL = methodRequest.getBackendResourceURL();
            UserToken userToken = redisService.getCacheUser(token);
            if (userToken == null) {
                return false;
            }
            AuthorizationHelper.logMethodRequest(methodRequest, userToken.getUserID());
            int roleID = userToken.getRoleID();
            if (roleID > 0) {
                return hasAuthorization(roleID, requestType, backendResourceURL);
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * 检查是否有功能操作权限
     *
     * @param token          用户token
     * @param permissionName 功能权限名称
     * @return true = 有操作权限 ; false = 无操作权限
     */
    public boolean hasOperatePermission(String token, String permissionName) {
        UserToken userToken = redisService.getCacheUser(token);
        if (userToken == null) {
            return false;
        } else {
            return redisService.hasOperatePermission(userToken.getRoleID(), permissionName);
        }
    }

    private boolean hasAuthorization(int roleID, int requestType, String backendResourceURL) {
        if (requestType > 0 && StringUtils.isNotEmpty(backendResourceURL)) {
            return redisService.checkBackendResourcePermission(roleID, backendResourceURL, requestType);
        } else {
            return false;
        }
    }
}
