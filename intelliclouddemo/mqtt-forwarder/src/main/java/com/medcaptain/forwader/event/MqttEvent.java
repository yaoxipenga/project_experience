package com.medcaptain.forwader.event;

import org.springframework.context.ApplicationEvent;

public class MqttEvent extends ApplicationEvent {
    private String message;

    public MqttEvent(Object source, String message) {
        super(source);
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
