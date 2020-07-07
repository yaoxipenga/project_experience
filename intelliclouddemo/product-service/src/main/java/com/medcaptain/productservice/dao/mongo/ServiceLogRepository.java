package com.medcaptain.productservice.dao.mongo;

import com.medcaptain.productservice.entity.dto.mongo.ServiceLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceLogRepository extends MongoRepository<ServiceLog, Long> {
    List<ServiceLog> findServbiceLog(String productKey, String deviceName, String identifier, Integer page, Integer perPage, String startTime, String endTime);
    long serviceLogAmount(String productKey, String deviceName, String identifier, Integer page, Integer perPage, String startTime, String endTime);
}
