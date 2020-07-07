package com.medcaptain.productservice.entity.mybatis;

import java.util.Date;

public class Translog {
    private Integer indexId;

    private Integer deviceTripleId;

    private Integer clinicBizType;

    private String postTopic;

    private Date creatTime;

    private String clinicBizContent;

    public Integer getIndexId() {
        return indexId;
    }

    public void setIndexId(Integer indexId) {
        this.indexId = indexId;
    }

    public Integer getDeviceTripleId() {
        return deviceTripleId;
    }

    public void setDeviceTripleId(Integer deviceTripleId) {
        this.deviceTripleId = deviceTripleId;
    }

    public Integer getClinicBizType() {
        return clinicBizType;
    }

    public void setClinicBizType(Integer clinicBizType) {
        this.clinicBizType = clinicBizType;
    }

    public String getPostTopic() {
        return postTopic;
    }

    public void setPostTopic(String postTopic) {
        this.postTopic = postTopic == null ? null : postTopic.trim();
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public String getClinicBizContent() {
        return clinicBizContent;
    }

    public void setClinicBizContent(String clinicBizContent) {
        this.clinicBizContent = clinicBizContent == null ? null : clinicBizContent.trim();
    }
}