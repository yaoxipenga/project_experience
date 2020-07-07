package com.medcaptain.datastatistics.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.medcaptain.annotation.Log;
import com.medcaptain.datastatistics.entity.StatisticsScript;
import com.medcaptain.datastatistics.service.StatisticsScriptService;
import com.medcaptain.datastatistics.statistics.CommonSelectFactory;
import com.medcaptain.datastatistics.statistics.CommonSelectService;
import com.medcaptain.dto.RestResult;
import com.medcaptain.enums.HttpResponseCode;
import com.medcaptain.logging.ExceptionLog;

import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 数据统计接口
 *
 * @author zhixiong.yang
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
     * @return 格式化后的统计结果
     */
    @Log(logParameterNames = "scriptName")
    @GetMapping(value = "/statistics/{scriptName}")
    public RestResult statistics(HttpServletRequest request, @PathVariable(name = "scriptName") String scriptName) {
        RestResult statisticsResult = executeStatisticsScript(request, scriptName);
        if (statisticsResult.getCode() == HttpResponseCode.SUCCESS.getCode()) {
            List executeResultDate = (List) statisticsResult.getData();
            Map<String, List> formatResult = formatStatisticsResult(executeResultDate);
            return RestResult.getInstance(HttpResponseCode.SUCCESS, formatResult);
        }
        return statisticsResult;
    }

    /**
     * 定时统计任务服务调用
     *
     * @param request    HTTP请求
     * @param scriptName 统计项目名称
     * @return 统计结果数据
     */
    @Log(logParameterNames = "scriptName")
    @GetMapping(value = "/internalStatistics/{scriptName}")
    public RestResult internalStatistics(HttpServletRequest request, @PathVariable(name = "scriptName") String scriptName) {
        return executeStatisticsScript(request, scriptName);
    }

    private RestResult executeStatisticsScript(HttpServletRequest request, String scriptName) {
        if (StringUtils.isBlank(scriptName)) {
            return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }
        logger.info("统计查询，脚本名称 = {}", scriptName);
        StatisticsScript statisticsScript = statisticsScriptService.getScript(scriptName);
        if (statisticsScript == null || StringUtils.isBlank(statisticsScript.getScriptContent())) {
            return RestResult.getInstance(HttpResponseCode.RESOURCE_NOT_FOUND, null);
        }
        CommonSelectService commonSelectService = commonSelectFactory.getCommonSelectService(statisticsScript.getDatabaseType());
        if (commonSelectService == null) {
            return RestResult.getInstance(HttpResponseCode.RESOURCE_NOT_FOUND, null);
        }
        String sql = statisticsScript.getScriptContent();
        String parameterJson = request.getParameter("queryParameters");
        try {
            JSONObject parameters = null;
            if (StringUtils.isNotBlank(parameterJson)) {
                parameters = JSON.parseObject(parameterJson);
            }
            parameters = replaceMultiCondition(parameters);
            List resultList = commonSelectService.select(sql, parameters);
            return RestResult.getInstance(HttpResponseCode.SUCCESS, resultList);
        } catch (Exception ex) {
            HashMap<String, Object> parameters = new HashMap<>(3);
            parameters.put("scriptName", scriptName);
            parameters.put("sql", sql);
            parameters.put("parameters", parameterJson);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "通用查询", parameters);
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }

    /**
     * 替换传入条件中的多选项
     *
     * @param parameters 前端传入的统计条件
     * @return 替换后的查询条件
     */
    private JSONObject replaceMultiCondition(JSONObject parameters) {
        if (parameters != null) {
            for (String parameterName : parameters.keySet()) {
                String parameterValue = parameters.getString(parameterName);
                if (StringUtils.isNotBlank(parameterValue)) {
                    parameterValue = parameterValue.replaceAll(",", "\",\"");
                    parameters.put(parameterName, parameterValue);
                }
            }
        }
        return parameters;
    }

    /**
     * 格式化返回给前端的结果
     *
     * @param statisticsResult 统计结果列表
     * @return 格式化的统计结果，包含columns,rows
     */
    private Map<String, List> formatStatisticsResult(List statisticsResult) {
        Map<String, List> formatResult = new HashMap<>(2);
        if (statisticsResult != null && statisticsResult.size() > 0) {
            List<String> columnNameList = new ArrayList<>();
            Document resultItem = (Document) statisticsResult.get(0);
            for (String columnName : resultItem.keySet()) {
                columnNameList.add(columnName);
            }
            formatResult.put("columns", columnNameList);
            formatResult.put("rows", statisticsResult);
        } else {
            formatResult.put("columns", new ArrayList());
            formatResult.put("rows", new ArrayList());
        }
        return formatResult;
    }
}
