package com.medcaptain.cloud.usermanage.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.medcaptain.annotation.Log;
import com.medcaptain.cloud.usermanage.entity.FrontendResource;
import com.medcaptain.cloud.usermanage.redis.RedisService;
import com.medcaptain.cloud.usermanage.service.FrontendResourceService;
import com.medcaptain.cloud.usermanage.service.LogService;
import com.medcaptain.cloud.usermanage.service.ResourceMappingService;
import com.medcaptain.dto.RestResult;
import com.medcaptain.enums.HttpResponseCode;
import com.medcaptain.logging.ExceptionLog;
import com.medcaptain.logging.OperateLogLevel;
import com.medcaptain.utils.ByteUtils;
import com.medcaptain.utils.IntegerUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static com.medcaptain.cloud.usermanage.constant.UserConstant.ENABLE_INT;

/**
 * 前端资源信息控制器
 *
 * @author bingwen.ai
 */
@RestController
public class FrontendResourceController {
    private Logger logger = LoggerFactory.getLogger(FrontendResourceController.class);

    @Autowired
    FrontendResourceService frontendResourceService;

    @Autowired
    ResourceMappingService resourceMappingService;

    @Autowired
    RedisService redisService;

    @Autowired
    LogService logService;

    @Log
    @PostMapping(value = "/frontendresource")
    public RestResult addResource(HttpServletRequest request) {
        String resourceName = request.getParameter("resourceName");
        String elementID = request.getParameter("elementId");
        byte resourceType = ByteUtils.tryParse(request.getParameter("resourceType"), (byte) 0);
        String remark = request.getParameter("remark");
        String parentResourceIDParameter = request.getParameter("parentResourceId");
        int parentResourceID = IntegerUtil.tryParse(parentResourceIDParameter, 0);
        String menuURL = request.getParameter("menuURL");
        String menuIcon = request.getParameter("menuIcon");
        try {
            boolean isIllegalArgument = StringUtils.isBlank(resourceName) || resourceType <= 0
                    || (StringUtils.isNotBlank(parentResourceIDParameter) && parentResourceID < 0);
            if (isIllegalArgument) {
                return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
            }
            if (frontendResourceService.exist(resourceName, resourceType)) {
                return RestResult.getInstance(HttpResponseCode.ERROR_RESOURCE_ALREADY_EXIST, null);
            }

            FrontendResource resource = new FrontendResource();
            resource.setResourceName(resourceName);
            resource.setElementId(elementID);
            resource.setResourceType(resourceType);
            if (StringUtils.isNotBlank(remark)) {
                resource.setRemark(remark);
            }
            if (StringUtils.isNotBlank(menuIcon)) {
                resource.setMenuIcon(menuIcon);
            }
            if (StringUtils.isNotBlank(menuURL)) {
                resource.setMenuUrl(menuURL);
            }
            if (parentResourceID > 0) {
                resource.setParentResourceId(parentResourceID);
            }
            if (frontendResourceService.addResource(resource)) {
                String logContent = "资源名称 = " + resourceName + " ,资源类型 = " + String.valueOf(resourceType);
                logService.logOperate(logger, request, "新增前端资源", logContent, OperateLogLevel.critical);
                return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
            } else {
                return RestResult.getInstance(HttpResponseCode.ERROR, null);
            }
        } catch (Exception ex) {
            Map<String, Object> parameters = new HashMap<>(5);
            parameters.put("resourceName", resourceName);
            parameters.put("elementID", elementID);
            parameters.put("resourceType", resourceType);
            parameters.put("remark", remark);
            parameters.put("parentResourceIDParameter", parentResourceIDParameter);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "添加前端资源", parameters);
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }

    @Log
    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping(value = "/frontendresource/{frontendResourceId}")
    public RestResult deleteResource(HttpServletRequest request, @PathVariable(value = "frontendResourceId") int frontendResourceId) {
        if (frontendResourceId <= 0) {
            return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }
        try {
            if (redisService.removeRolePermissionByFrontendResource(frontendResourceId)
                    && frontendResourceService.delete(frontendResourceId)
                    && resourceMappingService.deleteByFrontendResource(frontendResourceId)) {
                logService.logOperate(logger, request, "删除前端资源", String.valueOf(frontendResourceId), OperateLogLevel.critical);
                return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
            } else {
                return RestResult.getInstance(HttpResponseCode.ERROR, null);
            }
        } catch (Exception ex) {
            Map<String, Object> parameters = new HashMap<>(1);
            parameters.put("frontendResourceId", frontendResourceId);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "删除前端资源", parameters);
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }

