package com.medcaptain.cloud.usermanage.service;

import com.medcaptain.cloud.usermanage.entity.OrganizationDomain;

import java.util.List;

/**
 * 机构域名映射服务
 *
 * @author bingwen.ai
 */
public interface OrganizationDomainService {
    /**
     * 新增机构域名
     *
     * @param organizationId 机构编号
     * @param domain         域名
     * @return true = 新增成功 ; false = 新增失败
     */
    boolean addOrganizationDomain(int organizationId, String domain);

    /**
     * 修改机构域名
     *
     * @param domainID 机构域名编号
     * @param domain   域名
     * @return true = 修改成功 ; false = 修改失败
     */
    boolean updateOrganizationDomain(int domainID, String domain);

    /**
     * 删除机构域名
     *
     * @param domainID 机构域名编号
     * @return true = 删除成功 ; false = 删除失败
     */
    boolean deleteOrganizationDomain(int domainID);

    /**
     * 列出机构所有与寂寞
     *
     * @param organizationID 机构编号
     * @return 域名列表
     */
    List<OrganizationDomain> listDomains(int organizationID);

    /**
     * 判断域名是否已指定机构
     *
     * @param domainURL 域名地址
     * @return true = 已存在 ； false = 不存在
     */
    boolean existDomain(String domainURL);

    /**
     * 判断域名是否已指定机构
     *
     * @param domainID 机构域名编号
     * @return true = 已存在 ； false = 不存在
     */
    boolean existDomain(int domainID);

    /**
     * 判断域名是否已指定机构
     *
     * @param domainURL      域名地址
     * @param organizationID 机构编号
     * @return true = 已存在 ； false = 不存在
     */
    boolean existDomain(int organizationID, String domainURL);

    /**
     * 根据请求域名获取对应的机构编号
     *
     * @param domainUrl 域名
     * @return 机构编号, 未查到数据时默认为0
     */
    int getOrganizationID(String domainUrl);
}
