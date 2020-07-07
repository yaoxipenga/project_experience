package com.medcaptain.cloud.usermanage.entity;

/**
 * 部门模板
 *
 * @author bingwen.ai
 */
public class DepartmentTemplate {
    private Integer departmentTemplateId;

    private String departmentName;

    private String remark;

    public Integer getDepartmentTemplateId() {
        return departmentTemplateId;
    }

    public void setDepartmentTemplateId(Integer departmentTemplateId) {
        this.departmentTemplateId = departmentTemplateId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName == null ? null : departmentName.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}
