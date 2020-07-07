package com.medcaptain.mqttdeviceauthenticate.dao;

import com.medcaptain.mqttdeviceauthenticate.entity.Deviceinfo;
import org.springframework.stereotype.Component;

@Component
public interface DeviceinfoMapper {
    String getDeviceSecret(String productKey, String deviceName);

    int deleteByPrimaryKey(Integer deviceTripleId);

    int insert(Deviceinfo record);

    int insertSelective(Deviceinfo record);

    Deviceinfo selectByPrimaryKey(Integer deviceTripleId);

    int updateByPrimaryKeySelective(Deviceinfo record);

    int updateByPrimaryKey(Deviceinfo record);
}