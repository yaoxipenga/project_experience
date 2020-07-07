package com.medcaptain.cloud.usermanage.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import static com.medcaptain.constant.TimeConstant.SECONDS_ONE_MINUTE;

/**
 * 用户管理服务相关配置
 *
 * @author bingwen.ai
 */
@Component
@ConfigurationProperties(prefix = "user-manage")
public class UserManageProperties {

    private long userTokenExpireSeconds = 30 * SECONDS_ONE_MINUTE;

    private long loginFailExpireSeconds = 2 * SECONDS_ONE_MINUTE;

    private long captchaExpireSeconds = 5 * SECONDS_ONE_MINUTE;

    private long rolePermissionExpireDays = 7;

    private long thirdPartUserTokenExpireSeconds = 120 * SECONDS_ONE_MINUTE;

    private long userResetPasswordTokenExpireSeconds = 30 * SECONDS_ONE_MINUTE;

    private String webDomain = "http://localhost:8750";

    public long getUserTokenExpireSeconds() {
        return userTokenExpireSeconds;
    }

    public void setUserTokenExpireSeconds(long userTokenExpireSeconds) {
        this.userTokenExpireSeconds = userTokenExpireSeconds;
    }

    public long getLoginFailExpireSeconds() {
        return loginFailExpireSeconds;
    }

    public void setLoginFailExpireSeconds(long loginFailExpireSeconds) {
        this.loginFailExpireSeconds = loginFailExpireSeconds;
    }

    public long getCaptchaExpireSeconds() {
        return captchaExpireSeconds;
    }

    public void setCaptchaExpireSeconds(long captchaExpireSeconds) {
        this.captchaExpireSeconds = captchaExpireSeconds;
    }

    public long getRolePermissionExpireDays() {
        return rolePermissionExpireDays;
    }

    public void setRolePermissionExpireDays(long rolePermissionExpireDays) {
        this.rolePermissionExpireDays = rolePermissionExpireDays;
    }

    public long getThirdPartUserTokenExpireSeconds() {
        return thirdPartUserTokenExpireSeconds;
    }

    public void setThirdPartUserTokenExpireSeconds(long thirdPartUserTokenExpireSeconds) {
        this.thirdPartUserTokenExpireSeconds = thirdPartUserTokenExpireSeconds;
    }

    public long getUserResetPasswordTokenExpireSeconds() {
        return userResetPasswordTokenExpireSeconds;
    }

    public void setUserResetPasswordTokenExpireSeconds(long userResetPasswordTokenExpireSeconds) {
        this.userResetPasswordTokenExpireSeconds = userResetPasswordTokenExpireSeconds;
    }

    public String getWebDomain() {
        return webDomain;
    }

    public void setWebDomain(String webDomain) {
        this.webDomain = webDomain;
    }
}
