package com.medcaptain.parsedata.service.impl;

import com.medcaptain.RedisUtilConstant;
import com.medcaptain.dto.RestResult;
import com.medcaptain.enums.DeviceStatus;
import com.medcaptain.enums.HttpResponseCode;
import com.medcaptain.parsedata.constant.CommonConstant;
import com.medcaptain.parsedata.entity.mysql.Deviceinfo;
import com.medcaptain.parsedata.entity.mysql.Runningstatus;
import com.medcaptain.parsedata.mapper.DeviceinfoMapper;
import com.medcaptain.parsedata.service.MQTT;
import com.medcaptain.parsedata.feign.ProductTemplateService;
import com.medcaptain.parsedata.mapper.RunningstatusMapper;
import com.medcaptain.parsedata.mapper.my.SaveLogMapper;
import com.medcaptain.parsedata.redis.RedisOption;
import com.medcaptain.parsedata.service.LoginSevice;
import com.medcaptain.parsedata.config.ProductModelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 设备登录服务
 *
 * @author Adam.Chen
 */
@Service
public class LoginImpl implements LoginSevice {
    private static final Logger log = LoggerFactory.getLogger(LoginImpl.class);
    @Autowired
    RunningstatusMapper runningstatusMapper;

    @Autowired
    ProductTemplateService productTemplateService;

    @Autowired
    SaveLogMapper saveLogMapper;

    @Autowired
    DeviceinfoMapper deviceinfoMapper;

    @Autowired
    MQTT mqtt;

    @Override
    public RestResult deviceLogin(String productKey, String deviceName, String address) {
        try {
            statusRefresh(productKey, deviceName, address);
//            deviceStartRunning(productKey, deviceName);

            /**
             * 刷新下行消息id
             */
            Integer deviceId = saveLogMapper.getDeviceId(productKey, deviceName);
            System.out.println(RedisOption.keyCheck(CommonConstant.DEVICE_MESSAGE_ID + deviceId));

        } catch (Exception e) {
            return RestResult.getInstance(HttpResponseCode.ERROR, null);
        }
        return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
    }

    /**
     * 刷新运行状态
     *
     * @param productKey
     * @param deviceName
     * @param address
     */
    public void statusRefresh(String productKey, String deviceName, String address) {

        Runningstatus runningstatus = new Runningstatus();
        runningstatus.setDeviceIp(address);
        runningstatus.setDeviceStatus(DeviceStatus.ONLINE.getCode());
        Deviceinfo deviceinfo = deviceinfoMapper.selectByProductKeyAndDeviceName(productKey, deviceName);
        runningstatus.setDeviceTripleId(deviceinfo.getDeviceTripleId());
        runningstatus.setFirmwareVersion(deviceinfo.getDeviceVersion());
        runningstatusMapper.insertSelective(runningstatus);
        deviceinfoMapper.updateOnlineStatus(1, deviceinfo.getDeviceTripleId());
    }

    /**
     * 设备正常运行所需操作
     *
     * @param productKey
     * @param deviceName
     */
    public void deviceStartRunning(String productKey, String deviceName) {
        //TODO 加载产品模板
        //productTemplateService.sendModelFile(ProductModelUtil.getJson(productKey));

        /**
         * 订阅设备topic
         */
        log.info("开始订阅");
        mqtt.deviceOnline(productKey, deviceName);
        log.info("订阅结束");

        //TODO 同步影子设备
    }

}
