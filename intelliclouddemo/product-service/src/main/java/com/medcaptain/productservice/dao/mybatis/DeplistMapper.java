package com.medcaptain.productservice.dao.mybatis;

import com.medcaptain.productservice.entity.mybatis.Deplist;

public interface DeplistMapper {
    int deleteByPrimaryKey(Integer deptId);

    int insert(Deplist record);

    int insertSelective(Deplist record);

    Deplist selectByPrimaryKey(Integer deptId);

    int updateByPrimaryKeySelective(Deplist record);

    int updateByPrimaryKey(Deplist record);
}