package com.medcaptain.cloud.usermanage.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.medcaptain.annotation.Log;
import com.medcaptain.cloud.usermanage.entity.BackendResource;
import com.medcaptain.cloud.usermanage.redis.RedisService;
import com.medcaptain.cloud.usermanage.service.BackendResourceService;
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

import static com.medcaptain.cloud.usermanage.constant.UserConstant.*;

/**
 * 后端资源信息控制器
 *
 * @author bingwen.ai
 */
@RestController
public class BackendResourceController {
    private Logger logger = LoggerFactory.getLogger(BackendResourceController.class);

    @Autowired
    BackendResourceService backendResourceService;

    @Autowired
    ResourceMappingService resourceMappingService;

    @Autowired
    RedisService redisService;

    @Autowired
    LogService logService;

    @Log
    @PostMapping(value = "/backendresource")
    public RestResult addResource(HttpServletRequest request) {
        String resourceName = request.getParameter("resourceName");
        String resourceURL = request.getParameter("resourceURL");
        // 后端资源的请求类型 1 = POST ; 2 = DELETE ; 3 = PUT ; 4 = GET
        byte requestType = ByteUtils.tryParse(request.getParameter("requestType"), (byte) 0);
        String remark = request.getParameter("remark");
        try {
            if (StringUtils.isBlank(resourceName) || requestType < MIN_REQUEST_TYPE || requestType > MAX_REQUEST_TYPE) {
                return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
            }
            if (backendResourceService.exist(resourceName)) {
                return RestResult.getInstance(HttpResponseCode.ERROR_RESOURCE_ALREADY_EXIST, null);
            }
            BackendResource resource = new BackendResource();
            resource.setResourceName(resourceName);
            resource.setRequestType(requestType);
            resource.setResourceUrl(resourceURL);
            resource.setRemark(remark);
            if (backendResourceService.addResource(resource)) {
                logService.logOperate(logger, request, "新增后端资源", resourceName, OperateLogLevel.critical);
                return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
            } else {
                return RestResult.getInstance(HttpResponseCode.ERROR, null);
            }
        } catch (Exception ex) {
            HashMap<String, Object> params = new HashMap<>(4);
            params.put("resourceName", resourceName);
            params.put("resourceURL", resourceURL);
            params.put("requestType", requestType);
            params.put("remark", remark);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "新增后端资源");
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, params);
        }
    }

    @Log
    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping(value = "/backendresource/{backendResourceId}")
    public RestResult deleteResource(HttpServletRequest request, @PathVariable(value = "backendResourceId") int backendResourceID) {
        try {
            if (backendResourceID <= 0) {
                return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
            }
            if (redisService.removeRolePermissionByBackendResource(backendResourceID)
                    && resourceMappingService.deleteByBackendResource(backendResourceID)
                    && backendResourceService.delete(backendResourceID)) {
                logService.logOperate(logger, request, "删除后端资源", String.valueOf(backendResourceID), OperateLogLevel.critical);
                return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
            } else {
                return RestResult.getInstance(HttpResponseCode.ERROR, null);
            }
        } catch (Exception ex) {
            ExceptionLog exceptionLog = new ExceptionLog(ex, "删除后端资源");
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }

    @Log
    @PutMapping(value = "/backendresource")
    public RestResult updateResource(HttpServletRequest request) {
        try {
            int backendResourceID = IntegerUtil.tryParse(request.getParameter("backendResourceId"), 0);
            String resourceName = request.getParameter("resourceName");
            String resourceURL = request.getParameter("resourceURL");
            byte requestType = ByteUtils.tryParse(request.getParameter("requestType"), (byte) 0);
            String remark = request.getParameter("remark");
            String enable = request.getParameter("enable");

            if (backendResourceID <= 0) {
                return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
            }

            BackendResource resource = new BackendResource();
            resource.setBackendResourceId(backendResourceID);
            if (resourceName != null) {
                resource.setResourceName(resourceName);
            }
            if (resourceURL != null) {
                resource.setResourceUrl(resourceURL);
            }
            if (requestType > 0) {
                resource.setRequestType(requestType);
            }
            if (remark != null) {
                resource.setRemark(remark);
            }

            if (enable != null) {
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

            if (backendResourceService.updateResource(resource)) {
                logService.logOperate(logger, request, "更新后端资源", JSON.toJSONString(resource), OperateLogLevel.critical);
                return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
            } else {
                return RestResult.getInstance(HttpResponseCode.ERROR, null);
            }
        } catch (Exception ex) {
            ExceptionLog exceptionLog = new ExceptionLog(ex, "更新后端资源");
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }

    @GetMapping(value = "/backendresource/{resourceId}")
    public RestResult getResource(@PathVariable(value = "resourceId") int resourceID) {
        try {
            if (resourceID <= 0) {
                return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
            }
            return RestResult.getInstance(HttpResponseCode.SUCCESS, backendResourceService.getResource(resourceID));
        } catch (Exception ex) {
            ExceptionLog exceptionLog = new ExceptionLog(ex, "获取单个后端资源");
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }

    @Log
    @GetMapping(value = "/backendresources")
    public RestResult listBackendResources(HttpServletRequest request) {
        try {
            int pageIndex = IntegerUtil.tryParse(request.getParameter("page"), 0);
            int pageSize = IntegerUtil.tryParse(request.getParameter("per_page"), 10);
            PageInfo<BackendResource> backendResourcePageInfo = backendResourceService.listResources(pageIndex, pageSize);
            return RestResult.getInstance(HttpResponseCode.SUCCESS, backendResourcePageInfo);
        } catch (Exception ex) {
            ExceptionLog exceptionLog = new ExceptionLog(ex, "获取后端资源列表");
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }
}
