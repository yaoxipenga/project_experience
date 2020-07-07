package com.medcaptain.productservice.dao.mybatis;

import com.medcaptain.productservice.entity.mybatis.ArrayData;

public interface ArrayDataMapper {
    int deleteByPrimaryKey(Integer indexId);

    int insert(ArrayData record);

    int insertSelective(ArrayData record);

    ArrayData selectByPrimaryKey(Integer indexId);

    int updateByPrimaryKeySelective(ArrayData record);

    int updateByPrimaryKey(ArrayData record);
}