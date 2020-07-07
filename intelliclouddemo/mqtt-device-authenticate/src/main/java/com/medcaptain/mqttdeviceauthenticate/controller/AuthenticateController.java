package com.medcaptain.mqttdeviceauthenticate.controller;

import com.medcaptain.annotation.AuthorizationFree;
import com.medcaptain.mqttdeviceauthenticate.dao.ProducttopicMapper;
import com.medcaptain.mqttdeviceauthenticate.service.AuthenticateService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/***
 * 鉴权接口
 *
 * @author hengyuluo
 * @date 2018/10/31
 *
 */
@RestController
@RequestMapping(value = "authenticate")
public class AuthenticateController {

    @Autowired
    ProducttopicMapper producttopicMapper;
//    private static Logger log = LoggerFactory.getLogger(AuthenticateController.class);

    @Autowired
    AuthenticateService authenticateService;

    @AuthorizationFree
    @GetMapping(value = "login")
    public String loginCheck(@RequestParam(value = "username") String username,
                             @RequestParam(value = "password") String password,
                             @RequestParam(value = "clientId") String clientID) {
        System.out.println("username:" + username);
        System.out.println("pwd:" + password);
        System.out.println("id:" + clientID);
        return authenticateService.loginCheck(username, password, clientID);
    }
    @AuthorizationFree
    @GetMapping(value = "topic")
    public String aclCheck(String username, String topic, Integer access) {
        System.out.println("topic:" + topic);
        System.out.println("access:" + access);
        return authenticateService.aclCheck(username, topic, access);
    }


}