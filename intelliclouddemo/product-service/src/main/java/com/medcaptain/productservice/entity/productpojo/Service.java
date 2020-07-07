package com.medcaptain.productservice.entity.productpojo;

import java.util.List;


/**
 * 产品服务定义
 *
 * @author bingwen.ai
 */
public class Service {
    private String productKey;

    private String identifier;

    private String name;

    private String method;

    private boolean required;

    private String callType;

    private String desc;

    private List<Parameter> outputData;

    private List<Parameter> inputData;

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<Parameter> getOutputData() {
        return outputData;
    }

    public void setOutputData(List<Parameter> outputData) {
        this.outputData = outputData;
    }

    public List<Parameter> getInputData() {
        return inputData;
    }

    public void setInputData(List<Parameter> inputData) {
        this.inputData = inputData;
    }
}
