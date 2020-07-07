package com.medcaptain.productservice.dao.mybatis;

import com.medcaptain.productservice.entity.mybatis.Translog;

public interface TranslogMapper {
    int deleteByPrimaryKey(Integer indexId);

    int insert(Translog record);

    int insertSelective(Translog record);

    Translog selectByPrimaryKey(Integer indexId);

    int updateByPrimaryKeySelective(Translog record);

    int updateByPrimaryKeyWithBLOBs(Translog record);

    int updateByPrimaryKey(Translog record);
}