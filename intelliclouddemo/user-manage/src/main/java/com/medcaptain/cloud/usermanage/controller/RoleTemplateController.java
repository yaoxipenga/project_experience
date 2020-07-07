package com.medcaptain.cloud.usermanage.controller;

import com.alibaba.fastjson.JSON;
import com.medcaptain.annotation.Log;
import com.medcaptain.cloud.usermanage.entity.RoleTemplate;
import com.medcaptain.cloud.usermanage.service.LogService;
import com.medcaptain.cloud.usermanage.service.RoleTemplateService;
import com.medcaptain.dto.RestResult;
import com.medcaptain.enums.HttpResponseCode;
import com.medcaptain.logging.ExceptionLog;
import com.medcaptain.logging.OperateLogLevel;
import com.medcaptain.utils.ByteUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 角色模板相关REST接口
 * <p>操作角色模板信息，机构初始化时为默认管理员角色分配前端权限</p>
 *
 * @author bingwen.ai
 */

@RestController
public class RoleTemplateController {
    private Logger logger = LoggerFactory.getLogger(RoleTemplateController.class);

    @Autowired
    RoleTemplateService roleTemplateService;

    @Autowired
    LogService logService;

    /**
     * 新增角色模板
     *
     * @param templateName 角色模板名称
     * @param templateType 角色模板类型
     * @param remark       备注信息
     * @return 操作结果
     */
    @Log
    @PostMapping(value = "/roleTemplate")
    public RestResult addTemplate(@RequestParam(value = "templateName") String templateName,
                                  @RequestParam(value = "templateType") String templateType,
                                  @RequestParam(value = "remark", defaultValue = "") String remark,
                                  HttpServletRequest request) {
        if (StringUtils.isBlank(templateName) || StringUtils.isBlank(templateType)) {
            return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }

        try {
            if (roleTemplateService.existTemplate(templateType) || roleTemplateService.existTemplate(templateName, templateType)) {
                return RestResult.getInstance(HttpResponseCode.ERROR_RESOURCE_ALREADY_EXIST, null);
            }

            if (roleTemplateService.addTemplate(templateName, templateType, remark)) {
                String logContent = "角色模板名称 = " + templateName + " ,模板类型 = " + templateType;
                logService.logOperate(logger, request, "新增角色模板", logContent, OperateLogLevel.critical);
                return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
            } else {
                return RestResult.getInstance(HttpResponseCode.ERROR, null);
            }
        } catch (Exception ex) {
            Map<String, Object> parameters = new HashMap<>(3);
            parameters.put("templateName", templateName);
            parameters.put("templateType", templateType);
            parameters.put("remark", remark);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "新增角色模板", parameters);
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }

    /**
     * 删除角色模板
     *
     * @param templateID 角色模板编号
     * @return 操作结果
     */
    @Log
    @DeleteMapping(value = "/roleTemplate/{roleTemplateId}")
    public RestResult deleteTemplate(@PathVariable(value = "roleTemplateId") int templateID, HttpServletRequest request) {
        if (templateID <= 0) {
            return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }
        try {
            if (!roleTemplateService.existTemplate(templateID)) {
                return RestResult.getInstance(HttpResponseCode.RESOURCE_NOT_FOUND, null);
            }

            if (roleTemplateService.deleteTemplate(templateID)) {
                logService.logOperate(logger, request, "新增角色模板", "删除角色模板,ID = " + templateID, OperateLogLevel.critical);
                return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
            } else {
                return RestResult.getInstance(HttpResponseCode.ERROR, null);
            }
        } catch (Exception ex) {
            Map<String, Object> parameters = new HashMap<>(1);
            parameters.put("templateID", templateID);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "删除角色模板", parameters);
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }

    /**
     * 更新角色模板信息
     *
     * @param templateID 模板编号
     * @param request    HTTP请求
     * @return 操作结果
     */
    @Log
    @PutMapping(value = "/roleTemplate/{roleTemplateId}")
    public RestResult updateTemplate(@PathVariable(value = "roleTemplateId") int templateID, HttpServletRequest request) {
        if (templateID <= 0) {
            return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }

        String templateName = request.getParameter("templateName");
        String templateType = request.getParameter("templateType");
        String remark = request.getParameter("remark");
        byte isEnable = ByteUtils.tryParse(request.getParameter("isEnable"), (byte) -1);
        try {
            RoleTemplate roleTemplate = new RoleTemplate();
            roleTemplate.setRoleTemplateId(templateID);
            if (templateName != null) {
                roleTemplate.setTemplateName(templateName);
            }
            if (templateType != null) {
                roleTemplate.setTemplateType(templateType);
            }
            if (remark != null) {
                roleTemplate.setRemark(remark);
            }
            if (isEnable == 0 || isEnable == 1) {
                roleTemplate.setIsEnable(isEnable);
            }

            if (roleTemplateService.updateTemplate(roleTemplate)) {
                logService.logOperate(logger, request, "更新角色模板", JSON.toJSONString(roleTemplate), OperateLogLevel.critical);
                return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
            } else {
                return RestResult.getInstance(HttpResponseCode.ERROR, null);
            }
        } catch (Exception ex) {
            Map<String, Object> parameters = new HashMap<>(4);
            parameters.put("templateID", templateID);
            parameters.put("templateName", templateName);
            parameters.put("templateType", templateType);
            parameters.put("remark", remark);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "更新角色模板", parameters);
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }

    /**
     * 获取角色模板列表
     *
     * @param pageIndex 分页页码
     * @param pageSize  每页数目
     * @return 角色模板列表json
     */
    @Log
    @GetMapping(value = "/roleTemplates")
    public RestResult listRoleTemplates(@RequestParam(value = "page", defaultValue = "0") int pageIndex,
                                        @RequestParam(value = "per_page", defaultValue = "10") int pageSize) {
        if (pageIndex < 0 || pageSize <= 0) {
            return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }
        return RestResult.getInstance(HttpResponseCode.SUCCESS, roleTemplateService.listTemplates(pageIndex, pageSize));
    }
}
