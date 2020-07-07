package com.medcaptain.cloud.schedulestatistics.service;

import com.medcaptain.cloud.schedulestatistics.mongo.MongoDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 存储定时统计任务结果到MongoDB
 *
 * @author bingwen.ai
 */
@Service
public class StatisticsResultService {
    @Autowired
    MongoDbService mongoDbService;

    /**
     * 保存统计结果
     *
     * @param collectionName     mongodb集合名称
     * @param resultList         统计结果列表
     * @param isReplaceOldResult 是否覆盖已有数据数据
     * @return true = 保存成功 ； false = 保存失败
     */
    boolean saveResult(String collectionName, List<Object> resultList, boolean isReplaceOldResult) {
        if (resultList != null) {
            return mongoDbService.saveStatisticsResult(collectionName, resultList, isReplaceOldResult);
        }
        return true;
    }
}
