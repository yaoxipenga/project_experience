package com.medcaptain.file.mapper;

import com.medcaptain.file.entity.mysql.FirmwareUpgrade;
import com.medcaptain.file.entity.mysql.FirmwareUpgradeExample;
import com.medcaptain.file.entity.mysql.FirmwareUpgradeKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface FirmwareUpgradeMapper {

    int countByExample(FirmwareUpgradeExample example);

    int deleteByExample(FirmwareUpgradeExample example);

    int deleteByPrimaryKey(FirmwareUpgradeKey key);

    int insert(FirmwareUpgrade record);

    int insertSelective(FirmwareUpgrade record);

    List<FirmwareUpgrade> selectByExample(FirmwareUpgradeExample example);

    FirmwareUpgrade selectByPrimaryKey(FirmwareUpgradeKey key);

    int updateByExampleSelective(@Param("record") FirmwareUpgrade record,
                                 @Param("example") FirmwareUpgradeExample example);

    int updateByExample(@Param("record") FirmwareUpgrade record, @Param("example") FirmwareUpgradeExample example);

    int updateByPrimaryKeySelective(FirmwareUpgrade record);

    int updateByPrimaryKey(FirmwareUpgrade record);
}