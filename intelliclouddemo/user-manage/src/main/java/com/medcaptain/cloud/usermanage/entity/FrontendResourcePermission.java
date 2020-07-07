package com.medcaptain.cloud.usermanage.entity;

/**
 * 角色前端资源授权信息
 *
 * @author bingwen.ai
 */
public class FrontendResourcePermission {
    private Integer permissionId;

    private Integer roleId;

    private Integer frontendResourceId;

    private String frontendResourceName;

    private int parentResourceID;

    private int resourceType;

    private String menuURL;

    private String menuIcon;

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
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

    public int getParentResourceID() {
        return parentResourceID;
    }

    public void setParentResourceID(int parentResourceID) {
        this.parentResourceID = parentResourceID;
    }

    public String getMenuURL() {
        return menuURL;
    }

    public void setMenuURL(String menuURL) {
        this.menuURL = menuURL;
    }

    public int getResourceType() {
        return resourceType;
    }

    public void setResourceType(int resourceType) {
        this.resourceType = resourceType;
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }
}
