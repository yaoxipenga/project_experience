package com.medcaptain.forwader.feign;

import com.medcaptain.dto.RestResult;
import com.medcaptain.forwader.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
//@FeignClient(value = "eureka-client", configuration = FeignConfig.class)

/**
 * 加上快速失败的处理类。该处理类是作为Feign熔断器的逻辑处理类，必须实现被@FeignClient修饰的接口。例如案例中的HiHystrix类实现了接口EurekaClientFeign，
 * 最后需要以Spring Bean的形式注入IoC容器中
 */
@FeignClient(value = "parse-data-service", configuration = FeignConfig.class)
@Service
public interface ParseDataService {
    @PostMapping("/device/log/data")
    String parseDataFromDevice(@RequestParam("topic") String topic,
                               @RequestParam("logContent") String data);

    @PostMapping("/device/login")
    RestResult login(@RequestParam("product_key") String productKey,
                     @RequestParam("device_name") String deviceName,
                     @RequestParam("address") String address);

    @PostMapping("/device/logout")
    RestResult logout(@RequestParam("product_key") String productKey,
                      @RequestParam("device_name") String deviceName,
                      @RequestParam("code") Integer code);
}
