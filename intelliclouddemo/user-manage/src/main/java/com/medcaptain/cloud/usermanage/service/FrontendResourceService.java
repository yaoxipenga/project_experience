package com.medcaptain.cloud.usermanage.service;

import com.github.pagehelper.PageInfo;
import com.medcaptain.cloud.usermanage.entity.FrontendResource;

import java.util.List;

/**
 * 前端资源信息服务
 *
 * @author bingwen.ai
 */
public interface FrontendResourceService {
    boolean addResource(FrontendResource resource);

    boolean exist(String resourceName, byte resourceType);

    /**
     * 查询指定ID的资源是否存在
     *
     * @param frontendResourceID 前端资源编号
     * @param isEnable           是否可用
     * @return true = 存在 ; false = 不存在
     */
    boolean exist(int frontendResourceID, boolean isEnable);

    boolean delete(int frontendResourceID);

    boolean updateResource(FrontendResource resource);

    FrontendResource getResource(int resourceID);

    FrontendResource getResourceByName(String resourceName, byte resourceType);

    PageInfo<FrontendResource> listResources(int pageIndex, int pageSize);
}
