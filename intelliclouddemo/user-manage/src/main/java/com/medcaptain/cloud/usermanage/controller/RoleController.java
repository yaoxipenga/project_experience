package com.medcaptain.cloud.usermanage.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.medcaptain.annotation.AuthorizationFree;
import com.medcaptain.annotation.Log;
import com.medcaptain.cloud.usermanage.constant.FrontendResourceType;
import com.medcaptain.cloud.usermanage.entity.Department;
import com.medcaptain.cloud.usermanage.entity.FrontendResource;
import com.medcaptain.cloud.usermanage.entity.RoleInfo;
import com.medcaptain.cloud.usermanage.entity.UserInfo;
import com.medcaptain.cloud.usermanage.redis.RedisService;
import com.medcaptain.cloud.usermanage.service.DepartmentService;
import com.medcaptain.cloud.usermanage.service.FrontendResourceService;
import com.medcaptain.cloud.usermanage.service.LogService;
import com.medcaptain.cloud.usermanage.service.RoleService;
import com.medcaptain.cloud.usermanage.service.UserService;
import com.medcaptain.dto.RestResult;
import com.medcaptain.enums.HttpResponseCode;
import com.medcaptain.enums.OperatePermissionEnum;
import com.medcaptain.helper.HttpServletHelper;
import com.medcaptain.logging.ExceptionLog;
import com.medcaptain.logging.OperateLogLevel;
import com.medcaptain.utils.IntegerUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * 角色操作
 *
 * @author yangzhixiong
 */
@RestController
public class RoleController {
    private static Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    RoleService roleService;

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    LogService logService;

    @Autowired
    FrontendResourceService frontendResourceService;

    /**
     * 获取角色列表
     *
     * @return 角色列表json
     */
    @Log
    @GetMapping(value = "/roles")
    public RestResult listRoles(HttpServletRequest request) {
        int pageIndex = IntegerUtil.tryParse(request.getParameter("page"), 0);
        int pageSize = IntegerUtil.tryParse(request.getParameter("per_page"), 10);
        String roleName = request.getParameter("role_name");
        int organizationID = IntegerUtil.tryParse(request.getParameter("organizationId"), 0);
        int departmentID = IntegerUtil.tryParse(request.getParameter("departmentId"), 0);
        try {
            String token = HttpServletHelper.getRequestToken(request);
            UserInfo currentUser = userService.getCurrentUser(token);
            if (currentUser == null) {
                return RestResult.getInstance(HttpResponseCode.CURRENT_USER_NOT_FOUND, null);
            }
            if (pageIndex < 0 || pageSize <= 0 || organizationID < 0 || departmentID < 0) {
                return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
            }
            boolean canManagePlatform = redisService.hasOperatePermission(currentUser.getRoleId(),
                    OperatePermissionEnum.PLATFORM_MANAGE.getMsg());
            boolean canManageOrganization = redisService.hasOperatePermission(currentUser.getRoleId(),
                    OperatePermissionEnum.ORGANIZATION_MANAGE.getMsg());
            if (!canManagePlatform) {
                organizationID = currentUser.getOrganizationId();
                if (!canManageOrganization) {
                    departmentID = currentUser.getDepartmentId();
                }
            }
            PageInfo<RoleInfo> roleInfoList = roleService.listRoles(pageIndex, pageSize, roleName, organizationID, departmentID);
            return RestResult.getInstance(HttpResponseCode.SUCCESS, roleInfoList);
        } catch (Exception ex) {
            HashMap<String, Object> parameters = new HashMap<>(5);
            parameters.put("roleName", roleName);
            parameters.put("page", pageIndex);
            parameters.put("perPage", pageSize);
            parameters.put("organizationID", organizationID);
            parameters.put("departmentID", departmentID);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "获取角色列表", parameters);
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }

    /**
     * 获取角色详情
     *
     * @param roleID 角色编号
     * @return 角色信息
     */
    @Log(logParameterNames = "roleId")
    @GetMapping(value = "/role/{roleId}")
    public RestResult getRoleInfo(@PathVariable(value = "roleId") int roleID, HttpServletRequest request) {
        if (roleID <= 0) {
            return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }
        String token = HttpServletHelper.getRequestToken(request);
        RoleInfo roleInfo = getRoleInfoWithPermissionCheck(roleID, token);
        return RestResult.getInstance(HttpResponseCode.SUCCESS, roleInfo);
    }

