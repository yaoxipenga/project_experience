package com.medcaptain.cloud.usermanage.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.medcaptain.cloud.usermanage.entity.DepartmentTemplate;
import com.medcaptain.cloud.usermanage.entity.DepartmentTemplateExample;
import com.medcaptain.cloud.usermanage.mapper.DepartmentTemplateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 部门模板信息服务
 *
 * @author bingwen.ai
 */
@Component
public class DepartmentTemplateService {

    @Autowired
    private DepartmentTemplateMapper departmentTemplateMapper;

    public boolean addTemplate(DepartmentTemplate departmentTemplate) {
        return departmentTemplateMapper.insert(departmentTemplate) == 1;
    }

    public boolean exist(String departmentName) {
        DepartmentTemplateExample example = new DepartmentTemplateExample();
        example.createCriteria().andDepartmentNameEqualTo(departmentName);
        return departmentTemplateMapper.countByExample(example) > 0;
    }

    public PageInfo<DepartmentTemplate> listDepartmentTemplates(int pageIndex, int pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        DepartmentTemplateExample example = new DepartmentTemplateExample();
        example.setOrderByClause(" department_template_id asc");
        List<DepartmentTemplate> departmentTemplateList = departmentTemplateMapper.selectByExample(example);
        return new PageInfo<>(departmentTemplateList);
    }

    public DepartmentTemplate selectByTemplateID(int departmentTemplateID) {
        return departmentTemplateMapper.selectByPrimaryKey(departmentTemplateID);
    }

    public boolean deleteByTemplate(int departmentTemplateID) {
        return departmentTemplateMapper.deleteByPrimaryKey(departmentTemplateID) > 0;
    }

    public boolean updateTemplate(DepartmentTemplate departmentTemplate) {
        return departmentTemplateMapper.updateByPrimaryKeySelective(departmentTemplate) > 0;
    }


}
