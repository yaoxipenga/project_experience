package com.medcaptain.cloud.usermanage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.medcaptain.cloud.usermanage.entity.FrontendResource;
import com.medcaptain.cloud.usermanage.entity.FrontendResourceExample;
import com.medcaptain.cloud.usermanage.mapper.FrontendResourceMapper;
import com.medcaptain.cloud.usermanage.service.FrontendResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 前端授权信息服务实现
 *
 * @author bingwen.ai
 */
@Service
public class FrontendResourceServiceImpl implements FrontendResourceService {

    @Autowired
    FrontendResourceMapper frontendResourceMapper;

    @Override
    public boolean addResource(FrontendResource resource) {
        return frontendResourceMapper.insertSelective(resource) > 0;
    }

    @Override
    public boolean exist(String resourceName, byte resourceType) {
        FrontendResourceExample example = new FrontendResourceExample();
        example.createCriteria().andResourceNameEqualTo(resourceName).andResourceTypeEqualTo(resourceType);
        return frontendResourceMapper.countByExample(example) > 0;
    }

    @Override
    public boolean exist(int frontendResourceID, boolean isEnable) {
        FrontendResourceExample example = new FrontendResourceExample();
        FrontendResourceExample.Criteria criteria = example.createCriteria();
        criteria = criteria.andFrontendResourceIdEqualTo(frontendResourceID);
        if (isEnable) {
            criteria = criteria.andIsEnableEqualTo((byte) 1);
        }
        return frontendResourceMapper.countByExample(example) > 0;
    }

    @Override
    public boolean delete(int frontendResourceID) {
        return frontendResourceMapper.deleteByPrimaryKey(frontendResourceID) > 0;
    }

    @Override
    public boolean updateResource(FrontendResource resource) {
        return frontendResourceMapper.updateByPrimaryKeySelective(resource) > 0;
    }

    @Override
    public FrontendResource getResource(int resourceID) {
        return frontendResourceMapper.selectByPrimaryKey(resourceID);
    }

    @Override
    public PageInfo<FrontendResource> listResources(int pageIndex, int pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        FrontendResourceExample example = new FrontendResourceExample();
        example.setOrderByClause(" resource_type desc,frontend_resource_id asc");
        return new PageInfo<>(frontendResourceMapper.selectByExample(example));
    }

    @Override
    public FrontendResource getResourceByName(String resourceName, byte resourceType) {
        FrontendResourceExample example = new FrontendResourceExample();
        example.createCriteria().andResourceNameEqualTo(resourceName).andIsEnableEqualTo((byte) 1)
                .andResourceTypeEqualTo(resourceType);
        List<FrontendResource> resourceList = frontendResourceMapper.selectByExample(example);
        if (resourceList.size() == 1) {
            return resourceList.get(0);
        } else {
            return null;
        }
    }
}
