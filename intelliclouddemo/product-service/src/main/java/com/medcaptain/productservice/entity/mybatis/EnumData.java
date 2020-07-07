package com.medcaptain.productservice.entity.mybatis;

import java.util.Date;

public class EnumData extends EnumKey {
    private String enumDesc;

    private String paraIdentifier;

    private Integer ioType;

    private Integer userId;

    private Date createTime;

    public String getEnumDesc() {
        return enumDesc;
    }

    public void setEnumDesc(String enumDesc) {
        this.enumDesc = enumDesc == null ? null : enumDesc.trim();
    }

    public String getParaIdentifier() {
        return paraIdentifier;
    }

    public void setParaIdentifier(String paraIdentifier) {
        this.paraIdentifier = paraIdentifier == null ? null : paraIdentifier.trim();
    }

    public Integer getIoType() {
        return ioType;
    }

    public void setIoType(Integer ioType) {
        this.ioType = ioType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}