package com.medcaptain.productservice.dao.mybatis;

import com.medcaptain.productservice.entity.mybatis.Productcategory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface ProductcategoryMapper {
    List<Map<String, Object>> getAllCategory();

    int deleteByPrimaryKey(Integer productCate);

    int insert(Productcategory record);

    int insertSelective(Productcategory record);

    Productcategory selectByPrimaryKey(Integer productCate);

    int updateByPrimaryKeySelective(Productcategory record);

    int updateByPrimaryKey(Productcategory record);
}