package com.medcaptain.parsedata.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.medcaptain.dto.RestResult;
import com.medcaptain.enums.*;
import com.medcaptain.parsedata.constant.CommonConstant;
import com.medcaptain.parsedata.entity.device.JsonData;
import com.medcaptain.parsedata.entity.mysql.Runningstatus;
import com.medcaptain.parsedata.feign.FileService;
import com.medcaptain.parsedata.mapper.RunningstatusMapper;
import com.medcaptain.parsedata.mapper.my.SaveLogMapper;
import com.medcaptain.parsedata.redis.RedisOption;
import com.medcaptain.parsedata.service.DeviceTopicOtaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 关于ota升级的设备topic服务
 * @author Adam.Chen
 */
@Service
public class DeviceTopicOtaImpl implements DeviceTopicOtaService {
    @Autowired
    private SaveLogMapper saveLogMapper;

    @Autowired
    private RunningstatusMapper runningstatusMapper;

    @Autowired
    private FileService fileService;

    @Override
    public RestResult parseDataFromDevice(String topic, String data){
        String[] topicMessage = topic.split("/");
        String productKey = topicMessage[OtaTopic.PRODUCT_KEY.getCode()];
        String deviceName = topicMessage[OtaTopic.DEVICE_NAME.getCode()];
        Integer deviceId = saveLogMapper.getDeviceId(productKey,deviceName);
        if (null == deviceId) {
            return RestResult.getInstance(HttpResponseCode.ERROR_DEVICE_NOT_EXIST, null);
        }
        JsonData jsonObject = JSON.parseObject(data, JsonData.class);
        String id = jsonObject.getId().toString();
        Double timestamp = Double.valueOf(jsonObject.getTimestamp());
        String key = CommonConstant.DEVICE_POST_MESSAGE;
        key += deviceId;

        if(!RedisOption.checkMassageID(key, id, timestamp)){
            return RestResult.getInstance(HttpResponseCode.ERROR_MESSAGE_DUPLICATION, null);
        }
        if(!topicMessage[OtaTopic.DEVICE.getCode()].equals(CommonConstant.DEVICE)){
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR,null);
        }
        switch (topicMessage[OtaTopic.FUNCTION.getCode()]) {
            case "inform":
                return this.postDeviceVersion(topic, data);
            case "progress":
                return this.postOtaProgress(topic, data);
            default:
                return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }
    private void saveLogData(String topic, String data){
        String[] topicMessage = topic.split("/");
        String productKey = topicMessage[OtaTopic.PRODUCT_KEY.getCode()];
        String deviceName = topicMessage[OtaTopic.DEVICE_NAME.getCode()];
        saveLogMapper.insertMessageRecord(productKey,deviceName,topic,data);
    }

    private RestResult postDeviceVersion(String topic, String data){
        this.saveLogData(topic, data);
        String[] topicMessage = topic.split("/");
        String productKey = topicMessage[OtaTopic.PRODUCT_KEY.getCode()];
        String deviceName = topicMessage[OtaTopic.DEVICE_NAME.getCode()];
        Integer deviceId = saveLogMapper.getDeviceId(productKey,deviceName);
        Runningstatus runningstatus = runningstatusMapper.selectOneByDeviceTripleId(deviceId);
        JSONObject params = JSON.parseObject(data, JsonData.class).getParams();
        String firmwareVersion = params.getString("version");
        runningstatus.setFirmwareVersion(firmwareVersion);
        runningstatusMapper.updateByPrimaryKeySelective(runningstatus);

        Integer firmwareId = RedisOption.getUpgradingFirmwareId(productKey, deviceName);
        //检查升级结果
        fileService.checkUpgradeResult(deviceId, firmwareId, firmwareVersion);
        //将redis中的固件id清零
        RedisOption.addDevice(productKey, deviceName, 0.0);
        return RestResult.getInstance(HttpResponseCode.SUCCESS,null);
    }

    private RestResult postOtaProgress(String topic, String data){
        this.saveLogData(topic, data);
        String[] topicMessage = topic.split("/");
        String productKey = topicMessage[OtaTopic.PRODUCT_KEY.getCode()];
        String deviceName = topicMessage[OtaTopic.DEVICE_NAME.getCode()];
        JSONObject params = JSON.parseObject(data, JsonData.class).getParams();
        Integer step = params.getInteger("step");
        Integer firmwareId = RedisOption.getUpgradingFirmwareId(productKey, deviceName);
        fileService.reflush(firmwareId, deviceName, step);
        //RedisOption.setOtaProgress(productKey, deviceName, step);
        return RestResult.getInstance(HttpResponseCode.SUCCESS,null);
    }
}
