package com.medcaptain.productservice.dao.mybatis;

import com.medcaptain.productservice.entity.mybatis.Roleinfo;

public interface RoleinfoMapper {
    int deleteByPrimaryKey(Integer roleId);

    int insert(Roleinfo record);

    int insertSelective(Roleinfo record);

    Roleinfo selectByPrimaryKey(Integer roleId);

    int updateByPrimaryKeySelective(Roleinfo record);

    int updateByPrimaryKey(Roleinfo record);
}