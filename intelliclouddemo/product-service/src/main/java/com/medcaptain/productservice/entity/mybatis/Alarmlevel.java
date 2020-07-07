package com.medcaptain.productservice.entity.mybatis;

public class Alarmlevel {
    private Integer alarmLevelId;

    private String alarmLevelDesc;

    public Integer getAlarmLevelId() {
        return alarmLevelId;
    }

    public void setAlarmLevelId(Integer alarmLevelId) {
        this.alarmLevelId = alarmLevelId;
    }

    public String getAlarmLevelDesc() {
        return alarmLevelDesc;
    }

    public void setAlarmLevelDesc(String alarmLevelDesc) {
        this.alarmLevelDesc = alarmLevelDesc == null ? null : alarmLevelDesc.trim();
    }
}