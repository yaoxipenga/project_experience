package com.medcaptain.cloud.producttemplate.entity;


/**
 * 产品参数
 *
 * @author bingwen.ai
 */
public class Parameter {
    private String identifier;

    private DataType dataType;

    private String name;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
