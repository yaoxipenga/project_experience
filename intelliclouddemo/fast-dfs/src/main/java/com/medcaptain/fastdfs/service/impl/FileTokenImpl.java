package com.medcaptain.fastdfs.service.impl;

import com.medcaptain.dto.RestResult;
import com.medcaptain.enums.FileType;
import com.medcaptain.enums.HttpResponseCode;
import com.medcaptain.exception.ApiRuntimeException;
import com.medcaptain.fastdfs.redis.RedisOption;
import com.medcaptain.fastdfs.redis.entity.FileDownloadTokenEntity;
import com.medcaptain.fastdfs.redis.entity.FileUploadTokenEntity;
import com.medcaptain.fastdfs.service.FileTokenService;
import com.medcaptain.utils.UUIDUtil;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * token设置的静态方法类
 *
 * @author Adam.Chen
 */
@Service
public class FileTokenImpl implements FileTokenService {
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
     * @throws FileNotFoundException
     */
    @Override
    public RestResult setUploadToken(String token, int userID,
                                     int code, String md5, String fileName, Date time) {
        FileUploadTokenEntity fileUploadTokenEntity = new FileUploadTokenEntity();
        fileUploadTokenEntity.setCode(code);
        fileUploadTokenEntity.setFileName(fileName);
        fileUploadTokenEntity.setMd5(md5);
        fileUploadTokenEntity.setUserId(userID);
        fileUploadTokenEntity.setTime(time);
        if (RedisOption.setFileUploadInfo(token, fileUploadTokenEntity)) {
            return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
        } else {
            throw new ApiRuntimeException(HttpResponseCode.ERROR_FILE_NOT_FOUND, null);
        }
    }

    /**
     * 设置下载token
     *
     * @param token
     * @param path
     * @param fileName
     * @return
     */
    @Override
    public RestResult setDownloadToken(String token, String path, String fileName) {
        FileDownloadTokenEntity fileDownloadTokenEntity = new FileDownloadTokenEntity();
        fileDownloadTokenEntity.setFileName(fileName);
        fileDownloadTokenEntity.setPath(path);
        if (RedisOption.setFileDownloadInfo(token, fileDownloadTokenEntity)) {
            return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
        } else {
            throw new ApiRuntimeException(HttpResponseCode.ERROR_FILE_DOWNLOAD, null);
        }
    }

    /**
     * 设置上传token
     *
     * @param userID
     * @param md5
     * @param fileName
     * @return
     * @throws FileNotFoundException
     */
    @Override
    public Object userFile(int userID, String md5, String fileName, Integer size) {
        FileUploadTokenEntity fileUploadTokenEntity = new FileUploadTokenEntity();
        fileUploadTokenEntity.setCode(FileType.PUBLIC.getCode());
        fileUploadTokenEntity.setFileName(fileName);
        fileUploadTokenEntity.setMd5(md5);
        fileUploadTokenEntity.setUserId(userID);
        fileUploadTokenEntity.setTime(new Date());
        String token = UUIDUtil.getUUID();
        Map<String, String> returnMap = new HashMap<String, String>(2);
        if (RedisOption.setFileUploadInfo(token, fileUploadTokenEntity)) {
            returnMap.put("url", "/api/file/file?token=" + token);
        }
        return returnMap;
    }
}
