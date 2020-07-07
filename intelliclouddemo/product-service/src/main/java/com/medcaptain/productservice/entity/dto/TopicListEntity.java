package com.medcaptain.productservice.entity.dto;

public class TopicListEntity {
    private String topicName;
    private int operationPermission;
    private int topicId;
    private String productKey;
    private String topicDesc;

    public int getOperationPermission() {
        return operationPermission;
    }

    public int getTopicId() {
        return topicId;
    }

    public String getProductKey() {
        return productKey;
    }

    public String getTopicDesc() {
        return topicDesc;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setOperationPermission(int operationPermission) {
        this.operationPermission = operationPermission;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public void setTopicDesc(String topicDesc) {
        this.topicDesc = topicDesc;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }
}
