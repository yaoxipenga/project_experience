package com.medcaptain.cloud.producttemplate.controller;

import com.medcaptain.cloud.producttemplate.entity.ShadowProperty;
import com.medcaptain.cloud.producttemplate.service.ShadowDeviceService;
import com.medcaptain.dto.RestResult;
import com.medcaptain.enums.HttpResponseCode;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 影子设备访问控制器
 *
 * @author bingwen.ai
 */
@RestController
public class ShadowDeviceController {
    private Logger logger = LoggerFactory.getLogger(ShadowDeviceController.class);

    @Autowired
    ShadowDeviceService shadowDeviceService;

    /**
     * 添加影子设备属性
     *
     * @param deviceTripleID 设备三元组
     * @param paramJSon      json格式的属性列表
     * @return 操作结果json
     */
    @PutMapping("/shadowdevice/{deviceTripleId}")
    public RestResult updateDeviceProperty(@PathVariable(value = "deviceTripleId") String deviceTripleID,
                                           @RequestParam(value = "paramJson") String paramJSon) {
        //TODO 设备授权
        if (StringUtils.isBlank(deviceTripleID) || StringUtils.isBlank(paramJSon)) {
            return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }
        if (shadowDeviceService.saveShadowProperties(deviceTripleID, paramJSon)) {
            return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
        } else {
            return RestResult.getInstance(HttpResponseCode.ERROR, null);
        }
    }

    /**
     * 获取影子设备属性
     *
     * @param deviceTripleID      设备三元组
     * @param propertyIdentifiers 属性标识列表，以逗号分隔
     * @return 属性列表json
     */
    @GetMapping("/shadowdevice/{deviceTripleId}")
    public RestResult getDeviceProperty(@PathVariable(value = "deviceTripleId") String deviceTripleID,
                                        @RequestParam(value = "propertyIdentifiers", defaultValue = "") String propertyIdentifiers) {
        if (StringUtils.isBlank(deviceTripleID)) {
            return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }

        List<ShadowProperty> propertyList = shadowDeviceService.getShadowProperties(deviceTripleID, propertyIdentifiers);
        return RestResult.getInstance(HttpResponseCode.SUCCESS, propertyList);
    }
}
