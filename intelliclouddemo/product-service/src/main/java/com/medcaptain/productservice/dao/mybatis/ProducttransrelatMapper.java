package com.medcaptain.productservice.dao.mybatis;

import com.medcaptain.productservice.entity.mybatis.Producttransrelat;
import com.medcaptain.productservice.entity.mybatis.ProducttransrelatKey;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface ProducttransrelatMapper {
    int deleteTransactionByProductKey(String productKey);

    int copyFromTemplate(Map map);

    int getTransactionId();

    int deleteTransaction(Map map);

    int updateProperty(Map map);

    int addProperty(Map map);

    List<Map<String, Object>> getProductsAllTransaction(String productKey);

    int deleteByPrimaryKey(ProducttransrelatKey key);

    int insert(Producttransrelat record);

    int insertSelective(Producttransrelat record);

    Producttransrelat selectByPrimaryKey(ProducttransrelatKey key);

    int updateByPrimaryKeySelective(Producttransrelat record);

    int updateByPrimaryKey(Producttransrelat record);
}