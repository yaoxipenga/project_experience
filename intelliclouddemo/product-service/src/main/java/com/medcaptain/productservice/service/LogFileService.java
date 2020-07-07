package com.medcaptain.productservice.service;

import com.medcaptain.dto.RestResult;

import java.util.Map;

public interface LogFileService {
    RestResult getDeviceLogs(String productKey, String deviceName, Integer page, Integer size);

    RestResult getUploadLogs(Integer userId, String productKey, String deviceName, Integer page, Integer size);

    RestResult getLogs(String productKey, String deviceName, Integer type);

    RestResult getLogFile(String productKey, String deviceName, String fileName, Integer type, Integer userId);

    RestResult getAlarmlogs(String productKey, String deviceName, Integer page, Integer size);

    void synchronizeLog(String md5, String path, boolean successful);

    Map<String, Object> getDeviceUploadProgress(String productKey, String deviceName);

    Map<String, Object> getFileDownloadUrl(String productKey, String deviceName, String fileName);
}
