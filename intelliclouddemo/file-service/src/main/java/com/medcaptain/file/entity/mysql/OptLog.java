package com.medcaptain.file.entity.mysql;

import java.util.Date;

public class OptLog {
    private Integer optlogId;

    private Integer userId;

    private Integer deptId;

    private Integer hospitalId;

    private Integer optType;

    private Date creattime;

    private String logContent;

    public Integer getOptlogId() {
        return optlogId;
    }

    public void setOptlogId(Integer optlogId) {
        this.optlogId = optlogId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Integer hospitalId) {
        this.hospitalId = hospitalId;
    }

    public Integer getOptType() {
        return optType;
    }

    public void setOptType(Integer optType) {
        this.optType = optType;
    }

    public Date getCreattime() {
        return creattime;
    }

    public void setCreattime(Date creattime) {
        this.creattime = creattime;
    }

    public String getLogContent() {
        return logContent;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent == null ? null : logContent.trim();
    }
}