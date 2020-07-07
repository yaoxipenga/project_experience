package com.medcaptain.productservice.redis.entity;

import java.sql.Timestamp;

public class FileInfoEntity {
    private  String path;
    private  int size;
    private String md5;
    private String fileName;
    private Timestamp time;
    private int code;

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public String getPath() {
        return path;
    }

    public String getFileName() {
        return fileName;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getMd5() {
        return md5;
    }

    public Timestamp getTime() {
        return time;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
