package com.medcaptain.cloud.schedulestatistics.service;

import com.medcaptain.cloud.schedulestatistics.entity.ScheduleTask;
import com.medcaptain.cloud.schedulestatistics.mongo.MongoDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 定时任务信息服务
 * <p>处理定时任务基本信息的mongoDB操作</p>
 *
 * @author bingwen.ai
 */
@Service
public class ScheduleTaskService {
    @Autowired
    MongoDbService mongoDbService;

    /**
     * 添加定时任务
     *
     * @param taskName            任务名称
     * @param businessServiceName 业务微服务名称
     * @param scriptName          执行业务脚本名称
     * @param resultCollection    结果保存集合
     * @param executePeriod       执行周期,分钟
     * @param remark              备注信息
     * @return true = 添加成功 ； false = 添加失败
     */
    public boolean saveTask(String taskName, String businessServiceName, String scriptName, String resultCollection, long executePeriod, String remark, boolean isMorningExecute) {
        try {
            ScheduleTask scheduleTask = new ScheduleTask();
            scheduleTask.setTaskName(taskName);
            scheduleTask.setBusinessServiceName(businessServiceName);
            scheduleTask.setScriptName(scriptName);
            scheduleTask.setResultCollection(resultCollection);
            scheduleTask.setExecutePeriod(executePeriod);
            scheduleTask.setRemark(remark);
            scheduleTask.setEnable(true);
            scheduleTask.setDeleted(false);
            scheduleTask.setMorningExecute(isMorningExecute);
            mongoDbService.saveScheduleTask(scheduleTask);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * 按名称获取定时任务信息
     *
     * @param taskName 任务名称
     * @return 定时任务实体
     */
    public ScheduleTask getTask(String taskName) {
        return mongoDbService.getTask(taskName);
    }

    /**
     * 是否存在定时任务
     *
     * @param taskName 任务名称
     * @return true = 存在 ; false = 不存在
     */
    public boolean existTask(String taskName) {
        return mongoDbService.exist(taskName);
    }

    /**
     * 按执行周期查询定时任务列表
     *
     * @param executePeriod 执行周期,分钟
     * @return 定时任务列表
     */
    public List<ScheduleTask> listTasks(long executePeriod, boolean isMorningExecute) {
        return mongoDbService.listScheduleTask(executePeriod, isMorningExecute);
    }

    public List<ScheduleTask> listTasks(int pageIndex, int pageSize) {
        return mongoDbService.listScheduleTask(pageIndex, pageSize);
    }

    public long taskCount() {
        return mongoDbService.taskCount();
    }

    /**
     * 更新任务完成时间
     *
     * @param taskName 任务名称
     * @return true = 更新成功 ; false = 更新失败
     */
    boolean finishTask(String taskName) {
        return mongoDbService.finishScheduleTask(taskName);
    }

    public boolean deleteTask(String taskName) {
        return mongoDbService.deleteScheduleTask(taskName);
    }

    public boolean updateTask(ScheduleTask task) {
        return mongoDbService.updateScheduleTask(task);
    }
}
