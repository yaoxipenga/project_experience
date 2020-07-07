package com.medcaptain.mqttdeviceauthenticate.dao;

import com.medcaptain.mqttdeviceauthenticate.entity.ProductBasicTopic;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ProductBasicTopicMapper {
    int deleteByPrimaryKey(Integer indexId);

    int insert(ProductBasicTopic record);

    int insertSelective(ProductBasicTopic record);

    ProductBasicTopic selectByPrimaryKey(Integer indexId);

    int updateByPrimaryKeySelective(ProductBasicTopic record);

    int updateByPrimaryKey(ProductBasicTopic record);

    int getPermission(@Param("topic") String topic);

    List<Map<String, Object>> getAllTopic();
}