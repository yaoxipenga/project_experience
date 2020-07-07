package com.medcaptain.productservice.entity.mybatis;

import java.util.Date;

public class ArrayData {
    private Integer indexId;

    private Integer tableId;

    private Integer dataTypeId;

    private Integer userId;

    private Date creatTime;

    public Integer getIndexId() {
        return indexId;
    }

    public void setIndexId(Integer indexId) {
        this.indexId = indexId;
    }

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public Integer getDataTypeId() {
        return dataTypeId;
    }

    public void setDataTypeId(Integer dataTypeId) {
        this.dataTypeId = dataTypeId;
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
}