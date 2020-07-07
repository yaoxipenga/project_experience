package com.medcaptain.productservice.dao.mybatis;

import com.medcaptain.productservice.entity.mybatis.DeviceEventLog;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface DeviceEventLogMapper {
    int eventCount(String productKey, String deviceName);

    int checkData(Integer checkResult, Integer deviceLogId);

    List<Map<String, Object>> getDeviceEventLog(Map map);

    int deleteByPrimaryKey(Integer deviceLogId);

    int insert(DeviceEventLog record);

    int insertSelective(DeviceEventLog record);

    DeviceEventLog selectByPrimaryKey(Integer deviceLogId);

    int updateByPrimaryKeySelective(DeviceEventLog record);

    int updateByPrimaryKeyWithBLOBs(DeviceEventLog record);

    int updateByPrimaryKey(DeviceEventLog record);
}