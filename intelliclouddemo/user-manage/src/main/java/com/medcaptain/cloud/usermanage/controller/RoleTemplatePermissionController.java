package com.medcaptain.cloud.usermanage.controller;

import com.medcaptain.annotation.Log;
import com.medcaptain.cloud.usermanage.service.FrontendResourceService;
import com.medcaptain.cloud.usermanage.service.LogService;
import com.medcaptain.cloud.usermanage.service.RoleTemplatePermissionService;
import com.medcaptain.cloud.usermanage.service.RoleTemplateService;
import com.medcaptain.dto.RestResult;
import com.medcaptain.enums.HttpResponseCode;
import com.medcaptain.logging.OperateLogLevel;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 角色模板授权信息REST接口
 *
 * @author bingwen.ai
 */
@RestController
public class RoleTemplatePermissionController {
    private Logger logger = LoggerFactory.getLogger(RoleTemplatePermissionController.class);

    @Autowired
    LogService logService;

    @Autowired
    RoleTemplatePermissionService roleTemplatePermissionService;

    @Autowired
    RoleTemplateService roleTemplateService;

    @Autowired
    FrontendResourceService frontendResourceService;

    @Log
    @PostMapping(value = "/roleTemplatePermission")
    public RestResult addPermissions(@RequestParam(value = "roleTemplateId") int roleTemplateID,
                                     @RequestParam(value = "frontendResourceIds") String frontendResourceIDs,
                                     HttpServletRequest request) {
        if (roleTemplateID <= 0 || StringUtils.isBlank(frontendResourceIDs)) {
            return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }

        int[] frontendResourceIDList = splitIDs(frontendResourceIDs, ",");
        if (frontendResourceIDList == null) {
            return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }
        if (!roleTemplateService.existTemplate(roleTemplateID)) {
            return RestResult.getInstance(HttpResponseCode.RESOURCE_NOT_FOUND, null);
        }
        for (int frontendResourceID : frontendResourceIDList) {
            if (!frontendResourceService.exist(frontendResourceID, true)) {
                return RestResult.getInstance(HttpResponseCode.RESOURCE_NOT_FOUND, null);
            }
            if (roleTemplatePermissionService.exist(roleTemplateID, frontendResourceID)) {
                return RestResult.getInstance(HttpResponseCode.ERROR_RESOURCE_ALREADY_EXIST, null);
            }
        }

        if (roleTemplatePermissionService.addPermissions(roleTemplateID, frontendResourceIDList)) {
            String logContent = "角色模板编号 = " + roleTemplateID + " ,前端资源编号 = " + frontendResourceIDs;
            logService.logOperate(logger, request, "新增角色模板授权", logContent, OperateLogLevel.critical);
            return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
        } else {
            return RestResult.getInstance(HttpResponseCode.ERROR, null);
        }
    }

    @Log
    @DeleteMapping(value = "/roleTemplatePermission/{permissionId}")
    public RestResult deletePermission(@PathVariable(value = "permissionId") int permissionID, HttpServletRequest request) {
        if (permissionID <= 0) {
            return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }
        if (!roleTemplatePermissionService.exist(permissionID)) {
            return RestResult.getInstance(HttpResponseCode.RESOURCE_NOT_FOUND, null);
        }
        if (roleTemplatePermissionService.deletePermission(permissionID)) {
            logService.logOperate(logger, request, "删除角色模板授权", String.valueOf(permissionID), OperateLogLevel.critical);
            return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
        } else {
            return RestResult.getInstance(HttpResponseCode.ERROR, null);
        }
    }

    @Log
    @GetMapping(value = "/roleTemplatePermissions/{roleTemplateId}")
    public RestResult listRoleTemplatePermissions(@RequestParam(value = "page", defaultValue = "0") int pageIndex,
                                                  @RequestParam(value = "per_page", defaultValue = "10") int pageSize,
                                                  @PathVariable(value = "roleTemplateId") int roleTemplateID) {
        if (roleTemplateID <= 0) {
            return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }
        return RestResult.getInstance(HttpResponseCode.SUCCESS,
                roleTemplatePermissionService.listPermissions(roleTemplateID, pageIndex, pageSize));
    }

    private int[] splitIDs(String frontendResourceIDs, String seperateChar) {
        try {
            String[] idList = StringUtils.split(frontendResourceIDs, seperateChar);
            if (idList != null && idList.length > 0) {
                int[] frontendResourceIDList = new int[idList.length];
                for (int i = 0; i < idList.length; i++) {
                    frontendResourceIDList[i] = Integer.valueOf(idList[i]);
                }
                return frontendResourceIDList;
            }
            return null;
        } catch (Exception ex) {
            return null;
        }
    }
}
