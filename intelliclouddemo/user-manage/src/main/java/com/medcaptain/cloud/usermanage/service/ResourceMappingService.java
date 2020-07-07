package com.medcaptain.cloud.usermanage.service;

import com.medcaptain.cloud.usermanage.entity.ResourceMapping;

import java.util.List;

/**
 * 前后端资源映射信息服务
 *
 * @author bingwen.ai
 */
public interface ResourceMappingService {
    boolean addMapping(int frontendResourceID, int backendResourceID);

    boolean exist(int frontendResourceID, int backendResourceID);

    boolean deleteMapping(int resourceMappingID);

    boolean deleteMapping(int backendResourceID, int frontendResourceID);

    ResourceMapping getMapping(int resourceMappingID);

    List<ResourceMapping> listResourceMappings(int frontendResourceID);

    boolean deleteByBackendResource(int backendResourceID);

    boolean deleteByFrontendResource(int frontendResourceID);
}
