package com.medcaptain.productservice.dao.mybatis;

import com.medcaptain.productservice.entity.mybatis.Producttopic;
import com.medcaptain.productservice.entity.dto.TopicListEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface ProducttopicMapper {
    int insertProductTopic(Map map);

    int deleteByPrimaryKey(Integer indexId);

    int insert(Producttopic record);

    int insertSelective(Producttopic record);

    Producttopic selectByPrimaryKey(Integer indexId);

    int updateByPrimaryKeySelective(Producttopic record);

    int updateByPrimaryKey(Producttopic record);

    List<TopicListEntity> getTopics(@Param("productKey") String productKey);

    /**
     * 获取一个产品下全部 topic
     * @param productKey 设备key
     * @return ProducttopicList
     */
    List<Producttopic> selectByproductKey(@Param("productKey") String productKey);
}