package com.medcaptain.productservice.dao.mongo.impl;

import com.medcaptain.productservice.entity.dto.ParamEntity;
import com.medcaptain.productservice.entity.dto.mongo.PropertyLog;
import com.medcaptain.productservice.enums.ParamDataEnum;
import com.medcaptain.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Field;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PropertyLogRepositoryImpl {
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<PropertyLog> findPropertyLog(String productKey, String deviceName, String identifier, Integer page, Integer perPage, String startTime, String endTime) {
        //查询标识符限定
        Criteria criteriaIdentifier = Criteria.where("product_key").is(productKey).and("device_name").is(deviceName).
                and("log_content.params." + identifier).exists(true);
        //查询时间限定
        Criteria criteriaTime = Criteria.where("log_content.timestamp").lt(Long.parseLong(endTime)).gt(Long.parseLong(startTime));
        Criteria criteria = new Criteria();
        criteria.andOperator(criteriaIdentifier, criteriaTime);
        //排序 & 分页
        Query query = Query.query(criteria).with(new Sort(Sort.Direction.DESC, "log_content.timestamp")).skip((page - 1) * perPage).limit(perPage + 1);
        //投影
        Field field = query.fields();
        field.include("log_content.params." + identifier);
        field.include("log_content.version");
        field.include("log_content.timestamp");
        field.include("log_content.version");
        field.include("product_key");
        field.include("device_name");
        return mongoTemplate.find(query, PropertyLog.class);
    }

    private Criteria CommonPropertyLong(ParamEntity paramEntity) {
        //查询标识符限定
        if (paramEntity == null) {
            return null;
        }
        Criteria criteria = new Criteria();
        boolean criteriaBool = true;
        if (!StringUtil.isEmpty(paramEntity.getProductKey())) {
            if (criteriaBool) {
                criteria = Criteria.where("product_key").is(paramEntity.getProductKey());
                criteriaBool = false;
            } else {
                criteria = criteria.and("product_key").is(paramEntity.getProductKey());
            }
        }
        if (!StringUtil.isEmpty(paramEntity.getDeviceName())) {
            if (criteriaBool) {
                criteria = Criteria.where("device_name").is(paramEntity.getDeviceName());
                criteriaBool = false;
            } else {
                criteria = criteria.and("device_name").is(paramEntity.getDeviceName());
            }
        }
        //
        if (paramEntity.getParamKV() != null) {
            List<String> paramList = new ArrayList<>();
            for (Map.Entry<String, Object> param : paramEntity.getParamKV().entrySet()) {
                if (criteriaBool) {
                    criteria = Criteria.where("log_content.params." + param.getKey()).is(param.getValue());
                    criteriaBool = false;
                } else {
                    criteria = criteria.and("log_content.params." + param.getKey()).is(param.getValue());
                }
                paramList.add(param.getKey());
            }
            if (paramEntity.getParamNameAnd() != null) {
                for (String param : paramEntity.getParamNameAnd()) {
                    if (!paramList.contains(param)) {
                        criteria = criteria.and("log_content.params." + param).exists(true);
                    }
                }
            }
        } else {
            if (paramEntity.getParamNameAnd() != null) {
                for (String param : paramEntity.getParamNameAnd()) {
                    if (criteriaBool) {
                        criteria = Criteria.where("log_content.params." + param).exists(true);
                        criteriaBool = false;
                    } else {
                        criteria = criteria.and("log_content.params." + param).exists(true);
                    }
                }
            }
        }
        Criteria criteria1 = null;
        if (paramEntity.getParamNameOr() != null) {
            criteria1 = new Criteria();
            Criteria[] criteriaList = new Criteria[paramEntity.getParamNameOr().size()];
            int i = 0;
            for (String param : paramEntity.getParamNameOr()) {
                criteriaList[i] = Criteria.where("log_content.params." + param).exists(true);
                i++;
            }
            criteria1 = criteria1.orOperator(criteriaList);
        }
        //查询时间限定
        if (paramEntity.getStartTime() != null && paramEntity.getEndTime() != null) {
            if (criteria1 != null) {
                criteria = criteria.andOperator(criteria1, Criteria.where("log_content.timestamp").lt(paramEntity.getEndTime()).gt(paramEntity.getStartTime()));
            }
        } else if (criteria1 != null) {
            criteria = criteria.andOperator(criteria1);
        }
        return criteria;
    }

    private Query projection(Query query, String... fieldStringKey) {
        // 投影
        Field field = query.fields();
        for (String s : fieldStringKey) {
            field.include(s);
        }
        return query;
    }

    private Query toQuery(ParamEntity paramEntity, int type) {
        //排序 & 分页
        Query query;
        switch (type) {
            case 1:
                query = Query.query(CommonPropertyLong(paramEntity)).with(new Sort(Sort.Direction.DESC, "log_content.timestamp")).skip((paramEntity.getPage() - 1) * paramEntity.getPerPage()).limit(paramEntity.getPerPage());
                break;
            case 2:
                query = Query.query(CommonPropertyLong(paramEntity));
                break;
            default:
                query = new Query();
                break;
        }
        projection(query, "log_content.version", "log_content.timestamp", "product_key", "device_name");
        return query;
    }


    public List<PropertyLog> findCommonPropertyLog(ParamEntity paramEntity) {
        if (paramEntity.getDataType() == ParamDataEnum.SIMPLE && paramEntity.getParamNameOr() != null) {
            List<PropertyLog> propertyLogList = new ArrayList<>();
            for (int i = 0; i < paramEntity.getParamNameOr().size(); i++) {
                propertyLogList.addAll(mongoTemplate.find(data(paramEntity, 1, i), PropertyLog.class));
            }
            return propertyLogList;
        } else {
            return mongoTemplate.find(data(paramEntity, 1, 0), PropertyLog.class);
        }
    }

    /**
     * @param paramEntity
     * @param type        数据类型
     * @param i
     * @return
     */
    private Query data(ParamEntity paramEntity, int type, int i) {
        if (paramEntity.getDataType() != null) {
            switch (paramEntity.getDataType()) {
                case ORDINARY:
                    break;
                case SIMPLE:
                    Query query;
                    if (paramEntity.getParamNameOr() != null && paramEntity.getParamNameOr().size() > 0) {
                        ParamEntity paramEntity1 = ParamEntity.copy(paramEntity);
                        paramEntity1.setParamNameOr(Collections.singletonList(paramEntity.getParamNameOr().get(i)));
                        query = projection(toQuery(paramEntity1, type));
                        if (paramEntity1.getParamKV() != null) {
                            for (Map.Entry<String, Object> param : paramEntity1.getParamKV().entrySet()) {
                                query = projection(query, "log_content.params." + param.getKey());
                            }
                        }
                        if (paramEntity1.getParamNameAnd() != null && paramEntity1.getParamNameAnd().size() > 0) {
                            for (String param : paramEntity1.getParamNameAnd()) {
                                query = projection(query, "log_content.params." + param);
                            }
                        }
                        if (paramEntity1.getParamKV() == null && paramEntity1.getParamNameAnd() == null) {
                        }
                        query = projection(query, "log_content.params." + paramEntity.getParamNameOr().get(i));
                        return query;
                    } else {
                        return projection(toQuery(paramEntity, type));
                    }
                default:
                    break;
            }
        }
        return projection(toQuery(paramEntity, type), "log_content.params");
    }

    public Long findCommonPropertyLogCount(ParamEntity paramEntity) {
        Long count = 0L;
        if (paramEntity.getDataType() == ParamDataEnum.SIMPLE && paramEntity.getParamNameOr() != null) {
            for (int i = 0; i < paramEntity.getParamNameOr().size(); i++) {
                count += mongoTemplate.count(data(paramEntity, 2, i), PropertyLog.class);
            }
            return count;
        } else {
            return mongoTemplate.count(data(paramEntity, 2, 0), PropertyLog.class);
        }
    }
}
