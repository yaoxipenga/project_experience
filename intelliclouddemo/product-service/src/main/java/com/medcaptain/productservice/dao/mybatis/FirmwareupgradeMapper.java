package com.medcaptain.productservice.dao.mybatis;

import com.medcaptain.productservice.entity.mybatis.Firmwareupgrade;

public interface FirmwareupgradeMapper {
    int deleteByPrimaryKey(Integer firmwareId);

    int insert(Firmwareupgrade record);

    int insertSelective(Firmwareupgrade record);

    Firmwareupgrade selectByPrimaryKey(Integer firmwareId);

    int updateByPrimaryKeySelective(Firmwareupgrade record);

    int updateByPrimaryKey(Firmwareupgrade record);
}