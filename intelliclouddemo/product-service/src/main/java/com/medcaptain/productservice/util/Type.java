package com.medcaptain.productservice.util;

public enum Type {
    INT("int"),
    FLOAT("float"),
    DOUBLE("double"),
    ENUM("enum"),
    BOOL("bool"),
    TEXT("text"),
    DATE("date"),
    STRUCT("struct"),
    ARRAY("array"),
    STRING("string");

    private String name;

    Type(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
