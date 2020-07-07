package com.medcaptain.productservice.dao.mybatis;

import com.medcaptain.productservice.entity.mybatis.TransParaIo;
import com.medcaptain.productservice.entity.mybatis.TransParaIoKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface TransParaIoMapper {
    int deleteByProductKey(String productKey);

    List<Map<String, Object>> getTransactionParameter(String identifier);

    int deleteParameter(Integer transactionId);

    Map<String, Object> getTransactionData(Map map);

    List<Map<String, Object>> getTransactionDetails(@Param(value = "productKey") String productKey, @Param(value = "productTransactionIdentifier") String productTransactionIdentifier);

    int addProperty(Map map);

    Map<String, Object> getParamDetails(Map map);

    List<String> getParameterType(String transactionIdentity, String productKey);

    int deleteByPrimaryKey(TransParaIoKey key);

    int insert(TransParaIo record);

    int insertSelective(TransParaIo record);

    TransParaIo selectByPrimaryKey(TransParaIoKey key);

    int updateByPrimaryKeySelective(TransParaIo record);

    int updateByPrimaryKey(TransParaIo record);
}