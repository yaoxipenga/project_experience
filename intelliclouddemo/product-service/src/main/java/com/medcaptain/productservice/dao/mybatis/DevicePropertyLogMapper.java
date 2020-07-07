package com.medcaptain.productservice.dao.mybatis;

import com.medcaptain.productservice.entity.mybatis.DevicePropertyLog;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface DevicePropertyLogMapper {
    int checkData(Integer checkResult, Integer deviceLogId);

    List<Map<String, Object>> listPropertyLog(Map map);

    int deleteByPrimaryKey(Integer deviceLogId);

    int insert(DevicePropertyLog record);

    int insertSelective(DevicePropertyLog record);

    DevicePropertyLog selectByPrimaryKey(Integer deviceLogId);

    int updateByPrimaryKeySelective(DevicePropertyLog record);

    int updateByPrimaryKeyWithBLOBs(DevicePropertyLog record);

    int updateByPrimaryKey(DevicePropertyLog record);
}