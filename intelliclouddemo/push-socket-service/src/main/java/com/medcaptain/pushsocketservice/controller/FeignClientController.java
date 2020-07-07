package com.medcaptain.pushsocketservice.controller;


import com.medcaptain.dto.RestResult;
import com.medcaptain.enums.HttpResponseCode;
import com.medcaptain.pushsocketservice.feign.ProductServiceManageService;
import com.medcaptain.pushsocketservice.service.SocketService;
import com.medcaptain.pushsocketservice.socket.Greeting;
import com.medcaptain.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "device")
public class FeignClientController {


    @Autowired
    SocketService socketService;
    @Autowired
    ProductServiceManageService productServiceManageService;

    @RequestMapping("alarm")
    public int pullAlarm(
            @RequestParam(value = "product_key") String productKey,
            @RequestParam(value = "device_name") String deviceName,
            @RequestParam(value = "topic") String topic,
            @RequestParam(value = "title") String title,
            @RequestParam(value = "json_string") String jsonString
    ) {
        System.out.println("productKey：" + productKey);
        System.out.println("deviceName：" + deviceName);
        System.out.println("topic：" + topic);
        System.out.println("jsonString：" + jsonString);
        // 推送 创建管理员
        List<Integer> userList = productServiceManageService.getDevicePermissionUserList(productKey, deviceName);
        for (Integer userId : userList) {
            Greeting greeting = socketService.format(productKey, deviceName, topic, title, jsonString);
            socketService.pullUserId(userId.toString(), greeting);
        }
        // 推送给超级管理员角色
        List<Integer> roleList = Arrays.asList(1, 53);
        for (Integer roleId : roleList) {
            Greeting greeting = socketService.format(productKey, deviceName, topic, title, jsonString);
            socketService.pullRoleId(roleId.toString(), greeting);
        }
        return 1;
    }

    @RequestMapping("socket_test")
    public RestResult test(
            @RequestParam(value = "test") Integer test,
            @RequestParam(value = "roleid") String roleId,
            @RequestParam(value = "sign") Integer sign,
            @RequestParam(value = "title") String title,
            @RequestParam(value = "content") String content
    ) {
        // TODO  测试接口
        if (test.equals(1)) {
            Greeting greeting = new Greeting(sign, title, content, DateUtil.getDateTimeFormat(new Date()));
            socketService.pullUserId(roleId, greeting);
        } else if (test.equals(2)) {
            Greeting greeting = new Greeting(sign, "广播一条消息：", content, DateUtil.getDateTimeFormat(new Date()));
            socketService.pullBroadcast(greeting);
        }
        return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
    }
}
