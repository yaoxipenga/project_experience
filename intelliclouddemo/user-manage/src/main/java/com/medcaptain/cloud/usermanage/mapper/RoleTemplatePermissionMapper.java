package com.medcaptain.cloud.usermanage.mapper;

import com.medcaptain.cloud.usermanage.entity.RoleTemplatePermission;
import com.medcaptain.cloud.usermanage.entity.RoleTemplatePermissionExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 角色模板授权SQL映射器
 *
 * @author bingwen.ai
 */
@Component
public interface RoleTemplatePermissionMapper {
    int countByExample(RoleTemplatePermissionExample example);

    int deleteByExample(RoleTemplatePermissionExample example);

    int deleteByPrimaryKey(Integer permissionId);

    int insert(RoleTemplatePermission record);

    int insertSelective(RoleTemplatePermission record);

    List<RoleTemplatePermission> selectByExample(RoleTemplatePermissionExample example);

    RoleTemplatePermission selectByPrimaryKey(Integer permissionId);

    int updateByExampleSelective(@Param("record") RoleTemplatePermission record, @Param("example") RoleTemplatePermissionExample example);

    int updateByExample(@Param("record") RoleTemplatePermission record, @Param("example") RoleTemplatePermissionExample example);

    int updateByPrimaryKeySelective(RoleTemplatePermission record);

    int updateByPrimaryKey(RoleTemplatePermission record);

    List<RoleTemplatePermission> listPermissions(int roleTemplateID);
}