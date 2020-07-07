package com.medcaptain.file.service.impl;

import com.medcaptain.dto.RestResult;
import com.medcaptain.enums.FileType;
import com.medcaptain.enums.HttpResponseCode;
import com.medcaptain.enums.UpgradeStatus;
import com.medcaptain.file.constant.CommonConstant;
import com.medcaptain.file.entity.OtaData;
import com.medcaptain.file.entity.OtaJson;
import com.medcaptain.file.entity.dto.FilePathEntity;
import com.medcaptain.file.entity.dto.FirmwareInfoOneEntity;
import com.medcaptain.file.entity.mysql.*;
import com.medcaptain.file.feign.DeviceCommandService;
import com.medcaptain.file.mapper.DeviceinfoMapper;
import com.medcaptain.file.mapper.FirmwareInfoMapper;
import com.medcaptain.file.mapper.FirmwareMapper;
import com.medcaptain.file.mapper.FirmwareUpgradeMapper;
import com.medcaptain.file.redis.RedisOption;
import com.medcaptain.file.redis.entity.FileDownloadTokenEntity;
import com.medcaptain.file.redis.entity.FileUploadTokenEntity;
import com.medcaptain.file.service.FirmwareService;
import com.medcaptain.utils.UUIDUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.medcaptain.file.enums.DeviceUpgradeType;


/**
 * 固件服务
 *
 * @author adam.chen
 */
@Service
public class FirmwareImpl implements FirmwareService {

    @Autowired
    private DeviceCommandService deviceCommandService;

    @Autowired
    private FirmwareMapper firmwareMapper;

    @Autowired
    private FirmwareInfoMapper firmwareInfoMapper;

    @Autowired
    private FirmwareUpgradeMapper firmwareUpgradeMapper;

    @Autowired
    private DeviceinfoMapper deviceinfoMapper;

    @Override
    public RestResult putFirmwareFile(int userId, String md5, int size, String name) {
        try {
            FileUploadTokenEntity fileUploadTokenEntity = new FileUploadTokenEntity();
            fileUploadTokenEntity.setCode(FileType.PRIVATE.getCode());
            fileUploadTokenEntity.setFileName(name);
            fileUploadTokenEntity.setMd5(md5);
            fileUploadTokenEntity.setTime(new Date());
            fileUploadTokenEntity.setUserId(userId);

            //TODO 生成token
            String token = UUIDUtil.getUUID();

            RedisOption.setFileUploadInfo(token, fileUploadTokenEntity);

            //TODO 返回url
            String url = "/api/file/file?token=" + token;

            FilePathEntity filePath = new FilePathEntity();
            filePath.setUrl(url);
            return RestResult.getInstance(HttpResponseCode.SUCCESS, filePath);
        } catch (Exception e) {
            return RestResult.getInstance(HttpResponseCode.ERROR_FILE_NOT_FOUND, e);
        }
    }

    @Override
    public RestResult getFirmwareList(int userId, int page, int per, String productKey) {
        FirmwareInfoExample firmwareInfoExample = new FirmwareInfoExample();
        firmwareInfoExample.createCriteria().andProductKeyEqualTo(productKey).andIsDelEqualTo(0).andUserIdEqualTo(userId);
        Map<String, Object> returnData = new HashMap<String, Object>();
        returnData.put("firmwareList", firmwareMapper.selectList(userId, page * per, per, productKey));
        returnData.put("total", firmwareInfoMapper.countByExample(firmwareInfoExample));
        return RestResult.getInstance(HttpResponseCode.SUCCESS, returnData);
    }

    @Override
    public RestResult putFirmware(int userId, String productKey, String firmwareName, String firmwareVersion,
                                  String md5, String firmwareDesc, String firmwareUrl, int size) {
        FirmwareInfo firmwareInfo = new FirmwareInfo();
        firmwareInfo.setProductKey(productKey);
        firmwareInfo.setFirmwareName(firmwareName);
        firmwareInfo.setFirmwareVersion(firmwareVersion);
        firmwareInfo.setMd5(md5);
        firmwareInfo.setRemark(firmwareDesc);
        firmwareInfo.setDpsitPath(firmwareUrl);
        firmwareInfo.setFirmwareSize(size);
        firmwareInfo.setUserId(userId);
        firmwareInfoMapper.insertSelective(firmwareInfo);
        return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
    }

