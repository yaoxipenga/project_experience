package com.medcaptain.productservice.dao.mybatis;

import com.medcaptain.productservice.entity.mybatis.Deviceuploadfile;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Component
public interface DeviceuploadfileMapper {
    /**
     * 查询已经上载的日志文件列表
     *
     * @param deviceId
     * @param page
     * @param size
     * @return
     */
    List<Map<String, Object>> selectLogFileList(@Param("deviceId") Integer deviceId,
                                                @Param("userId") Integer userId,
                                                @Param("page") Integer page,
                                                @Param("size") Integer size);

    Integer countByUserIdAndDeviceId(@Param("deviceId") Integer deviceId,
                                     @Param("userId") Integer userId);

    int deleteByPrimaryKey(Integer fileId);

    int insert(Deviceuploadfile record);

    int insertSelective(Deviceuploadfile record);

    Deviceuploadfile selectByPrimaryKey(Integer fileId);

    int updateByPrimaryKeySelective(Deviceuploadfile record);

    int updateByPrimaryKey(Deviceuploadfile record);

    int updateByMd5(@Param("md5") String md5,
                    @Param("path") String path,
                    @Param("successful") boolean successful);

    List<Deviceuploadfile> getDeviceUploadFileStatus(@Param("deviceTripleId") Integer deviceTripleId,
                                               @Param("fileName") String fileName);

    Map<String, Object> getDeviceTripleIdAndDeviceNameByMd5(@Param("md5") String md5);

    String getFilePath(@Param("deviceTripleId") Integer deviceTripleId, @Param("fileName") String fileName);
}