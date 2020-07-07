package com.medcaptain.cloud.usermanage.service.impl;

import com.medcaptain.cloud.usermanage.constant.FrontendResourceType;
import com.medcaptain.cloud.usermanage.entity.FrontendResourcePermission;
import com.medcaptain.cloud.usermanage.entity.FrontendResourcePermissionExample;
import com.medcaptain.cloud.usermanage.mapper.FrontendResourcePermissionMapper;
import com.medcaptain.cloud.usermanage.redis.RedisService;
import com.medcaptain.cloud.usermanage.service.FrontendResourcePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 前端资源授权信息服务实现
 *
 * @author bingwen.ai
 */
@Service
public class FrontendResourcePermissionServiceImpl implements FrontendResourcePermissionService {

    @Autowired
    FrontendResourcePermissionMapper frontendResourcePermissionMapper;

    @Autowired
    RedisService redisService;

    @Override
    public boolean addMapping(int frontendResourceID, int roleID) {
        FrontendResourcePermission permission = new FrontendResourcePermission();
        permission.setFrontendResourceId(frontendResourceID);
        permission.setRoleId(roleID);
        int effectedRows = frontendResourcePermissionMapper.insertSelective(permission);
        if (effectedRows > 0) {
            redisService.addFrontendResourcePermission(roleID, frontendResourceID);
            return true;
        } else {
            return false;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addMappings(List<Integer> frontendResourceIDList, int roleID) {
        boolean insertSuccess = true;
        for (int frontendResourceID : frontendResourceIDList) {
            insertSuccess = insertSuccess && addMapping(frontendResourceID, roleID);
        }
        return insertSuccess;
    }

    @Override
    public boolean exist(int frontendResourceID, int roleID) {
        FrontendResourcePermissionExample example = new FrontendResourcePermissionExample();
        example.createCriteria().andFrontendResourceIdEqualTo(frontendResourceID).andRoleIdEqualTo(roleID);
        return frontendResourcePermissionMapper.countByExample(example) > 0;
    }

    @Override
    public boolean exist(int permissionID) {
        FrontendResourcePermissionExample example = new FrontendResourcePermissionExample();
        example.createCriteria().andPermissionIdEqualTo(permissionID);
        return frontendResourcePermissionMapper.countByExample(example) > 0;
    }

    @Override
    public boolean deleteMapping(int permissionID) {
        FrontendResourcePermission frontendResoourcePermission = getPermission(permissionID);
        if (frontendResoourcePermission != null) {
            redisService.removeFrontendResourcePermission(frontendResoourcePermission.getRoleId(),
                    frontendResoourcePermission.getFrontendResourceId());
        }
        return frontendResourcePermissionMapper.deleteByPrimaryKey(permissionID) > 0;
    }

    @Override
    public boolean deleteMapping(int frontendResourceID, int roleID) {
        redisService.removeFrontendResourcePermission(roleID, frontendResourceID);
        FrontendResourcePermissionExample example = new FrontendResourcePermissionExample();
        example.createCriteria().andFrontendResourceIdEqualTo(frontendResourceID).andRoleIdEqualTo(roleID);
        return frontendResourcePermissionMapper.deleteByExample(example) > 0;
    }

    @Override
    public FrontendResourcePermission getPermission(int permissionID) {
        Map<String, String> params = new HashMap<>(1);
        params.put("permissionId", Integer.toString(permissionID));
        List<FrontendResourcePermission> permissions = frontendResourcePermissionMapper.listPermissions(params);
        if (permissions.size() > 0) {
            return permissions.get(0);
        } else {
            return null;
        }
    }

    @Override
    public FrontendResourcePermission getPermission(int frontendResourceID, int roleID) {
        FrontendResourcePermissionExample example = new FrontendResourcePermissionExample();
        example.createCriteria().andFrontendResourceIdEqualTo(frontendResourceID)
                .andRoleIdEqualTo(roleID);
        List<FrontendResourcePermission> permissionList = frontendResourcePermissionMapper.selectByExample(example);
        return permissionList != null && permissionList.size() > 0 ? permissionList.get(0) : null;
    }

    @Override
    public List<FrontendResourcePermission> listPermissions(int roleID, FrontendResourceType resourceType) {
        Map<String, String> params = new HashMap<>(2);
        params.put("roleId", Integer.toString(roleID));
        if (resourceType.ordinal() > 0) {
            params.put("resourceType", Integer.toString(resourceType.ordinal()));
        }
        return frontendResourcePermissionMapper.listPermissions(params);
    }
}
