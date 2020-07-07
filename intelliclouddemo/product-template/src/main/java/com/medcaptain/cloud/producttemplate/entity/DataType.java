package com.medcaptain.cloud.producttemplate.entity;


/**
 * 数据类型
 *
 * @author bingwen.ai
 */
public class DataType {
    private String type;

    private String specs;

    private boolean isArray;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    public boolean getIsArray() {
        return isArray;
    }

    public void setIsArray(boolean isArray) {
        this.isArray = isArray;
    }
}
