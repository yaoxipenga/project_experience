package com.medcaptain.cloud.usermanage.entity;

/**
 * 机构域名信息实体
 *
 * @author bingwen.ai
 */
public class OrganizationDomain {
    private Integer domainId;

    private String domainUrl;

    private Integer organizationId;

    public Integer getDomainId() {
        return domainId;
    }

    public void setDomainId(Integer domainId) {
        this.domainId = domainId;
    }

    public String getDomainUrl() {
        return domainUrl;
    }

    public void setDomainUrl(String domainUrl) {
        this.domainUrl = domainUrl == null ? null : domainUrl.trim();
    }

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }
}