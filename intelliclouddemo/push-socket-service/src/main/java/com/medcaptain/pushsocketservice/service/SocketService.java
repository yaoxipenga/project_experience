package com.medcaptain.pushsocketservice.service;

import com.medcaptain.pushsocketservice.socket.Greeting;

public interface SocketService {


    void pullRoleId(String roleId, Greeting greeting);


    void pullUserId(String userId, Greeting greeting);


    void pullBroadcast(Greeting greeting);


    Greeting format(String productKey, String deviceName, String topic, String title, String jsonString);
}
