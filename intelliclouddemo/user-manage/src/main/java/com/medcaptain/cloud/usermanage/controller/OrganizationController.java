package com.medcaptain.cloud.usermanage.controller;

import static com.medcaptain.cloud.usermanage.constant.UserConstant.*;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.medcaptain.annotation.Log;
import com.medcaptain.cloud.usermanage.config.UserManageProperties;
import com.medcaptain.cloud.usermanage.entity.Organization;
import com.medcaptain.cloud.usermanage.entity.UserToken;
import com.medcaptain.cloud.usermanage.redis.RedisService;
import com.medcaptain.cloud.usermanage.service.LogService;
import com.medcaptain.cloud.usermanage.service.OrganizationService;
import com.medcaptain.dto.RestResult;
import com.medcaptain.enums.HttpResponseCode;
import com.medcaptain.helper.HttpServletHelper;
import com.medcaptain.logging.ExceptionLog;
import com.medcaptain.logging.OperateLogLevel;
import com.medcaptain.utils.IntegerUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 机构信息控制器
 *
 * @author bingwen.ai
 */
@RestController
public class OrganizationController {
    private Logger logger = LoggerFactory.getLogger(OrganizationController.class);

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    RedisService redisService;

    @Autowired
    UserManageProperties userManageProperties;

    @Autowired
    LogService logService;

