package com.medcaptain.productservice.dao.mybatis;

import com.medcaptain.productservice.entity.mybatis.Roleprodperm;

public interface RoleprodpermMapper {
    int deleteByPrimaryKey(Integer roleprodpermId);

    int insert(Roleprodperm record);

    int insertSelective(Roleprodperm record);

    Roleprodperm selectByPrimaryKey(Integer roleprodpermId);

    int updateByPrimaryKeySelective(Roleprodperm record);

    int updateByPrimaryKey(Roleprodperm record);
}