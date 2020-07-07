package com.medcaptain.parsedata.entity;

public class Property extends Parameter {

    private String accessMode;

    private boolean required;

    public String getAccessMode() {
        return accessMode;
    }

    public void setAccessMode(String accessMode) {
        this.accessMode = accessMode;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }
}
