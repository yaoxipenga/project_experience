package com.medcaptain.mqttdeviceauthenticate.service.impl;


import com.medcaptain.mqttdeviceauthenticate.dao.DeviceinfoMapper;
import com.medcaptain.mqttdeviceauthenticate.dao.ProductBasicTopicMapper;
import com.medcaptain.mqttdeviceauthenticate.dao.ProducttopicMapper;
import com.medcaptain.mqttdeviceauthenticate.dao.redis.TopicPermRedis;
import com.medcaptain.mqttdeviceauthenticate.entity.ProductBasicTopic;
import com.medcaptain.mqttdeviceauthenticate.service.AuthenticateService;

import org.bouncycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.Map;

/***
 *  鉴权接口类具体实现
 *
 *  @author hengyuluo
 *  @date 2018/10/31
 */
@Service
public class AuthenticateServiceImpl implements AuthenticateService {

    @Autowired
    ProducttopicMapper producttopicMapper;
    @Autowired
    DeviceinfoMapper deviceinfoMapper;
    @Autowired
    TopicPermRedis topicPermRedis;
    @Autowired
    ProductBasicTopicMapper productBasicTopicMapper;

    @Override
    public String loginCheck(String username, String password, String clientId) {
        //用于将base64解码
        Base64.Decoder decoder = Base64.getDecoder();
        //用于分割字符串的临时变量
        String[] tempArray;
        //username的长度限制
        int usernameMaxLength = 43;
        int usernameMinLength = 15;

        //username规范的正则表达式
        String usernameRegex = "[u4e00-u9fa5[a-zA-Z[0-9]]]{4,32}&[a-zA-Z[0-9]]{11}";
        //clientId规范的正则表达式
        String clientIdRegex = "[u4e00-u9fa5[a-zA-Z[0-9]]]+\\|securemode=[0-9],signmethod=[a-zA-Z[0-9]]+,timestamp=[1-9][0-9]*\\|";

        //将base64编码的username进行解码
        String decodeUsername = new String(decoder.decode(username), StandardCharsets.UTF_8);
        String decodeClientId = new String(decoder.decode(clientId), StandardCharsets.UTF_8);
        System.out.println("decodeUsername:" + decodeUsername);
        //如果长度不符合要求，则返回错误
        if (decodeUsername.length() > usernameMaxLength || decodeUsername.length() < usernameMinLength) {
            return "1";
        }


        //如果username不符合规范，则返回错误
        if (!decodeUsername.matches(usernameRegex)) {
            return "2";
        }

        //从decodeUsername中解析出deviceName和productKey
        tempArray = decodeUsername.split("&", 2);
        String deviceName = tempArray[0];
        String productKey = tempArray[1];
        //根据deviceName和productKey查询设备
        String deviceSecret = deviceinfoMapper.getDeviceSecret(productKey, deviceName);
        System.out.println("deviceSecret:" + deviceSecret);
        //如果不存在该设备，则返回错误
        if (null == deviceSecret) {
            return "3";
        }

        //如果clientId不符合规范，则返回错误
        if (!decodeClientId.matches(clientIdRegex)) {
            return "4";
        }
        //从clientId中解析出clientidxxx和securemode=xxx,sighmethod=xxx,timestamp=xxx
        tempArray = decodeClientId.split("\\|", 3);
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
        System.out.println("combine: " + combination);

        //根据不同的方法进行加密
        switch (realSignMethod) {
            case "hmacsha1": {
                realSignMethod = "HmacSha1";
                break;
            }
            default:
                return "5";
        }
        String realPassword = null;
        try {
            realPassword = encode(combination, deviceSecret, realSignMethod);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("reallpwd:" + realPassword);

        //将加密后的字符串和密码进行严格比较，若相同则返回0
        if (realPassword.equals(password)) {
            return "0";
        }
        return "7";
    }

    @Override
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

    @Override
    public String aclCheck(String username, String topic, Integer access) {
        //用于将base64解码
        Base64.Decoder decoder = Base64.getDecoder();
        String decodeUsername = new String(decoder.decode(username), StandardCharsets.UTF_8);

        String[] usernameSplit = decodeUsername.split("&");
        String productKey = usernameSplit[1];
        String deviceName = usernameSplit[0];
        //自己人！
        if("medcaptain0".equals(productKey)) return "0";
        //topic的基本格式
        String topicRegex = "[/[a-zA-Z[0-9]]]+";
        if (!topic.matches(topicRegex)) return "3";


        //记录该用户对话题的读写权限
        int permission = -8;
        //用于分割字符串的临时变量
        String[] tempArray;


        String[] topicSplit = topic.split("/");
        String topicProductKey;
        String topicDeviceName;
        //设备的topic中的productKey和deviceName与自己的是否符合
        try {
            switch (topicSplit[1]) {
                case "sys":
                case "manufacture":
                    topicProductKey = topicSplit[2];
                    topicDeviceName = topicSplit[3];
                    if (!productKey.equals(topicProductKey) || !deviceName.equals(topicDeviceName)) return "5";
                    break;
                case "ota":
                case "file":
                    topicProductKey = topicSplit[4];
                    topicDeviceName = topicSplit[5];
                    if (!productKey.equals(topicProductKey) || !deviceName.equals(topicDeviceName)) return "5";
                    break;
            }
        } catch (NullPointerException e) {
            return "4";
        }

        topic = topic.replace(productKey, "${productKey}");
        topic = topic.replace(deviceName, "${deviceName}");
        if (topicPermRedis.hasKey("topic")) permission = Integer.valueOf(topicPermRedis.getTopic("topic", topic).toString());
        else {
            permission = productBasicTopicMapper.getPermission(topic);
            List<Map<String, Object>> topicList = productBasicTopicMapper.getAllTopic();
            for(Map map : topicList){
                topicPermRedis.setTopic("topic", map);
            }
        }
//        //在Redis中查到该设备对该话题的权限
//        if (topicPermRedis.hHasKey(decodeUsername, topic)) {
//            permission = (int) (topicPermRedis.hget(decodeUsername, topic));
//        }
//        //如果在Redis中查不到，则到mysql中查询
//        else {
//            //从decodeUsername中解析出deviceName和productKey
//            tempArray = decodeUsername.split("&", 2);
//            String productKey = tempArray[0];
//            String deviceName = tempArray[1];
//            permission = producttopicMapper.getPermission(productKey, topic);
//
//            //如果在mysql中查不到，说明该设备无此权限
//            if (-8 == permission) {
//                return "1";
//            }
//            //如果在mysql中查到，则写入Redis中以便后续查询
//            topicPermRedis.hset(decodeUsername, topic, permission);
//        }

        //用于比较读写权限的中间变量

        /*
        在数据库中，4表示只可读，2表示只可写，7表示可读可写，0表示不可读不可写
        设备进行发布/订阅时，4表示订阅，2表示发布，1表示订阅时发布
         */
        return ((0 == (permission & access)) ? "2" : "0");
    }
}
