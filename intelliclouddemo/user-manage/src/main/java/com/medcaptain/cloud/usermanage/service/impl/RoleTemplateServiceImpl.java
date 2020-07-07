package com.medcaptain.cloud.usermanage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.medcaptain.cloud.usermanage.entity.RoleTemplate;
import com.medcaptain.cloud.usermanage.entity.RoleTemplateExample;
import com.medcaptain.cloud.usermanage.mapper.RoleTemplateMapper;
import com.medcaptain.cloud.usermanage.service.RoleTemplatePermissionService;
import com.medcaptain.cloud.usermanage.service.RoleTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色模板服务实现
 *
 * @author bingwen.ai
 */
@Service
public class RoleTemplateServiceImpl implements RoleTemplateService {
    @Autowired
    RoleTemplateMapper roleTemplateMapper;

    @Autowired
    RoleTemplatePermissionService roleTemplatePermissionService;

    @Override
    public boolean existTemplate(String templateName, String templateType) {
        RoleTemplateExample example = new RoleTemplateExample();
        example.createCriteria().andIsDeletedEqualTo((byte) 0).andIsEnableEqualTo((byte) 1)
                .andTemplateNameEqualTo(templateName).andTemplateTypeEqualTo(templateType);
        return roleTemplateMapper.countByExample(example) > 0;
    }

    @Override
    public boolean existTemplate(String templateType) {
        RoleTemplateExample example = new RoleTemplateExample();
        example.createCriteria().andIsDeletedEqualTo((byte) 0).andIsEnableEqualTo((byte) 1)
                .andTemplateTypeEqualTo(templateType);
        return roleTemplateMapper.countByExample(example) > 0;
    }

    @Override
    public boolean addTemplate(String templateName, String templateType, String remark) {
        RoleTemplate roleTemplate = new RoleTemplate();
        roleTemplate.setTemplateName(templateName);
        roleTemplate.setTemplateType(templateType);
        roleTemplate.setRemark(remark);
        return roleTemplateMapper.insertSelective(roleTemplate) > 0;
    }

    @Override
    public boolean existTemplate(int templateID) {
        RoleTemplateExample example = new RoleTemplateExample();
        example.createCriteria().andRoleTemplateIdEqualTo(templateID);
        return roleTemplateMapper.countByExample(example) > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteTemplate(int templateID) {
        RoleTemplate roleTemplate = new RoleTemplate();
        roleTemplate.setRoleTemplateId(templateID);
        roleTemplate.setIsDeleted((byte) 1);
        return roleTemplateMapper.updateByPrimaryKeySelective(roleTemplate) > 0
                && roleTemplatePermissionService.deletePermissions(templateID);
    }

    @Override
    public boolean updateTemplate(RoleTemplate roleTemplate) {
        return roleTemplateMapper.updateByPrimaryKeySelective(roleTemplate) > 0;
    }

    @Override
    public PageInfo<RoleTemplate> listTemplates(int pageIndex, int pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        RoleTemplateExample example = new RoleTemplateExample();
        example.createCriteria().andIsDeletedEqualTo((byte) 0);
        example.setOrderByClause(" role_template_id asc");
        List<RoleTemplate> roleTemplateList = roleTemplateMapper.selectByExample(example);
        return new PageInfo<>(roleTemplateList);
    }

    @Override
    public RoleTemplate getTemplate(String roleTemplateType) {
        RoleTemplateExample example = new RoleTemplateExample();
        example.createCriteria().andIsDeletedEqualTo((byte) 0).andIsEnableEqualTo((byte) 1)
                .andTemplateTypeEqualTo(roleTemplateType);
        List<RoleTemplate> templateList = roleTemplateMapper.selectByExample(example);
        if (templateList != null && templateList.size() > 0) {
            return templateList.get(0);
        } else {
            return null;
        }
    }
}
