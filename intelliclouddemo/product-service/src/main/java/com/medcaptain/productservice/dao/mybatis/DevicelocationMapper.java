package com.medcaptain.productservice.dao.mybatis;

import com.medcaptain.productservice.entity.mybatis.Devicelocation;
import org.springframework.stereotype.Component;

@Component
public interface DevicelocationMapper {
    int deleteByPrimaryKey(Integer devicelocationId);

    int insert(Devicelocation record);

    int insertSelective(Devicelocation record);

    Devicelocation selectByPrimaryKey(Integer devicelocationId);

    int updateByPrimaryKeySelective(Devicelocation record);

    int updateByPrimaryKey(Devicelocation record);
}