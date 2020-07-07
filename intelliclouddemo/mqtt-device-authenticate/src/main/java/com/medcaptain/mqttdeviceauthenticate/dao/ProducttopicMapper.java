package com.medcaptain.mqttdeviceauthenticate.dao;

import com.medcaptain.mqttdeviceauthenticate.entity.Producttopic;
import org.springframework.stereotype.Component;

@Component
public interface ProducttopicMapper {
    int getPermission(String productKey, String topic);

    int deleteByPrimaryKey(Integer indexId);

    int insert(Producttopic record);

    int insertSelective(Producttopic record);

    Producttopic selectByPrimaryKey(Integer indexId);

    int updateByPrimaryKeySelective(Producttopic record);

    int updateByPrimaryKey(Producttopic record);
}