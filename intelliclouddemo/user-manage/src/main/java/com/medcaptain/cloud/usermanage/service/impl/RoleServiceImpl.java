package com.medcaptain.cloud.usermanage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.medcaptain.cloud.usermanage.entity.RoleInfo;
import com.medcaptain.cloud.usermanage.entity.RoleInfoExample;
import com.medcaptain.cloud.usermanage.mapper.RoleInfoMapper;
import com.medcaptain.cloud.usermanage.service.RoleService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色操作
 *
 * @author yangzhixiong
 */
@Service
public class RoleServiceImpl implements RoleService {
    private static Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    RoleInfoMapper roleInfoMapper;

    @Override
    public PageInfo<RoleInfo> listRoles(Integer page, Integer perPage, String roleName, int organizationID, int departmentID) {
        PageHelper.startPage(page, perPage);
        Map<String, String> params = new HashMap<>(6);
        params.put("isDeleted", "0");
        params.put("isEnable", "1");
        if (organizationID > 0) {
            params.put("organizationId", Integer.toString(organizationID));
        }
        if (departmentID > 0) {
            params.put("departmentId", Integer.toString(departmentID));
        }
        if (StringUtils.isNotEmpty(roleName)) {
            params.put("roleName", roleName);
        }
        List<RoleInfo> roleInfoList = roleInfoMapper.listRoles(params);
        PageInfo<RoleInfo> roleInfos = new PageInfo<RoleInfo>(roleInfoList);
        return roleInfos;
    }

    @Override
    public RoleInfo getRole(Integer roleId) {
        Map<String, String> params = new HashMap<>(1);
        params.put("roleId", roleId.toString());
        List<RoleInfo> roleInfoList = roleInfoMapper.listRoles(params);
        if (roleInfoList != null && roleInfoList.size() > 0) {
            return roleInfoList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Integer updateRole(RoleInfo roleInfo) {
        return roleInfoMapper.updateByPrimaryKeySelective(roleInfo);
    }

    @Override
    public Integer addRole(RoleInfo roleInfo) {
        return roleInfoMapper.insertSelective(roleInfo);
    }

    @Override
    public Integer deleteRole(Integer roleId) {
        RoleInfo roleInfo = new RoleInfo();
        roleInfo.setRoleId(roleId);
        roleInfo.setIsDeleted((byte) 1);
        return roleInfoMapper.updateByPrimaryKeySelective(roleInfo);
    }

    @Override
    public boolean exist(int roleID) {
        RoleInfoExample roleInfoExample = new RoleInfoExample();
        roleInfoExample.createCriteria().andRoleIdEqualTo(roleID);
        return roleInfoMapper.countByExample(roleInfoExample) > 0;
    }

    @Override
    public boolean exist(int departmentID, String roleName) {
        RoleInfoExample example = new RoleInfoExample();
        example.createCriteria().andRoleNameEqualTo(roleName).andDepartmentIdEqualTo(departmentID);
        return roleInfoMapper.countByExample(example) > 0;
    }

    @Override
    public RoleInfo getRoleInfo(int departmentID, String roleName) {
        RoleInfoExample example = new RoleInfoExample();
        example.createCriteria().andRoleNameEqualTo(roleName).andDepartmentIdEqualTo(departmentID);
        List<RoleInfo> roleInfoList = roleInfoMapper.selectByExample(example);
        if (roleInfoList != null && roleInfoList.size() > 0) {
            return roleInfoList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public PageInfo<RoleInfo> listRolesHasPermission(Integer page, Integer perPage, int frontendResourceID, int organizationID) {
        PageHelper.startPage(page, perPage);
        Map<String, String> params = new HashMap<>(2);
        if (organizationID > 0) {
            params.put("organizationID", String.valueOf(organizationID));
        }
        params.put("frontendResourceID", String.valueOf(frontendResourceID));
        List<RoleInfo> roleInfoList = roleInfoMapper.listRolesHasPermission(params);
        PageInfo<RoleInfo> roleInfos = new PageInfo<RoleInfo>(roleInfoList);
        return roleInfos;
    }
}
