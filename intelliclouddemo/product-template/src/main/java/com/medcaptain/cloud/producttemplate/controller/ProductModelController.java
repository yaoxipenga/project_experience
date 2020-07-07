package com.medcaptain.cloud.producttemplate.controller;

import com.medcaptain.cloud.producttemplate.service.ProductModelService;
import com.medcaptain.dto.RestResult;
import com.medcaptain.enums.HttpResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h2>物模型相关接口</h2>
 *
 * @author bingwen.ai
 * @since 20190108
 */
@RestController
public class ProductModelController {
    @Autowired
    ProductModelService productModelService;

    @DeleteMapping("/serviceModel/{productKey}/{serviceIdentifier}")
    public RestResult deleteServiceModel(@PathVariable(name = "productKey") String productKey,
                                         @PathVariable(name = "serviceIdentifier") String serviceIdentifier) {
        if (productModelService.deleteService(productKey, serviceIdentifier)) {
            return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
        } else {
            return RestResult.getInstance(HttpResponseCode.ERROR, null);
        }
    }

    @DeleteMapping("/propertyModel/{productKey}/{propertyIdentifier}")
    public RestResult deletePropertyModel(@PathVariable(name = "productKey") String productKey,
                                          @PathVariable(name = "propertyIdentifier") String propertyIdentifier) {
        if (productModelService.deleteProperty(productKey, propertyIdentifier)) {
            return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
        } else {
            return RestResult.getInstance(HttpResponseCode.ERROR, null);
        }
    }

    @DeleteMapping("/eventModel/{productKey}/{eventIdentifier}")
    public RestResult deleteEventModel(@PathVariable(name = "productKey") String productKey,
                                       @PathVariable(name = "eventIdentifier") String eventIdentifier) {
        if (productModelService.deleteEvent(productKey, eventIdentifier)) {
            return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
        } else {
            return RestResult.getInstance(HttpResponseCode.ERROR, null);
        }
    }
}
