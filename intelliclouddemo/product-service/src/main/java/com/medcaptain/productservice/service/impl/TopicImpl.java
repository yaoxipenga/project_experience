package com.medcaptain.productservice.service.impl;

import com.medcaptain.dto.RestResult;
import com.medcaptain.enums.HttpResponseCode;
import com.medcaptain.productservice.constant.DeviceConstant;
import com.medcaptain.productservice.dao.mybatis.DeviceinfoMapper;
import com.medcaptain.productservice.dao.mybatis.DevicelogMapper;
import com.medcaptain.productservice.dao.mybatis.ProducttopicMapper;
import com.medcaptain.productservice.entity.dto.TopicListEntity;
import com.medcaptain.productservice.entity.mybatis.Producttopic;
import com.medcaptain.productservice.service.TopicService;
import com.medcaptain.utils.TopicUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TopicImpl implements TopicService {
    @Autowired
    private ProducttopicMapper producttopicMapper;

    @Autowired
    DeviceinfoMapper deviceinfoMapper;

    @Autowired
    DevicelogMapper devicelogMapper;

    @Override
    public RestResult getTopics(String productKey) {
        List<TopicListEntity> topicList = producttopicMapper.getTopics(productKey);
        Map<String, Object> returnData = new HashMap<>(2);
        returnData.put("topicList", topicList);
        returnData.put("total", topicList.size());
        return RestResult.getInstance(HttpResponseCode.SUCCESS, returnData);
    }

    @Override
    public RestResult postTopic(int userId, String productKey, String topicName, int operationPermission, String topicDesc) {
        Producttopic producttopic = new Producttopic();
        producttopic.setUserId(userId);
        producttopic.setProductKey(productKey);
        producttopic.setProductTopic(topicName);
        producttopic.setTopicType(operationPermission);
        producttopic.setDesc(topicDesc);
        return RestResult.getInstance(HttpResponseCode.SUCCESS, producttopicMapper.insertSelective(producttopic));
    }

    @Override
    public RestResult putTopic(int topicId, String topicName, int operation, String topicDesc) {
        Producttopic producttopic = new Producttopic();
        producttopic.setIndexId(topicId);
        if (topicName != null) {
            producttopic.setProductTopic(topicName);
        }
        if (operation != -1) {
            producttopic.setTopicType(operation);
        }
        if (topicDesc != null) {
            producttopic.setDesc(topicDesc);
        }
        return RestResult.getInstance(HttpResponseCode.SUCCESS, producttopicMapper.updateByPrimaryKey(producttopic));
    }

    @Override
    public RestResult deleteTopic(int topicId) {
        Producttopic producttopic = new Producttopic();
        producttopic.setIndexId(topicId);
        producttopic.setIsDel(1);
        return RestResult.getInstance(HttpResponseCode.SUCCESS, producttopicMapper.updateByPrimaryKeySelective(producttopic));
    }

    @Override
    public Object postDeviceTopicList(String deviceName, String productKey) {
        Map<String, Object> returnMap = new HashMap<>(2);
        List<HashMap<String, Object>> deviceTopicList = new ArrayList<>();
        List<Producttopic> producttopicList = producttopicMapper.selectByproductKey(productKey);
        for (Producttopic producttopic : producttopicList) {
            HashMap<String, Object> deviceTopicMap = new HashMap<>(8);
            String topic = TopicUtil.analysisReplaceTopic(producttopic.getProductTopic(), "deviceName", deviceName);
            deviceTopicMap.put(
                    DeviceConstant.TOPIC_MESSAGE_COUNT,
                    devicelogMapper.selectByTopicCount(topic)
            );
            deviceTopicMap.put(DeviceConstant.OPERATION_PERMISSION, producttopic.getTopicType());
            deviceTopicMap.put(
                    DeviceConstant.TOPIC_NAME,
                    TopicUtil.replaceTopicWildcardDeviceName(producttopic.getProductTopic(), "deviceName", deviceName)
            );
            deviceTopicList.add(deviceTopicMap);
        }
        returnMap.put("deviceTopicList", deviceTopicList);
        returnMap.put("total", producttopicList.size());
        return returnMap;
    }


//    @Override
//    public RestResult getDeviceTopics(Integer deviceId) {
//        Deviceinfo deviceinfo = deviceinfoMapper.selectBydeviceTripleId(deviceId);
//        if (deviceinfo == null) {
//            throw new ApiRuntimeException(HttpResponseCode.ERROR_DEVICE_NOT_EXIST, null);
//        }
//        List<TopicListEntity> topicList = producttopicMapper.getTopics(deviceinfo.getProductKey());
//        List<Map> deviceTopicList = new ArrayList<>();
//        for (TopicListEntity topicListEntity : topicList) {
//            Map topicMap = new HashMap(3);
//            topicMap.put(TopicConstant.TOPIC_MESSAGE_COUNT, devicelogMapper.selectByTopicCount(topicListEntity.getTopicName()));
//            topicMap.put(TopicConstant.OPERATION_PERMISSION, topicListEntity.getOperationPermission());
//            topicMap.put(TopicConstant.TOPIC_NAME, topicListEntity.getTopicName());
//            deviceTopicList.add(topicMap);
//        }
//        Map<String, Object> returnData = new HashMap<>(2);
//        returnData.put(TopicConstant.DEVICE_TOPIC_LIST, deviceTopicList);
//        returnData.put(TopicConstant.TOTAL, topicList.size());
//        return null;
//    }
}
