package com.medcaptain.cloud.usermanage.controller;

import com.github.pagehelper.PageInfo;
import com.medcaptain.cloud.usermanage.entity.DepartmentTemplate;
import com.medcaptain.cloud.usermanage.service.DepartmentTemplateService;
import com.medcaptain.cloud.usermanage.service.LogService;
import com.medcaptain.dto.RestResult;
import com.medcaptain.enums.HttpResponseCode;
import com.medcaptain.logging.OperateLogLevel;
import com.medcaptain.utils.IntegerUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 部门模板信息控制器
 *
 * @author bingwen.ai
 */
@RestController
@RequestMapping
public class DepartmentTemplateController {
    private Logger logger = LoggerFactory.getLogger(DepartmentTemplateController.class);

    @Autowired
    private DepartmentTemplateService departmentTemplateService;

    @Autowired
    LogService logService;

    @PostMapping(value = "/departmenttemplate")
    public RestResult addDepartmentTemplate(@RequestParam(value = "departmentName") String departmentName,
                                            @RequestParam(value = "remark", required = false) String remark,
                                            HttpServletRequest request) {
        //查找是否存在相同名称模板
        if (departmentTemplateService.exist(departmentName)) {
            return RestResult.getInstance(HttpResponseCode.ERROR_RESOURCE_ALREADY_EXIST, null);
        }

        DepartmentTemplate departmentTemplate = new DepartmentTemplate();
        departmentTemplate.setDepartmentName(departmentName);
        if (StringUtils.isNotBlank(remark)) {
            departmentTemplate.setRemark(remark);
        }

        if (departmentTemplateService.addTemplate(departmentTemplate)) {
            logService.logOperate(logger, request, "新增部门模板", departmentName, OperateLogLevel.critical);
            return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
        } else {
            return RestResult.getInstance(HttpResponseCode.ERROR, null);
        }
    }

    @GetMapping(value = "/departmenttemplates")
    public RestResult listDepartmentTemplates(HttpServletRequest request) {
        int pageIndex = IntegerUtil.tryParse(request.getParameter("page"), 0);
        int pageSize = IntegerUtil.tryParse(request.getParameter("per_page"), 10);
        PageInfo<DepartmentTemplate> templates = departmentTemplateService.listDepartmentTemplates(pageIndex, pageSize);
        return RestResult.getInstance(HttpResponseCode.SUCCESS, templates);
    }

    @GetMapping(value = "/departmenttemplate/{templateID}")
    public RestResult getDepartmentTemplate(@PathVariable(value = "templateID") int templateID) {
        DepartmentTemplate template = departmentTemplateService.selectByTemplateID(templateID);
        return RestResult.getInstance(HttpResponseCode.SUCCESS, template);
    }

    @DeleteMapping(value = "/departmenttemplate/{templateID}")
    public RestResult deleteDepartmentTemplate(HttpServletRequest request, @PathVariable(value = "templateID") int templateID) {
        if (departmentTemplateService.deleteByTemplate(templateID)) {
            logService.logOperate(logger, request, "删除部门模板", String.valueOf(templateID), OperateLogLevel.critical);
            return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
        } else {
            return RestResult.getInstance(HttpResponseCode.ERROR, null);
        }
    }

    @PutMapping(value = "/departmenttemplate")
    public RestResult updateDepartmentTemplate(@RequestParam(value = "departmentTemplateId") int departmentTemplateID,
                                               @RequestParam(value = "departmentName") String departmentName,
                                               @RequestParam(value = "remark", required = false) String remark,
                                               HttpServletRequest request) {
        if (departmentTemplateID <= 0) {
            return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }

        DepartmentTemplate departmentTemplate = new DepartmentTemplate();
        departmentTemplate.setDepartmentTemplateId(departmentTemplateID);
        if (departmentName != null) {
            departmentTemplate.setDepartmentName(departmentName);
        }
        if (remark != null) {
            departmentTemplate.setRemark(remark);
        }
        if (departmentTemplateService.updateTemplate(departmentTemplate)) {
            String logContent = "模板编号 = " + departmentTemplateID + " , 模板名称 = " + departmentName + " ，备注 = " + remark;
            logService.logOperate(logger, request, "修改部门模板", logContent, OperateLogLevel.critical);
            return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
        } else {
            return RestResult.getInstance(HttpResponseCode.ERROR, null);
        }
    }
}
