package com.medcaptain.productservice.dao.mybatis;

import com.medcaptain.productservice.entity.mybatis.Hospdeprelat;

public interface HospdeprelatMapper {
    int deleteByPrimaryKey(Integer hospiDeptRelatId);

    int insert(Hospdeprelat record);

    int insertSelective(Hospdeprelat record);

    Hospdeprelat selectByPrimaryKey(Integer hospiDeptRelatId);

    int updateByPrimaryKeySelective(Hospdeprelat record);

    int updateByPrimaryKey(Hospdeprelat record);
}