    /**
     * 获取角色信息并检查当前用户是否用获取此信息的权限
     *
     * @param roleID    角色编号
     * @param userToken 当前用户token
     * @return 角色信息
     */
    private RoleInfo getRoleInfoWithPermissionCheck(int roleID, String userToken) {
        UserInfo currentUser = userService.getCurrentUser(userToken);
        if (currentUser == null) {
            return null;
        }
        int organizationID = currentUser.getOrganizationId();
        int departmentID = currentUser.getDepartmentId();
        boolean managePlatform = redisService.hasOperatePermission(currentUser.getRoleId(), OperatePermissionEnum.PLATFORM_MANAGE.getMsg());
        boolean manageOrganization = redisService.hasOperatePermission(currentUser.getRoleId(), OperatePermissionEnum.ORGANIZATION_MANAGE.getMsg());
        RoleInfo roleInfo = roleService.getRole(roleID);
        if (!managePlatform && roleInfo != null) {
            if (manageOrganization) {
                if (organizationID != roleInfo.getOrganizationId()) {
                    roleInfo = null;
                }
            } else {
                if (departmentID != roleInfo.getDepartmentId()) {
                    roleInfo = null;
                }
            }
        }
        return roleInfo;
    }

    /**
     * 新增角色
     *
     * @param departmentId 所属部门id
     * @param roleName     角色名称
     * @param remark       角色说明
     * @return 操作结果
     */
    @Log(logParameterNames = "roleName")
    @PostMapping(value = "/role")
    public RestResult addRole(HttpServletRequest request, @RequestParam(value = "departmentId") int departmentId,
                              @RequestParam(value = "roleName") String roleName,
                              @RequestParam(value = "remark", required = false, defaultValue = "") String remark) {
        try {
            Department department = departmentService.getDepartment(departmentId);
            if (department == null) {
                return RestResult.getInstance(HttpResponseCode.RESOURCE_NOT_FOUND, null);
            }
            if (roleService.exist(departmentId, roleName)) {
                return RestResult.getInstance(HttpResponseCode.ERROR_RESOURCE_ALREADY_EXIST, null);
            }
            int organizationID = department.getOrganizationId();
            RoleInfo roleInfo = new RoleInfo();
            roleInfo.setDepartmentId(departmentId);
            roleInfo.setOrganizationId(organizationID);
            roleInfo.setRoleName(roleName);
            roleInfo.setRemark(remark);
            roleInfo.setIsDeleted((byte) 0);
            roleInfo.setIsEnable((byte) 1);
            if (roleService.addRole(roleInfo) > 0) {
                String logContent = "角色名称 = " + roleName + " ,部门编号 = " + departmentId;
                logService.logOperate(logger, request, "添加角色", logContent, OperateLogLevel.critical);
                return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
            } else {
                return RestResult.getInstance(HttpResponseCode.ERROR, null);
            }
        } catch (Exception ex) {
            HashMap<String, Object> parameters = new HashMap<>(3);
            parameters.put("departmentId", departmentId);
            parameters.put("roleName", roleName);
            parameters.put("roleDescription", remark);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "新增角色信息", parameters);
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }

