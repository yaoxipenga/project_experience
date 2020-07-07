package com.medcaptain.productservice.dao.mybatis;

import com.medcaptain.productservice.entity.mybatis.Unit;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface UnitMapper {
    Map<String, Object> getUnitDetail(Integer unitId);

    List<Map<String, Object>> getAllUnit();

    String getUnitDescribe(int unitId);

    int deleteByPrimaryKey(Integer unitId);

    int insert(Unit record);

    int insertSelective(Unit record);

    Unit selectByPrimaryKey(Integer unitId);

    int updateByPrimaryKeySelective(Unit record);

    int updateByPrimaryKey(Unit record);
}