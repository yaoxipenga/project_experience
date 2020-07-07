package com.medcaptain.fastdfs.service;

import com.medcaptain.dto.RestResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.Date;

/**
 * file的service
 * @author Adam.Chen
 */
public interface FileService {

    /**
     * 删除文件
     * @param ID
     * @return
     */
    RestResult deleteFile(String ID);

    /**
     * 上传文件
     * @param file
     * @param token
     * @return
     */
    RestResult uploadFile(MultipartFile file, String token, String type);

    /**
     * 上传特殊excel文件
     * @param data
     * @param name
     * @return
     */
    RestResult uploadExcelFile(byte[] data, String name);

    /**
     * 下载非私密文件
     * @param ID
     * @return
     */
    ResponseEntity<byte[]> downloadFile(String ID);

    /**
     * 下载私密文件
     * @param ID
     * @param token
     * @return
     */
    ResponseEntity<byte[]> downloadFileS(String ID, String token);
}
