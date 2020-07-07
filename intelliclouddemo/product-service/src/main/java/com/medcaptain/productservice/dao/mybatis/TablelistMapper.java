package com.medcaptain.productservice.dao.mybatis;

import com.medcaptain.productservice.entity.mybatis.Tablelist;
import org.springframework.stereotype.Component;

@Component
public interface TablelistMapper {
    String getTableName(Integer tableId);

    int deleteByPrimaryKey(Integer tableId);

    int insert(Tablelist record);

    int insertSelective(Tablelist record);

    Tablelist selectByPrimaryKey(Integer tableId);

    int updateByPrimaryKeySelective(Tablelist record);

    int updateByPrimaryKey(Tablelist record);
}