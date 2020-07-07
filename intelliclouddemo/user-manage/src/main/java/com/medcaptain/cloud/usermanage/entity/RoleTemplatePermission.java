package com.medcaptain.cloud.usermanage.entity;

/**
 * 角色模板授权实体
 *
 * @author bingwen.ai
 */
public class RoleTemplatePermission {
    private Integer permissionId;

    private Integer roleTemplateId;

    private Integer frontendResourceId;

    private String frontendResourceName;

    private Integer frontendResourceType;

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public Integer getRoleTemplateId() {
        return roleTemplateId;
    }

    public void setRoleTemplateId(Integer roleTemplateId) {
        this.roleTemplateId = roleTemplateId;
    }

    public Integer getFrontendResourceId() {
        return frontendResourceId;
    }

    public void setFrontendResourceId(Integer frontendResourceId) {
        this.frontendResourceId = frontendResourceId;
    }

    public String getFrontendResourceName() {
        return frontendResourceName;
    }

    public void setFrontendResourceName(String frontendResourceName) {
        this.frontendResourceName = frontendResourceName;
    }

    public Integer getFrontendResourceType() {
        return frontendResourceType;
    }

    public void setFrontendResourceType(Integer frontendResourceType) {
        this.frontendResourceType = frontendResourceType;
    }
}