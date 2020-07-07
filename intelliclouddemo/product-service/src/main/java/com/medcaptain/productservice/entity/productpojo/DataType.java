package com.medcaptain.productservice.entity.productpojo;


import com.medcaptain.productservice.util.Type;

import javax.annotation.Nullable;

/**
 * 数据类型
 *
 * @author bingwen.ai
 */
public class DataType {
    private Object type;

    private Object specs;

    private boolean isArray;

    public Object getType() {
        return type;
    }

    public void setType(Object type) {
        this.type = type;
    }

    public Object getSpecs() {
        return specs;
    }

    public void setSpecs(Object specs) {
        this.specs = specs;
    }

    public boolean getIsArray() {
        return isArray;
    }

    public void setIsArray(boolean isArray) {
        this.isArray = isArray;
    }
}
