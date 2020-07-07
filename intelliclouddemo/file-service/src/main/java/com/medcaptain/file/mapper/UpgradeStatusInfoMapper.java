package com.medcaptain.file.mapper;

import com.medcaptain.file.entity.mysql.UpgradeStatusInfo;
import com.medcaptain.file.entity.mysql.UpgradeStatusInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UpgradeStatusInfoMapper {
    int countByExample(UpgradeStatusInfoExample example);

    int deleteByExample(UpgradeStatusInfoExample example);

    int deleteByPrimaryKey(Integer firmwareId);

    int insert(UpgradeStatusInfo record);

    int insertSelective(UpgradeStatusInfo record);

    List<UpgradeStatusInfo> selectByExample(UpgradeStatusInfoExample example);

    UpgradeStatusInfo selectByPrimaryKey(Integer firmwareId);

    int updateByExampleSelective(@Param("record") UpgradeStatusInfo record,
                                 @Param("example") UpgradeStatusInfoExample example);

    int updateByExample(@Param("record") UpgradeStatusInfo record, @Param("example") UpgradeStatusInfoExample example);

    int updateByPrimaryKeySelective(UpgradeStatusInfo record);

    int updateByPrimaryKey(UpgradeStatusInfo record);
}