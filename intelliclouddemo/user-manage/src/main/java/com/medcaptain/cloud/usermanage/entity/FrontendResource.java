package com.medcaptain.cloud.usermanage.entity;

/**
 * 前端资源
 * <p>
 * 包含：菜单、前端资源、操作权限、页面按钮、页面链接
 *
 * @author bingwen.ai
 */
public class FrontendResource {
    private Integer frontendResourceId;

    private String resourceName;

    private String elementId;

    /**
     * 资源类型： 1 = 菜单 ； 2 = 前端资源 ； 3 = 功能权限 ；4 = 页面按钮 ； 5 = 页面链接
     */
    private Byte resourceType;

    private String remark;

    private Integer parentResourceId;

    private Byte isEnable;

    private String menuUrl;

    private String menuIcon;

    public Integer getFrontendResourceId() {
        return frontendResourceId;
    }

    public void setFrontendResourceId(Integer frontendResourceId) {
        this.frontendResourceId = frontendResourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName == null ? null : resourceName.trim();
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getParentResourceId() {
        return parentResourceId;
    }

    public void setParentResourceId(Integer parentResourceId) {
        this.parentResourceId = parentResourceId;
    }

    public Byte getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Byte isEnable) {
        this.isEnable = isEnable;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl == null ? null : menuUrl.trim();
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon == null ? null : menuIcon.trim();
    }
}