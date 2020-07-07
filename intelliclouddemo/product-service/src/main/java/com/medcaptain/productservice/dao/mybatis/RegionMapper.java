package com.medcaptain.productservice.dao.mybatis;

import com.medcaptain.productservice.entity.mybatis.Region;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

@Component
public interface RegionMapper {
    int deleteByPrimaryKey(Integer regionId);

    int insert(Region record);

    int insertSelective(Region record);

    Region selectByPrimaryKey(Integer regionId);

    int updateByPrimaryKeySelective(Region record);

    int updateByPrimaryKey(Region record);

    /**
     * 查询机构的位置
     * @param organizationId
     * @return
     */
    Region selectOrganizationId(@Param("organizationId") Integer organizationId);

}