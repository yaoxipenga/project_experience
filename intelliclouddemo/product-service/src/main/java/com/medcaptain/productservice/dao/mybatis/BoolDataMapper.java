package com.medcaptain.productservice.dao.mybatis;

import com.medcaptain.productservice.entity.mybatis.BoolData;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public interface BoolDataMapper {
    int getLastInsertId();

    int addBoolData(Map map);

    int deleteByPrimaryKey(Integer indexId);

    int insert(BoolData record);

    int insertSelective(BoolData record);

    BoolData selectByPrimaryKey(Integer indexId);

    int updateByPrimaryKeySelective(BoolData record);

    int updateByPrimaryKey(BoolData record);
}