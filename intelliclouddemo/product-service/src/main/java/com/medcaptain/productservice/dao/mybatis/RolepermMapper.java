package com.medcaptain.productservice.dao.mybatis;

import com.medcaptain.productservice.entity.mybatis.Roleperm;

public interface RolepermMapper {
    int deleteByPrimaryKey(Integer indexId);

    int insert(Roleperm record);

    int insertSelective(Roleperm record);

    Roleperm selectByPrimaryKey(Integer indexId);

    int updateByPrimaryKeySelective(Roleperm record);

    int updateByPrimaryKey(Roleperm record);
}