    @Log
    @GetMapping("/organizations")
    public RestResult listOrganizations(@RequestParam(value = "page", defaultValue = "0") int pageIndex,
                                        @RequestParam(value = "per_page", defaultValue = "10") int pageSize,
                                        @RequestParam(value = "organizationType", defaultValue = "-1") int organizationType,
                                        @RequestParam(value = "provinceName", defaultValue = "") String provinceName,
                                        @RequestParam(value = "regionCode", defaultValue = "-1") int regionCode,
                                        @RequestParam(value = "organizationName", defaultValue = "") String organizationName,
                                        @RequestParam(value = "orderBy", defaultValue = "create_time asc") String orderBy) {
        try {
            PageInfo<Organization> organizationPageInfo = organizationService.listOrganizations(pageIndex, pageSize, organizationType,
                    provinceName, regionCode, organizationName, orderBy);
            return RestResult.getInstance(HttpResponseCode.SUCCESS, organizationPageInfo);
        } catch (Exception ex) {
            ExceptionLog exceptionLog = new ExceptionLog(ex, "获取机构列表");
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }

    /**
     * 添加机构信息，需初始化机构、机构基础科室、管理员角色以及默认管理员授权(批操作)。
     *
     * @param request HTTP请求
     * @return 操作结果
     */
    @Log
    @PostMapping(value = "/organization")
    public RestResult addOrganization(HttpServletRequest request) {
        String organizationName = request.getParameter("organizationName");
        String remark = request.getParameter("remark");
        int organizationLevel = IntegerUtil.tryParse(request.getParameter("organizationLevel"), 0);
        int organizationType = IntegerUtil.tryParse(request.getParameter("organizationType"), 0);
        int regionID = IntegerUtil.tryParse(request.getParameter("regionId"), 0);
        String departments = request.getParameter("departmentList");
        String managerEmail = request.getParameter("managerEmail");
        try {
            String[] departmentList = StringUtils.split(departments, ",");
            int[] departmentIDs = null;
            if (departmentList != null && departmentList.length > 0) {
                departmentIDs = new int[departmentList.length];
                for (int i = 0; i < departmentList.length; i++) {
                    departmentIDs[i] = IntegerUtil.tryParse(departmentList[i], 0);
                }
            }

            if (StringUtils.isBlank(organizationName) || organizationLevel <= 0 || organizationType < 0 || regionID <= 0 || StringUtils.isBlank(managerEmail)) {
                return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
            }
            if (organizationService.exist(organizationName)) {
                return RestResult.getInstance(HttpResponseCode.ERROR_RESOURCE_ALREADY_EXIST, null);
            }
            UserToken currentUserToken = redisService.getCacheUser(HttpServletHelper.getRequestToken(request));
            int currentUserID = currentUserToken == null ? 0 : currentUserToken.getUserID();
            if (currentUserID <= 0) {
                return RestResult.getInstance(HttpResponseCode.RESOURCE_NOT_FOUND, null);
            }

            if (organizationService.addOrganization(organizationName, organizationLevel, organizationType,
                    regionID, currentUserID, remark, managerEmail, departmentIDs)) {
                String logContent = "机构名称 = " + organizationName + " ,区域编号 = " + regionID;
                logService.logOperate(logger, request, "新增机构信息", logContent, OperateLogLevel.critical);
                return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
            } else {
                return RestResult.getInstance(HttpResponseCode.ERROR, null);
            }
        } catch (IllegalArgumentException ex) {
            Map<String, Object> parameters = new HashMap<>(6);
            parameters.put("organizationName", organizationName);
            parameters.put("remark", remark);
            parameters.put("organizationLevel", organizationLevel);
            parameters.put("organizationType", organizationType);
            parameters.put("regionID", regionID);
            parameters.put("departments", departments);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "添加机构信息", parameters);
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }

    @Log
    @PutMapping("/organization")
    public RestResult updateOrganization(HttpServletRequest request) {
        try {
            Organization organization = new Organization();
            RestResult validateResult = validateUpdateParameter(request, organization);
            if (validateResult == null) {
                if (organizationService.updateOrganization(organization) > 0) {
                    logService.logOperate(logger, request, "更新机构信息", JSON.toJSONString(organization), OperateLogLevel.critical);
                    return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
                } else {
                    return RestResult.getInstance(HttpResponseCode.ERROR, null);
                }
            }
            return validateResult;
        } catch (Exception ex) {
            ExceptionLog exceptionLog = new ExceptionLog(ex, "更新机构信息");
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }

    @Log
    @DeleteMapping("/organization/{organizationId}")
    public RestResult deleteOrganization(HttpServletRequest request, @PathVariable(value = "organizationId") int organizationId) {
        try {
            if (organizationService.inUse(organizationId)) {
                return RestResult.getInstance(HttpResponseCode.RESOURCE_IN_USE, null);
            }

            if (organizationService.deleteOrganization(organizationId) > 0) {
                logService.logOperate(logger, request, "删除机构信息", String.valueOf(organizationId), OperateLogLevel.critical);
                return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
            } else {
                return RestResult.getInstance(HttpResponseCode.ERROR, null);
            }
        } catch (Exception ex) {
            HashMap<String, Object> parameters = new HashMap<>(1);
            parameters.put("organizationId", organizationId);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "删除机构信息", parameters);
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }

    @GetMapping("/organization/{organizationId}")
    public RestResult getOrganization(@PathVariable(value = "organizationId") int organizationID) {
        try {
            if (organizationID <= 0) {
                return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
            }
            return RestResult.getInstance(HttpResponseCode.SUCCESS, organizationService.getOrganization(organizationID));
        } catch (Exception ex) {
            HashMap<String, Object> parameters = new HashMap<>(1);
            parameters.put("organizationID", organizationID);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "获取机构信息", parameters);
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }

    /**
     * 判断机构名称是否已存在
     *
     * @param organizationName 机构名称
     * @return true = 名称已存在 ; false = 不存在
     */
    @GetMapping(value = "/organization/name/{organizationName}")
    public RestResult existOrganizationName(@PathVariable(value = "organizationName") String organizationName) {
        try {
            return RestResult.getInstance(HttpResponseCode.SUCCESS, organizationService.exist(organizationName));
        } catch (Exception ex) {
            ExceptionLog exceptionLog = new ExceptionLog(ex, "判断机构名称是否存在");
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }

    private RestResult validateUpdateParameter(HttpServletRequest request, Organization organization) {
        String organizationName = request.getParameter("organizationName");
        String remark = request.getParameter("remark");
        int organizationLevel = IntegerUtil.tryParse(request.getParameter("organizationLevel"), 0);
        int organizationType = IntegerUtil.tryParse(request.getParameter("organizationType"), 0);
        int regionID = IntegerUtil.tryParse(request.getParameter("regionId"), 0);
        int organizationID = IntegerUtil.tryParse(request.getParameter("organizationId"), 0);

        if (organizationID <= 0) {
            return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }
        if (!organizationService.exist(organizationID)) {
            return RestResult.getInstance(HttpResponseCode.RESOURCE_NOT_FOUND, null);
        }
        organization.setOrganizationId(organizationID);
        if (organizationName != null) {
            if (StringUtils.isBlank(organizationName)) {
                return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
            }
            Organization existOrganization = organizationService.getOrganization(organizationName);
            if (existOrganization != null && existOrganization.getOrganizationId() != organizationID) {
                return RestResult.getInstance(HttpResponseCode.ERROR_RESOURCE_ALREADY_EXIST, null);
            }
            organization.setOrganizationName(organizationName);
        }

        if (regionID > 0) {
            organization.setRegionId(regionID);
        }
        if (organizationLevel > 0) {
            organization.setOrganizationLevel(organizationLevel);
        }
        if (organizationType > 0) {
            organization.setOrganizationType(organizationType);
        }
        if (remark != null) {
            organization.setRemark(remark);
        }
        String enable = request.getParameter("enable");
        if (enable != null) {
            if (ENABLE_INT.equals(enable)) {
                organization.setIsEnable((byte) 1);
            } else if (DISABLE_INT.equals(enable)) {
                if (organizationService.inUse(organizationID)) {
                    return RestResult.getInstance(HttpResponseCode.RESOURCE_IN_USE, null);
                } else {
                    organization.setIsEnable((byte) 0);
                }
            } else {
                return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
            }
        }
        return null;
    }
}
