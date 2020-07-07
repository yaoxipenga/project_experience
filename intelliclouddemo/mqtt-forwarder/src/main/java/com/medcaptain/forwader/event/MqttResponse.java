package com.medcaptain.forwader.event;

import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.HashMap;

import static java.lang.Thread.sleep;

@Component
@Order(2)
public class MqttResponse implements ApplicationListener<MqttEvent> {
    private  String returnMessage;
    public  HashMap<String, Boolean> receiveMap = new HashMap<>();

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    @Override
    public void onApplicationEvent(MqttEvent event) {
        String[] temp = event.getMessage().split("&", 2);
        String responseTopic = temp[1];
        returnMessage = temp[0];
        receiveMap.put(responseTopic, true);
    }

    public String response(String responseTopic) {
        Integer i = 0;
        while (!receiveMap.get(responseTopic)) {
            try {
                if(i++==400){
                    return null;
                }
                sleep(10);

            }catch (Exception e){
                e.printStackTrace();
            }
        }
        synchronized (this) {
            receiveMap.put(responseTopic, false);
            receiveMap.remove(responseTopic);
        }
        System.out.println("msg:" + returnMessage);
        return returnMessage;
    }
}
