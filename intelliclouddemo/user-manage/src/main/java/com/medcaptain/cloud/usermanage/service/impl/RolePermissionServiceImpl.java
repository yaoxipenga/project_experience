package com.medcaptain.cloud.usermanage.service.impl;

import com.medcaptain.cloud.usermanage.entity.RolePermission;
import com.medcaptain.cloud.usermanage.entity.RolePermissionExample;
import com.medcaptain.cloud.usermanage.mapper.RolePermissionMapper;
import com.medcaptain.cloud.usermanage.redis.RedisService;
import com.medcaptain.cloud.usermanage.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色授权信息服务实现
 *
 * @author bingwen.ai
 */
@Service
public class RolePermissionServiceImpl implements RolePermissionService {

    @Autowired
    RolePermissionMapper rolePermissionMapper;

    @Autowired
    RedisService redisService;

    @Override
    public List<RolePermission> listPermissions(int roleID) {
        RolePermissionExample example = new RolePermissionExample();
        example.createCriteria().andRoleIdEqualTo(roleID);
        return rolePermissionMapper.selectByExample(example);
    }

    @Override
    public List<RolePermission> listAllPermissions() {
        RolePermissionExample example = new RolePermissionExample();
        return rolePermissionMapper.selectByExample(example);
    }

    @Override
    public List<RolePermission> getPermissionsByBackendResource(int backendResourceID) {
        RolePermissionExample example = new RolePermissionExample();
        example.createCriteria().andBackendResourceIdEqualTo(backendResourceID);
        return rolePermissionMapper.selectByExample(example);
    }

    @Override
    public List<RolePermission> getPermissionsByFrontendResource(int frontendResourceID) {
        RolePermissionExample example = new RolePermissionExample();
        example.createCriteria().andFrontendResourceIdEqualTo(frontendResourceID);
        return rolePermissionMapper.selectByExample(example);
    }

    @Override
    public List<RolePermission> listPermissionByBackendResource(String backendResourceURL, int requestType) {
        RolePermissionExample example = new RolePermissionExample();
        example.createCriteria().andResourceUrlEqualTo(backendResourceURL).andRequestTypeEqualTo((byte) requestType);
        return rolePermissionMapper.selectByExample(example);
    }
}