    /**
     * 修改角色信息
     *
     * @param roleId 角色id
     * @return 操作结果
     */
    @Log(logParameterNames = "roleId")
    @PutMapping(value = "/role/{roleId}")
    public RestResult updateRole(@PathVariable int roleId, HttpServletRequest request) {
        String roleName = request.getParameter("roleName");
        String remark = request.getParameter("remark");
        int departmentId = IntegerUtil.tryParse(request.getParameter("departmentId"), 0);
        int enable = IntegerUtil.tryParse(request.getParameter("enable"), -1);
        if (roleId <= 0) {
            return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }
        if (!roleService.exist(roleId)) {
            return RestResult.getInstance(HttpResponseCode.RESOURCE_NOT_FOUND, null);
        }
        if (departmentId > 0 && !departmentService.exist(departmentId)) {
            return RestResult.getInstance(HttpResponseCode.RESOURCE_NOT_FOUND, null);
        }
        if (departmentId > 0 && StringUtils.isNotEmpty(roleName)) {
            RoleInfo roleInfo = roleService.getRoleInfo(departmentId, roleName);
            if (roleInfo != null && roleInfo.getRoleId() != roleId) {
                return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
            }
        }
        try {
            RoleInfo newRoleInfo = new RoleInfo();
            newRoleInfo.setRoleId(roleId);
            if (remark != null) {
                newRoleInfo.setRemark(remark);
            }
            if (roleName != null) {
                newRoleInfo.setRoleName(roleName);
            }
            if (departmentId > 0) {
                newRoleInfo.setDepartmentId(departmentId);
            }
            if (enable == 0 || enable == 1) {
                newRoleInfo.setIsEnable((byte) enable);
            }
            if (roleService.updateRole(newRoleInfo) > 0) {
                logService.logOperate(logger, request, "更新角色信息", JSON.toJSONString(newRoleInfo), OperateLogLevel.critical);
                return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
            } else {
                return RestResult.getInstance(HttpResponseCode.ERROR, null);
            }
        } catch (Exception ex) {
            HashMap<String, Object> parameters = new HashMap<>(5);
            parameters.put("roleId", roleId);
            parameters.put("roleName", roleName);
            parameters.put("remark", remark);
            parameters.put("departmentId", departmentId);
            parameters.put("enable", enable);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "修改角色信息", parameters);
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }

    /**
     * 删除角色
     *
     * @param roleId 角色id
     * @return 操作结果
     */
    @Log(logParameterNames = "roleId")
    @DeleteMapping(value = "/role/{roleId}")
    public RestResult deleteRole(HttpServletRequest request, @PathVariable(name = "roleId") int roleId) {
        if (roleId <= 0) {
            return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }
        if (!roleService.exist(roleId)) {
            return RestResult.getInstance(HttpResponseCode.RESOURCE_NOT_FOUND, null);
        }
        if (userService.existRole(roleId)) {
            return RestResult.getInstance(HttpResponseCode.RESOURCE_IN_USE, null);
        }
        try {
            if (roleService.deleteRole(roleId) > 0) {
                logService.logOperate(logger, request, "删除角色信息", String.valueOf(roleId), OperateLogLevel.critical);
                return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
            } else {
                return RestResult.getInstance(HttpResponseCode.ERROR, null);
            }
        } catch (Exception ex) {
            HashMap<String, Object> parameters = new HashMap<>(1);
            parameters.put("roleId", roleId);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "删除角色信息", parameters);
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }

    /**
     * 获取指定机构的管理员角色信息
     * <p>只用于服务间调用</p>
     *
     * @param request HTTP请求
     * @return 操作结果
     */
    @AuthorizationFree
    @GetMapping(value = "/role/managers")
    public RestResult getManagerRole(HttpServletRequest request) {
        int pageIndex = IntegerUtil.tryParse(request.getParameter("page"), 0);
        int pageSize = IntegerUtil.tryParse(request.getParameter("per_page"), 10);
        int organizationID = IntegerUtil.tryParse(request.getParameter("organizationId"), 0);
        try {
            if (pageIndex < 0 || pageSize <= 0 || organizationID <= 0) {
                return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
            }
            FrontendResource manageOrganization = frontendResourceService.getResourceByName(OperatePermissionEnum.ORGANIZATION_MANAGE.getMsg(), (byte) FrontendResourceType.OPERATE_FUNCTION.ordinal());
            if (manageOrganization == null) {
                return RestResult.getInstance(HttpResponseCode.RESOURCE_NOT_FOUND, null);
            }
            PageInfo<RoleInfo> roleList = roleService.listRolesHasPermission(pageIndex, pageSize, manageOrganization.getFrontendResourceId(), organizationID);
            return RestResult.getInstance(HttpResponseCode.SUCCESS, roleList);
        } catch (Exception ex) {
            HashMap<String, Object> parameters = new HashMap<>(3);
            parameters.put("page", pageIndex);
            parameters.put("perPage", pageSize);
            parameters.put("organizationID", organizationID);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "获取管理员角色列表", parameters);
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }
}
