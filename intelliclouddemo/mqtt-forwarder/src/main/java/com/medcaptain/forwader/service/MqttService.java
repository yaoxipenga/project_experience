package com.medcaptain.forwader.service;

//import com.example.mqtt.Dao.ResponseTopicRepository;
//import com.example.mqtt.Domain.ResponseTopic;

import com.medcaptain.enums.HttpResponseCode;
import com.medcaptain.exception.ApiRuntimeException;
import com.medcaptain.forwader.config.BasicTopic;
import com.medcaptain.forwader.config.MqttSettings;
import com.medcaptain.forwader.event.EventPublisher;
import com.medcaptain.forwader.event.MqttResponse;

import com.medcaptain.forwader.feign.ParseDataService;
import com.medcaptain.forwader.tool.SharedVariable;
import com.netflix.ribbon.proxy.annotation.Http;
import org.bouncycastle.util.encoders.Hex;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;

import java.util.List;

@Service
@Order(3)
public class MqttService {
    private MqttSettings mqttSettings;
    private MqttResponse mqttResponse;
    private BasicTopic basicTopic;
    private ParseDataService parseDataService;

    private static MqttClient client;
    private static MqttConnectOptions options;

    private static MqttClient getClient() {
        return client;
    }

    private static void setClient(MqttClient client) {
        MqttService.client = client;
    }

    @Autowired
    public MqttService(MqttSettings mqttSettings, MqttResponse mqttResponse, BasicTopic basicTopic, ParseDataService parseDataService) {
        this.mqttSettings = mqttSettings;
        this.mqttResponse = mqttResponse;
        this.basicTopic = basicTopic;
        this.parseDataService = parseDataService;
        String host = mqttSettings.getHost();
        String clientID = "111111|securemode=3,signmethod=hmacsha1,timestamp=" + System.currentTimeMillis() + "|";
        String username = mqttSettings.getUsername();
        //从decodeUsername中解析出deviceName和productKey
        String[] tempArray = username.split("&", 2);
        String deviceName = tempArray[0];
        String productKey = tempArray[1];

        //从clientId中解析出clientidxxx和securemode=xxx,sighmethod=xxx,timestamp=xxx
        tempArray = clientID.split("\\|", 3);
        String realClientId = tempArray[0];

        //从securemode=xxx,sighmethod=xxx,timestamp=xxx中解析出securemode、signmethod和timestamp
        String info = tempArray[1];
        tempArray = info.split(",", 3);
        String secureMode = tempArray[0];
        String signMethod = tempArray[1];
        String timeStamp = tempArray[2];
        tempArray = secureMode.split("=", 2);
        String realSecureMode = tempArray[1];
        tempArray = signMethod.split("=", 2);
        String realSignMethod = tempArray[1];
        tempArray = timeStamp.split("=", 2);
        String realTimeStamp = tempArray[1];

        //将解析到的字符进行拼接和加密，用于密码校验
        String combination = "clientId" + realClientId + "deviceName" + deviceName + "productKey" + productKey + "timestamp" + realTimeStamp;
        switch (realSignMethod) {
            case "hmacsha1": {
                realSignMethod = "HmacSha1";
                break;
            }
            default:
                break;
        }

        String deviceSecret = mqttSettings.getSecret();
        String realPassword = null;
        try {
            realPassword = encode(combination, deviceSecret, realSignMethod);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
        String password = realPassword;
        try {
            client = new MqttClient(host, clientID, new MemoryPersistence());
//            client.setTimeToWait(2000);
            options = new MqttConnectOptions();
            options.setCleanSession(false);
            options.setUserName(username);
            options.setPassword(password.toCharArray());
            options.setConnectionTimeout(10);
            options.setKeepAliveInterval(20);
            /**
             * 证书验证
             */
//            options.setSocketFactory(SslUtil.getSocketFactory(mqttSettings.getCa(),
//                    mqttSettings.getClientCrt(), mqttSettings.getClientKey(), ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void connect() {
        MqttService.setClient(client);
        client.setCallback(new PushCallback(this, parseDataService));
        try {
            client.connect(options);
            for (String topic : basicTopic.getTopicList()) {
                subscribe(topic, 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void publish(int qos, boolean retained, String topic, String pushMessage) {
        MqttMessage message = new MqttMessage();
        message.setQos(qos);
        message.setRetained(retained);
        message.setPayload(pushMessage.getBytes());
        MqttTopic mTopic = MqttService.getClient().getTopic(topic);
        MqttDeliveryToken token;
        try {
            token = mTopic.publish(message);
            token.waitForCompletion();
        } catch (MqttPersistenceException e) {
            e.printStackTrace();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void subscribe(String topic, int qos) {
        try {
            MqttService.getClient().subscribe(topic, qos);
        } catch (MqttException e) {
            System.out.println(e.getMessage());
        }
    }

    public void unsubscribe(String topic) {
        try {
            MqttService.getClient().unsubscribe(topic);
        } catch (MqttException e) {
            System.out.println(e.getMessage());
        }
    }

    public String response(String responseTopic) {
        //订阅相关话题
        System.out.println(responseTopic);
        String res = null;
        if (responseTopic != null) {
            if (SharedVariable.deviceResponseTopicList.containsKey(responseTopic))
                return "doing";
//                throw new ApiRuntimeException(HttpResponseCode.ERROR_LOG_IS_OBTAINING, null);
            SharedVariable.deviceResponseTopicList.put(responseTopic, responseTopic);
//            subscribe(responseTopic, 1);
            synchronized (SharedVariable.deviceResponseTopicList.get(responseTopic)) {
                try {
                    SharedVariable.deviceResponseTopicList.get(responseTopic).wait(3000);
                    res = SharedVariable.deviceResponseTopicList.get(responseTopic);
                    if (res.equals(responseTopic))
                        return "too late";
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    SharedVariable.deviceResponseTopicList.remove(responseTopic);
                }
            }
            return res;
//            mqttResponse.receiveMap.put(responseTopic, false);
//            String msg = mqttResponse.response(responseTopic);
//            unsubscribe(responseTopic);
//            System.out.println("message:" + msg);
//            return msg;
        } else {
            throw new ApiRuntimeException(HttpResponseCode.ERROR, null);
        }
    }

    public Boolean isConnected() {
        return client.isConnected();
    }

    public void reconnect() throws Exception {
        while (null != client && !isConnected()) {
            try {
                Thread.sleep(3000);
                client.connect(options);
                System.out.println(client.isConnected());
                for (String topic : basicTopic.getTopicList()) {
                    subscribe(topic, 1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public String encode(String data, String key, String signMethod) {
        byte[] tempKey = key.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec secretKey = new SecretKeySpec(tempKey, signMethod);
        Mac mac = null;
        try {
            mac = Mac.getInstance(signMethod);
            mac.init(secretKey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.out.println(e);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            System.out.println(e);
        }
        byte[] text = data.getBytes(StandardCharsets.UTF_8);
        return Hex.toHexString(mac.doFinal(text));
    }


}
