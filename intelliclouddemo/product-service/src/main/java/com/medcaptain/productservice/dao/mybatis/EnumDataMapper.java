package com.medcaptain.productservice.dao.mybatis;

import com.medcaptain.productservice.entity.mybatis.EnumData;
import com.medcaptain.productservice.entity.mybatis.EnumKey;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public interface EnumDataMapper {
    int addEnumData(Map map);

    Map<String,Object> getEnumData(String productKey, String productTransactionIdentifier);

    int deleteByPrimaryKey(EnumKey key);

    int insert(EnumData record);

    int insertSelective(EnumData record);

    EnumData selectByPrimaryKey(EnumKey key);

    int updateByPrimaryKeySelective(EnumData record);

    int updateByPrimaryKey(EnumData record);
}