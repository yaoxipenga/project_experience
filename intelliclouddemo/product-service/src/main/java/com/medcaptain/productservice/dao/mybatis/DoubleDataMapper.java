package com.medcaptain.productservice.dao.mybatis;

import com.medcaptain.productservice.entity.mybatis.DoubleData;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public interface DoubleDataMapper {
    int getLastInsertId();

    int addDoubleData(Map map);

    int deleteByPrimaryKey(Integer indexId);

    int insert(DoubleData record);

    int insertSelective(DoubleData record);

    DoubleData selectByPrimaryKey(Integer indexId);

    int updateByPrimaryKeySelective(DoubleData record);

    int updateByPrimaryKey(DoubleData record);
}