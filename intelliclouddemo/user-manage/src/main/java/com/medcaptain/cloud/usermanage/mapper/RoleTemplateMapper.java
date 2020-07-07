package com.medcaptain.cloud.usermanage.mapper;

import com.medcaptain.cloud.usermanage.entity.RoleTemplate;
import com.medcaptain.cloud.usermanage.entity.RoleTemplateExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 角色模板SQL映射器
 *
 * @author bingwen.ai
 */
@Component
public interface RoleTemplateMapper {
    int countByExample(RoleTemplateExample example);

    int deleteByExample(RoleTemplateExample example);

    int deleteByPrimaryKey(Integer roleTemplateId);

    int insert(RoleTemplate record);

    int insertSelective(RoleTemplate record);

    List<RoleTemplate> selectByExample(RoleTemplateExample example);

    RoleTemplate selectByPrimaryKey(Integer roleTemplateId);

    int updateByExampleSelective(@Param("record") RoleTemplate record, @Param("example") RoleTemplateExample example);

    int updateByExample(@Param("record") RoleTemplate record, @Param("example") RoleTemplateExample example);

    int updateByPrimaryKeySelective(RoleTemplate record);

    int updateByPrimaryKey(RoleTemplate record);
}