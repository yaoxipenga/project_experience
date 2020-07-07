package com.medcaptain.productservice.dao.mongo.impl;

import com.medcaptain.productservice.entity.dto.ParamEntity;
import com.medcaptain.productservice.entity.dto.mongo.AlarmLogEntity;
import com.medcaptain.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AlarmLogRepositoryImpl {

    @Autowired
    MongoTemplate mongoTemplate;


    public List<AlarmLogEntity> findCommonAlarmLog(ParamEntity paramEntity) {
        Criteria criteria = CommonAlarmLog(paramEntity);
        if (criteria == null) {
            return new ArrayList<>();
        } else {
            //排序 & 分页
            Query query = Query.query(CommonAlarmLog(paramEntity)).with(new Sort(Sort.Direction.DESC, "log_content.timestamp")).skip((paramEntity.getPage() - 1) * paramEntity.getPerPage()).limit(paramEntity.getPerPage());
            return mongoTemplate.find(query, AlarmLogEntity.class);
        }
    }

    public Long findCommonAlarmLogCount(ParamEntity paramEntity) {

        Criteria criteria = CommonAlarmLog(paramEntity);
        if (criteria == null) {
            return 0L;
        } else {
            Query query = Query.query(criteria);
            return mongoTemplate.count(query, AlarmLogEntity.class);
        }
    }


    private Criteria CommonAlarmLog(ParamEntity paramEntity) {
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


}
