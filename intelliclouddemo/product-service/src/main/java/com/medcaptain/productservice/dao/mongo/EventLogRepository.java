package com.medcaptain.productservice.dao.mongo;

import com.medcaptain.productservice.entity.dto.ParamEntity;
import com.medcaptain.productservice.entity.dto.mongo.EventLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventLogRepository extends MongoRepository<EventLog, Long> {
    List<EventLog> findEventLog(String productKey, String deviceName, String identifier, Integer page, Integer perPage, String startTime, String endTime);

    long eventLogAmount(String productKey, String deviceName, String identifier, Integer page, Integer perPage, String startTime, String endTime);

    /**
     * 通用事件查询
     */
    List<EventLog> findCommonEventLog(ParamEntity paramEntity);

    /**
     * 通用事件查询
     */
    Long findCommonEventLogCount(ParamEntity paramEntity);
}
