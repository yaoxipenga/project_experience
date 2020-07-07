package com.medcaptain.cloud.schedulestatistics.service;

import com.medcaptain.annotation.Log;
import com.medcaptain.cloud.schedulestatistics.entity.ScheduleTask;
import com.medcaptain.cloud.schedulestatistics.feign.StatisticsService;
import com.medcaptain.cloud.schedulestatistics.feign.StatisticsServiceFactory;
import com.medcaptain.dto.RestResult;
import com.medcaptain.enums.HttpResponseCode;
import com.medcaptain.logging.ExceptionLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 定时任务执行器
 *
 * @author bingwen.ai
 */
@Component
@Scope(value = "prototype")
public class ScheduleTaskExecutor implements Runnable {
    private Logger logger = LoggerFactory.getLogger(ScheduleTaskExecutor.class);

    private ScheduleTask scheduleTask;

    @Autowired
    ScheduleTaskService scheduleTaskService;

    @Autowired
    StatisticsServiceFactory statisticsServiceFactory;

    @Autowired
    StatisticsResultService statisticsResultService;

    void setScheduleTask(ScheduleTask scheduleTask) {
        this.scheduleTask = scheduleTask;
    }

    @Log
    @Override
    public void run() {
        if (scheduleTask != null) {
            try {
                long startTime = System.currentTimeMillis();
                String businessServiceName = scheduleTask.getBusinessServiceName();
                StatisticsService statisticsService = statisticsServiceFactory.getStatisticsService(businessServiceName);
                if (statisticsService != null) {
                    RestResult statisticResult = statisticsService.statistics(scheduleTask.getScriptName());
                    if (statisticResult.getCode() == HttpResponseCode.SUCCESS.getCode()) {
                        List<Object> resultData = (ArrayList) statisticResult.getData();
                        if (statisticsResultService.saveResult(scheduleTask.getResultCollection(), resultData, scheduleTask.isReplaceOldResult())) {
                            scheduleTaskService.finishTask(scheduleTask.getTaskName());
                            long endTime = System.currentTimeMillis();
                            logger.info("定时任务执行完成,任务名称 = {} , 结果集条数 = {} ,耗时 = {} ms . ", scheduleTask.getTaskName(), resultData == null ? 0 : resultData.size(), endTime - startTime);
                        } else {
                            logger.warn("保存统计中间结果异常,任务名称 = {}", scheduleTask.getTaskName());
                        }
                    } else {
                        logger.warn("调用业务服务执行统计任务异常,业务服务 = {} , 统计脚本名称 = {}，返回异常 = {}", businessServiceName, scheduleTask.getScriptName(), statisticResult.getMsg());
                    }
                } else {
                    logger.warn("获取业务服务实例异常，服务名 = " + businessServiceName);
                }
            } catch (Exception ex) {
                String taskName = scheduleTask.getTaskName();
                Map<String, Object> parameters = new HashMap<>(3);
                parameters.put("taskName", taskName);
                ExceptionLog exceptionLog = new ExceptionLog(ex, "执行定时统计任务", parameters);
                logger.error(exceptionLog.toString());
            }
        }
    }
}
