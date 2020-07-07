package com.medcaptain.cloud.schedulestatistics.schedule;

import com.medcaptain.cloud.schedulestatistics.entity.ScheduleTask;
import com.medcaptain.cloud.schedulestatistics.service.ScheduleTaskManager;
import com.medcaptain.cloud.schedulestatistics.service.ScheduleTaskService;
import com.medcaptain.constant.TimeConstant;
import com.medcaptain.logging.ExceptionLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * 任务安排
 *
 * @author bingwen.ai
 */
@Component
public class TaskSchedule {
    private Logger logger = LoggerFactory.getLogger(TaskSchedule.class);

    @Autowired
    ScheduleTaskService scheduleTaskService;

    @Autowired
    ScheduleTaskManager scheduleTaskManager;

    /**
     * 执行按天任务
     * <p>每天23:55执行一次</p>
     */
    @Scheduled(cron = "0 55 23 * * ?")
    void executeDailyTask() {
        executeTask(TimeConstant.MINUTES_ONE_DAY, false);
    }

    /**
     * 执行按天任务
     * <p>每天02:00执行一次</p>
     */
    @Scheduled(cron = "0 0 2 * * ?")
    void executeDailyMorningTask() {
        executeTask(TimeConstant.MINUTES_ONE_DAY, true);
    }

    /**
     * 循环执行每小时任务
     */
    @Scheduled(fixedRate = TimeConstant.MILLISECONDS_ONE_HOUR, initialDelay = TimeConstant.MILLISECONDS_ONE_MINUTE)
    void executeHourTask() {
        executeTask(TimeConstant.MINUTES_ONE_HOUR, false);
    }

    /**
     * 循环执行半小时任务
     */
    @Scheduled(fixedRate = TimeConstant.MILLISECONDS_ONE_MINUTE * 30, initialDelay = TimeConstant.MILLISECONDS_ONE_MINUTE)
    void executeHalfHourTask() {
        executeTask(30, false);
    }

    /**
     * 循环执行十分钟任务
     */
    @Scheduled(fixedRate = TimeConstant.MILLISECONDS_ONE_MINUTE * 10, initialDelay = TimeConstant.MILLISECONDS_ONE_MINUTE)
    void executeTenMinuteTask() {
        executeTask(10, false);
    }

    /**
     * 循环执行五分钟任务
     */
    @Scheduled(fixedRate = TimeConstant.MILLISECONDS_ONE_MINUTE * 5, initialDelay = TimeConstant.MILLISECONDS_ONE_MINUTE)
    void executeFiveMinuteTask() {
        executeTask(5, false);
    }


    /**
     * 间隔一分钟执行任务
     */
    @Scheduled(fixedDelay = TimeConstant.MILLISECONDS_ONE_MINUTE, initialDelay = TimeConstant.MILLISECONDS_ONE_SECOND * 30)
    void executeMinuteTask() {
        executeTask(1, false);
    }

    /**
     * 查找并执行定时任务
     *
     * @param executePeriod    执行周期
     * @param isMorningExecute 是否在凌晨执行(按天执行的任务分为23:55和2:00)
     */
    private void executeTask(int executePeriod, boolean isMorningExecute) {
        try {
            List<ScheduleTask> scheduleTaskList = scheduleTaskService.listTasks(executePeriod, isMorningExecute);
            int taskCount = scheduleTaskList == null ? 0 : scheduleTaskList.size();
            logger.debug("执行统计任务,执行周期 = {} 分钟，任务数 = {}", executePeriod, taskCount);
            if (scheduleTaskList != null) {
                for (ScheduleTask scheduleTask : scheduleTaskList) {
                    scheduleTaskManager.addTask(scheduleTask);
                }
            }
        } catch (Exception ex) {
            HashMap<String, Object> parameters = new HashMap<>(2);
            parameters.put("executePeriod", executePeriod);
            parameters.put("isMorningExecute", isMorningExecute);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "按周期执行统计任务");
            exceptionLog.setParameters(parameters);
            logger.error(exceptionLog.toString());
        }
    }
}
