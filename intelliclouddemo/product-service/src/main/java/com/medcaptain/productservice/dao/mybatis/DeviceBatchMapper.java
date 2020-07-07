package com.medcaptain.productservice.dao.mybatis;

import com.medcaptain.productservice.entity.mybatis.DeviceBatch;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DeviceBatchMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DeviceBatch record);

    int insertSelective(DeviceBatch record);

    DeviceBatch selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DeviceBatch record);

    int updateByPrimaryKey(DeviceBatch record);

    List<DeviceBatch> selectByUserIdAndOrganizationId(
            @Param("userId") Integer userId,
            @Param("organizationId") Integer organizationId,
            @Param("batch") String batch
    );

    DeviceBatch selectByBatch(
            @Param("batch") String batch,
            @Param("userId") Integer userId,
            @Param("organizationId") Integer organizationId
    );

}