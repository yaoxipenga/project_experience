package com.medcaptain.cloud.usermanage.service;

import com.github.pagehelper.PageInfo;
import com.medcaptain.cloud.usermanage.entity.RoleInfo;

import java.util.List;

/**
 * 角色信息服务
 *
 * @author bingwen.ai
 */
public interface RoleService {

    /**
     * 获取角色列表
     *
     * @param page           第几页  从0开始
     * @param perPage        一页多少条
     * @param roleName       搜索角色名称
     * @param organizationID 机构编号
     * @param departmentID   部门编号
     * @return 角色列表
     */
    PageInfo<RoleInfo> listRoles(Integer page, Integer perPage, String roleName, int organizationID, int departmentID);

    /**
     * 获取一个角色信息
     *
     * @param roleId 角色编号
     * @return 角色信息
     */
    RoleInfo getRole(Integer roleId);

    /**
     * 修改角色信息
     * 改接口不修改敏感信息，如删除字段，启用禁用字段  等 数据
     *
     * @param roleInfo 需要修改的角色实体，更新实体主键修改
     * @return 是否修改成功
     */
    Integer updateRole(RoleInfo roleInfo);

    /**
     * 新增角色
     *
     * @param roleInfo 需要添加的角色实体
     * @return 是否添加成功
     */
    Integer addRole(RoleInfo roleInfo);

    /**
     * 根据roleId删除角色
     *
     * @param roleId 角色id
     * @return 删除的id
     */
    Integer deleteRole(Integer roleId);

    /**
     * 按编号判断角色是否存在
     *
     * @param roleID 角色编号
     * @return true = 存在 ；false = 角色不存在
     */
    boolean exist(int roleID);

    /**
     * 按名称判断角色是否存在
     *
     * @param departmentID 部门编号
     * @param roleName     角色名称
     * @return true = 存在 ；false = 角色不存在
     */
    boolean exist(int departmentID, String roleName);

    /**
     * 按名称获取角色详情
     *
     * @param departmentID 部门编号
     * @param roleName     角色名称
     * @return 角色详情
     */
    RoleInfo getRoleInfo(int departmentID, String roleName);

    /**
     * 获取拥有前端资源权限的角色列表
     *
     * @param page               页码
     * @param perPage            每页数量
     * @param frontendResourceID 前端资源编号
     * @param organizationID     机构编号
     * @return 角色列表
     */
    PageInfo<RoleInfo> listRolesHasPermission(Integer page, Integer perPage, int frontendResourceID, int organizationID);
}
