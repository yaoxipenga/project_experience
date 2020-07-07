package com.medcaptain.cloud.schedulestatistics.mongo;

import com.medcaptain.cloud.schedulestatistics.entity.ScheduleTask;
import com.medcaptain.constant.TimeConstant;
import com.medcaptain.logging.ExceptionLog;
import com.mongodb.client.result.UpdateResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * mongodb数据操作服务
 *
 * @author bingwen.ai
 */
@Service
public class MongoDbService {
    private Logger logger = LoggerFactory.getLogger(MongoDbService.class);

    private static final String SCHEDULE_TASK_COLLECTION = "scheduleTask";

    @Autowired
    MongoTemplate mongoTemplate;

    public void saveScheduleTask(ScheduleTask scheduleTask) {
        if (exist(scheduleTask.getTaskName())) {
            updateScheduleTask(scheduleTask);
        } else {
            mongoTemplate.save(scheduleTask, SCHEDULE_TASK_COLLECTION);
        }
    }

    public ScheduleTask getTask(String taskName) {
        Criteria criteria = Criteria.where("isEnable").is(true).and("isDeleted").is(false).and("taskName").is(taskName);
        Query query = new Query(criteria);
        return mongoTemplate.findOne(query, ScheduleTask.class, SCHEDULE_TASK_COLLECTION);
    }

    public List<ScheduleTask> listScheduleTask(long executePeriod, boolean isMorningExecute) {
        try {
            long lastExecuteTime = System.currentTimeMillis() - executePeriod * TimeConstant.MILLISECONDS_ONE_MINUTE;
            // 最后执行时间偏移30秒，减少因任务执行耗时查询不到结果的概率
            lastExecuteTime = lastExecuteTime + 30 * TimeConstant.MILLISECONDS_ONE_SECOND;
            Criteria criteria = Criteria.where("isEnable").is(true).and("isDeleted").is(false).and("executePeriod").is(executePeriod)
                    .and("lastExecuteTime").lt(lastExecuteTime);
            if (executePeriod == TimeConstant.MINUTES_ONE_DAY) {
                criteria = criteria.and("isMorningExecute").is(isMorningExecute);
            }
            Query query = new Query(criteria);
            return mongoTemplate.find(query, ScheduleTask.class, SCHEDULE_TASK_COLLECTION);
        } catch (Exception ex) {
            HashMap<String, Object> parameters = new HashMap<>(2);
            parameters.put("executePeriod", executePeriod);
            parameters.put("isMorningExecute", isMorningExecute);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "获取定时任务列表");
            exceptionLog.setParameters(parameters);
            logger.error(exceptionLog.toString());
            return null;
        }
    }

    public List<ScheduleTask> listScheduleTask(int pageIndex, int pageSize) {
        try {
            int skipCount = (pageIndex - 1) * pageSize;
            skipCount = skipCount < 0 ? 0 : skipCount;
            Criteria criteria = Criteria.where("isDeleted").is(false);
            Query query = new Query(criteria);
            query.skip(skipCount).limit(pageSize).with(Sort.by("taskName"));
            return mongoTemplate.find(query, ScheduleTask.class, SCHEDULE_TASK_COLLECTION);
        } catch (Exception ex) {
            HashMap<String, Object> parameters = new HashMap<>(2);
            parameters.put("pageIndex", pageIndex);
            parameters.put("pageSize", pageSize);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "获取定时任务列表");
            exceptionLog.setParameters(parameters);
            logger.error(exceptionLog.toString());
            return null;
        }
    }

    public long taskCount() {
        Criteria criteria = Criteria.where("isDeleted").is(false);
        Query query = new Query(criteria);
        return mongoTemplate.count(query, ScheduleTask.class, SCHEDULE_TASK_COLLECTION);
    }

    public boolean finishScheduleTask(String taskName) {
        Criteria criteria = Criteria.where("taskName").is(taskName);
        Query query = new Query(criteria);
        Update update = new Update().set("lastExecuteTime", System.currentTimeMillis());
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, SCHEDULE_TASK_COLLECTION);
        return updateResult.getModifiedCount() > 0;
    }

    public boolean deleteScheduleTask(String taskName) {
        Criteria criteria = Criteria.where("taskName").is(taskName).and("isEnable").is(true).and("isDeleted").is(false);
        Query query = new Query(criteria);
        Update update = new Update().set("isDeleted", true);
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, SCHEDULE_TASK_COLLECTION);
        return updateResult.getModifiedCount() > 0;
    }

    public boolean enableScheduleTask(String taskName, boolean enable) {
        Criteria criteria = Criteria.where("taskName").is(taskName).and("isEnable").is(true).and("isDeleted").is(false);
        Query query = new Query(criteria);
        Update update = new Update().set("isEnable", enable);
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, SCHEDULE_TASK_COLLECTION);
        return updateResult.getModifiedCount() > 0;
    }

    public boolean updateScheduleTask(ScheduleTask scheduleTask) {
        Criteria criteria = Criteria.where("taskName").is(scheduleTask.getTaskName()).and("isEnable").is(true)
                .and("isDeleted").is(false);
        Query query = new Query(criteria);
        Update update = new Update().set("businessServiceName", scheduleTask.getBusinessServiceName())
                .set("scriptName", scheduleTask.getScriptName())
                .set("lastExecuteName", scheduleTask.getLastExecuteTime())
                .set("resultCollection", scheduleTask.getResultCollection())
                .set("executePeriod", scheduleTask.getExecutePeriod())
                .set("remark", scheduleTask.getRemark())
                .set("isEnable", scheduleTask.isEnable())
                .set("isDeleted", scheduleTask.isDeleted());
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, SCHEDULE_TASK_COLLECTION);
        return updateResult.getModifiedCount() > 0;
    }

    public boolean exist(String taskName) {
        Criteria criteria = Criteria.where("taskName").is(taskName).and("isEnable").is(true).and("isDeleted").is(false);
        Query query = new Query(criteria);
        return mongoTemplate.exists(query, ScheduleTask.class, SCHEDULE_TASK_COLLECTION);
    }

    public synchronized boolean saveStatisticsResult(String collectionName, List<Object> statisticsResult, boolean isReplaceOldResult) {
        try {
            if (isReplaceOldResult) {
                if (mongoTemplate.collectionExists(collectionName)) {
                    mongoTemplate.dropCollection(collectionName);
                }
            }
            if (statisticsResult != null && statisticsResult.size() > 0) {
                mongoTemplate.insert(statisticsResult, collectionName);
            }
            return true;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return false;
        }
    }
}
