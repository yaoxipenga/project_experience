package com.medcaptain.productservice.service;

import com.medcaptain.dto.RestResult;


public interface TopicService {
    RestResult getTopics(String productKey);

    RestResult postTopic(int userId, String productKey, String topicName, int operationPermission, String topicDesc);

    RestResult putTopic(int topicId, String topicName, int operation, String topicDesc);

    RestResult deleteTopic(int topicId);

    /**
     * 获取设备 topic 列表
     *
     * @param deviceName 设备name
     * @return topic 列表
     */
    Object postDeviceTopicList(String deviceName, String productKey);

//    Object getDeviceTopics( Integer deviceId);
}
