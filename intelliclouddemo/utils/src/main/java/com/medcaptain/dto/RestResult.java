package com.medcaptain.dto;

import com.medcaptain.enums.HttpResponseCode;

/**
 * @author : yangzhixiong
 * @description : 接口返回的信息DTO
 * @date : 2018/9/26
 */
public class RestResult<T> {
    private int code;

    private String msg;

    private T data;

    public RestResult() {

    }

    private RestResult(HttpResponseCode httpResponseCode, T responseData) {
        this.msg = httpResponseCode.getMsg();
        this.code = httpResponseCode.getCode();
        this.data = responseData;
    }

    private RestResult(int code, String message, T responseData) {
        this.msg = message;
        this.code = code;
        this.data = responseData;
    }

    public static <T> RestResult getInstance(HttpResponseCode httpResponseCode, T responseData) {
        return new RestResult(httpResponseCode, responseData);
    }

    public static <T> RestResult getInstance(int code, String message, T responseData) {
        return new RestResult(code, message, responseData);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}