package com.medcaptain.productservice.dao.mybatis;

import com.medcaptain.productservice.entity.mybatis.Producttriple;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public interface ProducttripleMapper {
    int createTriple(Map map);

    int resetProductSecret(@Param(value = "newProductSecret") String newProductSecret, @Param("productKey") String productKey);

    int deleteByPrimaryKey(String productKey);

    int insert(Producttriple record);

    int insertSelective(Producttriple record);

    Producttriple selectByPrimaryKey(String productKey);

    int updateByPrimaryKeySelective(Producttriple record);

    int updateByPrimaryKey(Producttriple record);
}