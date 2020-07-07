package com.medcaptain.productservice.entity.mybatis;

import java.util.Date;

public class DeviceDepartmentRelated extends DeviceDepartmentRelatedKey {
    private Date gmtModified;

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}