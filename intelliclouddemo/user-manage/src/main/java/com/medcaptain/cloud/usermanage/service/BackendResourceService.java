package com.medcaptain.cloud.usermanage.service;

import com.github.pagehelper.PageInfo;
import com.medcaptain.cloud.usermanage.entity.BackendResource;

import java.util.List;

/**
 * 后端资源信息服务
 *
 * @author bingwen.ai
 */
public interface BackendResourceService {

    boolean addResource(BackendResource resource);

    boolean exist(String resourceName);

    boolean exist(int backendResourceID);

    boolean delete(int backendResourceID);

    boolean updateResource(BackendResource resource);

    BackendResource getResource(int resourceID);

    List<BackendResource> getResources(int parentResourceID);

    PageInfo<BackendResource> listResources(int pageIndex, int pageSize);
}
