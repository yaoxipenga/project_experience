package com.medcaptain.cloud.usermanage.mapper;

import com.medcaptain.cloud.usermanage.entity.FrontendResourcePermission;
import com.medcaptain.cloud.usermanage.entity.FrontendResourcePermissionExample;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 前端资源授权数据库操作映射
 *
 * @author bingwen.ai
 */

@Component
public interface FrontendResourcePermissionMapper {
    int countByExample(FrontendResourcePermissionExample example);

    int deleteByExample(FrontendResourcePermissionExample example);

    int deleteByPrimaryKey(Integer permissionId);

    int insert(FrontendResourcePermission record);

    int insertSelective(FrontendResourcePermission record);

    List<FrontendResourcePermission> selectByExample(FrontendResourcePermissionExample example);

    List<FrontendResourcePermission> listPermissions(Map<String, String> params);

    FrontendResourcePermission selectByPrimaryKey(Integer permissionId);

    int updateByExampleSelective(@Param("record") FrontendResourcePermission record,
                                 @Param("example") FrontendResourcePermissionExample example);

    int updateByExample(@Param("record") FrontendResourcePermission record,
                        @Param("example") FrontendResourcePermissionExample example);

    int updateByPrimaryKeySelective(FrontendResourcePermission record);

    int updateByPrimaryKey(FrontendResourcePermission record);
}
