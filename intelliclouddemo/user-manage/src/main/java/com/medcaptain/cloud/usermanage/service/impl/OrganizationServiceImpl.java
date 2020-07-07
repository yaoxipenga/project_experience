package com.medcaptain.cloud.usermanage.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.medcaptain.cloud.usermanage.entity.*;

import com.medcaptain.cloud.usermanage.mapper.DepartmentTemplateMapper;
import com.medcaptain.cloud.usermanage.mapper.OrganizationMapper;
import com.medcaptain.cloud.usermanage.redis.RedisService;
import com.medcaptain.cloud.usermanage.service.*;
import com.medcaptain.utils.UUIDUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 机构信息服务实现
 *
 * @author bingwen.ai
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {
    private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    OrganizationMapper organizationMapper;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    RoleService roleService;

    @Autowired
    RoleTemplateService roleTemplateService;

    @Autowired
    RoleTemplatePermissionService roleTemplatePermissionService;

    @Autowired
    FrontendResourcePermissionService frontendResourcePermissionService;

    @Autowired
    DepartmentTemplateMapper departmentTemplateMapper;

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    private static final String COMMA = ",";

    @Override
    public PageInfo<Organization> listOrganizations(int pageIndex, int pageSize, int organizationType, String provinceName,
                                                    int regionCode, String organizationName, String orderBy) {
        PageHelper.startPage(pageIndex, pageSize);
        Map<String, Object> params = new HashMap<>(2);
        if (StringUtils.isNotBlank(provinceName)) {
            List<String> provinceNames = new ArrayList<>();
            if (provinceName.contains(COMMA)) {
                provinceNames = Arrays.asList(provinceName.split(COMMA));
            } else {
                provinceNames.add(provinceName);
            }
            params.put("provinceNames", provinceNames);
        }
        if (regionCode > 0) {
            params.put("regionCode", String.valueOf(regionCode));
        }
        if (StringUtils.isNotEmpty(organizationName)) {
            params.put("organizationName", organizationName);
        }
        if (organizationType >= 0) {
            params.put("organizationType", Integer.toString(organizationType));
        }
        params.put("orderBy", orderBy);
        List<Organization> organizations = organizationMapper.listOrganizations(params);
        return new PageInfo<>(organizations);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addOrganization(String organizationName, int organizationLevel, int organizationType,
                                   int regionCode, int createUserID, String remark, String managerEmail, int[] departmentList)
            throws IllegalArgumentException {
        Organization organization = new Organization();
        organization.setOrganizationName(organizationName);
        organization.setOrganizationLevel(organizationLevel);
        organization.setRemark(remark);
        organization.setRegionId(regionCode);
        organization.setCreateUserId(createUserID);
        organization.setOrganizationType(organizationType);
        organizationMapper.insertSelective(organization);
        Organization newOrganization = getOrganization(organizationName);
        if (newOrganization == null) {
            throw new IllegalArgumentException("新增机构失败");
        }
        Department managerDepartment = null;
        if (departmentList != null && departmentList.length > 0) {
            for (int departmentTemplateID : departmentList) {
                DepartmentTemplate departmentTemplate = departmentTemplateMapper.selectByPrimaryKey(departmentTemplateID);
                if (departmentTemplate == null) {
                    throw new IllegalArgumentException("部门模板不存在");
                }

                String departmentName = departmentTemplate.getDepartmentName();
                int organizationID = newOrganization.getOrganizationId();
                departmentService.addDepartment(departmentName, organizationID, null);

                if ("机构管理".equals(departmentName)) {
                    managerDepartment = departmentService.getDepartment(departmentName, organizationID);
                }
            }
        }
        // 添加默认管理员
        return addDefaultOrganizationManager(managerDepartment, newOrganization, managerEmail);
    }

    /**
     * 添加管理员默认部门并分配权限
     *
     * @param managerDepartment 管理员所在科室，默认为"机构管理",前端未传入时另外新增
     * @param newOrganization   新增的机构信息
     */
    private boolean addDefaultOrganizationManager(Department managerDepartment, Organization newOrganization, String managerEmail) {
        managerDepartment = addDefaultManageDepartment(newOrganization, managerDepartment);
        // 生成管理员角色，并分配权限
        if (managerDepartment != null) {
            RoleInfo managerRole = addManageRole(managerDepartment);
            addDefaultRolePermission(managerRole, newOrganization.getOrganizationType());
            return addDefaultManageUser(managerRole, managerEmail);
        }
        return false;
    }

    /**
     * 添加默认管理员用户
     *
     * @param manageRole 管理员角色
     * @return true = 添加成功 ； false = 添加失败
     */
    private boolean addDefaultManageUser(RoleInfo manageRole, String managerEmail) {
        if (manageRole != null) {
            try {
                String userName = "admin";
                // 下面的密码故意让用户无法登录，首次登录前需要用户重置密码
                String password = "InitializePassword";
                int roleID = manageRole.getRoleId();
                int departmentID = manageRole.getDepartmentId();
                String nickname = "系统管理员";
                return userService.addUser(roleID, departmentID, userName, password, nickname, null, null, managerEmail);
            } catch (Exception ex){
                return false;
            }
        }
        return false;
    }

    /**
     * 新增管理员默认部门
     *
     * @param newOrganization   新增机构
     * @param managerDepartment 管理员默认部门，未传入时新增
     * @return 管理员部门
     */
    private Department addDefaultManageDepartment(Organization newOrganization, Department managerDepartment) {
        // 没有默认管理部门则添加
        if (managerDepartment == null) {
            String departmentName = "机构管理";
            int organizationID = newOrganization.getOrganizationId();
            departmentService.addDepartment(departmentName, organizationID, null);
            managerDepartment = departmentService.getDepartment(departmentName, organizationID);
        }
        return managerDepartment;
    }

    /**
     * 新增默认管理员角色
     *
     * @param manageDepartment 管理员所在科室
     * @return 新增的管理员信息
     */
    private RoleInfo addManageRole(Department manageDepartment) {
        RoleInfo managerRole = new RoleInfo();
        managerRole.setOrganizationId(manageDepartment.getOrganizationId());
        managerRole.setRoleName("机构管理员");
        managerRole.setDepartmentId(manageDepartment.getDepartmentId());
        managerRole.setRemark("默认机构管理员角色");
        if (roleService.addRole(managerRole) > 0) {
            return roleService.getRoleInfo(manageDepartment.getDepartmentId(), "机构管理员");
        } else {
            return null;
        }
    }

    /**
     * 为新增的管理员角色分配权限
     * <p>根据新增的机构类型(医院/厂商)获取角色模板中的权限，复制到新增的管理员角色</p>
     *
     * @param manageRole       管理员角色
     * @param organizationType 新增的机构类型
     */
    private void addDefaultRolePermission(RoleInfo manageRole, int organizationType) {
        if (manageRole != null) {
            RoleTemplate roleTemplate = null;
            if (organizationType == 0) {
                roleTemplate = roleTemplateService.getTemplate("医院");
            } else if (organizationType == 1) {
                roleTemplate = roleTemplateService.getTemplate("厂商");
            } else {
                return;
            }
            if (manageRole != null && roleTemplate != null) {
                List<RoleTemplatePermission> roleTemplatePermissionList = roleTemplatePermissionService.listPermissions(roleTemplate.getRoleTemplateId());
                for (RoleTemplatePermission roleTemplatePermission : roleTemplatePermissionList) {
                    int frontendResourceID = roleTemplatePermission.getFrontendResourceId();
                    int roleID = manageRole.getRoleId();
                    frontendResourcePermissionService.addMapping(frontendResourceID, roleID);
                }
            }
        }
    }

    @Override
    public Organization getOrganization(String organizationName) {
        OrganizationExample example = new OrganizationExample();
        example.createCriteria().andOrganizationNameEqualTo(organizationName);
        List<Organization> organizations = organizationMapper.selectByExample(example);
        if (organizations != null && organizations.size() > 0) {
            return organizations.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Organization getOrganization(int organizationID) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("organizationID", Integer.toString(organizationID));
        List<Organization> organizations = organizationMapper.listOrganizations(params);
        if (organizations.size() > 0) {
            return organizations.get(0);
        } else {
            return null;
        }
    }

    @Override
    public int updateOrganization(Organization organization) {
        return organizationMapper.updateByPrimaryKeySelective(organization);
    }

    @Override
    public int deleteOrganization(int organizationID) {
        Organization organization = new Organization();
        organization.setOrganizationId(organizationID);
        organization.setIsDeleted((byte) 1);
        return organizationMapper.updateByPrimaryKeySelective(organization);
    }

    @Override
    public boolean exist(String organizationName) {
        OrganizationExample example = new OrganizationExample();
        example.createCriteria().andOrganizationNameEqualTo(organizationName);
        return organizationMapper.countByExample(example) > 0;
    }

    @Override
    public boolean exist(int organizationID) {
        OrganizationExample example = new OrganizationExample();
        example.createCriteria().andOrganizationIdEqualTo(organizationID);
        return organizationMapper.countByExample(example) > 0;
    }

    @Override
    public boolean inUse(int organizationId) {
        return departmentService.hasDepartment(organizationId);
    }
}
