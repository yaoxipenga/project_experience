package com.medcaptain.productservice.dao.mybatis;

import com.medcaptain.productservice.entity.mybatis.Productbasicinfotemp;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ProductbasicinfotempMapper {
    List<Integer> getProductTemplates(Integer productDescribe);

    int deleteByPrimaryKey(Integer templetId);

    int insert(Productbasicinfotemp record);

    int insertSelective(Productbasicinfotemp record);

    Productbasicinfotemp selectByPrimaryKey(Integer templetId);

    int updateByPrimaryKeySelective(Productbasicinfotemp record);

    int updateByPrimaryKey(Productbasicinfotemp record);
}