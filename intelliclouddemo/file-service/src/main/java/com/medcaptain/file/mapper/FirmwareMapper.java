package com.medcaptain.file.mapper;

import com.medcaptain.file.entity.dto.FirmwareEntity;
import com.medcaptain.file.entity.dto.FirmwareInfoOneEntity;
import com.medcaptain.file.entity.dto.UpgradeInfo;
import com.medcaptain.file.entity.dto.UpgradingInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface FirmwareMapper {


    FirmwareInfoOneEntity selectInfo(@Param(value = "firmwareid") int firmwareid);

    List<FirmwareEntity> selectList(int userId, int start, int size, String key);

    List<UpgradeInfo> getUpgradeList(int start, int size, int firmwareid, int type, String deviceName);

    List<UpgradingInfo> getUpgradingList(int start, int size, int firmwareid, int type, String deviceName);
}