    @Log
    @PutMapping(value = "/frontendresource")
    public RestResult updateResource(HttpServletRequest request) {
        int frontendResourceID = IntegerUtil.tryParse(request.getParameter("frontendResourceId"), 0);
        String resourceName = request.getParameter("resourceName");
        String elementID = request.getParameter("elementId");
        byte resourceType = ByteUtils.tryParse(request.getParameter("resourceType"), (byte) 0);
        String remark = request.getParameter("remark");
        String parentResourceIDParameter = request.getParameter("parentResourceId");
        String enable = request.getParameter("enable");
        int parentResourceID = IntegerUtil.tryParse(parentResourceIDParameter, 0);
        String menuURL = request.getParameter("menuURL");
        String menuIcon = request.getParameter("menuIcon");
        try {
            if (frontendResourceID <= 0) {
                return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
            }
            FrontendResource resource = new FrontendResource();
            resource.setFrontendResourceId(frontendResourceID);
            if (resourceName != null) {
                resource.setResourceName(resourceName);
            }
            if (elementID != null) {
                resource.setElementId(elementID);
            }
            if (resourceType > 0) {
                resource.setResourceType(resourceType);
            }
            if (remark != null) {
                resource.setRemark(remark);
            }
            if (menuURL != null) {
                resource.setMenuUrl(menuURL);
            }
            if (menuIcon != null) {
                resource.setMenuIcon(menuIcon);
            }
            if (parentResourceID > 0) {
                resource.setParentResourceId(parentResourceID);
            }
            if (StringUtils.isNotEmpty(enable)) {
                try {
                    boolean enableValue = ENABLE_INT.equals(enable);
                    if (enableValue) {
                        resource.setIsEnable((byte) 1);
                    } else {
                        resource.setIsEnable((byte) 0);
                    }
                } catch (Exception ex) {
                }
            }

            if (frontendResourceService.updateResource(resource)) {
                logService.logOperate(logger, request, "更新前端资源", JSON.toJSONString(resource), OperateLogLevel.critical);
                return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
            } else {
                return RestResult.getInstance(HttpResponseCode.ERROR, null);
            }
        } catch (Exception ex) {
            Map<String, Object> parameters = new HashMap<>(6);
            parameters.put("frontendResourceID", frontendResourceID);
            parameters.put("resourceName", resourceName);
            parameters.put("elementID", elementID);
            parameters.put("resourceType", resourceType);
            parameters.put("remark", remark);
            parameters.put("parentResourceIDParameter", parentResourceIDParameter);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "更新前端资源", parameters);
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }

    @GetMapping(value = "/frontendresource/{frontendResourceId}")
    public RestResult getResource(@PathVariable(value = "frontendResourceId") int frontendResourceId) {
        if (frontendResourceId <= 0) {
            return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }
        return RestResult.getInstance(HttpResponseCode.SUCCESS, frontendResourceService.getResource(frontendResourceId));
    }

    @Log
    @GetMapping(value = "/frontendresources")
    public RestResult listResources(HttpServletRequest request) {
        int pageIndex = IntegerUtil.tryParse(request.getParameter("page"), 0);
        int pageSize = IntegerUtil.tryParse(request.getParameter("per_page"), 10);
        try {
            PageInfo<FrontendResource> frontendResourceList = frontendResourceService.listResources(pageIndex, pageSize);
            return RestResult.getInstance(HttpResponseCode.SUCCESS, frontendResourceList);
        } catch (Exception ex) {
            Map<String, Object> parameters = new HashMap<>(2);
            parameters.put("pageIndex", pageIndex);
            parameters.put("pageSize", pageSize);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "获取前端资源列表", parameters);
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }
}
