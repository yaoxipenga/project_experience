package com.medcaptain.parsedata.service;

import com.medcaptain.parsedata.feign.MQTTService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MQTT {
    private static final Logger log = LoggerFactory.getLogger(MQTT.class);

    @Autowired
    MQTTService mqttService;

    @Async
    public void subscribe(List<String> topicList) {
        log.info("线程名称：{} be ready to read data!", Thread.currentThread().getName());
        log.info(mqttService.subscribeTopics(topicList));
    }

    @Async
    public void unsubscribe(List<String> topicList) {
        log.info("线程名称：{} be ready to read data!", Thread.currentThread().getName());
        log.info(mqttService.unsubscribeTopics(topicList));
    }

    @Async
    public void deviceOnline(String productKey,String deviceName) {
        log.info("线程名称：{} be ready to read data!", Thread.currentThread().getName());
        log.info(mqttService.deviceOnline(productKey, deviceName));
    }

    @Async
    public void deviceOffline(String productKey,String deviceName) {
        log.info("线程名称：{} be ready to read data!", Thread.currentThread().getName());
        log.info(mqttService.deviceOffline(productKey, deviceName));
    }
}
