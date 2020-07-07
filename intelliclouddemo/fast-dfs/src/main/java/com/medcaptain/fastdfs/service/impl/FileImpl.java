package com.medcaptain.fastdfs.service.impl;

import com.medcaptain.dto.RestResult;
import com.medcaptain.entity.DeviceLogFileEntity;
import com.medcaptain.enums.HttpResponseCode;
import com.medcaptain.enums.FileType;
import com.medcaptain.exception.ApiRuntimeException;
import com.medcaptain.exception.FileRuntimeException;
import com.medcaptain.fastdfs.connection.ConnectionManager;
import com.medcaptain.fastdfs.feign.ProductService;
import com.medcaptain.fastdfs.redis.RedisOption;
import com.medcaptain.fastdfs.redis.entity.FileDownloadTokenEntity;
import com.medcaptain.fastdfs.redis.entity.FileInfoEntity;
import com.medcaptain.fastdfs.redis.entity.FilePathEntity;
import com.medcaptain.fastdfs.redis.entity.FileUploadTokenEntity;
import com.medcaptain.fastdfs.service.FileService;
import com.medcaptain.utils.UUIDUtil;
import com.medcaptain.utils.encrypt.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件服务的静态方法类
 *
 * @author Adam.Chen
 */
@Service
public class FileImpl implements FileService {
    private static final Logger log = LoggerFactory.getLogger(FileImpl.class);

    @Autowired
    private ProductService productService;

    /**
     * 删除文件的方法
     *
     * @param ID
     * @return
     */
    @Override
    public RestResult deleteFile(String ID) {
        if (ConnectionManager.deleteFile(ID) == 0) {
            return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
        } else {
            return RestResult.getInstance(HttpResponseCode.ERROR_PARAMETER, null);
        }
    }

    /**
     * 上传文件方法
     *
     * @param file
     * @param token
     * @return
     */
    @Override
    public RestResult
    uploadFile(MultipartFile file, String token, String type) {
        String md5;
        int code;
        String name = file.getOriginalFilename();
        if (name.lastIndexOf(".") == -1) {
            String suffix = "";
        } else {
            String suffix = name.substring(name.lastIndexOf(".") + 1);
        }
        /**
         * 判断文件是否为空
         */
        if (!file.isEmpty()) {
            try {
                FileUploadTokenEntity fileUploadTokenEntity = RedisOption.getFileUploadInfo(token);
                if (fileUploadTokenEntity == null) {
                    return RestResult.getInstance(HttpResponseCode.ERROR_PARAMETER, null);
                }
                md5 = fileUploadTokenEntity.getMd5().toUpperCase();
                code = fileUploadTokenEntity.getCode();
                String md5_now = MD5Util.md5(file.getBytes()).toUpperCase();
                log.info("md5_save:" + md5);
                log.info("md5_file:" + md5_now);


                /**
                 * 校验md5
                 */
                if (!md5_now.equals(md5)) {
                    return RestResult.getInstance(HttpResponseCode.ERROR_PARAMETER, null);
                }
                /**
                 * 文件入库方式的类型，private为私密文件，public为非私密文件
                 */
                String rs = null;
                switch (code) {
                    case 0:
                        rs = ConnectionManager.uploadFile(FileType.PRIVATE.getCode(), file.getBytes(), name);
                        break;
                    case 1:
                        rs = ConnectionManager.uploadFile(FileType.PUBLIC.getCode(), file.getBytes(), name);
                        break;
                    default:
                        return RestResult.getInstance(HttpResponseCode.ERROR_PARAMETER, null);
                }
                /**
                 * rs为null表示上传失败
                 */
                if (rs == null) {
                    if ("log".equals(type)) {
                        productService.synchronizeLog(md5, rs, true);
                    }
                    return RestResult.getInstance(HttpResponseCode.ERROR_PARAMETER, null);
                }
                /**
                 * 上传完毕信息存储到redis中
                 */
                FileInfoEntity fileInfoEntity = new FileInfoEntity();
                fileInfoEntity.setPath(rs);
                fileInfoEntity.setMd5(md5);
                fileInfoEntity.setFileName(fileUploadTokenEntity.getFileName());
                fileInfoEntity.setSize((int) file.getSize());
                fileInfoEntity.setTime(new Timestamp(fileUploadTokenEntity.getTime().getTime()));
                fileInfoEntity.setCode(code);
                log.info("上传结束");
                //上传完毕，调用日志服务接口，将上传状态设为已完成
                if ("log".equals(type)) {
                    productService.synchronizeLog(md5, rs, true);
                } else {
                    if (RedisOption.setFileInfo(md5, fileInfoEntity)) {

                        //TODO 上传成功，删除token，测试时注释掉
                        RedisOption.deleteUploadToken(token);

                        FilePathEntity filePathEntity = new FilePathEntity();
                        filePathEntity.setUrl(rs);
                        return RestResult.getInstance(HttpResponseCode.SUCCESS, filePathEntity);
                    } else {
                        /**
                         * 如果信息不能存储，则视为上传失败，删除文件
                         */
                        this.deleteFile(rs);
                        return RestResult.getInstance(HttpResponseCode.ERROR_FILE_NOT_FOUND, null);
                    }
                }
                return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new ApiRuntimeException(HttpResponseCode.ERROR_FILE_NOT_FOUND, null, e);
            } catch (IOException e) {
                e.printStackTrace();
                throw new ApiRuntimeException(HttpResponseCode.ERROR_FILE_NOT_FOUND, null, e);
            }
        } else {
            return RestResult.getInstance(HttpResponseCode.ERROR_PARAMETER, null);
        }
    }

