package com.medcaptain.forwader.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = 1)
public class StartupRunner implements CommandLineRunner {

    @Autowired
    private MqttService mqttService;

    @Override
    public void run(String... args) throws Exception {
        do {
            mqttService.connect();
            try {
                Thread.sleep(5000);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        while (!mqttService.isConnected());
//        mqttService.subscribe("/ota/#", 0);
//        mqttService.subscribe("/file/#", 0);
//        mqttService.subscribe("/online", 1);
//        mqttService.subscribe("/offline", 1);
    }
}
