package com.medcaptain.productservice.dao.mongo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.medcaptain.productservice.entity.dto.mongo.PhysicalModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PhysicalModelRepository extends MongoRepository<PhysicalModel, Long> {
    @Query(value = "{'profile.productKey':'?0'}",
            fields = "{'services.id' : 0, 'services.description' : 0, 'properties.id' : 0, 'properties.description' : 0, 'events.id' : 0, 'events.description' : 0}")
    PhysicalModel findByProductKey(String productKey);

    void add(PhysicalModel physicalModel);

    void addTransaction(String transactionType, String productKey, String identifier, String transactionName, JSONObject transactionJson);

    void deleteByProductKey(String productKey);

    void updateProperty(String productKey, String identifier, String propertyName, JSONObject dataSpecJson, String id, String propertyDescription, String accessMode);

    void updateService(String productKey, String identifier, String serviceName, JSONArray outputParam, JSONArray inputParam, String id, String serviceDescription, String callType);

    void updateEvent(String productKey, String identifier, String eventName, JSONArray outputParam, String id, String eventDescription, String eventType);

    void deleteTransaction(String productKey, String transactionType, String id);

    PhysicalModel completeModel(String productKey);

    void updatePhysicalModel(PhysicalModel physicalModel, String productKey);
}
