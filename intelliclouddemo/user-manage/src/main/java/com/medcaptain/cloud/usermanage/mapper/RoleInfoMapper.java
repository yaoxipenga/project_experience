package com.medcaptain.cloud.usermanage.mapper;

import com.medcaptain.cloud.usermanage.entity.RoleInfo;
import com.medcaptain.cloud.usermanage.entity.RoleInfoExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 角色信息数据库操作映射
 *
 * @author bingwen.ai
 */

@Component
public interface RoleInfoMapper {
    int countByExample(RoleInfoExample example);

    int deleteByExample(RoleInfoExample example);

    int deleteByPrimaryKey(Integer roleId);

    int insert(RoleInfo record);

    int insertSelective(RoleInfo record);

    List<RoleInfo> selectByExample(RoleInfoExample example);

    RoleInfo selectByPrimaryKey(Integer roleId);

    int updateByExampleSelective(@Param("record") RoleInfo record, @Param("example") RoleInfoExample example);

    int updateByExample(@Param("record") RoleInfo record, @Param("example") RoleInfoExample example);

    int updateByPrimaryKeySelective(RoleInfo record);

    int updateByPrimaryKey(RoleInfo record);

    List<RoleInfo> listRoles(Map<String, String> map);

    List<RoleInfo> listRolesHasPermission(Map<String, String> map);
}
