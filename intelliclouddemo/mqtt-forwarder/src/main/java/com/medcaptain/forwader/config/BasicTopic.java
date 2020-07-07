package com.medcaptain.forwader.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@Configuration
@PropertySource(value = "classpath:/application.yml", encoding = "utf-8")
@ConfigurationProperties(prefix = "basic-topic")
public class BasicTopic {
    private List<String> topicList;

    public void setTopicList(List<String> topicList) {
        this.topicList = topicList;
    }

    public List<String> getTopicList() {
        return topicList;
    }
}
