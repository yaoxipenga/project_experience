package com.medcaptain.cloud.usermanage.service.impl;

import com.medcaptain.cloud.usermanage.entity.OrganizationDomain;
import com.medcaptain.cloud.usermanage.entity.OrganizationDomainExample;
import com.medcaptain.cloud.usermanage.mapper.OrganizationDomainMapper;
import com.medcaptain.cloud.usermanage.service.OrganizationDomainService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 机构域名映射服务实现
 *
 * @author bingwen.ai
 */
@Service
public class OrganizationDomainServiceImpl implements OrganizationDomainService {
    @Autowired
    OrganizationDomainMapper organizationDomainMapper;

    @Override
    public boolean addOrganizationDomain(int organizationId, String domain) {
        OrganizationDomain organizationDomain = new OrganizationDomain();
        organizationDomain.setDomainUrl(domain);
        organizationDomain.setOrganizationId(organizationId);
        return organizationDomainMapper.insertSelective(organizationDomain) > 0;
    }

    @Override
    public boolean updateOrganizationDomain(int domainID, String domain) {
        OrganizationDomain organizationDomain = new OrganizationDomain();
        organizationDomain.setDomainUrl(domain);
        organizationDomain.setDomainId(domainID);
        return organizationDomainMapper.updateByPrimaryKeySelective(organizationDomain) > 0;
    }

    @Override
    public boolean deleteOrganizationDomain(int domainID) {
        return organizationDomainMapper.deleteByPrimaryKey(domainID) > 0;
    }

    @Override
    public List<OrganizationDomain> listDomains(int organizationID) {
        OrganizationDomainExample example = new OrganizationDomainExample();
        example.createCriteria().andOrganizationIdEqualTo(organizationID);
        return organizationDomainMapper.selectByExample(example);
    }

    @Override
    public boolean existDomain(String domainURL) {
        OrganizationDomainExample example = new OrganizationDomainExample();
        example.createCriteria().andDomainUrlEqualTo(domainURL);
        return organizationDomainMapper.countByExample(example) > 0;
    }

    @Override
    public boolean existDomain(int domainID) {
        OrganizationDomainExample example = new OrganizationDomainExample();
        example.createCriteria().andDomainIdEqualTo(domainID);
        return organizationDomainMapper.countByExample(example) > 0;
    }

    @Override
    public boolean existDomain(int organizationID, String domainURL) {
        OrganizationDomainExample example = new OrganizationDomainExample();
        example.createCriteria().andDomainUrlEqualTo(domainURL).andOrganizationIdEqualTo(organizationID);
        return organizationDomainMapper.countByExample(example) > 0;
    }

    @Override
    public int getOrganizationID(String domainUrl) {
        if (StringUtils.isNotEmpty(domainUrl)) {
            OrganizationDomainExample example = new OrganizationDomainExample();
            example.createCriteria().andDomainUrlEqualTo(domainUrl);
            List<OrganizationDomain> domainList = organizationDomainMapper.selectByExample(example);
            if (domainList != null && domainList.size() > 0) {
                return domainList.get(0).getOrganizationId();
            }
        }
        return 0;
    }
}
