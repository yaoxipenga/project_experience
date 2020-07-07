package com.medcaptain.productservice.dao.mybatis;

import com.medcaptain.productservice.entity.mybatis.Devicestatustype;

public interface DevicestatustypeMapper {
    int deleteByPrimaryKey(Integer statusId);

    int insert(Devicestatustype record);

    int insertSelective(Devicestatustype record);

    Devicestatustype selectByPrimaryKey(Integer statusId);

    int updateByPrimaryKeySelective(Devicestatustype record);

    int updateByPrimaryKey(Devicestatustype record);
}