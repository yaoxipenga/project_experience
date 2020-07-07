package com.medcaptain.gateway.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

public class UserToken {

    private static final String EMPTY_STRING = "";

    private static final String ONLINE_STATUS = "在线";

    private String sessionID;

    private UserInfo userInfo;


    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @JSONField(serialize = false)
    public Integer getUserID() {
        return userInfo == null ? null : userInfo.getUserId();
    }

    @JSONField(serialize = false)
    public Integer getRoleID() {
        if (userInfo == null || userInfo.getUserId() == null) {
            return null;
        } else {
            return userInfo.getRoleId();
        }
    }

    @JSONField(serialize = false)
    public Integer getDepartmentID() {
        return userInfo == null ? null : userInfo.getDepartmentId();
    }

    @JSONField(serialize = false)
    public Integer getOrganizationID() {
        return userInfo == null ? null : userInfo.getOrganizationId();
    }

    @JSONField(serialize = false)
    public String getUserNickName() {
        return userInfo == null ? EMPTY_STRING : userInfo.getNickname();
    }

    @JSONField(serialize = false)
    public String getGender() {
        return userInfo == null ? EMPTY_STRING : userInfo.getGender();
    }

    @JSONField(serialize = false)
    public String getRoleName() {
        return userInfo == null ? EMPTY_STRING : userInfo.getRoleName();
    }

    @JSONField(serialize = false)
    public String getDepartmentName() {
        return userInfo == null ? EMPTY_STRING : userInfo.getDepartmentName();
    }

    @JSONField(serialize = false)
    public String getOrganizationName() {
        return userInfo == null ? EMPTY_STRING : userInfo.getOrganizationName();
    }

    @JSONField(serialize = false)
    public String getUserType() {
        return userInfo == null ? EMPTY_STRING : userInfo.getUserType();
    }

    @JSONField(serialize = false)
    public String getStatus() {
        return ONLINE_STATUS;
    }

    public String toJSON() {
        return JSON.toJSONString(this);
    }

    public static UserToken parseFromJSON(String userTokenJSON) {
        try {
            return JSON.parseObject(userTokenJSON, UserToken.class);
        } catch (Exception ex) {
            return null;
        }
    }
}
