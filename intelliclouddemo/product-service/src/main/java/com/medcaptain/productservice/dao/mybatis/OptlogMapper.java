package com.medcaptain.productservice.dao.mybatis;

import com.medcaptain.productservice.entity.mybatis.Optlog;

public interface OptlogMapper {
    int deleteByPrimaryKey(Integer optlogId);

    int insert(Optlog record);

    int insertSelective(Optlog record);

    Optlog selectByPrimaryKey(Integer optlogId);

    int updateByPrimaryKeySelective(Optlog record);

    int updateByPrimaryKeyWithBLOBs(Optlog record);

    int updateByPrimaryKey(Optlog record);
}