    @Override
    public RestResult deleteFirmware(int firmwareId) {
        FirmwareInfo firmwareInfo = new FirmwareInfo();
        firmwareInfo.setFirmwareId(firmwareId);
        firmwareInfo.setIsDel(1);
        firmwareInfoMapper.updateByPrimaryKeySelective(firmwareInfo);
        return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
    }

    @Override
    public RestResult getFirmware(int firmwareId) {
        FirmwareInfoOneEntity firmware = firmwareMapper.selectInfo(firmwareId);
        String token = UUIDUtil.getUUID();
        FileDownloadTokenEntity fileDownloadTokenEntity = new FileDownloadTokenEntity();
        fileDownloadTokenEntity.setFileName(firmware.getFirmwareName());
        fileDownloadTokenEntity.setPath(firmware.getFirmwareUrl());
        RedisOption.setFileDownloadInfo(token, fileDownloadTokenEntity);
        firmware.setFirmwareUrl("/api/file?id=" + firmware.getFirmwareUrl() + "&token=" + token);
        return RestResult.getInstance(HttpResponseCode.SUCCESS, firmwareMapper.selectInfo(firmwareId));
    }

    @Override
    public RestResult upgradeFirmware(int page, int per, int firmwareId, int type, String deviceName) {
        FirmwareUpgradeExample firmwareUpgradeExample = new FirmwareUpgradeExample();
        firmwareUpgradeExample.createCriteria().andFirmwareIdEqualTo(firmwareId).andUpgradeStatusEqualTo(type).andDeviceNameLike("%" + deviceName + "%");
        Map<String, Object> returnData = new HashMap<String, Object>();
        switch (type) {
            case 1:
                returnData.put("upgradeList", firmwareMapper.getUpgradingList(page * per, per, firmwareId, type, "%" + deviceName + "%"));
                break;
            default:
                returnData.put("upgradeList", firmwareMapper.getUpgradeList(page * per, per, firmwareId, type, "%" + deviceName + "%"));
        }
        returnData.put("total", firmwareUpgradeMapper.countByExample(firmwareUpgradeExample));
        return RestResult.getInstance(HttpResponseCode.SUCCESS, returnData);
    }

    @Override
    public RestResult verifyFirmware(Integer userId, Integer firmwareId, String productKey, String deviceName) {
        //固件是否在升级中
        FirmwareUpgradeExample firmwareUpgradeExample = new FirmwareUpgradeExample();
        firmwareUpgradeExample.createCriteria().andFirmwareIdEqualTo(firmwareId).andDeviceNameEqualTo(deviceName)
                .andUpgradeStatusEqualTo(UpgradeStatus.UPGRADING.getCode());
        if (firmwareUpgradeMapper.countByExample(firmwareUpgradeExample) != 0) {
            return RestResult.getInstance(HttpResponseCode.ERROR_DEVICE_IS_UPGRADING, null);
        }

        FirmwareInfo firmwareInfo = firmwareInfoMapper.selectByPrimaryKey(firmwareId);
        Deviceinfo deviceinfo = deviceinfoMapper.selectByProductKeyAndDeviceName(productKey, deviceName);

        //获取固件信息
        String md5 = firmwareInfo.getMd5();
        String name = firmwareInfo.getFirmwareName();
        String id = firmwareInfo.getDpsitPath();
        String version = firmwareInfo.getFirmwareVersion();
        Integer size = firmwareInfo.getFirmwareSize();

        Long timestamp = System.currentTimeMillis();

        //生成token
        String token = UUIDUtil.getUUID();
        FileDownloadTokenEntity file = new FileDownloadTokenEntity();
        file.setPath(id);
        file.setFileName(name);
        RedisOption.setFileDownloadInfo(token, file);

        //获取消息下行的id
        Integer deviceId = deviceinfo.getDeviceTripleId();
        Integer jsonId = RedisOption.increment(CommonConstant.DEVICE_MESSAGE_ID + deviceId);

        //组装json
        String topic = CommonConstant.OTA_UPGRADE_TOPIC.replace("${productKey}", productKey).replace("${deviceName}", deviceName);
        OtaJson msgJson = new OtaJson();
        OtaData msgData = new OtaData();
        msgData.setType(DeviceUpgradeType.MASTER_CONTROL.getCode());
        msgData.setMd5(md5);
        msgData.setSign(md5);
        msgData.setSignMethod("Md5");
        msgData.setSize(size);
        msgData.setUrl(CommonConstant.URL_FOR_FILE + token + "&id=" + id);
        msgData.setVersion(version);
        msgJson.setParams(msgData);
        msgJson.setId(jsonId);
        msgJson.setTimestamp(timestamp.toString());
        msgJson.setVersion(CommonConstant.JSON_VERSION);
        String message = JSONObject.fromObject(msgJson).toString();
        System.out.println(message);

        //记录升级设备
        FirmwareUpgrade firmwareUpgrade = new FirmwareUpgrade();
        firmwareUpgrade.setDeviceFirmwareVersion(deviceinfo.getDeviceVersion());
        firmwareUpgrade.setDeviceName(deviceName);
        firmwareUpgrade.setUpgradeStatus(UpgradeStatus.WAIT_TO_UPGRADE);
        firmwareUpgrade.setDeviceTripleId(deviceId);
        firmwareUpgrade.setFirmwareId(firmwareId);
        firmwareUpgradeMapper.insertSelective(firmwareUpgrade);

        //设置升级标记
        RedisOption.setUpgradingFirmwareId(productKey, deviceName, firmwareId);

        //发送消息
        deviceCommandService.publishMessage(topic, message);
        return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
    }

