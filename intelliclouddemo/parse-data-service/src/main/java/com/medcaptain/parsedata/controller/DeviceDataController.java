package com.medcaptain.parsedata.controller;

import com.medcaptain.annotation.AuthorizationFree;
import com.medcaptain.dto.RestResult;
import com.medcaptain.enums.HttpResponseCode;
import com.medcaptain.parsedata.constant.CommonConstant;
import com.medcaptain.parsedata.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 设备数据的Controller
 *
 * @author Adam.Chen
 */
@RestController
public class DeviceDataController {
    @Autowired
    private LoginSevice loginService;

    @Autowired
    private LogoutService logoutService;

    @Autowired
    private DeviceTopicSysService deviceTopicSysService;

    @Autowired
    private DeviceTopicOtaService deviceTopicOtaService;

    @Autowired
    private DeviceTopicFileService deviceTopicFileService;

    @Autowired
    private ManufactureService manufactureService;

    @AuthorizationFree
    @PostMapping("/device/log/data")
    public RestResult parseDataFromDevice(@RequestParam(value = "topic") String topic,
                                          @RequestParam(value = "logContent") String data) {
//        System.out.println(topic+"\n"+data);
        String serviceName = topic.split("/")[1];
        switch (serviceName) {
            case "sys":
                return deviceTopicSysService.parseDataFromDevice(topic, data);
            case "ota":
                return deviceTopicOtaService.parseDataFromDevice(topic, data);
            case "file":
                return deviceTopicFileService.parseDataFromDevice(topic, data);
            case "manufacture":
                return manufactureService.bindingDeviceSN(topic, data);
            default:
                return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }

    @AuthorizationFree
    @PostMapping("/device/login")
    public RestResult login(@RequestParam("product_key") String productKey,
                            @RequestParam("device_name") String deviceName,
                            @RequestParam("address") String address) {
        if (productKey.equals(CommonConstant.MY_PRODUCT_KEY)) {
            System.out.println(productKey);
            return RestResult.getInstance(HttpResponseCode.SUCCESS_INTERNAL_DEVICE, null);
        }
        return loginService.deviceLogin(productKey, deviceName, address);
    }

    @AuthorizationFree
    @PostMapping("/device/logout")
    public RestResult logout(@RequestParam("product_key") String productKey,
                             @RequestParam("device_name") String deviceName,
                             @RequestParam("code") Integer code) {
        if (productKey.equals(CommonConstant.MY_PRODUCT_KEY)) {
            System.out.println(productKey);
            return RestResult.getInstance(HttpResponseCode.SUCCESS_INTERNAL_DEVICE, null);
        }
        return logoutService.deviceLogout(productKey, deviceName, code);
    }
}
