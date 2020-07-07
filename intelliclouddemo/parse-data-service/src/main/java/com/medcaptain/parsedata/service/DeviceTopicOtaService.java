package com.medcaptain.parsedata.service;

import com.medcaptain.dto.RestResult;

public interface DeviceTopicOtaService {
    /**
     * 关于固件升级的topic数据解析服务总入口
     * @param topic
     * @param data
     * @return
     */
    RestResult parseDataFromDevice(String topic, String data);
}
