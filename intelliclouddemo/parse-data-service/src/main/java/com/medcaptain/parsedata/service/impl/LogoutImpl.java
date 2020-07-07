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
import com.medcaptain.parsedata.feign.WebSocketService;
import com.medcaptain.parsedata.mapper.RunningstatusMapper;
import com.medcaptain.parsedata.mapper.my.SaveLogMapper;
import com.medcaptain.parsedata.redis.RedisOption;
import com.medcaptain.parsedata.service.LogoutService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 设备登出服务服务
 * @author Adam.Chen
 */
@Service
public class LogoutImpl implements LogoutService {
    private static final Logger log = LoggerFactory.getLogger(LogoutImpl.class);

    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    private RunningstatusMapper runningstatusMapper;

    @Autowired
    private SaveLogMapper saveLogMapper;

    @Autowired
    private DeviceinfoMapper deviceinfoMapper;

    @Autowired
    private MQTT mqtt;

    @Override
    public RestResult deviceLogout(String productKey, String deviceName,Integer code){
        try{
            RedisOption.delDevice(productKey, deviceName);
            System.out.println(RedisOption.decrement(RedisUtilConstant.REDIS_DEVICE_ONLINE_COUNT));
        }catch (Exception e){
            return RestResult.getInstance(HttpResponseCode.ERROR, null);
        }

        //刷新运行状态
        Runningstatus runningstatus = new Runningstatus();
        runningstatus.setDeviceStatus(DeviceStatus.OFFLINE.getCode());
        Deviceinfo deviceinfo = deviceinfoMapper.selectByProductKeyAndDeviceName(productKey,deviceName);
        runningstatus.setDeviceTripleId(deviceinfo.getDeviceTripleId());
        runningstatus.setFirmwareVersion(deviceinfo.getDeviceVersion());
        runningstatusMapper.insertSelective(runningstatus);
        if(1 == code){
            //webSocketService.alertTo(productKey,deviceName,null, DeviceStatus.ABNORMAL_OFFLINE.getMsg());
            runningstatus.setDeviceStatus(DeviceStatus.ABNORMAL_OFFLINE.getCode());
        }
        deviceinfoMapper.updateOnlineStatus(0, deviceinfo.getDeviceTripleId());

        //取消订阅topic
        log.debug("线程名称：{} be ready to read data!", Thread.currentThread().getName());
//        mqtt.deviceOffline(productKey, deviceName);

        //清除该设备在Redis中的消息id
        Integer deviceTripleId = deviceinfoMapper.getDeviceTripleId(productKey, deviceName);
        RedisOption.deleteDeviceMessageId(CommonConstant.DEVICE_POST_MESSAGE + deviceTripleId);
//        Date date = new Date();
//        Long time_now = date.getTime();
//        Long end= time_now- CommonConstant.DEVICE_MESSAGE_FLUSH_TIME;
//        RedisOption.limitMessageTime(CommonConstant.DEVICE_POST_MESSAGE,
//                CommonConstant.DEVICE_MESSAGE_FLUSH_TIME_START,
//                end.doubleValue()
//        );

        //TODO 同步影子设备
        return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
    }
}
