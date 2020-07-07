package com.medcaptain.productservice.dao.mybatis;

import com.medcaptain.productservice.entity.mybatis.Eventtype;

public interface EventtypeMapper {
    int deleteByPrimaryKey(Integer eventType);

    int insert(Eventtype record);

    int insertSelective(Eventtype record);

    Eventtype selectByPrimaryKey(Integer eventType);

    int updateByPrimaryKeySelective(Eventtype record);

    int updateByPrimaryKey(Eventtype record);
}