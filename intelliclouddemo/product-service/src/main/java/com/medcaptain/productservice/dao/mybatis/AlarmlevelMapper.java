package com.medcaptain.productservice.dao.mybatis;

import com.medcaptain.productservice.entity.mybatis.Alarmlevel;

public interface AlarmlevelMapper {
    int deleteByPrimaryKey(Integer alarmLevelId);

    int insert(Alarmlevel record);

    int insertSelective(Alarmlevel record);

    Alarmlevel selectByPrimaryKey(Integer alarmLevelId);

    int updateByPrimaryKeySelective(Alarmlevel record);

    int updateByPrimaryKey(Alarmlevel record);
}