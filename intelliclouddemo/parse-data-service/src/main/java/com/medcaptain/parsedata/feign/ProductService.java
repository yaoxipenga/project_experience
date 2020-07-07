package com.medcaptain.parsedata.feign;

import com.medcaptain.dto.RestResult;
import com.medcaptain.parsedata.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@FeignClient(value = "product-service", configuration = FeignConfig.class)
public interface ProductService {
    @PutMapping(value = "/device/binding/deviceSN")
    RestResult bindingDeviceSN(
            @RequestParam(value = "product_key") String productKey,
            @RequestParam(value = "device_name") String deviceName,
            @RequestParam(value = "deviceSN") String deviceSN
    );
}
