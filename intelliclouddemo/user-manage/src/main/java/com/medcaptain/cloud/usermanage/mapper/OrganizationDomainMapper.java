package com.medcaptain.cloud.usermanage.mapper;

import com.medcaptain.cloud.usermanage.entity.OrganizationDomain;
import com.medcaptain.cloud.usermanage.entity.OrganizationDomainExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 机构域名信息SQL映射
 *
 * @author bingwen.ai
 */
@Component
public interface OrganizationDomainMapper {
    int countByExample(OrganizationDomainExample example);

    int deleteByExample(OrganizationDomainExample example);

    int deleteByPrimaryKey(Integer domainId);

    int insert(OrganizationDomain record);

    int insertSelective(OrganizationDomain record);

    List<OrganizationDomain> selectByExample(OrganizationDomainExample example);

    OrganizationDomain selectByPrimaryKey(Integer domainId);

    int updateByExampleSelective(@Param("record") OrganizationDomain record, @Param("example") OrganizationDomainExample example);

    int updateByExample(@Param("record") OrganizationDomain record, @Param("example") OrganizationDomainExample example);

    int updateByPrimaryKeySelective(OrganizationDomain record);

    int updateByPrimaryKey(OrganizationDomain record);
}