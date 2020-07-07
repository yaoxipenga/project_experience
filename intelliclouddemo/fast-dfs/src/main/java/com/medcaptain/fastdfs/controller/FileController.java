package com.medcaptain.fastdfs.controller;

import com.medcaptain.annotation.AuthorizationFree;
import com.medcaptain.dto.RestResult;
import com.medcaptain.enums.HttpResponseCode;
import com.medcaptain.exception.FileRuntimeException;
import com.medcaptain.fastdfs.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.regex.Pattern;

/**
 * 文件服务的接口
 *
 * @author Adam.Chen
 */
@RestController
public class FileController {
    @Autowired
    private FileService fileService;
    /**
     * 从配置文件中读取文件服务器存储方式M00和M01的规则
     */
    @Value("${com.medcaptain.fastdfs.M00}")
    private String M00;
    @Value("${com.medcaptain.fastdfs.M01}")
    private String M01;

    @AuthorizationFree
    @PostMapping(value = "/file/excel", produces = "application/json;charset=UTF-8")
    public RestResult recFile(@RequestParam("data") byte[] data, @RequestParam("fileName") String name) {
        System.out.println(data.length);
        return fileService.uploadExcelFile(data, name);
    }

    /**
     * 文件上传接口
     *
     * @param file
     * @param token
     * @return
     * @throws FileNotFoundException
     */
    @AuthorizationFree
    @PostMapping(value = "/file", produces = "application/json;charset=UTF-8")
    public RestResult recFile(@RequestParam("file") MultipartFile file, @RequestParam("token") String token,
                              @RequestParam(value = "type", required = false) String type) {
        return fileService.uploadFile(file, token, type);
    }

    /**
     * 文件下载接口
     *
     * @param ID    文件的id
     * @param token
     * @return
     */
    @AuthorizationFree
    @GetMapping(value = "/file", produces = "application/json;charset=UTF-8")
    public ResponseEntity<byte[]> downloadFileS(@RequestParam("id") String ID, @RequestParam(value = "token",
            defaultValue = "") String token) {
        Pattern pattern_M01 = Pattern.compile(M01);
        if (pattern_M01.matcher(ID).matches()) {
            System.out.println(0);
            return fileService.downloadFile(ID);
        }
        Pattern pattern_M00 = Pattern.compile(M00);
        if (pattern_M00.matcher(ID).matches()) {
            System.out.println(1);
            return fileService.downloadFileS(ID, token);
        } else {
            System.out.println(2);
            throw new FileRuntimeException(HttpResponseCode.ERROR_FILE_DOWNLOAD, null);
        }
    }

    /**
     * 文件删除接口
     *
     * @param ID 文件的id
     * @return
     */
    @AuthorizationFree
    @DeleteMapping(value = "/file", produces = "application/json;charset=UTF-8")
    public RestResult delete(@RequestParam("ID") String ID) {
        return fileService.deleteFile(ID);
    }
}
