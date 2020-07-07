package com.medcaptain.parsedata.service.impl;

import com.alibaba.fastjson.JSON;
import com.medcaptain.dto.RestResult;
import com.medcaptain.parsedata.entity.device.JsonData;
import com.medcaptain.parsedata.feign.ProductService;
import com.medcaptain.parsedata.service.ManufactureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManufactureServiceImpl implements ManufactureService {
    @Autowired
    private ProductService productService;

    @Override
    public RestResult bindingDeviceSN(String topic, String payload) {
        String[] temp = topic.split("/");
        String productKey = temp[2];
        String deviceName = temp[3];
        JsonData dataJson = JSON.parseObject(payload, JsonData.class);
        String sn = dataJson.getParams().getString("serialNumber");
        return productService.bindingDeviceSN(productKey, deviceName, sn);
    }
}
