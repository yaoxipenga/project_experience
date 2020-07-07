package com.medcaptain.file.controller;

import com.medcaptain.dto.RestResult;
import com.medcaptain.enums.HttpResponseCode;
import com.medcaptain.file.service.FirmwareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 固件的Controller
 *
 * @author Adam.Chen
 */
@RestController
public class FirmwareController {
    @Autowired
    private FirmwareService firmwareService;

    //固件列表
    @GetMapping("/firmwares")
    public RestResult getFirmwareList(@RequestHeader(value = "userId") Integer userId,
                                      @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                      @RequestParam(value = "per_page", defaultValue = "10", required = false) int per,
                                      @RequestParam(value = "product_key") String productKey) {
        //int userId = 111;
        return firmwareService.getFirmwareList(userId, page, per, productKey);
    }

    //添加固件
    @PostMapping("/firmware")
    public RestResult putFirmware(@RequestHeader(value = "userId") Integer userId,
                                  @RequestParam(value = "productKey") String productKey,
                                  @RequestParam(value = "firmwareName") String firmwareName,
                                  @RequestParam(value = "firmwareVersion") String firmwareVersion,
                                  @RequestParam(value = "MD5") String md5,
                                  @RequestParam(value = "firmwareDesc", defaultValue = "") String firmwareDesc,
                                  @RequestParam(value = "firmwareUrl") String firmwareUrl,
                                  @RequestParam(value = "firmwareSize") Integer size) {
        //int userId = 111;

        return firmwareService.putFirmware(userId, productKey, firmwareName, firmwareVersion, md5, firmwareDesc, firmwareUrl, size);
    }

    //删除固件
    @DeleteMapping("/firmware/{firmware_id}")
    public RestResult deleteFirmware(@PathVariable(value = "firmware_id") Integer id) {
        return firmwareService.deleteFirmware(id);
    }

    //验证固件
    @PostMapping("/firmware/verification")
    public RestResult verifyFirmware(@RequestHeader(value = "userId") Integer userId,
                                     @RequestParam("firmwareId") Integer firmwareId,
                                     @RequestParam("productKey") String productKey,
                                     @RequestParam("deviceName") String deviceName

    ) {
        return firmwareService.verifyFirmware(userId, firmwareId, productKey, deviceName);
    }

    //固件详情
    @GetMapping("/firmware/{firmware_id}")
    public RestResult getFirmware(@PathVariable(value = "firmware_id") Integer id) {
        return firmwareService.getFirmware(id);
    }

    //升级列表
    @GetMapping("/firmware/upgrade")
    public RestResult upgradeFirmware(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                      @RequestParam(value = "size", defaultValue = "10") Integer per,
                                      @RequestParam(value = "firmware_id") Integer firmwareId,
                                      @RequestParam(value = "type") Integer type,
                                      @RequestParam(value = "device_name", defaultValue = "") String deviceName) {
        return firmwareService.upgradeFirmware(page, per, firmwareId, type, deviceName);
    }

    //上传固件
    @PostMapping("/firmware/file")
    public RestResult putFirmwareFile(@RequestHeader(value = "userId") Integer userId,
                                      @RequestParam(value = "MD5") String md5,
                                      @RequestParam(value = "size") Integer size,
                                      @RequestParam(value = "name") String name) {

        return firmwareService.putFirmwareFile(userId, md5, size, name);
    }

    //上报升级进度
    @PostMapping("/firmware/progress")
    public RestResult reflush(@RequestParam(value = "firmwareId") Integer firmwareId,
                              @RequestParam(value = "deviceName") String deviceName,
                              @RequestParam(value = "progress") Integer progress) {
        return firmwareService.reflushProgress(firmwareId, deviceName, progress);
    }

    //上报固件版本
    @PostMapping("/firmware/version")
    public RestResult checkUpgradeResult(@RequestParam(value = "deviceTripleId") Integer deviceTripleId,
                                         @RequestParam(value = "firmwareId") Integer firmwareId,
                                         @RequestParam(value = "pushVersion") String pushVersion) {
        firmwareService.checkUpgradeResult(deviceTripleId, firmwareId, pushVersion);
        return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
    }
}
