package com.medcaptain.fastdfs.service;

import com.medcaptain.dto.RestResult;

import java.util.Date;

/**
 * token的service
 *
 * @author Adam.Chen
 */
public interface FileTokenService {
    /**
     * 设置上传token
     *
     * @param token
     * @param userID
     * @param code
     * @param md5
     * @param fileName
     * @param time
     * @return
     */
    RestResult setUploadToken(String token, int userID,
                              int code, String md5, String fileName, Date time);

    /**
     * 设置下载token
     *
     * @param token
     * @param path
     * @param fileName
     * @return
     */
    RestResult setDownloadToken(String token, String path, String fileName);

    Object userFile(int userID, String md5, String fileName, Integer size);
}
