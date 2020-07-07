package com.medcaptain.productservice.dao.mongo;

import com.medcaptain.productservice.entity.dto.ParamEntity;
import com.medcaptain.productservice.entity.dto.mongo.PropertyLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyLogRepository extends MongoRepository<PropertyLog, Long> {
    List<PropertyLog> findPropertyLog(String productKey, String deviceName, String identifier, Integer page, Integer perPage, String startTime, String endTime);

    List<PropertyLog> findCommonPropertyLog(ParamEntity paramEntity);
    Long findCommonPropertyLogCount(ParamEntity paramEntity);
}
