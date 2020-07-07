package com.medcaptain.productservice.dao.mybatis;

import com.medcaptain.productservice.entity.mybatis.Organization;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

@Component
public interface OrganizationMapper {
    int deleteByPrimaryKey(Integer organizationId);

    int insert(Organization record);

    int insertSelective(Organization record);

    Organization selectByPrimaryKey(Integer organizationId);

    int updateByPrimaryKeySelective(Organization record);

    int updateByPrimaryKey(Organization record);

    Organization selectByType(
            @Param("organizationId") Integer organizationId,
            @Param("organizationType") Integer organizationType
    );

}