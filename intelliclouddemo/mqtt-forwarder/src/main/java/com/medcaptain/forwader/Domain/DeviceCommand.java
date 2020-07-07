package com.medcaptain.forwader.Domain;

public class DeviceCommand {
    private String topic;

    private String payload;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "DeviceCommand{" +
                "topic='" + topic + '\'' +
                ", payload='" + payload + '\'' +
                '}';
    }
}
