package com.medcaptain.productservice.entity.productpojo;


/**
 * 产品属性
 *
 * @author bingwen.ai
 */
public class Property extends Parameter {
    private String productKey;

    private String accessMode;

    private boolean required;

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

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
