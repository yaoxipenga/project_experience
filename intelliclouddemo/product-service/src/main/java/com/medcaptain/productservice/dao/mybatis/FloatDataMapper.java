package com.medcaptain.productservice.dao.mybatis;

import com.medcaptain.productservice.entity.mybatis.FloatData;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public interface FloatDataMapper {
    int getLastInsertId();

    int addFloatData(Map map);

    int deleteByPrimaryKey(Integer indexId);

    int insert(FloatData record);

    int insertSelective(FloatData record);

    FloatData selectByPrimaryKey(Integer indexId);

    int updateByPrimaryKeySelective(FloatData record);

    int updateByPrimaryKey(FloatData record);
}