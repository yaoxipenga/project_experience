package com.medcaptain.exception;

import com.medcaptain.enums.HttpResponseCode;

/**
 * @author : yangzhixiong
 * @description : api异常封装
 * @date : 2018/9/14
 */
public class ApiRuntimeException extends RuntimeException {

    private Integer code;

    private String msg;

    private Object data;

    private Exception e;

    public ApiRuntimeException(Integer code, String msg, Object data, Exception e) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.e = e;
    }

    public ApiRuntimeException(HttpResponseCode code, Object data, Exception e) {
        this.code = code.getCode();
        this.msg = code.getMsg();
        this.data = data;
        this.e = e;
    }

    public ApiRuntimeException(HttpResponseCode code, String data) {
        this.code = code.getCode();
        this.msg = code.getMsg();
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Exception getE() {
        return e;
    }

    public void setE(Exception e) {
        this.e = e;
    }
}
