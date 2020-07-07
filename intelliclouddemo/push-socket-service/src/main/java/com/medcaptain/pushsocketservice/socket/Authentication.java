package com.medcaptain.pushsocketservice.socket;


import javax.security.auth.Subject;
import java.security.Principal;

public class Authentication implements Principal {

    private String name;

    private Integer userId;

    private Integer roleId;

    public Authentication(String name,Integer userId,Integer roleId) {
        this.name = name;
        this.userId = userId;
        this.roleId = roleId;
    }

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

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean implies(Subject subject) {
        return false;
    }
}
