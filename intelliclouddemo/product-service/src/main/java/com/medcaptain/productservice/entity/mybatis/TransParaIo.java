package com.medcaptain.productservice.entity.mybatis;

import java.util.Date;

public class TransParaIo extends TransParaIoKey {
    private Integer dataTypeId;

    private Integer tableId;

    private String paraName;

    private Date creatTime;

    private String paraDesc;

    private String remark;

    private Integer isDel;

    public Integer getDataTypeId() {
        return dataTypeId;
    }

    public void setDataTypeId(Integer dataTypeId) {
        this.dataTypeId = dataTypeId;
    }

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public String getParaName() {
        return paraName;
    }

    public void setParaName(String paraName) {
        this.paraName = paraName == null ? null : paraName.trim();
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public String getParaDesc() {
        return paraDesc;
    }

    public void setParaDesc(String paraDesc) {
        this.paraDesc = paraDesc == null ? null : paraDesc.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}