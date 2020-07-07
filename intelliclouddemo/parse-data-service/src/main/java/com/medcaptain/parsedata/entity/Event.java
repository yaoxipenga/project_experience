package com.medcaptain.parsedata.entity;

import java.util.List;

public class Event {
    private String identifier;

    private String name;

    private String method;

    private boolean required;

    private String type;

    private String desc;

    private List<Parameter> outputData;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}
