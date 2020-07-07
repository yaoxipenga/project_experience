package com.medcaptain.productservice.entity.mybatis;

import java.util.Date;

public class Tablelist {
    private Integer tableId;

    private String tableName;

    private Integer userId;

    private Date creatTime;

    private Date modifTime;

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName == null ? null : tableName.trim();
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

    public Date getModifTime() {
        return modifTime;
    }

    public void setModifTime(Date modifTime) {
        this.modifTime = modifTime;
    }
}