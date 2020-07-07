package com.medcaptain.cloud.usermanage.controller;

import com.medcaptain.annotation.Log;
import com.medcaptain.authorization.MethodRequest;
import com.medcaptain.cloud.usermanage.service.AuthorizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <h2>后端接口鉴权</h2>
 * <p>
 * 供其他服务调用
 *
 * @author bingwen.ai
 */
@RestController
public class AuthorizationController {
    private Logger logger = LoggerFactory.getLogger(AuthorizationController.class);

    @Autowired
    AuthorizationService authorizationService;

    /**
     * 检查是否有后端接口操作权限（供平台其它微服务调用）
     *
     * @param methodRequest 请求相关信息
     * @return true = 有后端接口操作权限 ； false = 无后端接口操作权限
     */
    @Log
    @PostMapping(value = "/authorization")
    public boolean checkAuthorization(@RequestBody MethodRequest methodRequest) {
        if (logger.isDebugEnabled() && methodRequest != null) {
            logger.debug("检查接口授权: token = {} , 接口路径 = {}", methodRequest.getUserToken(), methodRequest.getBackendResourceURL());
        }
        return authorizationService.checkAuthorization(methodRequest);
    }

    /**
     * 检查是否有功能操作权限
     *
     * @param token          用户token
     * @param permissionName 操作权限名称
     * @return true - 有此操作权限 ； false - 无此操作权限
     */
    @Log
    @GetMapping(value = "/authorization/{token}/{permissionName}")
    public boolean hasOperatePermission(@PathVariable(name = "token") String token,
                                        @PathVariable(name = "permissionName") String permissionName) {
        if (logger.isDebugEnabled()) {
            logger.debug("检查用户功能权限：token = {}，功能权限 = {}");
        }
        return authorizationService.hasOperatePermission(token, permissionName);
    }
}
