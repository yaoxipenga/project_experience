package com.medcaptain.cloud.usermanage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.medcaptain.cloud.usermanage.entity.Department;
import com.medcaptain.cloud.usermanage.entity.DepartmentExample;
import com.medcaptain.cloud.usermanage.entity.RoleInfo;
import com.medcaptain.cloud.usermanage.entity.RoleInfoExample;
import com.medcaptain.cloud.usermanage.mapper.DepartmentMapper;
import com.medcaptain.cloud.usermanage.mapper.RoleInfoMapper;
import com.medcaptain.cloud.usermanage.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 部门信息服务实现
 *
 * @author bingwen.ai
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {
    private static Logger log = LoggerFactory.getLogger(DepartmentServiceImpl.class);

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    RoleInfoMapper roleInfoMapper;

    @Override
    public PageInfo<Department> listDepartments(int pageIndex, int pageSize, int organizationID) {
        PageHelper.startPage(pageIndex, pageSize);
        DepartmentExample example = new DepartmentExample();
        DepartmentExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeletedEqualTo((byte) 0);
        if (organizationID > 0) {
            criteria.andOrganizationIdEqualTo(organizationID);
        }
        example.setOrderByClause(" organization_id,department_id asc");
        List<Department> departments = departmentMapper.selectByExample(example);
        return new PageInfo<>(departments);
    }

    @Override
    public Department getDepartment(int departmentID) {
        return departmentMapper.selectByPrimaryKey(departmentID);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addDepartment(String departmentName, int organizationID, String[] roleList) {
        Department department = new Department();
        department.setDepartmentName(departmentName);
        department.setOrganizationId(organizationID);
        departmentMapper.insertSelective(department);

        department = getDepartment(departmentName, organizationID);
        if (department != null && roleList != null) {
            for (int i = 0; i < roleList.length; i++) {
                RoleInfo roleInfo = new RoleInfo();
                roleInfo.setDepartmentId(department.getDepartmentId());
                roleInfo.setRoleName(roleList[i]);
                roleInfo.setOrganizationId(organizationID);
                roleInfoMapper.insertSelective(roleInfo);
            }
        }
        return true;
    }

    @Override
    public Department getDepartment(String departmentName, int organizationID) {
        DepartmentExample example = new DepartmentExample();
        example.createCriteria().andDepartmentNameEqualTo(departmentName).andOrganizationIdEqualTo(organizationID);
        List<Department> departmentList = departmentMapper.selectByExample(example);
        if (departmentList.size() > 0) {
            return departmentList.get(0);
        }
        return null;
    }

    @Override
    public int updateDepartment(Department department) {
        return departmentMapper.updateByPrimaryKeySelective(department);
    }

    @Override
    public int deleteDepartment(int departmentID) {
        Department department = new Department();
        department.setDepartmentId(departmentID);
        department.setIsDeleted((byte) 1);
        return departmentMapper.updateByPrimaryKeySelective(department);
    }

    @Override
    public boolean exist(int departmentID) {
        DepartmentExample departmentExample = new DepartmentExample();
        departmentExample.createCriteria().andDepartmentIdEqualTo(departmentID);
        return departmentMapper.countByExample(departmentExample) > 0;
    }

    @Override
    public boolean exist(String departmentName, int organizationID) {
        DepartmentExample example = new DepartmentExample();
        example.createCriteria().andDepartmentNameEqualTo(departmentName).andOrganizationIdEqualTo(organizationID);
        return departmentMapper.countByExample(example) > 0;
    }

    @Override
    public boolean inUse(int departmentId) {
        RoleInfoExample example = new RoleInfoExample();
        example.createCriteria().andIsDeletedEqualTo((byte) 0).andIsEnableEqualTo((byte) 1).andDepartmentIdEqualTo(departmentId);
        return roleInfoMapper.countByExample(example) > 0;
    }

    @Override
    public boolean hasDepartment(int organizationID) {
        DepartmentExample example = new DepartmentExample();
        example.createCriteria().andOrganizationIdEqualTo(organizationID).andIsDeletedEqualTo((byte) 0)
                .andIsEnableEqualTo((byte) 1);
        return departmentMapper.countByExample(example) > 0;
    }
}
