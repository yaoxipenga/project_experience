package com.medcaptain.mqttdeviceauthenticate.entity;

import java.util.Date;

public class Producttopic {
    private Integer indexId;

    private String productKey;

    private String productTopic;

    private Integer topicType;

    private Integer isDel;

    private Integer userId;

    private Date creatTime;

    private Date modifTime;

    private String desc;

    public Integer getIndexId() {
        return indexId;
    }

    public void setIndexId(Integer indexId) {
        this.indexId = indexId;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey == null ? null : productKey.trim();
    }

    public String getProductTopic() {
        return productTopic;
    }

    public void setProductTopic(String productTopic) {
        this.productTopic = productTopic == null ? null : productTopic.trim();
    }

    public Integer getTopicType() {
        return topicType;
    }

    public void setTopicType(Integer topicType) {
        this.topicType = topicType;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }
}