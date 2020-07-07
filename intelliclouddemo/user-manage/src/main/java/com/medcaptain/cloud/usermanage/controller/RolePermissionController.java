package com.medcaptain.cloud.usermanage.controller;

import com.medcaptain.annotation.AuthorizationFree;
import com.medcaptain.annotation.Log;
import com.medcaptain.cloud.usermanage.entity.UserToken;
import com.medcaptain.cloud.usermanage.redis.RedisService;
import com.medcaptain.cloud.usermanage.service.RolePermissionService;
import com.medcaptain.dto.RestResult;
import com.medcaptain.enums.HttpResponseCode;
import com.medcaptain.helper.HttpServletHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 角色授权信息控制器
 *
 * @author bingwen.ai
 */
@RestController
public class RolePermissionController {
    @Autowired
    RolePermissionService rolePermissionService;

    @Autowired
    private RedisService redisService;

    /**
     * 获取角色的权限列表
     *
     * @param roleID 角色编号
     * @return 权限列表json
     */
    @Log(logParameterNames = "roleID")
    @GetMapping(value = "/rolepermissions/{roleId}")
    public RestResult listRolePermissions(@PathVariable(value = "roleId") int roleID) {
        if (roleID <= 0) {
            return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }
        return RestResult.getInstance(HttpResponseCode.SUCCESS, rolePermissionService.listPermissions(roleID));
    }

    /**
     * 获取<b>当前用户</b>的权限列表
     *
     * @return 权限列表json
     */
    @Log
    @GetMapping(value = "/permissions")
    public RestResult listRolePermissions(HttpServletRequest request) {
        String token = HttpServletHelper.getRequestToken(request);
        if (StringUtils.isBlank(token)) {
            return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }
        UserToken userToken = redisService.getCacheUser(token);
        if (userToken == null) {
            return RestResult.getInstance(HttpResponseCode.RESOURCE_NOT_FOUND, null);
        }
        int roleID = userToken.getRoleID();
        if (roleID > 0) {
            return RestResult.getInstance(HttpResponseCode.SUCCESS, rolePermissionService.listPermissions(roleID));
        } else {
            return RestResult.getInstance(HttpResponseCode.RESOURCE_NOT_FOUND, null);
        }
    }

    /**
     * 刷新redis中的角色权限信息
     * <b/>暂时设置成不需要鉴权，方便调试
     *
     * @return 刷新完成返回成功
     */
    @AuthorizationFree
    @PostMapping(value = "/rolepermissions/refresh")
    public RestResult refreshRolePermission() {
        redisService.initRolePermission();
        return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
    }

    @AuthorizationFree
    @GetMapping(value = "/rolepermission/{roleId}/{permissionName}")
    public boolean hasPermission(@PathVariable(name = "roleId") int roleID,
                                 @PathVariable(name = "permissionName") String permissionName) {
        return redisService.hasOperatePermission(roleID, permissionName);
    }

    @AuthorizationFree
    @GetMapping(value = "/rolepermission/{roleId}/{backendResourceURL}/{requestType}")
    public boolean hasPermission(@PathVariable(name = "roleId") int roleID,
                                 @PathVariable(name = "backendResourceURL") String backendResourceURL,
                                 @PathVariable(name = "requestType") int requestType) {
        return redisService.checkBackendResourcePermission(roleID, backendResourceURL, requestType);
    }
}
