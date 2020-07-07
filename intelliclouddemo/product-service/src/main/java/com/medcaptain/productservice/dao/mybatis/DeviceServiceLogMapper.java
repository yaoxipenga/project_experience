package com.medcaptain.productservice.dao.mybatis;

import com.medcaptain.productservice.entity.mybatis.DeviceServiceLog;
import com.medcaptain.productservice.entity.mybatis.DeviceServiceLogWithBLOBs;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface DeviceServiceLogMapper {
    List<String> getDeviceServiceLog(Map map);

    int deleteByPrimaryKey(Integer deviceLogId);

    int insert(DeviceServiceLogWithBLOBs record);

    int insertSelective(DeviceServiceLogWithBLOBs record);

    DeviceServiceLogWithBLOBs selectByPrimaryKey(Integer deviceLogId);

    int updateByPrimaryKeySelective(DeviceServiceLogWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(DeviceServiceLogWithBLOBs record);

    int updateByPrimaryKey(DeviceServiceLog record);
}