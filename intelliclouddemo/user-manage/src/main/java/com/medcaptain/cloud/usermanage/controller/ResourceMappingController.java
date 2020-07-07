package com.medcaptain.cloud.usermanage.controller;

import com.medcaptain.cloud.usermanage.constant.FrontendResourceType;
import com.medcaptain.cloud.usermanage.entity.FrontendResource;
import com.medcaptain.cloud.usermanage.entity.ResourceMapping;
import com.medcaptain.cloud.usermanage.service.BackendResourceService;
import com.medcaptain.cloud.usermanage.service.FrontendResourceService;
import com.medcaptain.cloud.usermanage.service.LogService;
import com.medcaptain.cloud.usermanage.service.ResourceMappingService;
import com.medcaptain.dto.RestResult;
import com.medcaptain.enums.HttpResponseCode;
import com.medcaptain.logging.ExceptionLog;
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
import java.util.HashMap;
import java.util.List;

/**
 * 前后端资源映射信息控制器
 *
 * @author bingwen.ai
 */
@RestController
public class ResourceMappingController {
    private Logger logger = LoggerFactory.getLogger(ResourceMappingController.class);

    @Autowired
    ResourceMappingService resourceMappingService;

    @Autowired
    FrontendResourceService frontendResourceService;

    @Autowired
    BackendResourceService backendResourceService;

    @Autowired
    LogService logService;

    @Transactional(rollbackFor = Exception.class)
    @PostMapping(value = "/resourcemapping")
    public RestResult addMappings(HttpServletRequest request) {
        int frontendResourceId = IntegerUtil.tryParse(request.getParameter("frontendResourceId"), 0);
        String backendResourceIds = request.getParameter("backendResourceId");
        try {
            String[] backendResourceIdArray = StringUtils.split(backendResourceIds, ",");
            List<Integer> backendResourceIdList = new ArrayList<>();
            try {
                for (String backendResourceId : backendResourceIdArray) {
                    backendResourceIdList.add(Integer.valueOf(backendResourceId));
                }
            } catch (Exception ex) {
                return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
            }
            if (frontendResourceId <= 0 || backendResourceIdList.size() <= 0) {
                return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
            }
            FrontendResource frontendResource = frontendResourceService.getResource(frontendResourceId);
            if (frontendResource == null) {
                return RestResult.getInstance(HttpResponseCode.RESOURCE_NOT_FOUND, null);
            } else if (frontendResource.getResourceType() != FrontendResourceType.RESOURCE.ordinal()) {
                return RestResult.getInstance(400, "只有前端资源能设置资源映射", null);
            }
            for (int backendResourceID : backendResourceIdList) {
                if (!backendResourceService.exist(backendResourceID)) {
                    return RestResult.getInstance(HttpResponseCode.RESOURCE_NOT_FOUND, null);
                }
                if (resourceMappingService.exist(frontendResourceId, backendResourceID)) {
                    return RestResult.getInstance(HttpResponseCode.ERROR_RESOURCE_ALREADY_EXIST, null);
                }
            }

            boolean insertSuccess = true;
            for (int backendResourceID : backendResourceIdList) {
                insertSuccess = insertSuccess && resourceMappingService.addMapping(frontendResourceId, backendResourceID);
                if (!insertSuccess) {
                    return RestResult.getInstance(HttpResponseCode.ERROR, null);
                }
            }
            String logContent = "前端资源编号 = " + frontendResourceId + " ,后端资源编号 = " + backendResourceIds;
            logService.logOperate(logger, request, "新增资源映射", logContent, OperateLogLevel.critical);
            return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
        } catch (Exception ex) {
            HashMap<String, Object> parameters = new HashMap<>(2);
            parameters.put("frontendResourceId", frontendResourceId);
            parameters.put("backendResourceIds", backendResourceIds);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "添加前后端资源映射", parameters);
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }

    @DeleteMapping(value = "/resourcemapping/{resourceMappingId}")
    public RestResult deleteMapping(HttpServletRequest request, @PathVariable(value = "resourceMappingId") String resourceMappingId) {
        try {
            String[] resourceMappingIds = StringUtils.split(resourceMappingId, ",");
            List<Integer> resourceMappingIDList = new ArrayList<>();
            try {
                for (String mappingID : resourceMappingIds) {
                    resourceMappingIDList.add(Integer.valueOf(mappingID));
                }
            } catch (Exception ex) {
                return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
            }
            if (resourceMappingIDList.size() <= 0) {
                return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
            }
            boolean deleteComplete = true;
            for (int resourceMappingID : resourceMappingIDList) {
                if (resourceMappingID > 0) {
                    ResourceMapping resourceMapping = resourceMappingService.getMapping(resourceMappingID);
                    deleteComplete = deleteComplete && resourceMappingService.deleteMapping(resourceMappingID);
                    if (!deleteComplete) {
                        return RestResult.getInstance(HttpResponseCode.ERROR, null);
                    }
                    if (resourceMapping != null) {
                        String logContent = "前端资源编号 = " + resourceMapping.getFrontendResourceId() + " ,后端资源编号 = " + resourceMapping.getBackendResourceId();
                        logService.logOperate(logger, request, "删除资源映射", logContent, OperateLogLevel.critical);
                    }
                }
            }
            return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
        } catch (Exception ex) {
            HashMap<String, Object> parameters = new HashMap<>(1);
            parameters.put("resourceMappingId", resourceMappingId);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "删除前后端资源映射", parameters);
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }

    @GetMapping(value = "/resourcemappings/{frontendResourceId}")
    public RestResult listMappings(@PathVariable(value = "frontendResourceId") int frontendResourceID) {
        try {
            if (frontendResourceID <= 0) {
                return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
            }

            List<ResourceMapping> resourceMappings = resourceMappingService.listResourceMappings(frontendResourceID);
            return RestResult.getInstance(HttpResponseCode.SUCCESS, resourceMappings);
        } catch (Exception ex) {
            HashMap<String, Object> parameters = new HashMap<>(1);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "获取前后端资源映射", parameters);
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }
}
