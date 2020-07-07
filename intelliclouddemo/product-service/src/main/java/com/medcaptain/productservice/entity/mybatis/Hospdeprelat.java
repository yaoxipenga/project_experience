package com.medcaptain.productservice.entity.mybatis;

public class Hospdeprelat {
    private Integer hospiDeptRelatId;

    private Integer hospitalId;

    private Integer deptId;

    private String remark;

    public Integer getHospiDeptRelatId() {
        return hospiDeptRelatId;
    }

    public void setHospiDeptRelatId(Integer hospiDeptRelatId) {
        this.hospiDeptRelatId = hospiDeptRelatId;
    }

    public Integer getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Integer hospitalId) {
        this.hospitalId = hospitalId;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}