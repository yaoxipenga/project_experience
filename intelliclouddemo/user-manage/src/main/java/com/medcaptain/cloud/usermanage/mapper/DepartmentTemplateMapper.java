package com.medcaptain.cloud.usermanage.mapper;

import com.medcaptain.cloud.usermanage.entity.DepartmentTemplate;
import com.medcaptain.cloud.usermanage.entity.DepartmentTemplateExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 部门模板信息数据库操作映射
 *
 * @author bingwen.ai
 */

@Component
public interface DepartmentTemplateMapper {
    int countByExample(DepartmentTemplateExample example);

    int deleteByExample(DepartmentTemplateExample example);

    int deleteByPrimaryKey(Integer departmentTemplateId);

    int insert(DepartmentTemplate record);

    int insertSelective(DepartmentTemplate record);

    List<DepartmentTemplate> selectByExample(DepartmentTemplateExample example);

    DepartmentTemplate selectByPrimaryKey(Integer departmentTemplateId);

    int updateByExampleSelective(@Param("record") DepartmentTemplate record,
                                 @Param("example") DepartmentTemplateExample example);

    int updateByExample(@Param("record") DepartmentTemplate record,
                        @Param("example") DepartmentTemplateExample example);

    int updateByPrimaryKeySelective(DepartmentTemplate record);

    int updateByPrimaryKey(DepartmentTemplate record);
}
