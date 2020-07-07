package com.medcaptain.parsedata.feign;

import com.medcaptain.parsedata.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@FeignClient(value = "push-socket-service", configuration = FeignConfig.class)
public interface WebSocketService {
    @GetMapping("/device/alarm")
    String alertTo(@RequestParam("product_key")String productKey,
                    @RequestParam("device_name")String deviceName,
                    @RequestParam("topic")String topic,
                    @RequestParam("title")String title,
                    @RequestParam("json_string")String json_string
                  );
}
