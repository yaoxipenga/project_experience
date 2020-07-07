package com.medcaptain.cloud.usermanage.service;

import com.github.pagehelper.PageInfo;
import com.medcaptain.cloud.usermanage.entity.RoleTemplatePermission;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色模板授权服务
 *
 * @author bingwen.ai
 */
@Service
public interface RoleTemplatePermissionService {
    /**
     * 判断授权是否存在
     *
     * @param roleTemplateID     角色模板编号
     * @param frontendResourceID 前端资源编号
     * @return true = 存在 ; false = 不存在
     */
    boolean exist(int roleTemplateID, int frontendResourceID);

    /**
     * 判断授权是否存在
     *
     * @param permissionID 角色模板授权编号
     * @return true = 存在 ; false = 不存在
     */
    boolean exist(int permissionID);

    /**
     * 新增授权
     *
     * @param roleTemplateID         角色模板编号
     * @param frontendResourceIDList 前端资源编号数组
     * @return true = 新增成功 ; false = 新增失败
     */
    boolean addPermissions(int roleTemplateID, int[] frontendResourceIDList);


    /**
     * 删除角色模板授权
     *
     * @param permissionID 前端资源编号
     * @return true = 删除成功 ; false = 删除失败
     */
    boolean deletePermission(int permissionID);

    /**
     * 按角色模板编号删除授权
     *
     * @param roleTemplateID 角色模板编号
     * @return true = 删除成功 ; false = 删除失败
     */
    boolean deletePermissions(int roleTemplateID);

    /**
     * 分页查询角色模板授权列表
     *
     * @param roleTemplateID 角色模板编号
     * @param pageIndex      分页页面
     * @param pageSize       每页数量
     * @return 角色模板授权列表
     */
    PageInfo<RoleTemplatePermission> listPermissions(int roleTemplateID, int pageIndex, int pageSize);

    /**
     * 按角色模板类型获取权限列表
     *
     * @param roleTemplateID 角色模板编号
     * @return 角色模板授权列表
     */
    List<RoleTemplatePermission> listPermissions(int roleTemplateID);
}
