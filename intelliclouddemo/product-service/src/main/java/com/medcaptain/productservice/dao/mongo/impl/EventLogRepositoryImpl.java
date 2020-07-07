package com.medcaptain.productservice.dao.mongo.impl;

import com.medcaptain.productservice.entity.dto.ParamEntity;
import com.medcaptain.productservice.entity.dto.mongo.EventLog;
import com.medcaptain.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class EventLogRepositoryImpl {
    @Autowired
    MongoTemplate mongoTemplate;

    public List<EventLog> findEventLog(String productKey, String deviceName, String identifier, Integer page, Integer perPage, String startTime, String endTime) {
        List<EventLog> eventLogList = new ArrayList<>();
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
        eventLogList = mongoTemplate.find(query1, EventLog.class);
        return eventLogList;
    }

    public long eventLogAmount(String productKey, String deviceName, String identifier, Integer page, Integer perPage, String startTime, String endTime) {
        //标识符限定
        Pattern pattern = Pattern.compile(identifier);
        Criteria criteriaIdentifier = Criteria.where("product_key").is(productKey).and("device_name").is(deviceName).and("log_content.params.identifier").regex(pattern);
        //时间限制
        Criteria criteriaTime = Criteria.where("log_content.timestamp").lt(Long.parseLong(endTime)).gt(Long.parseLong(startTime));
        Criteria criteria = new Criteria();
        criteria.andOperator(criteriaIdentifier, criteriaTime);
        Query query = Query.query(criteria);
        return mongoTemplate.count(query, EventLog.class);
    }

    private Criteria CommonEventLog(ParamEntity paramEntity) {
        //查询标识符限定
        if (paramEntity == null) {
            return null;
        }
        Criteria criteria;
        if (!StringUtil.isEmpty(paramEntity.getIdentifier())) {
            criteria = Criteria.where("log_content.params.identifier").is(paramEntity.getIdentifier());
        } else {
            return null;
        }
        if (!StringUtil.isEmpty(paramEntity.getProductKey())) {
            criteria = criteria.and("product_key").is(paramEntity.getProductKey());
        }
        if (!StringUtil.isEmpty(paramEntity.getDeviceName())) {
            criteria = criteria.and("device_name").is(paramEntity.getDeviceName());
        }
//        List<String> list1 = new ArrayList();
        if (paramEntity.getParamKV() != null) {
            for (Map.Entry<String, Object> param : paramEntity.getParamKV().entrySet()) {
                criteria = criteria.and("log_content.params.outputData." + param.getKey()).is(param.getValue());
//                list1.add(param.getKey());
            }
//            if(paramEntity.getParamName() != null){
//                list1.removeAll(paramEntity.getParamName());
//            }
        }
        //差集
//        for (String param : list1) {
        if (paramEntity.getParamNameAnd() != null) {
            for (String param : paramEntity.getParamNameAnd()) {
                criteria = criteria.and("log_content.params.outputData." + param).exists(true);
            }
        }
        //查询时间限定
        if (paramEntity.getStartTime() != null && paramEntity.getEndTime() != null) {
            criteria.andOperator(Criteria.where("log_content.timestamp").lt(paramEntity.getEndTime()).gt(paramEntity.getStartTime()));
        }
        return criteria;
    }

    public List<EventLog> findCommonEventLog(ParamEntity paramEntity) {
        Criteria criteria = CommonEventLog(paramEntity);
        if (criteria == null) {
            return new ArrayList<>();
        } else {
            //排序 & 分页
            Query query = Query.query(CommonEventLog(paramEntity)).with(new Sort(Sort.Direction.DESC, "log_content.timestamp")).skip((paramEntity.getPage() - 1) * paramEntity.getPerPage()).limit(paramEntity.getPerPage());
            return mongoTemplate.find(query, EventLog.class);
        }
    }

    public Long findCommonEventLogCount(ParamEntity paramEntity) {

        Criteria criteria = CommonEventLog(paramEntity);
        if (criteria == null) {
            return 0L;
        } else {
            Query query = Query.query(criteria);
            return mongoTemplate.count(query, EventLog.class);
        }
    }

}
