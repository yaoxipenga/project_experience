package com.medcaptain.fastdfs.feign;

import com.medcaptain.dto.RestResult;
import com.medcaptain.entity.DeviceLogFileEntity;
import com.medcaptain.fastdfs.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "product-service", configuration = FeignConfig.class)
@Service
public interface ProductService {
    @PostMapping("/synchronize/log")
    RestResult synchronizeLog(@RequestParam("md5") String md5,
                              @RequestParam("path") String path,
                              @RequestHeader("status") boolean successful);
}
