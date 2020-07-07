package com.medcaptain.cloud.usermanage.entity;

/**
 * 后端资源
 *
 * @author bingwen.ai
 */
public class BackendResource {
    private Integer backendResourceId;

    private String resourceName;

    private String resourceUrl;

    private Byte requestType;

    private String remark;

    private Byte isEnable;

    public Integer getBackendResourceId() {
        return backendResourceId;
    }

    public void setBackendResourceId(Integer backendResourceId) {
        this.backendResourceId = backendResourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName == null ? null : resourceName.trim();
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl == null ? null : resourceUrl.trim();
    }

    public Byte getRequestType() {
        return requestType;
    }

    public void setRequestType(Byte requestType) {
        this.requestType = requestType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Byte getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Byte isEnable) {
        this.isEnable = isEnable;
    }
}
