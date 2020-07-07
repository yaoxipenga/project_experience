package com.medcaptain.productservice.dao.mongo.impl;

import com.medcaptain.productservice.entity.dto.mongo.ServiceLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ServiceLogRepositoryImpl {
    @Autowired
    MongoTemplate mongoTemplate;
    public List<ServiceLog> findServbiceLog(String productKey, String deviceName, String identifier, Integer page, Integer perPage, String startTime, String endTime){
        List<ServiceLog> serviceLogList = new ArrayList<>();
        //标识符限定
        Pattern pattern = Pattern.compile(identifier);
        Criteria criteriaIdentifier = Criteria.where("product_key").is(productKey).and("device_name").is(deviceName).and("log_content.params.identifier").regex(pattern);
        //时间限制
        Criteria criteriaTime = Criteria.where("log_content.timestamp").lt(Long.parseLong(endTime)).gt(Long.parseLong(startTime));
        Criteria criteria = new Criteria();
        criteria.andOperator(criteriaIdentifier, criteriaTime);
        //分页
        Query query1 = Query.query(criteria).with(new Sort(Sort.Direction.DESC, "log_content.timestamp")).skip((page - 1) * perPage).limit(perPage);
        //统计总数
        serviceLogList = mongoTemplate.find(query1, ServiceLog.class);
        return serviceLogList;
    }

    public long serviceLogAmount(String productKey, String deviceName, String identifier, Integer page, Integer perPage, String startTime, String endTime){
        //标识符限定
        Pattern pattern = Pattern.compile(identifier);
        Criteria criteriaIdentifier = Criteria.where("product_key").is(productKey).and("device_name").is(deviceName).and("log_content.params.identifier").regex(pattern);
        //时间限制
        Criteria criteriaTime = Criteria.where("log_content.timestamp").lt(Long.parseLong(endTime)).gt(Long.parseLong(startTime));
        Criteria criteria = new Criteria();
        criteria.andOperator(criteriaIdentifier, criteriaTime);
        Query query = Query.query(criteria);
        return mongoTemplate.count(query, ServiceLog.class);
    }

}
