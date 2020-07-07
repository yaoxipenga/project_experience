package com.medcaptain.cloud.usermanage.service;

import com.medcaptain.cloud.usermanage.entity.RolePermission;

import java.util.List;

/**
 * 角色授权信息服务
 *
 * @author bingwen.ai
 */
public interface RolePermissionService {
    List<RolePermission> listPermissions(int roleID);

    List<RolePermission> listAllPermissions();

    List<RolePermission> getPermissionsByBackendResource(int backendResourceID);

    List<RolePermission> getPermissionsByFrontendResource(int frontendResourceID);

    List<RolePermission> listPermissionByBackendResource(String backendResourceURL, int requestType);
}
