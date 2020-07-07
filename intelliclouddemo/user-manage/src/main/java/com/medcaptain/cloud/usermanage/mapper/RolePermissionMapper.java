package com.medcaptain.cloud.usermanage.mapper;

import com.medcaptain.cloud.usermanage.entity.RolePermission;
import com.medcaptain.cloud.usermanage.entity.RolePermissionExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 角色授权数据库操作映射
 *
 * @author bingwen.ai
 */
@Component
public interface RolePermissionMapper {
    int countByExample(RolePermissionExample example);

    int deleteByExample(RolePermissionExample example);

    int insert(RolePermission record);

    int insertSelective(RolePermission record);

    List<RolePermission> selectByExample(RolePermissionExample example);

    int updateByExampleSelective(@Param("record") RolePermission record, @Param("example") RolePermissionExample example);

    int updateByExample(@Param("record") RolePermission record, @Param("example") RolePermissionExample example);
}
