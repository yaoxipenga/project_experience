package com.medcaptain.productservice.entity.mybatis;

import java.util.Date;

public class Deviceinfo {
    /**
     * 设备三元组id
     */
    private Integer deviceTripleId;

    /**
     * 角色ID （管理员赋予这个角色拥有给其他角色权限的权限）
     */
    private Integer roleId;
    /**
     * 产品Key
     */
    private String productKey;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 设备密钥
     */
    private String deviceSecret;
    /**
     * 设备序列号
     */
    private String deviceSN;
    /**
     * 设备版本号
     */
    private String deviceVersion;
    /**
     *  设备别名
     */
    private String deviceAlias;
    /**
     * 所属机构
     */
    private Integer organizationId;

    /**
     * 是否在线 0不在线1在线
     */
    private Boolean isOnline;
    /**
     * 是否激活（0未激活  1已激活）
     */
    private Boolean isEnabled;

    /**
     * 部门id
     */
    private Integer departmentId;

    /**
     * 设备负责人名称
     */
    private String manager;
    /**
     * 激活时间
     */
    private Date actTime;

    /**
     * 创建者
     */
    private Integer userId;

    /**
     * 创建时间
     */
    private Date creatTime;

    /**
     * 是否删除
     */
    private Integer isDel;

    /**
     * 备注
     */
    private String remark;

    public Integer getDeviceTripleId() {
        return deviceTripleId;
    }

    public void setDeviceTripleId(Integer deviceTripleId) {
        this.deviceTripleId = deviceTripleId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey == null ? null : productKey.trim();
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName == null ? null : deviceName.trim();
    }

    public String getDeviceSecret() {
        return deviceSecret;
    }

    public void setDeviceSecret(String deviceSecret) {
        this.deviceSecret = deviceSecret == null ? null : deviceSecret.trim();
    }

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public Boolean getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Boolean isOnline) {
        this.isOnline = isOnline;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public Date getActTime() {
        return actTime;
    }

    public void setActTime(Date actTime) {
        this.actTime = actTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getDeviceSN() {
        return deviceSN;
    }

    public void setDeviceSN(String deviceSN) {
        this.deviceSN = deviceSN;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getDeviceVersion() {
        return deviceVersion;
    }

    public void setDeviceVersion(String deviceVersion) {
        this.deviceVersion = deviceVersion;
    }

    public String getDeviceAlias() {
        return deviceAlias;
    }

    public void setDeviceAlias(String deviceAlias) {
        this.deviceAlias = deviceAlias;
    }
}