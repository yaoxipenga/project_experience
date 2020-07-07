package com.medcaptain.productservice.dao.mybatis;

import com.medcaptain.productservice.entity.mybatis.Int32Data;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public interface Int32DataMapper {
    int getLastInsertId();

    int addInt32Data(Map map);

    int deleteByPrimaryKey(Integer indexId);

    int insert(Int32Data record);

    int insertSelective(Int32Data record);

    Int32Data selectByPrimaryKey(Integer indexId);

    int updateByPrimaryKeySelective(Int32Data record);

    int updateByPrimaryKey(Int32Data record);
}