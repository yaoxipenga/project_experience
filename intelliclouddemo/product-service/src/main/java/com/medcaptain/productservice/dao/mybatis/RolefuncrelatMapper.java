package com.medcaptain.productservice.dao.mybatis;

import com.medcaptain.productservice.entity.mybatis.Rolefuncrelat;

public interface RolefuncrelatMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Rolefuncrelat record);

    int insertSelective(Rolefuncrelat record);

    Rolefuncrelat selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Rolefuncrelat record);

    int updateByPrimaryKey(Rolefuncrelat record);
}