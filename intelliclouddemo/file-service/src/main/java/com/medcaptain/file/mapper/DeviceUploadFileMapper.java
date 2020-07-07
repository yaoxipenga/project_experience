package com.medcaptain.file.mapper;

import com.medcaptain.file.entity.mysql.DeviceUploadFile;
import com.medcaptain.file.entity.mysql.DeviceUploadFileExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DeviceUploadFileMapper {
    int countByExample(DeviceUploadFileExample example);

    int deleteByExample(DeviceUploadFileExample example);

    int deleteByPrimaryKey(Integer fileId);

    int insert(DeviceUploadFile record);

    int insertSelective(DeviceUploadFile record);

    List<DeviceUploadFile> selectByExample(DeviceUploadFileExample example);

    DeviceUploadFile selectByPrimaryKey(Integer fileId);

    int updateByExampleSelective(@Param("record") DeviceUploadFile record,
                                 @Param("example") DeviceUploadFileExample example);

    int updateByExample(@Param("record") DeviceUploadFile record, @Param("example") DeviceUploadFileExample example);

    int updateByPrimaryKeySelective(DeviceUploadFile record);

    int updateByPrimaryKey(DeviceUploadFile record);
}