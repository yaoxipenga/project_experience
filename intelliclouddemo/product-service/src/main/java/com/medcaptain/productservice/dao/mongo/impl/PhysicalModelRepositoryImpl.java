package com.medcaptain.productservice.dao.mongo.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.medcaptain.enums.HttpResponseCode;
import com.medcaptain.exception.ApiRuntimeException;
import com.medcaptain.productservice.entity.dto.mongo.PhysicalModel;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.*;

public class PhysicalModelRepositoryImpl {
    @Autowired
    private MongoTemplate mongoTemplate;

    public void add(PhysicalModel physicalModel) {
        mongoTemplate.save(physicalModel);
    }

    private Boolean existOne(String transactionType, String productKey, String identifier, String transactionName) {
        //查看identifier是否重复
        Criteria criteriaIdentifier = Criteria.where("profile.productKey").is(productKey).and(transactionType + ".identifier").is(identifier);
        //查询propertyName是否重复
        Criteria criteriaName = Criteria.where("profile.productKey").is(productKey).and(transactionType + ".name").is(transactionName);
        Criteria criteria = new Criteria();
        criteria.orOperator(criteriaIdentifier, criteriaName);
        Query query = Query.query(criteria);
        return !mongoTemplate.find(query, PhysicalModel.class).isEmpty();
    }

    private Boolean existAnotherOne(String transactionType, String productKey, String identifier, String transactionName, String id) {
        //查看identifier是否重复
        Criteria criteriaIdentifier = Criteria.where("profile.productKey").is(productKey).and(transactionType).elemMatch(Criteria.where("identifier").is(identifier).and("id").ne(id));
        //查询propertyName是否重复
        Criteria criteriaName = Criteria.where("profile.productKey").is(productKey).and(transactionType).elemMatch(Criteria.where("name").is(transactionName).and("id").ne(id));
        Criteria criteria = new Criteria();
        criteria.orOperator(criteriaIdentifier, criteriaName);
        Query query = Query.query(criteria);
        return !mongoTemplate.find(query, PhysicalModel.class).isEmpty();
    }

    public void addTransaction(String transactionType, String productKey, String identifier, String transactionName, JSONObject transactionJson) {
        if (existOne(transactionType, productKey, identifier, transactionName)) {
            throw new ApiRuntimeException(HttpResponseCode.ERROR_RESOURCE_ALREADY_EXIST, "名称重复");
        }

        //新增业务
        Criteria criteriaProductKey = Criteria.where("profile.productKey").is(productKey);
        Update update = new Update();
        update.addToSet(transactionType, transactionJson);
        UpdateResult updateResult = mongoTemplate.updateFirst(new Query(criteriaProductKey), update, PhysicalModel.class);
        //productKey不存在
        if (0 == updateResult.getMatchedCount()) {
            throw new ApiRuntimeException(HttpResponseCode.ERROR_PRODUCT_NOT_EXIST, null);
        }
    }

    public void deleteByProductKey(String productKey) {
        mongoTemplate.remove(new Query(Criteria.where("profile.productKey").is(productKey)), PhysicalModel.class);
    }

    public void updateProperty(String productKey, String identifier, String propertyName, JSONObject dataSpecJson, String id, String propertyDescription, String accessMode) {
        if (existAnotherOne("property", productKey, identifier, propertyName, id)) {
            throw new ApiRuntimeException(HttpResponseCode.ERROR_RESOURCE_ALREADY_EXIST, "名称重复");
        }
        Criteria criteria = Criteria.where("profile.productKey").is(productKey).and("property.id").is(id);
        Update update = new Update();
        update.set("properties.$.identifier", identifier).set("properties.$.name", propertyName).set("properties.$.dataType", dataSpecJson)
                .set("properties.$.description", propertyDescription).set("properties.$.accessMode", accessMode);
        UpdateResult result = mongoTemplate.updateFirst(new Query(criteria), update, PhysicalModel.class);
        if (0 == result.getMatchedCount()) {
            throw new ApiRuntimeException(HttpResponseCode.ERROR_PRODUCT_NOT_EXIST, "不存在的信息");
        }
    }

    public void updateService(String productKey, String identifier, String serviceName, JSONArray outputParam, JSONArray inputParam, String id, String serviceDescription, String callType) {
        if (existAnotherOne("services", productKey, identifier, serviceName, id)) {
            throw new ApiRuntimeException(HttpResponseCode.ERROR_RESOURCE_ALREADY_EXIST, "名称重复");
        }
        Criteria criteria = Criteria.where("profile.productKey").is(productKey).and("services.id").is(id);
        Update update = new Update();
        update.set("services.$.identifier", identifier).set("services.$.name", serviceName).set("services.$.description", serviceDescription)
                .set("services.$.outputParameter", outputParam).set("services.$.inputParameter", inputParam).set("services.$.callType", callType);
        UpdateResult result = mongoTemplate.updateFirst(new Query(criteria), update, PhysicalModel.class);
        if (0 == result.getMatchedCount()) {
            throw new ApiRuntimeException(HttpResponseCode.ERROR_PRODUCT_NOT_EXIST, "不存在的信息");
        }
    }

    public void updateEvent(String productKey, String identifier, String eventName, JSONArray outputParam, String id, String eventDescription, String eventType) {
        if (existAnotherOne("events", productKey, identifier, eventName, id)) {
            throw new ApiRuntimeException(HttpResponseCode.ERROR_RESOURCE_ALREADY_EXIST, "名称重复");
        }
        Criteria criteria = Criteria.where("profile.productKey").is(productKey).and("events.id").is(id);
        Update update = new Update();
        update.set("events.$.identifier", identifier).set("events.$.name", eventName).set("events.$.description", eventDescription)
                .set("events.$.outputParameter", outputParam).set("events.$.type", eventType);
        UpdateResult result = mongoTemplate.updateFirst(new Query(criteria), update, PhysicalModel.class);
        if (0 == result.getMatchedCount()) {
            throw new ApiRuntimeException(HttpResponseCode.ERROR_PRODUCT_NOT_EXIST, "不存在的信息");
        }
    }

    public void deleteTransaction(String productKey, String transactionType, String id) {
        Criteria criteria = Criteria.where("profile.productKey").is(productKey);
        Update update = new Update();
        JSONObject idJson = new JSONObject();
        idJson.put("id", id);
        update.pull(transactionType, idJson);
        UpdateResult result = mongoTemplate.updateFirst(new Query(criteria), update, PhysicalModel.class);
        if (0 == result.getMatchedCount()) {
            throw new ApiRuntimeException(HttpResponseCode.ERROR_PRODUCT_NOT_EXIST, "不存在的信息");
        }
    }

    public PhysicalModel completeModel(String productKey) {
        return mongoTemplate.findOne(new Query(Criteria.where("profile.productKey").is(productKey)), PhysicalModel.class);
    }

    public void updatePhysicalModel(PhysicalModel physicalModel, String productKey) {
        Criteria criteria = Criteria.where("profile.productKey").is(productKey);
        mongoTemplate.remove(new Query(criteria), PhysicalModel.class);
        mongoTemplate.insert(physicalModel);
    }
}
