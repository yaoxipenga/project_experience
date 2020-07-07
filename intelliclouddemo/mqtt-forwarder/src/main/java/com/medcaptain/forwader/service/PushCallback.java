package com.medcaptain.forwader.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.medcaptain.forwader.config.BasicTopic;
import com.medcaptain.forwader.event.EventPublisher;
import com.medcaptain.forwader.feign.ParseDataService;
import com.medcaptain.forwader.tool.SharedVariable;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

//@Component
public class PushCallback implements MqttCallback {

    private ParseDataService parseDataService;

    private MqttService mqttService;

    private static PushCallback pushCallback;

    @PostConstruct
    public void init() {
        pushCallback = this;
    }

    @Autowired
    public PushCallback(MqttService mqttService, ParseDataService parseDataService) {
        this.mqttService = mqttService;
        this.parseDataService = parseDataService;
    }

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("断线重连");
        System.out.println(cause.getMessage());
        try {
            synchronized (SharedVariable.isConnecting) {
                if (!mqttService.isConnected()) {
                    mqttService.reconnect();
                } else System.out.println("正在连接中");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        //消息发布完成
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        System.out.println("topic:" + topic);
        System.out.println("message:" + message);
        String[] temp = topic.split("/");
        String topicPrefix = temp[0];
        switch (topicPrefix) {
            case "$SYS":
                JSON messageJson = JSON.parseObject(message.toString());
                String username = ((JSONObject) messageJson).getString("username");
                String[] usernameTemp = username.split("&");
                String productKey = usernameTemp[1];
                String deviceName = usernameTemp[0];
                if ("medcaptain0".equals(productKey)) return;
                switch (temp[5]) {
                    case "connected":
                        String ip = ((JSONObject) messageJson).getString("ipaddress");
                        parseDataService.login(productKey, deviceName, ip);
                        return;
                    case "disconnected":
                        String reason = ((JSONObject) messageJson).getString("reason");
                        Integer offlineCode = "normal".equals(reason) ? 0 : 1;
                        parseDataService.logout(productKey, deviceName, offlineCode);
                        return;
                }
                break;
            case "sys":
                break;
            case "ota":
                break;
            case "file":
                if (topic.contains("/file/device/post") || topic.contains("/file/device/inform")) {
                    if (!SharedVariable.deviceResponseTopicList.containsKey(topic)) return;
                    synchronized (SharedVariable.deviceResponseTopicList.get(topic)) {
                        String tempTopic = SharedVariable.deviceResponseTopicList.get(topic);
                        SharedVariable.deviceResponseTopicList.put(topic, message.toString());
                        tempTopic.notify();
//                mqttService.unsubscribe(topic);
                    }
                }
                break;
            case "manufacture":
                break;
            default:
                break;
        }
        DataManager.SendDataToCloud(topic, message.toString());
    }
}

