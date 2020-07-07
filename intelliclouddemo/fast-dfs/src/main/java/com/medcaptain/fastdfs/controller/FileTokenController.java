package com.medcaptain.fastdfs.controller;

import com.medcaptain.dto.RestResult;
import com.medcaptain.enums.HttpResponseCode;
import com.medcaptain.fastdfs.service.FileTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.Date;

/**
 * 设置文件上传下载参数的接口
 *
 * @author Adam.Chen
 */
@RestController
public class FileTokenController {
    @Autowired
    private FileTokenService fileTokenService;

    /**
     * 设置允许上传文件
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
    @PostMapping(value = "/file/uploadToken")
    public RestResult recFile(@RequestParam("token") String token, @RequestParam("userID") int userID,
                              @RequestParam("code") int code, @RequestParam("MD5") String md5, @RequestParam(
            "fileName") String fileName,
                              @RequestParam("time") Date time) throws FileNotFoundException {
        return fileTokenService.setUploadToken(token, userID, code, md5, fileName, time);

    }


    /**
     * 设置允许上传文件(普通用户之间设置)
     *
     * @return
     * @throws FileNotFoundException
     */
    @PostMapping(value = "/file/user")
    public RestResult userFile(
            @RequestHeader(name = "userId") Integer userId,
            @RequestParam("MD5") String md5,
            @RequestParam("name") String name,
            @RequestParam("size") Integer size) {
        return RestResult.getInstance(HttpResponseCode.SUCCESS, fileTokenService.userFile(userId, md5, name, size));
    }


    /**
     * 下载文件
     *
     * @param token
     * @param fileName 下载的文件名
     * @param path     文件id
     * @return
     */
    @PostMapping(value = "/file/downloadToken", produces = "application/json;" +
            "charset=UTF-8")
    public RestResult downloadFile(@RequestParam("token") String token, @RequestParam("fileName") String fileName,
                                   @RequestParam("path") String path) {
        System.out.println(token);
        System.out.println(fileName);
        System.out.println(path);
        return fileTokenService.setDownloadToken(token, path, fileName);
    }

}
