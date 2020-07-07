package com.medcaptain.fastdfs.redis.entity;

import java.util.Date;

/**
 * 文件上传token的redis格式
 * @author Adam.Chen
 */
public class FileUploadTokenEntity {
    private int code;
    private int userId;
    private String md5;
    private String fileName;
    private Date time;

    public Date getTime() {
        return time;
    }

    public int getCode() {
        return code;
    }

    public int getUserId() {
        return userId;
    }

    public String getFileName() {
        return fileName;
    }

    public String getMd5() {
        return md5;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
