package com.medcaptain.productservice.dao.mybatis;

import com.medcaptain.productservice.entity.mybatis.DeviceUploadStatus;
import org.apache.ibatis.annotations.Param;

public interface DeviceUploadStatusMapper {
    int deleteByPrimaryKey(Integer deviceUploadStatusId);

    int insert(DeviceUploadStatus record);

    int insertSelective(DeviceUploadStatus record);

    DeviceUploadStatus selectByPrimaryKey(Integer deviceUploadStatusId);

    int updateByPrimaryKeySelective(DeviceUploadStatus record);

    int updateByPrimaryKey(DeviceUploadStatus record);

    DeviceUploadStatus selectByDeviceTripleIdAndFilaName(@Param("deviceTripleId") Integer deviceTripleId,
                                                         @Param("fimeName") String fileName);
}