package com.medcaptain.file.mapper;

import com.medcaptain.file.entity.mysql.DeviceUploadStatus;
import com.medcaptain.file.entity.mysql.DeviceUploadStatusExample;
import com.medcaptain.file.entity.mysql.DeviceUploadStatusKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DeviceUploadStatusMapper {
    int countByExample(DeviceUploadStatusExample example);

    int deleteByExample(DeviceUploadStatusExample example);

    int deleteByPrimaryKey(DeviceUploadStatusKey key);

    int insert(DeviceUploadStatus record);

    int insertSelective(DeviceUploadStatus record);

    List<DeviceUploadStatus> selectByExample(DeviceUploadStatusExample example);

    DeviceUploadStatus selectByPrimaryKey(DeviceUploadStatusKey key);

    int updateByExampleSelective(@Param("record") DeviceUploadStatus record,
                                 @Param("example") DeviceUploadStatusExample example);

    int updateByExample(@Param("record") DeviceUploadStatus record, @Param("example") DeviceUploadStatusExample example);

    int updateByPrimaryKeySelective(DeviceUploadStatus record);

    int updateByPrimaryKey(DeviceUploadStatus record);
}