package com.medcaptain.productservice.dao.mybatis;

import com.medcaptain.productservice.entity.mybatis.TransType;

public interface TransTypeMapper {
    int deleteByPrimaryKey(Integer productTransType);

    int insert(TransType record);

    int insertSelective(TransType record);

    TransType selectByPrimaryKey(Integer productTransType);

    int updateByPrimaryKeySelective(TransType record);

    int updateByPrimaryKey(TransType record);
}