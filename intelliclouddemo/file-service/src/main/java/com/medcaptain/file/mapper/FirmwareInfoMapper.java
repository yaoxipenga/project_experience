package com.medcaptain.file.mapper;

import com.medcaptain.file.entity.mysql.FirmwareInfo;
import com.medcaptain.file.entity.mysql.FirmwareInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface FirmwareInfoMapper {


    int countByExample(FirmwareInfoExample example);

    int deleteByExample(FirmwareInfoExample example);

    int deleteByPrimaryKey(Integer firmwareId);

    int insert(FirmwareInfo record);

    int insertSelective(FirmwareInfo record);

    List<FirmwareInfo> selectByExample(FirmwareInfoExample example);

    FirmwareInfo selectByPrimaryKey(Integer firmwareId);

    int updateByExampleSelective(@Param("record") FirmwareInfo record, @Param("example") FirmwareInfoExample example);

    int updateByExample(@Param("record") FirmwareInfo record, @Param("example") FirmwareInfoExample example);

    int updateByPrimaryKeySelective(FirmwareInfo record);

    int updateByPrimaryKey(FirmwareInfo record);
}