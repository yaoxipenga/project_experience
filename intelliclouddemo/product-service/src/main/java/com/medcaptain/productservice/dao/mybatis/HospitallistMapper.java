package com.medcaptain.productservice.dao.mybatis;

import com.medcaptain.productservice.entity.mybatis.Hospitallist;

public interface HospitallistMapper {
    int deleteByPrimaryKey(Integer hospitalId);

    int insert(Hospitallist record);

    int insertSelective(Hospitallist record);

    Hospitallist selectByPrimaryKey(Integer hospitalId);

    int updateByPrimaryKeySelective(Hospitallist record);

    int updateByPrimaryKey(Hospitallist record);
}