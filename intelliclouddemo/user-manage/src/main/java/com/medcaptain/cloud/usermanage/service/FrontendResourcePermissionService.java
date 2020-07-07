package com.medcaptain.cloud.usermanage.service;

import com.medcaptain.cloud.usermanage.constant.FrontendResourceType;
import com.medcaptain.cloud.usermanage.entity.FrontendResourcePermission;

import java.util.List;

/**
 * 前端资源授权信息服务
 *
 * @author bingwen.ai
 */
public interface FrontendResourcePermissionService {
    boolean addMapping(int frontendResourceID, int roleID);

    boolean addMappings(List<Integer> frontendResourceIDList, int roleID);

    boolean exist(int frontendResourceID, int roleID);

    boolean exist(int permissionID);

    boolean deleteMapping(int permissionID);

    boolean deleteMapping(int frontendResourceID, int roleID);

    FrontendResourcePermission getPermission(int permissionID);

    FrontendResourcePermission getPermission(int frontendResourceID, int roleID);

    List<FrontendResourcePermission> listPermissions(int roleID, FrontendResourceType resourceType);
}
