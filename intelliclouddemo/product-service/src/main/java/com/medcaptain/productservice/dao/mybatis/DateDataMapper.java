package com.medcaptain.productservice.dao.mybatis;

import com.medcaptain.productservice.entity.mybatis.DateData;

public interface DateDataMapper {
    int deleteByPrimaryKey(Integer indexId);

    int insert(DateData record);

    int insertSelective(DateData record);

    DateData selectByPrimaryKey(Integer indexId);

    int updateByPrimaryKeySelective(DateData record);

    int updateByPrimaryKey(DateData record);
}