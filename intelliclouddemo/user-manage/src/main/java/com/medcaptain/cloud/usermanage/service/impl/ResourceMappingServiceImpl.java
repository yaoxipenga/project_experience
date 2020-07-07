package com.medcaptain.cloud.usermanage.service.impl;

import com.medcaptain.cloud.usermanage.entity.ResourceMapping;
import com.medcaptain.cloud.usermanage.entity.ResourceMappingExample;
import com.medcaptain.cloud.usermanage.mapper.ResourceMappingMapper;
import com.medcaptain.cloud.usermanage.redis.RedisService;
import com.medcaptain.cloud.usermanage.service.ResourceMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 前后端资源映射信息服务实现
 *
 * @author bingwen.ai
 */
@Service
public class ResourceMappingServiceImpl implements ResourceMappingService {
    @Autowired
    ResourceMappingMapper resourceMappingMapper;

    @Autowired
    RedisService redisService;


    @Override
    public boolean addMapping(int frontendResourceID, int backendResourceID) {
        ResourceMapping resourceMapping = new ResourceMapping();
        resourceMapping.setFrontendResourceId(frontendResourceID);
        resourceMapping.setBackendResourceId(backendResourceID);
        int effectedRows = resourceMappingMapper.insertSelective(resourceMapping);
        if (effectedRows > 0) {
            redisService.addResourceMapping(frontendResourceID, backendResourceID);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean exist(int frontendResourceID, int backendResourceID) {
        ResourceMappingExample example = new ResourceMappingExample();
        example.createCriteria().andBackendResourceIdEqualTo(backendResourceID).andFrontendResourceIdEqualTo(frontendResourceID);
        return resourceMappingMapper.countByExample(example) > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteMapping(int resourceMappingID) {
        ResourceMapping resourceMapping = getMapping(resourceMappingID);
        if (resourceMapping != null) {
            redisService.removeResourceMapping(resourceMapping.getFrontendResourceId(), resourceMapping.getBackendResourceId());
        }
        return resourceMappingMapper.deleteByPrimaryKey(resourceMappingID) > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteMapping(int backendResourceID, int frontendResourceID) {
        //移除权限缓存
        redisService.removeResourceMapping(frontendResourceID, backendResourceID);
        ResourceMappingExample example = new ResourceMappingExample();
        example.createCriteria().andBackendResourceIdEqualTo(backendResourceID).andFrontendResourceIdEqualTo(frontendResourceID);
        return resourceMappingMapper.deleteByExample(example) > 0;
    }

    @Override
    public ResourceMapping getMapping(int resourceMappingID) {
        return resourceMappingMapper.selectByPrimaryKey(resourceMappingID);
    }

    @Override
    public List<ResourceMapping> listResourceMappings(int frontendResourceID) {
        return resourceMappingMapper.selectByFrontendResourceID(frontendResourceID);
    }

    @Override
    public boolean deleteByBackendResource(int backendResourceID) {
        ResourceMappingExample example = new ResourceMappingExample();
        example.createCriteria().andBackendResourceIdEqualTo(backendResourceID);
        resourceMappingMapper.deleteByExample(example);
        return true;
    }

    @Override
    public boolean deleteByFrontendResource(int frontendResourceID) {
        ResourceMappingExample example = new ResourceMappingExample();
        example.createCriteria().andFrontendResourceIdEqualTo(frontendResourceID);
        resourceMappingMapper.deleteByExample(example);
        return true;
    }
}
