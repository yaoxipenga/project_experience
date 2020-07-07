package com.medcaptain.productservice.dao.mybatis;

import com.medcaptain.productservice.entity.mybatis.ProductTransactionIdentifiers;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ProductTransactionIdentifiersMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProductTransactionIdentifiers record);

    int insertSelective(ProductTransactionIdentifiers record);

    ProductTransactionIdentifiers selectByPrimaryKey(Integer id);

    List<ProductTransactionIdentifiers> selectByProductKey(
            @Param("productKey") String productKey,
            @Param("type") int type
    );

    int updateByPrimaryKeySelective(ProductTransactionIdentifiers record);

    int updateByPrimaryKey(ProductTransactionIdentifiers record);
}