package com.medcaptain.cloud.usermanage.mapper;

import com.medcaptain.cloud.usermanage.entity.Organization;
import com.medcaptain.cloud.usermanage.entity.OrganizationExample;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 机构信息数据库操作映射
 *
 * @author bingwen.ai
 */

@Component
public interface OrganizationMapper {
    int countByExample(OrganizationExample example);

    int deleteByExample(OrganizationExample example);

    int deleteByPrimaryKey(Integer organizationId);

    int insert(Organization record);

    int insertSelective(Organization record);

    List<Organization> selectByExample(OrganizationExample example);

    List<Organization> listOrganizations(Map<String, Object> params);

    Organization selectByPrimaryKey(Integer organizationId);

    int updateByExampleSelective(@Param("record") Organization record,
                                 @Param("example") OrganizationExample example);

    int updateByExample(@Param("record") Organization record,
                        @Param("example") OrganizationExample example);

    int updateByPrimaryKeySelective(Organization record);

    int updateByPrimaryKey(Organization record);

    List<Organization> selectOrganizationtime(@Param("timeStart") Date timeStart, @Param("timeEnd") Date timeEnd);
}
