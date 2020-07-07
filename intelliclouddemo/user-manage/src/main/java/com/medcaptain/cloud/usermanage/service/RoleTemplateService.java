package com.medcaptain.cloud.usermanage.service;

import com.github.pagehelper.PageInfo;
import com.medcaptain.cloud.usermanage.entity.RoleTemplate;

/**
 * 角色模板服务
 *
 * @author bingwen.ai
 */
public interface RoleTemplateService {
    /**
     * 是否存在已生效的角色模板
     *
     * @param templateName 角色模板名称
     * @param templateType 角色模板类型
     * @return true = 存在 ; false = 不存在
     */
    boolean existTemplate(String templateName, String templateType);

    /**
     * 是否存在已生效的角色模板
     * <p>同一类型的只允许有一个生效</p>
     *
     * @param templateType 角色模板类型
     * @return true = 存在 ; false = 不存在
     */
    boolean existTemplate(String templateType);

    /**
     * 是否存在已生效的角色模板
     * <p>同一类型的只允许有一个生效</p>
     *
     * @param templateID 角色模板编号
     * @return true = 存在 ; false = 不存在
     */
    boolean existTemplate(int templateID);

    /**
     * 新增角色模板
     *
     * @param templateName 角色模板名称
     * @param templateType 角色模板类型
     * @param remark       备注信息
     * @return true = 新增成功 ; false = 新增失败
     */
    boolean addTemplate(String templateName, String templateType, String remark);

    /**
     * 删除角色模板
     *
     * @param templateID 角色模板标识号
     * @return true = 删除成功 ; false = 删除失败
     */
    boolean deleteTemplate(int templateID);

    /**
     * 更新角色模板信息
     *
     * @param roleTemplate 角色模板
     * @return true = 更新成功 ; false = 更新失败
     */
    boolean updateTemplate(RoleTemplate roleTemplate);

    /**
     * 获取角色模板列表
     *
     * @param pageIndex 分页页码
     * @param pageSize  每页最大数目
     * @return 角色模板列表
     */
    PageInfo<RoleTemplate> listTemplates(int pageIndex, int pageSize);

    /**
     * 按类型获取角色模板
     *
     * @param roleTemplateType 角色模板类型
     * @return 角色模板
     */
    RoleTemplate getTemplate(String roleTemplateType);
}
