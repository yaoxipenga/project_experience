package com.medcaptain.parsedata.feign;

import com.medcaptain.parsedata.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Service
@FeignClient(value = "mqtt-forwarder", configuration = FeignConfig.class)
public interface MQTTService {
    @PostMapping("/subscribe")
    String subscribeTopics(@RequestParam("topicList") List<String> topicList);

    @PostMapping("/unsubscribe")
    String unsubscribeTopics(@RequestParam("topicList") List<String> topicList);

    /**
     * 设备上线
     * @param productKey
     * @param deviceName
     * @return
     */
    @PostMapping(value = "/online")
    String deviceOnline(@RequestParam("productKey") String productKey,
                        @RequestParam("deviceName") String deviceName);

    /**
     * 设备下线
     * @param productKey
     * @param deviceName
     * @return
     */
    @PostMapping("/offline")
    String deviceOffline(@RequestParam("productKey") String productKey,
                         @RequestParam("deviceName") String deviceName);
}
