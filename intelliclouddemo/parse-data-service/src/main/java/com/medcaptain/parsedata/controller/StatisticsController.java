package com.medcaptain.parsedata.controller;

import com.medcaptain.annotation.Log;
import com.medcaptain.dto.RestResult;
import com.medcaptain.enums.HttpResponseCode;
import com.medcaptain.logging.ExceptionLog;
import com.medcaptain.parsedata.entity.mysql.StatisticsScript;
import com.medcaptain.parsedata.service.StatisticsScriptService;
import com.medcaptain.parsedata.statistics.CommonSelectFactory;
import com.medcaptain.parsedata.statistics.CommonSelectService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * 数据统计REST接口
 *
 * @author bingwen.ai
 */
@RestController
public class StatisticsController {
    private Logger logger = LoggerFactory.getLogger(StatisticsController.class);

    @Autowired
    StatisticsScriptService statisticsScriptService;

    @Autowired
    CommonSelectFactory commonSelectFactory;

    /**
     * 按统计脚本名称统计数据
     *
     * @param scriptName 统计脚本名称
     * @return 统计结果
     */
    @Log(logParameterNames = "scriptName")
    @GetMapping(value = "/statistics/{scriptName}")
    public RestResult statistics(HttpServletRequest request, @PathVariable(name = "scriptName") String scriptName) {
        if (StringUtils.isEmpty(scriptName)) {
            return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }
        logger.info("统计查询,脚本名称 = " + scriptName);
        StatisticsScript statisticsScript = statisticsScriptService.getScript(scriptName);
        if (statisticsScript == null || StringUtils.isEmpty(statisticsScript.getScriptContent())) {
            return RestResult.getInstance(HttpResponseCode.RESOURCE_NOT_FOUND, null);
        }
        String sql = statisticsScript.getScriptContent();
        CommonSelectService commonSelectService = commonSelectFactory.getCommonSelectService(statisticsScript.getDatabaseType());
        if (commonSelectService == null) {
            return RestResult.getInstance(HttpResponseCode.RESOURCE_NOT_FOUND, null);
        }
        try {
            List resultList = commonSelectService.select(sql);
            return RestResult.getInstance(HttpResponseCode.SUCCESS, resultList);
        } catch (Exception ex) {
            HashMap<String, Object> parameters = new HashMap<>(1);
            parameters.put("sql", sql);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "通用查询", parameters);
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }
}
