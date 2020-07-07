package com.medcaptain.file.redis.entity;

/**
 * 设备下载token的redis格式
 * @author Adam.Chen
 */
public class FileDownloadTokenEntity {
    private String path;
    private String fileName;

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public String getPath() {
        return path;
    }
}
