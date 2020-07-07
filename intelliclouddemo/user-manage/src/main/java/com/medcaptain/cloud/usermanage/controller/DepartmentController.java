package com.medcaptain.cloud.usermanage.controller;

import com.alibaba.fastjson.JSON;
import com.medcaptain.annotation.Log;
import com.medcaptain.cloud.usermanage.entity.Department;
import com.medcaptain.cloud.usermanage.entity.UserInfo;
import com.medcaptain.cloud.usermanage.redis.RedisService;
import com.medcaptain.cloud.usermanage.service.DepartmentService;
import com.medcaptain.cloud.usermanage.service.LogService;
import com.medcaptain.cloud.usermanage.service.OrganizationService;
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
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static com.medcaptain.cloud.usermanage.constant.UserConstant.DISABLE_INT;
import static com.medcaptain.cloud.usermanage.constant.UserConstant.ENABLE_INT;

/**
 * 部门信息控制器
 *
 * @author bingwen.ai
 */
@RestController
public class DepartmentController {
    private Logger logger = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    OrganizationService organizationService;

    @Autowired
    RedisService redisService;

    @Autowired
    UserService userService;

    @Autowired
    LogService logService;

    @Log
    @GetMapping("/departments")
    public RestResult listDepartments(HttpServletRequest request) {
        int pageIndex = IntegerUtil.tryParse(request.getParameter("page"), 0);
        int pageSize = IntegerUtil.tryParse(request.getParameter("per_page"), 10);
        int organizationID = IntegerUtil.tryParse(request.getParameter("organizationId"), 0);
        UserInfo currentUser = null;
        try {
            String token = HttpServletHelper.getRequestToken(request);
            currentUser = userService.getCurrentUser(token);
            if (currentUser == null) {
                return RestResult.getInstance(HttpResponseCode.CURRENT_USER_NOT_FOUND, null);
            }
            if (organizationID <= 0) {
                if (redisService.hasOperatePermission(currentUser.getRoleId(), OperatePermissionEnum.PLATFORM_MANAGE.getMsg())) {
                    organizationID = 0;
                } else {
                    organizationID = currentUser.getOrganizationId();
                }
            }
            if (pageIndex < 0 || pageSize <= 0) {
                return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
            }

            return RestResult.getInstance(HttpResponseCode.SUCCESS,
                    departmentService.listDepartments(pageIndex, pageSize, organizationID));
        } catch (Exception ex) {
            Map<String, Object> parameters = new HashMap<>(4);
            parameters.put("organizationID", organizationID);
            parameters.put("pageIndex", pageIndex);
            parameters.put("pageSize", pageSize);
            parameters.put("currentUser", currentUser);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "获取部门列表", parameters);
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }

    @GetMapping("/department/{departmentID}")
    public RestResult getDepartment(@PathVariable(value = "departmentID") int departmentID) {
        return RestResult.getInstance(HttpResponseCode.SUCCESS, departmentService.getDepartment(departmentID));
    }

    @Log
    @PostMapping("/department")
    public RestResult addDepartment(HttpServletRequest request) {
        String departmentName = request.getParameter("departmentName");
        int organizationID = IntegerUtil.tryParse(request.getParameter("organizationId"), 0);
        String roleNames = request.getParameter("roleList");
        try {
            String[] roleTemplateList = StringUtils.split(roleNames, ",");
            if (StringUtils.isBlank(departmentName) || organizationID <= 0 || !organizationService.exist(organizationID)) {
                return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
            }
            if (departmentService.exist(departmentName, organizationID)) {
                return RestResult.getInstance(HttpResponseCode.ERROR_RESOURCE_ALREADY_EXIST, null);
            }

            if (departmentService.addDepartment(departmentName, organizationID, roleTemplateList)) {
                logService.logOperate(logger, request, "新增部门", departmentName + " " + String.valueOf(organizationID), OperateLogLevel.critical);
                return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
            } else {
                return RestResult.getInstance(HttpResponseCode.ERROR, null);
            }
        } catch (Exception ex) {
            Map<String, Object> parameters = new HashMap<>(4);
            parameters.put("departmentName", departmentName);
            parameters.put("organizationID", organizationID);
            parameters.put("roleNames", roleNames);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "新增部门数据", parameters);
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }

    @Log
    @PutMapping("/department")
    public RestResult updateDepartment(HttpServletRequest request) {
        int departmentID = IntegerUtil.tryParse(request.getParameter("departmentId"), 0);
        String departmentName = request.getParameter("departmentName");
        int organizationID = IntegerUtil.tryParse(request.getParameter("organizationId"), 0);
        String remark = request.getParameter("remark");
        String enable = request.getParameter("enable");
        try {
            if (departmentID <= 0 || !organizationService.exist(organizationID)) {
                return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
            }

            Department department = new Department();
            department.setDepartmentId(departmentID);
            if (departmentName != null) {
                department.setDepartmentName(departmentName);
            }
            if (organizationID > 0) {
                if (departmentName != null) {
                    Department existDepartment = departmentService.getDepartment(departmentName, organizationID);
                    if (existDepartment != null && existDepartment.getDepartmentId() != departmentID) {
                        return RestResult.getInstance(HttpResponseCode.ERROR_RESOURCE_ALREADY_EXIST, null);
                    }
                }
                department.setOrganizationId(organizationID);
            }
            if (remark != null) {
                department.setRemark(remark);
            }

            if (enable != null) {
                try {
                    if (ENABLE_INT.equals(enable)) {
                        department.setIsEnable((byte) 1);
                    } else if (DISABLE_INT.equals(enable)) {
                        if (departmentService.inUse(departmentID)) {
                            return RestResult.getInstance(HttpResponseCode.RESOURCE_IN_USE, null);
                        } else {
                            department.setIsEnable((byte) 0);
                        }
                    } else {
                        return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
                    }
                } catch (Exception ex) {
                }
            }

            if (departmentService.updateDepartment(department) > 0) {
                logService.logOperate(logger, request, "更新部门信息", JSON.toJSONString(department), OperateLogLevel.critical);
                return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
            } else {
                return RestResult.getInstance(HttpResponseCode.ERROR, null);
            }
        } catch (
                Exception ex) {
            Map<String, Object> parameters = new HashMap<>(5);
            parameters.put("departmentID", departmentID);
            parameters.put("departmentName", departmentName);
            parameters.put("organizationID", organizationID);
            parameters.put("remark", remark);
            parameters.put("enable", enable);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "更新部门数据", parameters);
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }

    @Log
    @DeleteMapping("/department/{departmentID}")
    public RestResult deleteDepartment(HttpServletRequest request, @PathVariable(value = "departmentID") int departmentID) {
        try {
            if (departmentID <= 0) {
                return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
            }
            if (departmentService.inUse(departmentID)) {
                return RestResult.getInstance(HttpResponseCode.RESOURCE_IN_USE, null);
            }
            if (departmentService.deleteDepartment(departmentID) > 0) {
                logService.logOperate(logger, request, "删除部门信息", String.valueOf(departmentID), OperateLogLevel.critical);
                return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
            } else {
                return RestResult.getInstance(HttpResponseCode.ERROR, null);
            }
        } catch (Exception ex) {
            Map<String, Object> parameters = new HashMap<>(1);
            parameters.put("departmentID", departmentID);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "删除部门数据", parameters);
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }
}
