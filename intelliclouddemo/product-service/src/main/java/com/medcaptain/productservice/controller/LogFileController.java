package com.medcaptain.productservice.controller;


import com.medcaptain.dto.RestResult;
import com.medcaptain.enums.HttpResponseCode;
import com.medcaptain.productservice.service.LogFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LogFileController {
    @Autowired
    LogFileService logFileService;

    //消息日志
    @GetMapping("/product_key/{product_key}/device/{device_name}/logs")
    public RestResult getDeviceLogs(
            @PathVariable("product_key") String productKey,
            @PathVariable("device_name") String deviceName,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "per_page", defaultValue = "10") Integer size
    ) {
        System.out.println(deviceName);
        System.out.println(page);
        return logFileService.getDeviceLogs(productKey, deviceName, page, size);
    }

    //已同步日志
    @GetMapping("/product_key/{product_key}/device/{device_name}/synchronized_logs")
    public RestResult getUploadLogs(
            @PathVariable("product_key") String productKey,
            @PathVariable("device_name") String deviceName,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "per_page", defaultValue = "10") Integer size,
            @RequestHeader(name = "userId") Integer userId
    ) {
        return logFileService.getUploadLogs(userId, productKey, deviceName, page, size);
    }

    //未同步日志
    @GetMapping("/product_key/{product_key}/device/{device_name}/unsynchronized_logs")
    public RestResult getLogs(
            @PathVariable("product_key") String productKey,
            @PathVariable("device_name") String deviceName,
            @RequestParam("type") Integer type
    ) {
        return logFileService.getLogs(productKey, deviceName, type);
    }

    //同步日志文件，获取文件信息，下发url
    @GetMapping("/product_key/{product_key}/device/{device_name}/log/{file_name}")
    public RestResult getLogFile(
            @PathVariable("product_key") String productKey,
            @PathVariable("device_name") String deviceName,
            @PathVariable("file_name") String fileName,
            @RequestParam("type") Integer type,
            @RequestHeader("userId") Integer userId
    ) {
        return logFileService.getLogFile(productKey, deviceName, fileName, type, userId);
    }

    //告警日志
    @GetMapping("/product_key/{product_key}/device/{device_name}/alarmlogs")
    public RestResult getAlarmlogs(
            @PathVariable("product_key") String productKey,
            @PathVariable("device_name") String deviceName,
            @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
            @RequestParam(value = "per_page", defaultValue = "10", required = false) Integer size
    ) {
        return logFileService.getAlarmlogs(productKey, deviceName, page, size);
    }

    //内部调用，完成日志同步
    @PostMapping("/synchronize/log")
    public RestResult synchronizeLog(@RequestParam("md5") String md5,
                                     @RequestParam("path") String path,
                                     @RequestHeader("status") boolean successful) {
        logFileService.synchronizeLog(md5, path, successful);
        return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
    }

    //查询设备上传文件进度
    @GetMapping("/product_key/{product_key}/device/{device_name}/synchronising")
    public RestResult deviceLogUploadingProgress(@PathVariable("product_key") String productKey,
                                                 @PathVariable("device_name") String deviceName
    ) {
        return RestResult.getInstance(HttpResponseCode.SUCCESS, logFileService.getDeviceUploadProgress(productKey, deviceName));
    }

    //下载同步日志
    @GetMapping("product_key/{product_key}/device/{device_name}/synchronized_logs/download")
    public RestResult deviceFileDownload(@PathVariable("product_key") String productKey,
                                         @PathVariable("device_name") String deviceName,
                                         @RequestParam("fileName")String fileName){
        return RestResult.getInstance(HttpResponseCode.SUCCESS, logFileService.getFileDownloadUrl(productKey, deviceName, fileName));
    }
}
