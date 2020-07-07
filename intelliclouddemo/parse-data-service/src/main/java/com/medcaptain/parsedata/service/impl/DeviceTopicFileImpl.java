package com.medcaptain.parsedata.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.medcaptain.dto.RestResult;
import com.medcaptain.enums.HttpResponseCode;
import com.medcaptain.enums.FileTopic;
import com.medcaptain.parsedata.constant.CommonConstant;
import com.medcaptain.parsedata.entity.device.JsonData;
import com.medcaptain.parsedata.mapper.DeviceinfoMapper;
import com.medcaptain.parsedata.mapper.my.SaveLogMapper;
import com.medcaptain.parsedata.redis.RedisOption;
import com.medcaptain.parsedata.service.DeviceTopicFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 关于日志文件的设备topic服务
 *
 * @author Adam.Chen
 */
@Service
public class DeviceTopicFileImpl implements DeviceTopicFileService {
    private static final Logger log = LoggerFactory.getLogger(DeviceTopicFileImpl.class);
    @Autowired
    private SaveLogMapper saveLogMapper;
    @Autowired
    private DeviceinfoMapper deviceinfoMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public RestResult parseDataFromDevice(String topic, String data) {
        String[] topicMessage = topic.split("/");
        String productKey = topicMessage[FileTopic.PRODUCT_KEY.getCode()];
        String deviceName = topicMessage[FileTopic.DEVICE_NAME.getCode()];
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
        if (!topicMessage[FileTopic.DEVICE.getCode()].equals(CommonConstant.DEVICE)) {
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
        switch (topicMessage[FileTopic.FUNCTION.getCode()]) {
            case "post":
                return this.postFileList(topic, data);
            case "inform":
                return this.postFileMessage(topic, data);
            case "process":
                return this.postProcess(topic, data);
            default:
                return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }

    private void saveLogData(String topic, String data) {
        String[] topicMessage = topic.split("/");
        String productKey = topicMessage[FileTopic.PRODUCT_KEY.getCode()];
        String deviceName = topicMessage[FileTopic.DEVICE_NAME.getCode()];
        saveLogMapper.insertMessageRecord(productKey, deviceName, topic, data);
    }

    private RestResult postFileList(String topic, String data) {
        this.saveLogData(topic, data);
        return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
    }

    private RestResult postFileMessage(String topic, String data) {
        this.saveLogData(topic, data);
        return RestResult.getInstance(HttpResponseCode.SUCCESS, null);

    }

    private RestResult postProcess(String topic, String data) {
        this.saveLogData(topic, data);
        String[] topicMessage = topic.split("/");
        String productKey = topicMessage[FileTopic.PRODUCT_KEY.getCode()];
        String deviceName = topicMessage[FileTopic.DEVICE_NAME.getCode()];
        JSONObject params = JSON.parseObject(data, JsonData.class).getParams();
        Integer percent = params.getInteger("percent");
        Integer deviceTripleId = deviceinfoMapper.getDeviceTripleId(productKey, deviceName);
        RedisOption.setFileProgress(deviceTripleId, percent, params.getString("file"));
        return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
    }
}
