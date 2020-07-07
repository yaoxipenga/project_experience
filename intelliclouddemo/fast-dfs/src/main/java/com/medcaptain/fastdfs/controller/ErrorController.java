package com.medcaptain.fastdfs.controller;


import com.medcaptain.dto.RestResult;
import com.medcaptain.enums.HttpResponseCode;
import com.medcaptain.exception.ApiRuntimeException;
import com.medcaptain.exception.FileRuntimeException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * controller 增强器
 *
 * @author sam
 * @since 2017/7/17
 */
@ControllerAdvice
public class ErrorController {

    /**
     * 全局异常捕捉处理
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public RestResult errorHandler(Exception ex) {
        return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
    }

    /**
     * 拦截捕捉自定义异常 ApiRuntimeException.class
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = ApiRuntimeException.class)
    public RestResult ApiRuntimeErrorHandler(ApiRuntimeException ex) {
        return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, ex.getData());
//        return RestResult.getInstance(ex.getCode(), ex.getMsg(), ex.getData());
    }

    /**
     * 拦截捕捉自定义异常 FileRuntimeException.class
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = FileRuntimeException.class)
    public RestResult FileRuntimeErrorHandler(FileRuntimeException ex, HttpServletResponse response) {
        response.setStatus(404);
        return RestResult.getInstance(HttpResponseCode.ERROR, ex.getE());
//        return RestResult.getInstance(ex.getCode(), ex.getMsg(), ex.getData(), ex.getE());
    }

}