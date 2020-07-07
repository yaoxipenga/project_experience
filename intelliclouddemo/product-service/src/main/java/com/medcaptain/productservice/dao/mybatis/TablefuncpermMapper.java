package com.medcaptain.productservice.dao.mybatis;

import com.medcaptain.productservice.entity.mybatis.Tablefuncperm;

public interface TablefuncpermMapper {
    int deleteByPrimaryKey(Integer funcPermId);

    int insert(Tablefuncperm record);

    int insertSelective(Tablefuncperm record);

    Tablefuncperm selectByPrimaryKey(Integer funcPermId);

    int updateByPrimaryKeySelective(Tablefuncperm record);

    int updateByPrimaryKey(Tablefuncperm record);
}