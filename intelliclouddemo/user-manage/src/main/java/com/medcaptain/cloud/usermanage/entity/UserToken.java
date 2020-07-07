package com.medcaptain.cloud.usermanage.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 用户token信息
 *
 * @author bingwen.ai
 */

public class UserToken {

    private static final String EMPTY_STRING = "";

    private static final String ONLINE_STATUS = "在线";

    private String clientID;

    private UserInfo userInfo;

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @JSONField(serialize = false)
    public int getUserID() {
        return userInfo == null ? 0 : userInfo.getUserId();
    }

    @JSONField(serialize = false)
    public int getRoleID() {
        if (userInfo == null || userInfo.getUserId() == null) {
            return 0;
        } else {
            return userInfo.getRoleId();
        }
    }

    @JSONField(serialize = false)
    public int getDepartmentID() {
        return userInfo == null ? 0 : userInfo.getDepartmentId();
    }

    @JSONField(serialize = false)
    public int getOrganizationID() {
        return userInfo == null ? 0 : userInfo.getOrganizationId();
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
