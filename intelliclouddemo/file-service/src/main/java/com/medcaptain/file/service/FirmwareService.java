package com.medcaptain.file.service;

import com.medcaptain.dto.RestResult;

/**
 * 固件的service
 * @author Adam.Chen
 */
public interface FirmwareService {
    /**
     * 添加固件
     * @param userId
     * @param productKey
     * @param firmwareName
     * @param firmwareVersion
     * @param md5
     * @param firmwareDesc
     * @param firmwareUrl
     * @param size
     * @return
     */
    RestResult putFirmware(int userId,String productKey, String firmwareName, String firmwareVersion,
                           String md5, String firmwareDesc, String firmwareUrl,int size);

    /**
     * 获取固件列表
     * @param userId
     * @param page
     * @param per
     * @param productKey
     * @return
     */
    RestResult getFirmwareList(int userId, int page,int per,String productKey);

    /**
     * 添加上传文件信息，获取上传token
     * @param userId
     * @param md5
     * @param size
     * @param name
     * @return
     */
    RestResult putFirmwareFile(int userId, String md5, int size, String name);

    /**
     * 删除固件
     * @param firmwareId
     * @return
     */
    RestResult deleteFirmware(int firmwareId);

    /**
     * 获取固件详情
     * @param firmwareId
     * @return
     */
    RestResult getFirmware(int firmwareId);

    /**
     * 固件升级信息获取
     * @param page
     * @param per
     * @param firmwareId
     * @param type
     * @param deviceName
     * @return
     */
    RestResult upgradeFirmware(int page, int per, int firmwareId, int type, String deviceName);

    /**
     * 固件验证
     * @param userId
     * @param firmwareId
     * @param productKey
     * @param deviceName
     * @return
     */
    RestResult verifyFirmware(Integer userId, Integer firmwareId, String productKey, String deviceName);

    /**
     * 刷新固件进度
     * @param deviceName
     * @param progress
     * @return
     */
    RestResult reflushProgress(Integer firmwareId, String deviceName, Integer progress);

    /***
     * 检查固件升级版本是否正确
     * @param deviceTripleId
     * @param firmwareId
     * @param pushVersion
     * @return
     */
    void checkUpgradeResult(Integer deviceTripleId, Integer firmwareId, String pushVersion);
}
