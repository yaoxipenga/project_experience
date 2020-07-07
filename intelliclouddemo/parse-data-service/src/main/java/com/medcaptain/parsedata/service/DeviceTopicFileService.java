package com.medcaptain.parsedata.service;

import com.medcaptain.dto.RestResult;

public interface DeviceTopicFileService {
    /**
     * 关于文件方面topic数据解析服务总入口
     * @param topic
     * @param data
     * @return
     */
    RestResult parseDataFromDevice(String topic, String data);
}
