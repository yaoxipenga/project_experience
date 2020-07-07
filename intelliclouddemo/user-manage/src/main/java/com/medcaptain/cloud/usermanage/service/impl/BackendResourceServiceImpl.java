package com.medcaptain.cloud.usermanage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.medcaptain.cloud.usermanage.entity.BackendResource;
import com.medcaptain.cloud.usermanage.entity.BackendResourceExample;
import com.medcaptain.cloud.usermanage.mapper.BackendResourceMapper;
import com.medcaptain.cloud.usermanage.service.BackendResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 后端资源信息服务实现
 *
 * @author bingwen.ai
 */
@Service
public class BackendResourceServiceImpl implements BackendResourceService {

    @Autowired
    BackendResourceMapper backendResourceMapper;

    @Override
    public boolean addResource(BackendResource resource) {
        return backendResourceMapper.insertSelective(resource) > 0;
    }

    @Override
    public boolean exist(String resourceName) {
        BackendResourceExample example = new BackendResourceExample();
        example.createCriteria().andResourceNameEqualTo(resourceName);
        return backendResourceMapper.countByExample(example) > 0;
    }

    @Override
    public boolean exist(int backendResourceID) {
        BackendResourceExample example = new BackendResourceExample();
        example.createCriteria().andBackendResourceIdEqualTo(backendResourceID);
        return backendResourceMapper.countByExample(example) > 0;
    }

    @Override
    public boolean delete(int backendResourceID) {
        return backendResourceMapper.deleteByPrimaryKey(backendResourceID) > 0;
    }

    @Override
    public boolean updateResource(BackendResource resource) {
        return backendResourceMapper.updateByPrimaryKeySelective(resource) > 0;
    }

    @Override
    public BackendResource getResource(int resourceID) {
        return backendResourceMapper.selectByPrimaryKey(resourceID);
    }

    @Override
    public List<BackendResource> getResources(int parentResourceID) {
        return null;
    }

    @Override
    public PageInfo<BackendResource> listResources(int pageIndex, int pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        BackendResourceExample example = new BackendResourceExample();
        example.setOrderByClause(" backend_resource_id asc");
        List<BackendResource> backendResourceList = backendResourceMapper.selectByExample(example);
        return new PageInfo<>(backendResourceList);
    }
}
