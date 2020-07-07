package com.medcaptain.productservice.entity.mybatis;

import java.util.Date;

public class Tablefuncperm {
    private Integer funcPermId;

    private String funcName;

    private Integer tableId;

    private Integer maskCode;

    private String remark;

    private Date creatTime;

    private Date modifTime;

    public Integer getFuncPermId() {
        return funcPermId;
    }

    public void setFuncPermId(Integer funcPermId) {
        this.funcPermId = funcPermId;
    }

    public String getFuncName() {
        return funcName;
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName == null ? null : funcName.trim();
    }

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public Integer getMaskCode() {
        return maskCode;
    }

    public void setMaskCode(Integer maskCode) {
        this.maskCode = maskCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public Date getModifTime() {
        return modifTime;
    }

    public void setModifTime(Date modifTime) {
        this.modifTime = modifTime;
    }
}