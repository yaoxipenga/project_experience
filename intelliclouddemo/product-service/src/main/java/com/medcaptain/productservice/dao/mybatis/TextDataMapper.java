package com.medcaptain.productservice.dao.mybatis;

import com.medcaptain.productservice.entity.mybatis.TextData;

public interface TextDataMapper {
    int deleteByPrimaryKey(Integer indexId);

    int insert(TextData record);

    int insertSelective(TextData record);

    TextData selectByPrimaryKey(Integer indexId);

    int updateByPrimaryKeySelective(TextData record);

    int updateByPrimaryKey(TextData record);
}