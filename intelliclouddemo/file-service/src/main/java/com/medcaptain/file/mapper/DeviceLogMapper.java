package com.medcaptain.file.mapper;

import com.medcaptain.file.entity.mysql.DeviceLog;
import com.medcaptain.file.entity.mysql.DeviceLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DeviceLogMapper {
    int countByExample(DeviceLogExample example);

    int deleteByExample(DeviceLogExample example);

    int deleteByPrimaryKey(Integer deviceLogId);

    int insert(DeviceLog record);

    int insertSelective(DeviceLog record);

    List<DeviceLog> selectByExampleWithBLOBs(DeviceLogExample example);

    List<DeviceLog> selectByExample(DeviceLogExample example);

    DeviceLog selectByPrimaryKey(Integer deviceLogId);

    int updateByExampleSelective(@Param("record") DeviceLog record, @Param("example") DeviceLogExample example);

    int updateByExampleWithBLOBs(@Param("record") DeviceLog record, @Param("example") DeviceLogExample example);

    int updateByExample(@Param("record") DeviceLog record, @Param("example") DeviceLogExample example);

    int updateByPrimaryKeySelective(DeviceLog record);

    int updateByPrimaryKeyWithBLOBs(DeviceLog record);

    int updateByPrimaryKey(DeviceLog record);
}