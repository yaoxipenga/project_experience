package com.medcaptain.productservice.dao.mybatis;

import com.medcaptain.productservice.entity.mybatis.Opttype;

public interface OpttypeMapper {
    int deleteByPrimaryKey(Integer optType);

    int insert(Opttype record);

    int insertSelective(Opttype record);

    Opttype selectByPrimaryKey(Integer optType);

    int updateByPrimaryKeySelective(Opttype record);

    int updateByPrimaryKey(Opttype record);
}