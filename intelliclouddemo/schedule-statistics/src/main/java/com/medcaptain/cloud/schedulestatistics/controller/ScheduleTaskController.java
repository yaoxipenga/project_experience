package com.medcaptain.cloud.schedulestatistics.controller;

import com.medcaptain.cloud.schedulestatistics.entity.ScheduleTask;
import com.medcaptain.cloud.schedulestatistics.service.ScheduleTaskManager;
import com.medcaptain.cloud.schedulestatistics.service.ScheduleTaskService;
import com.medcaptain.dto.RestResult;
import com.medcaptain.enums.HttpResponseCode;
import com.medcaptain.logging.ExceptionLog;
import com.medcaptain.utils.IntegerUtil;
import com.medcaptain.utils.LongUtil;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * 定时任务REST接口
 *
 * @author bingwen.ai
 */
@RestController
public class ScheduleTaskController {
    private Logger logger = LoggerFactory.getLogger(ScheduleTaskController.class);

    @Autowired
    ScheduleTaskService scheduleTaskService;

    @Autowired
    ScheduleTaskManager scheduleTaskManager;

    /**
     * 添加定时任务信息接口
     *
     * @param request Http请求
     * @return 添加结果
     */
    @PostMapping(value = "/scheduleTask")
    public RestResult addScheduleTask(HttpServletRequest request) {
        String taskName = request.getParameter("taskName");
        // TODO 需保证和BusinessMicroService常量一致
        String businessServiceName = request.getParameter("businessServiceName");
        String scriptName = request.getParameter("scriptName");
        String resultCollection = request.getParameter("resultCollection");
        long executePeriod = LongUtil.tryParse(request.getParameter("executePeriod"), 0);
        String remark = request.getParameter("remark");
        int morningExecute = IntegerUtil.tryParse(request.getParameter("isMorningExecute"), 1);
        boolean isMorningExecute = morningExecute == 1;
        if (StringUtils.isBlank(taskName) || StringUtils.isBlank(businessServiceName)
                || StringUtils.isBlank(scriptName) || StringUtils.isBlank(resultCollection) || executePeriod <= 0) {
            return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }
        if (scheduleTaskService.existTask(scriptName)) {
            return RestResult.getInstance(HttpResponseCode.ERROR_RESOURCE_ALREADY_EXIST, null);
        }
        if (scheduleTaskService.saveTask(taskName, businessServiceName, scriptName, resultCollection, executePeriod, remark, isMorningExecute)) {
            logger.info("新增定时任务:任务名称 = {} ,任务周期 = {}", taskName, executePeriod);
            return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
        } else {
            return RestResult.getInstance(HttpResponseCode.ERROR, null);
        }
    }

    /**
     * 删除定时任务
     *
     * @param request  HTTP请求
     * @param taskName 要删除的任务名称
     * @return 操作结果
     */
    @DeleteMapping(value = "/scheduleTask/{taskName}")
    public RestResult deleteScheduleTask(HttpServletRequest request, @PathVariable(name = "taskName") String taskName) {
        if (StringUtils.isBlank(taskName)) {
            return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }
        if (!scheduleTaskService.existTask(taskName)) {
            return RestResult.getInstance(HttpResponseCode.RESOURCE_NOT_FOUND, null);
        }
        if (scheduleTaskService.deleteTask(taskName)) {
            return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
        } else {
            return RestResult.getInstance(HttpResponseCode.ERROR, null);
        }
    }

    /**
     * 修改定时任务
     *
     * @param request  HTTP请求
     * @param taskName 定时任务名称
     * @return 操作结果
     */
    @PutMapping(value = "/scheduleTask/{taskName}")
    public RestResult updateTask(HttpServletRequest request, @PathVariable(name = "taskName") String taskName) {
        if (StringUtils.isBlank(taskName)) {
            return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }
        if (!scheduleTaskService.existTask(taskName)) {
            return RestResult.getInstance(HttpResponseCode.RESOURCE_NOT_FOUND, null);
        }
        String businessServiceName = request.getParameter("businessServiceName");
        String scriptName = request.getParameter("scriptName");
        String resultCollection = request.getParameter("resultCollection");
        long executePeriod = LongUtil.tryParse(request.getParameter("executePeriod"), 0);
        String remark = request.getParameter("remark");
        int morningExecute = IntegerUtil.tryParse(request.getParameter("isMorningExecute"), 1);
        boolean isMorningExecute = morningExecute == 1;
        int replaceOldResult = IntegerUtil.tryParse(request.getParameter("replaceOldResult"), 0);
        boolean isReplaceOldResult = replaceOldResult == 1;
        if (StringUtils.isBlank(taskName) || StringUtils.isBlank(businessServiceName)
                || StringUtils.isBlank(scriptName) || StringUtils.isBlank(resultCollection) || executePeriod <= 0) {
            return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }
        ScheduleTask scheduleTask = new ScheduleTask();
        scheduleTask.setTaskName(taskName);
        scheduleTask.setBusinessServiceName(businessServiceName);
        scheduleTask.setScriptName(scriptName);
        scheduleTask.setResultCollection(resultCollection);
        scheduleTask.setExecutePeriod(executePeriod);
        scheduleTask.setRemark(remark);
        scheduleTask.setMorningExecute(isMorningExecute);
        scheduleTask.setReplaceOldResult(isReplaceOldResult);
        if (scheduleTaskService.updateTask(scheduleTask)) {
            return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
        } else {
            return RestResult.getInstance(HttpResponseCode.ERROR, null);
        }
    }

    @GetMapping(value = "/scheduleTasks")
    public RestResult listTasks(HttpServletRequest request) {
        int pageIndex = IntegerUtil.tryParse(request.getParameter("page"), 0);
        int pageSize = IntegerUtil.tryParse(request.getParameter("per_page"), 10);
        if (pageIndex < 0 || pageSize <= 0) {
            return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }
        long taskCount = scheduleTaskService.taskCount();
        List<ScheduleTask> taskList = scheduleTaskService.listTasks(pageIndex, pageSize);
        return RestResult.getInstance(HttpResponseCode.SUCCESS, taskList);
    }

    /**
     * 按任务名称执行任务
     *
     * <p>用于手动执行任务</p>
     *
     * @param request  HTTP请求
     * @param taskName 任务名称
     * @return 执行结果
     */
    @PutMapping(value = "/scheduleTask/{taskName}/run")
    public RestResult executeTask(HttpServletRequest request, @PathVariable(name = "taskName") String taskName) {
        if (StringUtils.isBlank(taskName)) {
            return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }
        try {
            ScheduleTask task = scheduleTaskService.getTask(taskName);
            if (task == null) {
                return RestResult.getInstance(HttpResponseCode.RESOURCE_NOT_FOUND, null);
            }
            logger.info("手动执行任务: {}", taskName);
            scheduleTaskManager.addTask(task);
            return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
        } catch (Exception ex) {
            HashMap<String, Object> parameters = new HashMap<>(1);
            parameters.put("taskName", taskName);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "手动执行统计任务");
            exceptionLog.setParameters(parameters);
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }
}
