package com.medcaptain.forwader.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class EventPublisher {
    @Autowired
    ApplicationContext applicationContext;

    private static EventPublisher eventPublisher;

    @PostConstruct
    public void init(){
        eventPublisher = this;
    }

    public void eventPublish(String message, String responseTopic){
        eventPublisher.applicationContext.publishEvent(new MqttEvent(this, message + "&" + responseTopic));
    }
}
