package com.medcaptain.parsedata.feign;


import com.medcaptain.parsedata.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
@FeignClient(value = "product-template", configuration = FeignConfig.class)
public interface ProductTemplateService {
    @PostMapping("/producttemplate/property/{productKey}/upload")
    String saveProperties(@PathVariable(value = "productKey") String productKey,
                          @RequestParam(value = "deviceTripleId") String deviceTripleID,
                          @RequestParam(value = "paramJson") String paramJson
    );

    @PostMapping("/producttemplate")
    String sendModelFile(@RequestBody byte[] file);
}