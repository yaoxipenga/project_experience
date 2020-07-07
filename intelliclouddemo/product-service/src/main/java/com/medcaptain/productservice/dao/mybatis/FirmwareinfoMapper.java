package com.medcaptain.productservice.dao.mybatis;

import com.medcaptain.productservice.entity.mybatis.Firmwareinfo;

public interface FirmwareinfoMapper {
    int deleteByPrimaryKey(Integer firmwareId);

    int insert(Firmwareinfo record);

    int insertSelective(Firmwareinfo record);

    Firmwareinfo selectByPrimaryKey(Integer firmwareId);

    int updateByPrimaryKeySelective(Firmwareinfo record);

    int updateByPrimaryKey(Firmwareinfo record);
}