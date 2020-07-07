package com.medcaptain.productservice.controller;


import com.medcaptain.constant.InterceptorConstant;
import com.medcaptain.enums.HttpResponseCode;
import com.medcaptain.exception.ApiRuntimeException;
import com.medcaptain.exception.FileRuntimeException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * controller 增强器
 *
 * @author yangzhixiong
 * @since 2018/11/12
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
    public Map errorHandler(Exception ex) {
        Map map = new HashMap();
        map.put(InterceptorConstant.CODE, HttpResponseCode.SERVER_INTERNAL_ERROR.getCode());
        map.put(InterceptorConstant.LOG, ex.getMessage());
        map.put(InterceptorConstant.Data, ex.getMessage());
        map.put(InterceptorConstant.MSG, HttpResponseCode.SERVER_INTERNAL_ERROR.getMsg());
        return map;
    }

    /**
     * 拦截捕捉自定义异常 ApiRuntimeException.class
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = ApiRuntimeException.class)
    public Map ApiRuntimeErrorHandler(ApiRuntimeException ex) {
        Map map = new HashMap();
        map.put("code", ex.getCode());
        map.put("msg", ex.getMsg());
        map.put("data", ex.getData());
        map.put("log", ex.getE());
        return map;
    }

    /**
     * 拦截捕捉自定义异常 FileRuntimeException.class
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = FileRuntimeException.class)
    public Map FileRuntimeErrorHandler(FileRuntimeException ex) {
        Map map = new HashMap();
        map.put("code", ex.getCode());
        map.put("msg", ex.getMsg());
        map.put("data", ex.getData());
        map.put("log", ex.getE());
        return map;
    }

}