package com.medcaptain.mqttdeviceauthenticate.service;

import java.io.UnsupportedEncodingException;

/***
 * 鉴权接口类
 *
 * @author hengyuluo
 * @date 2018/10/31
 *
 */
public interface AuthenticateService {
    /***
     * 设备登录mosquitto鉴权
     * @param username 设备登录mosquitto的用户名，经过base64编码
     * @param password 设备登录mosquitto的密码
     * @param clientId 设备登录mosquitto的id
     * @return 返回鉴权结果，0代表成功
     */
    String loginCheck(String username, String password, String clientId) ;

    /***
     * 字符串加密
     * @param data 目标字符串
     * @param key 密钥
     * @param signMethod 加密方法
     * @return 加密结果
     * @throws Exception 加密异常
     */
    String encode(String data, String key, String signMethod) throws Exception;

    /***
     * 话题权限鉴权
     * @param username 设备的用户名
     * @param topic 要读写的话题
     * @param access 进行的操作（订阅/发布）
     * @return 鉴权结果，0表示成功
     */
    String aclCheck(String username, String topic, Integer access) ;
}
