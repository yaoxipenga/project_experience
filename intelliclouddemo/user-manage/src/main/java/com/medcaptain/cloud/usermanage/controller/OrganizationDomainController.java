package com.medcaptain.cloud.usermanage.controller;

import com.medcaptain.cloud.usermanage.service.LogService;
import com.medcaptain.cloud.usermanage.service.OrganizationDomainService;
import com.medcaptain.cloud.usermanage.service.OrganizationService;
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
 * 机构域名控制器
 *
 * @author bingwen.ai
 */
@RestController
public class OrganizationDomainController {
    private Logger logger = LoggerFactory.getLogger(OrganizationDomainController.class);

    @Autowired
    OrganizationDomainService organizationDomainService;

    @Autowired
    OrganizationService organizationService;

    @Autowired
    LogService logService;

    @PostMapping(value = "/organization/domain")
    public RestResult addDomain(HttpServletRequest request,
                                @RequestParam(value = "organizationId") int organizationID,
                                @RequestParam(value = "domainUrl") String domainUrl) {
        if (organizationID <= 0 || StringUtils.isBlank(domainUrl)) {
            return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }
        if (!organizationService.exist(organizationID)) {
            return RestResult.getInstance(HttpResponseCode.RESOURCE_NOT_FOUND, null);
        }
        if (organizationDomainService.existDomain(domainUrl)) {
            return RestResult.getInstance(400, "域名已被指定", null);
        }

        if (organizationDomainService.addOrganizationDomain(organizationID, domainUrl)) {
            String logContent = "机构编号 = " + organizationID + " ,域名 = " + domainUrl;
            logService.logOperate(logger, request, "新增机构域名", logContent, OperateLogLevel.critical);
            return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
        } else {
            return RestResult.getInstance(HttpResponseCode.ERROR, null);
        }
    }

    @DeleteMapping(value = "/organization/domain/{domainId}")
    public RestResult deleteDomain(HttpServletRequest request, @PathVariable(value = "domainId") int domainID) {
        if (domainID <= 0) {
            return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }
        if (!organizationDomainService.existDomain(domainID)) {
            return RestResult.getInstance(HttpResponseCode.RESOURCE_NOT_FOUND, null);
        }

        if (organizationDomainService.deleteOrganizationDomain(domainID)) {
            logService.logOperate(logger, request, "删除机构域名", String.valueOf(domainID), OperateLogLevel.critical);
            return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
        } else {
            return RestResult.getInstance(HttpResponseCode.ERROR, null);
        }
    }

    @PutMapping(value = "/organization/domain/{domainId}")
    public RestResult updateDomain(HttpServletRequest request, @PathVariable(value = "domainId") int domainID) {
        String domainUrl = request.getParameter("domainUrl");
        if (domainID <= 0 || StringUtils.isBlank(domainUrl)) {
            return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }
        if (organizationDomainService.existDomain(domainUrl)) {
            return RestResult.getInstance(HttpResponseCode.ERROR_RESOURCE_ALREADY_EXIST, null);
        }
        if (!organizationDomainService.existDomain(domainID)) {
            return RestResult.getInstance(HttpResponseCode.RESOURCE_NOT_FOUND, null);
        }
        if (organizationDomainService.updateOrganizationDomain(domainID, domainUrl)) {
            logService.logOperate(logger, request, "修改机构域名", String.valueOf(domainID) + " > " + domainUrl, OperateLogLevel.critical);
            return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
        } else {
            return RestResult.getInstance(HttpResponseCode.ERROR, null);
        }
    }

    @GetMapping(value = "/organization/domains/{organizationId}")
    public RestResult listDomains(@PathVariable(value = "organizationId") int organizationID) {
        if (organizationID <= 0) {
            return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }
        return RestResult.getInstance(HttpResponseCode.SUCCESS, organizationDomainService.listDomains(organizationID));
    }

    @GetMapping(value = "/organization/domain/{domainUrl}")
    public RestResult existDomain(@PathVariable(value = "domainUrl") String domainUrl) {
        return RestResult.getInstance(HttpResponseCode.SUCCESS, organizationDomainService.existDomain(domainUrl));
    }
}
