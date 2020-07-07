package com.medcaptain.parsedata.service.impl;

import com.alibaba.fastjson.JSON;
import com.medcaptain.dto.RestResult;
import com.medcaptain.enums.HttpResponseCode;
import com.medcaptain.enums.SysTopic;
import com.medcaptain.parsedata.constant.CommonConstant;
import com.medcaptain.parsedata.entity.device.JsonData;
import com.medcaptain.parsedata.entity.mongodb.*;
import com.medcaptain.parsedata.feign.ProductTemplateService;
import com.medcaptain.parsedata.mapper.my.SaveLogMapper;
import com.medcaptain.parsedata.redis.RedisOption;
import com.medcaptain.parsedata.service.DeviceTopicSysService;
import com.medcaptain.parsedata.feign.WebSocketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 关于系统的设备topic服务
 *
 * @author Adam.Chen
 */
@Service
public class DeviceTopicSysImpl implements DeviceTopicSysService {
    private static final Logger log = LoggerFactory.getLogger(DeviceTopicSysImpl.class);
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ProductTemplateService productTemplateService;

    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    private SaveLogMapper saveLogMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public RestResult parseDataFromDevice(String topic, String data) {
        String[] topicMessage = topic.split("/");
        String productKey = topicMessage[SysTopic.PRODUCT_KEY.getCode()];
        String deviceName = topicMessage[SysTopic.DEVICE_NAME.getCode()];
        Integer deviceId = saveLogMapper.getDeviceId(productKey, deviceName);
        if (null == deviceId) {
            return RestResult.getInstance(HttpResponseCode.ERROR_DEVICE_NOT_EXIST, null);
        }
        JsonData jsonObject = JSON.parseObject(data, JsonData.class);
        String id = jsonObject.getId().toString();
        Double timestamp = Double.valueOf(jsonObject.getTimestamp());
        String key = CommonConstant.DEVICE_POST_MESSAGE;
        key += deviceId;

        if (!RedisOption.checkMassageID(key, id, timestamp)) {
            return RestResult.getInstance(HttpResponseCode.ERROR_MESSAGE_DUPLICATION, null);
        }
        switch (topicMessage[4]) {
            case "thing":
                switch (topicMessage[5]) {
                    case "property":
                        return this.propertyData(topic, data);
                    case "event":
                        return this.eventData(topic, data);
                    case "log":
                        return this.deviceLog(topic, data);
                    case "alert":
                        return this.alertData(topic, data);
                    default:
                        return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
                }
            case "transaction":
                switch (topicMessage[5]) {
                    case "log":
                        return this.transactionLogData(topic, data);
                    case "alert":
                        return this.transactionAlertData(topic, data);
                    default:
                        return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
                }
            default:
                return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }

    private void logData(String topic, String data) {
        String[] topicMessage = topic.split("/");
        String productKey = topicMessage[SysTopic.PRODUCT_KEY.getCode()];
        String deviceName = topicMessage[SysTopic.DEVICE_NAME.getCode()];
        saveLogMapper.insertMessageRecord(productKey, deviceName, topic, data);
    }

    private RestResult propertyData(String topic, String data) {
        this.logData(topic, data);
        String[] topicMessage = topic.split("/");
        String productKey = topicMessage[SysTopic.PRODUCT_KEY.getCode()];
        String deviceName = topicMessage[SysTopic.DEVICE_NAME.getCode()];
        Integer deviceId = saveLogMapper.getDeviceId(productKey, deviceName);
        String response = productTemplateService.saveProperties(productKey, deviceId.toString(), data);
        RestResult result = JSON.parseObject(response, RestResult.class);
        System.out.println(result);
        if (result.getCode() == HttpResponseCode.SUCCESS.getCode()) {
            log.debug("成功存入MongoDB");
            PropertyLog propertyLog = new PropertyLog();
            propertyLog.setCreateTime(new Date());
            propertyLog.setProductKey(productKey);
            propertyLog.setDeviceName(deviceName);
            propertyLog.setLogContent(JSON.parseObject(data));
            mongoTemplate.save(propertyLog);
        } else {
            log.debug("存入MongoDB失败");
        }
        return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
    }

    private RestResult eventData(String topic, String data) {
        this.logData(topic, data);
        String[] topicMessage = topic.split("/");
        String productKey = topicMessage[SysTopic.PRODUCT_KEY.getCode()];
        String deviceName = topicMessage[SysTopic.DEVICE_NAME.getCode()];
        try {
            EventLog eventLog = new EventLog();
            eventLog.setLogContent(JSON.parseObject(data));
            eventLog.setCreateTime(new Date().toString());
            eventLog.setProductKey(productKey);
            eventLog.setDeviceName(deviceName);
            mongoTemplate.save(eventLog);
        } catch (Exception e) {
            EventErrorLog eventLog = new EventErrorLog();
            eventLog.setLogContent(data);
            eventLog.setCreateTime(new Date().toString());
            eventLog.setProductKey(productKey);
            eventLog.setDeviceName(deviceName);
            mongoTemplate.save(eventLog);
        }
        return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
    }

    private RestResult alertData(String topic, String data) {
        this.logData(topic, data);
        //TODO 设备告警流程暂时不明确，是否解析
        ValueOperations<String, String> redis = redisTemplate.opsForValue();
        String[] topicMessage = topic.split("/");
        String productKey = topicMessage[SysTopic.PRODUCT_KEY.getCode()];
        String deviceName = topicMessage[SysTopic.DEVICE_NAME.getCode()];
        redis.set("DeviceAlert:" + productKey + deviceName, data);
        redisTemplate.expire("DeviceAlert:" + productKey + deviceName, CommonConstant.ALERT_TTL, TimeUnit.SECONDS);
        //存mongodb
        AlarmLog alarmLog = new AlarmLog();
        alarmLog.setCreateTime(System.currentTimeMillis());
        alarmLog.setDeviceName(deviceName);
        alarmLog.setProductKey(productKey);
        alarmLog.setLogContent(JSON.parseObject(data));
        mongoTemplate.save(alarmLog);

        /**
         * 调用杨志雄的接口往前端推送消息
         */
        String title = "设备告警消息(" + deviceName + "):";
        saveLogMapper.insertPublishLog(topic, title, data);
        webSocketService.alertTo(productKey, deviceName, topic, title, data);

        return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
    }

    private RestResult transactionLogData(String topic, String data) {
        //TODO 业务流程暂时不明确，是否解析
        this.logData(topic, data);
        return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
    }

    private RestResult transactionAlertData(String topic, String data) {
        this.logData(topic, data);
        //TODO 业务告警流程暂时不明确，是否解析
        ValueOperations<String, String> redis = redisTemplate.opsForValue();
        String[] topicMessage = topic.split("/");
        String productKey = topicMessage[SysTopic.PRODUCT_KEY.getCode()];
        String deviceName = topicMessage[SysTopic.DEVICE_NAME.getCode()];
        redis.set("TransactionAlert:" + productKey + deviceName, data);
        redisTemplate.expire("TransactionAlert:" + productKey + deviceName, CommonConstant.ALERT_TTL, TimeUnit.SECONDS);

        /**
         * 调用杨志雄的接口往前端推送消息
         */
        String title = "设备告警消息(" + deviceName + "):";
        saveLogMapper.insertPublishLog(topic, title, data);
        webSocketService.alertTo(productKey, deviceName, topic, title, data);

        return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
    }

    private RestResult deviceLog(String topic, String data) {
        this.logData(topic, data);
        String[] topicMessage = topic.split("/");
        String productKey = topicMessage[SysTopic.PRODUCT_KEY.getCode()];
        String deviceName = topicMessage[SysTopic.DEVICE_NAME.getCode()];
        MDeviceLog deviceLog = new MDeviceLog();
        deviceLog.setCreateTime(System.currentTimeMillis());
        deviceLog.setDeviceName(deviceName);
        deviceLog.setProductKey(productKey);
        deviceLog.setLogContent(JSON.parseObject(data));
        mongoTemplate.save(deviceLog);
        return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
    }
}
