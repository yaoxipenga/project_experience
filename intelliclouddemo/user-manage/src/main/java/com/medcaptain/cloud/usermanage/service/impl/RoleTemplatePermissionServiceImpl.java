package com.medcaptain.cloud.usermanage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.medcaptain.cloud.usermanage.entity.RoleTemplatePermission;
import com.medcaptain.cloud.usermanage.entity.RoleTemplatePermissionExample;
import com.medcaptain.cloud.usermanage.mapper.RoleTemplatePermissionMapper;
import com.medcaptain.cloud.usermanage.service.RoleTemplatePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色模板授权服务实现类
 *
 * @author bingwen.ai
 */
@Service
public class RoleTemplatePermissionServiceImpl implements RoleTemplatePermissionService {
    @Autowired
    RoleTemplatePermissionMapper roleTemplatePermissionMapper;

    @Override
    public boolean exist(int roleTemplateID, int frontendResourceID) {
        RoleTemplatePermissionExample example = new RoleTemplatePermissionExample();
        example.createCriteria().andFrontendResourceIdEqualTo(frontendResourceID).andRoleTemplateIdEqualTo(roleTemplateID);
        return roleTemplatePermissionMapper.countByExample(example) > 0;
    }

    @Override
    public boolean exist(int permissionID) {
        RoleTemplatePermissionExample example = new RoleTemplatePermissionExample();
        example.createCriteria().andPermissionIdEqualTo(permissionID);
        return roleTemplatePermissionMapper.countByExample(example) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addPermissions(int roleTemplateID, int[] frontendResourceIDList) {
        boolean insertSuccess = true;
        for (int frontendResourceID : frontendResourceIDList) {
            RoleTemplatePermission permission = new RoleTemplatePermission();
            permission.setFrontendResourceId(frontendResourceID);
            permission.setRoleTemplateId(roleTemplateID);
            insertSuccess = insertSuccess && roleTemplatePermissionMapper.insertSelective(permission) > 0;
        }
        return insertSuccess;
    }

    @Override
    public boolean deletePermission(int permissionID) {
        return roleTemplatePermissionMapper.deleteByPrimaryKey(permissionID) > 0;
    }

    @Override
    public boolean deletePermissions(int roleTemplateID) {
        RoleTemplatePermissionExample example = new RoleTemplatePermissionExample();
        example.createCriteria().andRoleTemplateIdEqualTo(roleTemplateID);
        return roleTemplatePermissionMapper.deleteByExample(example) >= 0;
    }

    @Override
    public PageInfo<RoleTemplatePermission> listPermissions(int roleTemplateID, int pageIndex, int pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        List<RoleTemplatePermission> permissionList = listPermissions(roleTemplateID);
        return new PageInfo<>(permissionList);
    }

    @Override
    public List<RoleTemplatePermission> listPermissions(int roleTemplateID) {
        return roleTemplatePermissionMapper.listPermissions(roleTemplateID);
    }
}
