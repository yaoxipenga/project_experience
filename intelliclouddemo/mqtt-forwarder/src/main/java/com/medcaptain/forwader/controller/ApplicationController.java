package com.medcaptain.forwader.controller;


import com.medcaptain.annotation.AuthorizationFree;
import com.medcaptain.dto.RestResult;
import com.medcaptain.enums.HttpResponseCode;
import com.medcaptain.exception.ApiRuntimeException;
import com.medcaptain.forwader.Domain.DeviceCommand;
import com.medcaptain.forwader.service.MqttService;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

@RestController
public class ApplicationController {
    @Autowired
    MqttService mqttService;

    @AuthorizationFree
    @PostMapping("/order")
    public String order(@RequestBody DeviceCommand deviceCommand) {
        String topic = deviceCommand.getTopic();
        String payload = deviceCommand.getPayload();
        System.out.println(deviceCommand);
        try {
            mqttService.publish(1, false, topic, payload);
            return URLDecoder.decode(mqttService.response(topic), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "出错了";
    }

    @AuthorizationFree
    @PostMapping("/orderString")
    public String order(String topic, String payload) {
        try {
            mqttService.publish(1, false, topic, payload);
            return URLDecoder.decode(mqttService.response(topic), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "出错了";
    }

    @AuthorizationFree
    @PostMapping("/orderMessage")
    public RestResult orderMessage(String topic, String payload, String replyTopic,
                                   @RequestParam(value = "qos", defaultValue = "0") Integer qos) {
        mqttService.publish(qos, false, topic, payload);
        String response = mqttService.response(replyTopic);
        try {
            if (response != null) {
                return RestResult.getInstance(HttpResponseCode.SUCCESS, URLDecoder.decode(response, "utf-8"));
            }
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        throw new ApiRuntimeException(HttpResponseCode.ERROR, null);
    }

    @AuthorizationFree
    @PostMapping("/subscribe")
    public RestResult subscribeTopics(@RequestParam("topicList") List<String> topicList) {
        try {
            for (int i = 0; i < topicList.size(); i++) {
                mqttService.subscribe(topicList.get(i), 1);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
    }

    @AuthorizationFree
    @PostMapping("/unsubscribe")
    public RestResult unsubscribeTopics(@RequestParam("topicList") List<String> topicList) {
        try {
            for (int i = 0; i < topicList.size(); i++) {
                mqttService.unsubscribe(topicList.get(i));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return RestResult.getInstance(HttpResponseCode.ERROR, null);
        }
        return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
    }

    @AuthorizationFree
    @PostMapping("/publish")
    public RestResult publishMessage(@RequestParam("topic") String topic,
                                     @RequestParam("payload") String payload,
                                     @RequestParam(value = "qos", defaultValue = "0") Integer qos,
                                     @RequestParam(value = "retained", defaultValue = "false") Boolean retained) {
        mqttService.publish(qos, retained, topic, payload);
        return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
    }

    @AuthorizationFree
    @PostMapping("/online")
    public RestResult deviceOnline(@RequestParam("deviceName") String deviceName,
                                   @RequestParam("productKey") String productKey) {
        mqttService.publish(1, false, "/online", deviceName + "&" + productKey);
        return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
    }

    @AuthorizationFree
    @PostMapping("/offline")
    public RestResult deviceOffline(@RequestParam("deviceName") String deviceName,
                                    @RequestParam("productKey") String productKey) {
        mqttService.publish(1, false, "/offline", deviceName + "&" + productKey);
        return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
    }
}
