package com.medcaptain.parsedata.service;

import com.medcaptain.dto.RestResult;

/**
 * 系统方面topic解析服务
 * @author Adam.Chen
 */
public interface DeviceTopicSysService {
    /**
     * 数据解析服务总入口
     * @param topic
     * @param data
     * @return
     */
    RestResult parseDataFromDevice(String topic, String data);
}
