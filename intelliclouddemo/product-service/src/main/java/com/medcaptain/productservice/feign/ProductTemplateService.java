package com.medcaptain.productservice.feign;

import com.medcaptain.dto.RestResult;
import com.medcaptain.productservice.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "product-template", configuration = FeignConfig.class)
@Service
public interface ProductTemplateService {
    @RequestMapping(method = RequestMethod.POST, value = "/producttemplate/event/{productKey}")
    RestResult parseEventData(@PathVariable(value = "productKey") String productKey,
                          @RequestParam(value = "paramJson") String logJson);

    @RequestMapping(method = RequestMethod.POST, value = "/producttemplate/property/{productKey}")
    String parsePropertyData(@PathVariable(value = "productKey") String productKey,
                             @RequestParam(value = "paramJson") String logJson);

    @RequestMapping(method = RequestMethod.GET, value = "/product/{productKey}/device/{deviceTripleId}/properties")
    RestResult realTimeData(@PathVariable(value = "productKey") String productKey,
                            @PathVariable(value = "deviceTripleId") String deviceTripleId,
                            @RequestParam(value = "propertyIdentifiers", defaultValue = "") String propertyIdentifiers);

    @RequestMapping(method = RequestMethod.POST, value = "/producttemplate/property/{productKey}")
    String parseServiceData(@PathVariable(value = "productKey") String productKey,
                            @PathVariable(value = "paramJson") String logJson);

    @PostMapping("/producttemplate")
    String pushPhysicalModel(@RequestBody byte[] file);
}
