package com.medcaptain.parsedata.mapper.my;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 功能：存储日志
 * @author adam.chen
 */
@Component
public interface SaveLogMapper {
    /**
     * 插入日志记录
     * @param productKey
     * @param deviceName
     * @param topic
     * @param data
     * @return
     */
    Integer insertMessageRecord(@Param("productKey")String productKey,
                  @Param("deviceName")String deviceName,
                  @Param("topic")String topic,
                  @Param("data")String data);

    /**
     * 插入设备属性日志记录
     * @param productKey
     * @param deviceName
     * @param topic
     * @param data
     * @return
     */
    Integer insertPropertyLog(@Param("productKey")String productKey,
                      @Param("deviceName")String deviceName,
                      @Param("topic")String topic,
                      @Param("data")String data);

    /**
     * 插入设备事件日志记录
     * @param productKey
     * @param deviceName
     * @param topic
     * @param data
     * @return
     */
    Integer insertEventLog(@Param("productKey")String productKey,
                      @Param("deviceName")String deviceName,
                      @Param("topic")String topic,
                      @Param("data")String data);

    /**
     * 插入设备服务日志记录
     * @param productKey
     * @param deviceName
     * @param topic
     * @param data
     * @return
     */
    Integer insertServiceLog(@Param("productKey")String productKey,
                      @Param("deviceName")String deviceName,
                      @Param("topic")String topic,
                      @Param("data")String data);

    /**
     * 根据二元组查找设备唯一id
     * @param productKey
     * @param deviceName
     * @return
     */
    Integer getDeviceId(@Param("productKey")String productKey,
                        @Param("deviceName")String deviceName);

    Integer insertPublishLog(@Param("topic")String topic,
                             @Param("title")String title,
                             @Param("message")String message);

    String getOldFirmwareVersion(@Param("deviceId")Integer deviceId);
}
