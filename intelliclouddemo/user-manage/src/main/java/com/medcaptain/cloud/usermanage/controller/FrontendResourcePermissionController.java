package com.medcaptain.cloud.usermanage.controller;

import com.medcaptain.annotation.Log;
import com.medcaptain.cloud.usermanage.constant.FrontendResourceType;
import com.medcaptain.cloud.usermanage.entity.FrontendResourcePermission;
import com.medcaptain.cloud.usermanage.entity.UserToken;
import com.medcaptain.cloud.usermanage.redis.RedisService;
import com.medcaptain.cloud.usermanage.service.FrontendResourcePermissionService;
import com.medcaptain.cloud.usermanage.service.FrontendResourceService;
import com.medcaptain.cloud.usermanage.service.LogService;
import com.medcaptain.cloud.usermanage.service.RoleService;
import com.medcaptain.dto.RestResult;
import com.medcaptain.enums.HttpResponseCode;
import com.medcaptain.enums.OperatePermissionEnum;
import com.medcaptain.helper.HttpServletHelper;
import com.medcaptain.logging.OperateLogLevel;
import com.medcaptain.utils.IntegerUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 前端资源授权信息控制器
 *
 * @author bingwen.ai
 */
@RestController
public class FrontendResourcePermissionController {
    private Logger logger = LoggerFactory.getLogger(FrontendResourcePermissionController.class);

    @Autowired
    FrontendResourcePermissionService frontendResourcePermissionService;

    @Autowired
    FrontendResourceService frontendResourceService;

    @Autowired
    RoleService roleService;

    @Autowired
    LogService logService;

    @Autowired
    RedisService redisService;

    @Log
    @PostMapping(value = "/frontendresourcepermission")
    public RestResult addPermission(HttpServletRequest request) {
        String frontendResourceIDs = request.getParameter("frontendResourceId");
        int roleID = IntegerUtil.tryParse(request.getParameter("roleId"), 0);
        String[] frontendResourceIDArray = StringUtils.split(frontendResourceIDs, ",");
        List<Integer> frontendResourceIDList = new ArrayList<>();
        try {
            for (String frontendResourceID : frontendResourceIDArray) {
                frontendResourceIDList.add(Integer.valueOf(frontendResourceID));
            }
        } catch (Exception ex) {
            return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }
        if (frontendResourceIDList.size() <= 0 || roleID <= 0) {
            return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }

        String token = HttpServletHelper.getRequestToken(request);
        UserToken userToken = redisService.getCacheUser(token);
        if (userToken == null || userToken.getRoleID() <= 0) {
            return RestResult.getInstance(HttpResponseCode.ERROR_USER_INFO, null);
        }
        int currentUserRoleID = userToken.getRoleID();
        boolean roleExist = roleService.exist(roleID);
        if (!roleExist) {
            return RestResult.getInstance(HttpResponseCode.RESOURCE_NOT_FOUND, null);
        }
        boolean canManagePlatform = redisService.hasOperatePermission(currentUserRoleID, OperatePermissionEnum.PLATFORM_MANAGE.getMsg());
        if (!canManagePlatform) {
            // 当前用户没有平台管理权限时，判断是否有此权限
            for (int frontendResourceID : frontendResourceIDList) {
                if (!frontendResourceService.exist(frontendResourceID, true)) {
                    return RestResult.getInstance(HttpResponseCode.RESOURCE_NOT_FOUND, null);
                }
                if (!frontendResourcePermissionService.exist(frontendResourceID, currentUserRoleID)) {
                    return RestResult.getInstance(400, "当前用户无此权限,不能授予其他角色此权限", null);
                }
                if (frontendResourcePermissionService.exist(frontendResourceID, roleID)) {
                    return RestResult.getInstance(HttpResponseCode.ERROR_RESOURCE_ALREADY_EXIST, null);
                }
            }
        }

        boolean addComplete = frontendResourcePermissionService.addMappings(frontendResourceIDList, roleID);
        if (addComplete) {
            String logContent = "前端资源编号 = " + frontendResourceIDs + " , 角色编号 = " + roleID;
            logService.logOperate(logger, request, "新增前端资源授权", logContent, OperateLogLevel.critical);
            return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
        } else {
            return RestResult.getInstance(HttpResponseCode.ERROR, null);
        }
    }

    @Log
    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping(value = "/frontendresourcepermission/{permissionId}")
    public RestResult deletePermission(HttpServletRequest request, @PathVariable(value = "permissionId") String permissionIDs) {
        String[] permissionIDArray = StringUtils.split(permissionIDs, ",");
        List<Integer> permissionIDList = new ArrayList<>();
        try {
            for (String permissionID : permissionIDArray) {
                permissionIDList.add(Integer.valueOf(permissionID));
            }
        } catch (Exception ex) {
            return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }
        for (int permissionID : permissionIDList) {
            if (!frontendResourcePermissionService.exist(permissionID)) {
                return RestResult.getInstance(HttpResponseCode.RESOURCE_NOT_FOUND, null);
            }
        }
        boolean deleteComplete = true;
        for (int permissionID : permissionIDList) {
            FrontendResourcePermission permission = frontendResourcePermissionService.getPermission(permissionID);
            deleteComplete = deleteComplete && frontendResourcePermissionService.deleteMapping(permissionID);
            if (!deleteComplete) {
                return RestResult.getInstance(HttpResponseCode.ERROR, null);
            }
            if (permission != null) {
                String logContent = "前端资源 = " + permission.getFrontendResourceName() + " , 角色编号 = " + permission.getRoleId();
                logService.logOperate(logger, request, "删除前端资源授权", logContent, OperateLogLevel.critical);
            }
        }
        return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
    }

    /**
     * 获取角色的所有前端资源权限
     *
     * @param roleId 角色编号
     * @return 前端权限列表json
     */
    @Log
    @GetMapping(value = "/frontendresourcepermissions/{roleId}")
    public RestResult listBackendResources(@PathVariable(value = "roleId") int roleId) {
        if (roleId <= 0) {
            return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }

        return RestResult.getInstance(HttpResponseCode.SUCCESS,
                frontendResourcePermissionService.listPermissions(roleId, FrontendResourceType.ALL));
    }
}