    @Override
    public RestResult uploadExcelFile(byte[] data, String name) {
        System.out.println(name);
        try {
            String rs = ConnectionManager.uploadFile(FileType.PRIVATE.getCode(), data, name);
            if (rs == null) {
                throw new ApiRuntimeException(HttpResponseCode.ERROR_NO_STORAGE, null);
            }
            String md5_now = MD5Util.md5(data);
            log.info("md5_file:" + md5_now);
            String token = UUIDUtil.getUUID();
            FileDownloadTokenEntity fileDownloadTokenEntity = new FileDownloadTokenEntity();
            fileDownloadTokenEntity.setFileName(name);
            fileDownloadTokenEntity.setPath(rs);
            RedisOption.setFileDownloadInfo(token, fileDownloadTokenEntity);
            Map<String, String> returnData = new HashMap<String, String>();
            returnData.put("url", rs);
            returnData.put("token", token);
            return RestResult.getInstance(HttpResponseCode.SUCCESS, returnData);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }

    /**
     * 下载public文件方法
     *
     * @param ID
     * @return
     */
    @Override
    public ResponseEntity<byte[]> downloadFile(String ID) {
//        if (ID.indexOf("M01") !=ID.indexOf("/")+1){
//            return null;
//        }
        String fileName = ID.substring(ID.lastIndexOf("/") + 1);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", fileName);
        return new ResponseEntity<byte[]>(ConnectionManager.downloadFile(ID),
                headers, HttpStatus.CREATED);
    }

    /**
     * 下载private文件方法
     *
     * @param ID
     * @param token
     * @return
     */
    @Override
    public ResponseEntity<byte[]> downloadFileS(String ID, String token) {
        FileDownloadTokenEntity fileDownloadTokenEntity = RedisOption.getFileDownloadInfo(token);
        /**
         * 校验token
         */
        if (fileDownloadTokenEntity == null) {
//            throw new ApiRuntimeException(HttpResponseCode.ERROR_FileNotFound, null);
            throw new FileRuntimeException(HttpResponseCode.ERROR_FILE_DOWNLOAD, null);
        }
        /**
         * 校验文件的ID是否匹配
         */
        String ID_redis = fileDownloadTokenEntity.getPath();
        if (ID.equals(ID_redis) == false) {
            throw new FileRuntimeException(HttpResponseCode.ERROR_FILE_DOWNLOAD, null);
        }
        /**
         * token验证
         * 获取name和id
         */
        String fileName = fileDownloadTokenEntity.getFileName();
        if (fileName == null) {
            fileName = ID.substring(ID.lastIndexOf("/") + 1);
        }
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", fileName);
            return new ResponseEntity<byte[]>(ConnectionManager.downloadFile(ID),
                    headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
