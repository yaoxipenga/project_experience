package com.medcaptain.cloud.usermanage.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 用户信息
 *
 * @author bingwen.ai
 */
public class UserInfo {
    private Integer userId;

    private Integer roleId;

    private Integer departmentId;

    private String userName;

    private transient String password;

    /**
     * 在线状态：1 - 离线 ； 0 - 在线
     */
    @JSONField(serialize = false)
    private Integer status;

    @JSONField(serialize = false)
    private Byte isDeleted;

    @JSONField(serialize = false)
    private Byte isEnable;

    private String nickname;

    private String gender;

    @JSONField(serialize = false)
    private String icon;

    private String organizationName;

    private String departmentName;

    private String roleName;

    /**
     * 用户类型：0 - 平台 ； 1 - 医院
     */
    private String userType;

    private Integer organizationId;

    /**
     * 是否第三方厂商账户
     */
    private Byte isThirdPart;

    /**
     * 第三方账户失效事件
     */
    private Long invalidTime;

    private String email;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Byte getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Byte isEnable) {
        this.isEnable = isEnable;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public Byte getIsThirdPart() {
        return isThirdPart;
    }

    public void setIsThirdPart(Byte isThirdPart) {
        this.isThirdPart = isThirdPart;
    }

    public Long getInvalidTime() {
        return invalidTime;
    }

    public void setInvalidTime(Long invalidTime) {
        this.invalidTime = invalidTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
