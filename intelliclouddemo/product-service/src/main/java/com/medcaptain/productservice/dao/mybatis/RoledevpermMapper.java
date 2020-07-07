package com.medcaptain.productservice.dao.mybatis;

import com.medcaptain.productservice.entity.mybatis.Roledevperm;

public interface RoledevpermMapper {
    int deleteByPrimaryKey(Integer roledevpermId);

    int insert(Roledevperm record);

    int insertSelective(Roledevperm record);

    Roledevperm selectByPrimaryKey(Integer roledevpermId);

    int updateByPrimaryKeySelective(Roledevperm record);

    int updateByPrimaryKey(Roledevperm record);
}