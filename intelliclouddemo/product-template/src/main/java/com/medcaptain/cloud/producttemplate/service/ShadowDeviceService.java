package com.medcaptain.cloud.producttemplate.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.medcaptain.cloud.producttemplate.entity.ShadowProperty;
import com.medcaptain.cloud.producttemplate.mongodb.MongoDbService;
import com.medcaptain.cloud.producttemplate.redis.RedisService;
import com.medcaptain.logging.ExceptionLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * 影子设备服务
 *
 * @author bingwen.ai
 */
@Service
public class ShadowDeviceService {
    private static final String TIMESTAMP = "timestamp";

    private static final String VERSION = "version";

    private Logger logger = LoggerFactory.getLogger(ShadowDeviceService.class);

    @Autowired
    RedisService redisService;

    @Autowired
    MongoDbService mongoDbService;

    public boolean saveShadowProperties(String deviceTripleID, String paramJSon) {
        try {
            JSONObject params = JSON.parseObject(paramJSon);
            if (params != null) {
                JSONObject paramMap = params.getJSONObject("params");
                long deviceUploadTime = 0L;
                if (params.containsKey(TIMESTAMP)) {
                    deviceUploadTime = params.getLong(TIMESTAMP);
                }
                String version = null;
                if (params.containsKey(VERSION)) {
                    version = params.getString(VERSION);
                }

                List<ShadowProperty> propertyList = getPropertyList(paramMap, deviceUploadTime, deviceTripleID, version);
                mongoDbService.saveShadowDeviceProperties(propertyList);
                if (!redisService.existShadowProperty(deviceTripleID)) {
                    propertyList = mongoDbService.getShadowProperties(deviceTripleID, null);
                }
                redisService.addShadowProperties(propertyList);
                return true;
            }
        } catch (Exception ex) {
            HashMap<String, Object> parameters = new HashMap<>(2);
            parameters.put("deviceTripleID", deviceTripleID);
            parameters.put("paramJSon", paramJSon);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "保存影子设备属性", parameters);
            logger.error(exceptionLog.toString());
            return false;
        }
        return false;
    }

    public List<ShadowProperty> getShadowProperties(String deviceTripleID, String propertyIdentifiers) {
        if (redisService.existShadowProperty(deviceTripleID)) {
            return redisService.getDeviceProperties(deviceTripleID, propertyIdentifiers);
        } else {
            List<ShadowProperty> shadowPropertyList = mongoDbService.getShadowProperties(deviceTripleID, propertyIdentifiers);
            if (shadowPropertyList.size() > 0) {
                redisService.addShadowProperties(shadowPropertyList);
            }
            return shadowPropertyList;
        }
    }

    private List<ShadowProperty> getPropertyList(JSONObject propertyMap, long deviceUploadTime, String deviceTripleId, String version) {
        long serverTime = System.currentTimeMillis();
        List<ShadowProperty> propertyList = new ArrayList<>();
        for (String identifier : propertyMap.keySet()) {
            Object propertyValue = propertyMap.get(identifier);
            ShadowProperty property = new ShadowProperty();
            property.setIdentifier(identifier);
            property.setDeviceUploadTime(deviceUploadTime);
            property.setValue(propertyValue);
            property.setVersion(version);
            property.setServerReceiveTime(serverTime);
            property.setDeviceTripleId(deviceTripleId);
            propertyList.add(property);
        }
        return propertyList;
    }
}
