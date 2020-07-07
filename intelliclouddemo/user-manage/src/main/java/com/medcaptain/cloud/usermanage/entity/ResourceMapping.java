package com.medcaptain.cloud.usermanage.entity;

/**
 * 前后端资源映射关系
 *
 * @author bingwen.ai
 */
public class ResourceMapping {
    private Integer resourceMappingId;

    private Integer backendResourceId;

    private Integer frontendResourceId;

    private BackendResource backendResource;

    public Integer getResourceMappingId() {
        return resourceMappingId;
    }

    public void setResourceMappingId(Integer resourceMappingId) {
        this.resourceMappingId = resourceMappingId;
    }

    public Integer getBackendResourceId() {
        return backendResourceId;
    }

    public void setBackendResourceId(Integer backendResourceId) {
        this.backendResourceId = backendResourceId;
    }

    public Integer getFrontendResourceId() {
        return frontendResourceId;
    }

    public void setFrontendResourceId(Integer frontendResourceId) {
        this.frontendResourceId = frontendResourceId;
    }

    public BackendResource getBackendResource() {
        return backendResource;
    }

    public void setBackendResource(BackendResource backendResource) {
        this.backendResource = backendResource;
    }
}
