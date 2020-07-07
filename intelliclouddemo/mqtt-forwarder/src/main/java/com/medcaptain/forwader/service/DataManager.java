package com.medcaptain.forwader.service;

import com.medcaptain.forwader.feign.ParseDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DataManager {
    @Autowired
    private ParseDataService parseDataServiceExample;

    static private ParseDataService parseDataService;

    @PostConstruct void init(){
        parseDataService = parseDataServiceExample;
    }

    static public void SendDataToCloud(String topic,String data){
        System.out.println(parseDataService.parseDataFromDevice(topic, data));
    }

}
