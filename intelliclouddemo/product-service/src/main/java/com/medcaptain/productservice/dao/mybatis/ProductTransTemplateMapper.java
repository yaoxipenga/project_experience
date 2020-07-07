package com.medcaptain.productservice.dao.mybatis;

import com.medcaptain.productservice.entity.mybatis.ProductTransTemplate;
import com.medcaptain.productservice.entity.mybatis.ProductTransTemplateKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface ProductTransTemplateMapper {
    Map<String, Object> getTransactionTemplate(Integer templateId);

    List<Map<String, Object>> getAllTransactions(@Param(value="TransactionType") String TransactionType);

    int deleteByPrimaryKey(ProductTransTemplateKey key);

    int insert(ProductTransTemplate record);

    int insertSelective(ProductTransTemplate record);

    ProductTransTemplate selectByPrimaryKey(ProductTransTemplateKey key);

    int updateByPrimaryKeySelective(ProductTransTemplate record);

    int updateByPrimaryKey(ProductTransTemplate record);
}