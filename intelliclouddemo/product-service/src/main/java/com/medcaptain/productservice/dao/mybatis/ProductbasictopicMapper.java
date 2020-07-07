package com.medcaptain.productservice.dao.mybatis;

import com.medcaptain.productservice.entity.mybatis.Productbasictopic;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface ProductbasictopicMapper {
    List<Map<String,Object>> ListBasicTopic();

    int deleteByPrimaryKey(Integer indexId);

    int insert(Productbasictopic record);

    int insertSelective(Productbasictopic record);

    Productbasictopic selectByPrimaryKey(Integer indexId);

    int updateByPrimaryKeySelective(Productbasictopic record);

    int updateByPrimaryKey(Productbasictopic record);
}