    @Override
    public RestResult reflushProgress(Integer firmwareId, String deviceName, Integer progress) {
        FirmwareUpgradeExample firmwareUpgradeExample = new FirmwareUpgradeExample();
        firmwareUpgradeExample.createCriteria().andFirmwareIdEqualTo(firmwareId).andDeviceNameEqualTo(deviceName);
        try {
            FirmwareUpgrade firmwareUpgrade = firmwareUpgradeMapper.selectByExample(firmwareUpgradeExample).get(0);
            firmwareUpgrade.setUpgradeProgress(progress);
            firmwareUpgrade.setUpgradeStatus(UpgradeStatus.UPGRADING);
            firmwareUpgradeMapper.updateByExampleSelective(firmwareUpgrade, firmwareUpgradeExample);
        } catch (Exception e) {
            return RestResult.getInstance(HttpResponseCode.ERROR_DEVICE_IS_NOT_UPGRADING, null);
        }
        return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
    }

    @Override
    public void checkUpgradeResult(Integer deviceTripleId, Integer firmwareId, String pushVersion) {
        //查看设备是否处于升级状态
        FirmwareUpgradeKey firmwareUpgradeKey = new FirmwareUpgradeKey();
        firmwareUpgradeKey.setDeviceTripleId(deviceTripleId);
        firmwareUpgradeKey.setFirmwareId(firmwareId);
        FirmwareUpgrade firmwareUpgrade = firmwareUpgradeMapper.selectByPrimaryKey(firmwareUpgradeKey);
        //查看设备上报的固件版本是否和目标版本一致
        FirmwareInfo firmwareInfo = firmwareInfoMapper.selectByPrimaryKey(firmwareId);
        //设备处于升级状态，且上报的版本号同目标版本号不一致，则升级失败
        if (UpgradeStatus.UPGRADING == firmwareUpgrade.getUpgradeStatus() && !pushVersion.equals(firmwareInfo.getFirmwareVersion()))
            firmwareUpgrade.setUpgradeStatus(UpgradeStatus.DEFEAT);
            //设备在升级状态且上报的版本号与目标版本号一致,升级成功
        else if (UpgradeStatus.UPGRADING == firmwareUpgrade.getUpgradeStatus() && pushVersion.equals(firmwareInfo.getFirmwareVersion())) {
            firmwareUpgrade.setUpgradeStatus(UpgradeStatus.SUCCESS);
            //将固件状态设为已验证
            firmwareInfo.setIsVerified(1);
            firmwareInfoMapper.updateByPrimaryKey(firmwareInfo);
        }
        //设备不在升级状态，不管
        else return;
        //更新设备升级表的状态
        firmwareUpgradeMapper.updateByPrimaryKey(firmwareUpgrade);
    }
}
