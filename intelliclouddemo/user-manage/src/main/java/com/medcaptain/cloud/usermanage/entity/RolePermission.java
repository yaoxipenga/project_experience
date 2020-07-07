package com.medcaptain.cloud.usermanage.entity;

/**
 * 角色授权信息
 *
 * @author bingwen.ai
 */
public class RolePermission {
    private Integer roleId;

    private String roleName;

    private Byte roleEnable;

    private Byte roleDeleted;

    private Integer frontendResourceId;

    private String frontendResourceName;

    private String elementId;

    private Byte resourceType;

    private String frontendRemark;

    private Byte frontendEnable;

    private Integer backendResourceId;

    private String backendResourceName;

    private String resourceUrl;

    private Byte requestType;

    private String backendRemark;

    private Byte backendEnable;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public Byte getRoleEnable() {
        return roleEnable;
    }

    public void setRoleEnable(Byte roleEnable) {
        this.roleEnable = roleEnable;
    }

    public Byte getRoleDeleted() {
        return roleDeleted;
    }

    public void setRoleDeleted(Byte roleDeleted) {
        this.roleDeleted = roleDeleted;
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
        this.frontendResourceName = frontendResourceName == null ? null : frontendResourceName.trim();
    }

    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId == null ? null : elementId.trim();
    }

    public Byte getResourceType() {
        return resourceType;
    }

    public void setResourceType(Byte resourceType) {
        this.resourceType = resourceType;
    }

    public String getFrontendRemark() {
        return frontendRemark;
    }

    public void setFrontendRemark(String frontendRemark) {
        this.frontendRemark = frontendRemark == null ? null : frontendRemark.trim();
    }

    public Byte getFrontendEnable() {
        return frontendEnable;
    }

    public void setFrontendEnable(Byte frontendEnable) {
        this.frontendEnable = frontendEnable;
    }

    public Integer getBackendResourceId() {
        return backendResourceId;
    }

    public void setBackendResourceId(Integer backendResourceId) {
        this.backendResourceId = backendResourceId;
    }

    public String getBackendResourceName() {
        return backendResourceName;
    }

    public void setBackendResourceName(String backendResourceName) {
        this.backendResourceName = backendResourceName == null ? null : backendResourceName.trim();
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl == null ? null : resourceUrl.trim();
    }

    public Byte getRequestType() {
        return requestType;
    }

    public void setRequestType(Byte requestType) {
        this.requestType = requestType;
    }

    public String getBackendRemark() {
        return backendRemark;
    }

    public void setBackendRemark(String backendRemark) {
        this.backendRemark = backendRemark == null ? null : backendRemark.trim();
    }

    public Byte getBackendEnable() {
        return backendEnable;
    }

    public void setBackendEnable(Byte backendEnable) {
        this.backendEnable = backendEnable;
    }
}
