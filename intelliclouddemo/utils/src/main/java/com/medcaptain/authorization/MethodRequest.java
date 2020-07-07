package com.medcaptain.authorization;

/**
 * 请求后端接口信息
 *
 * @author bingwen.ai
 */
public class MethodRequest {
    private String userToken;

    private boolean needValidate;

    private int requestType;

    private String backendResourceURL;

    private String remoteIP;

    public MethodRequest(String userToken, boolean needValidate, int requestType, String backendResourceURL, String remoteIP) {
        this.userToken = userToken;
        this.needValidate = needValidate;
        this.requestType = requestType;
        this.backendResourceURL = backendResourceURL;
        this.remoteIP = remoteIP;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public boolean isNeedValidate() {
        return needValidate;
    }

    public void setNeedValidate(boolean needValidate) {
        this.needValidate = needValidate;
    }

    public int getRequestType() {
        return requestType;
    }

    public void setRequestType(int requestType) {
        this.requestType = requestType;
    }

    public String getBackendResourceURL() {
        return backendResourceURL;
    }

    public void setBackendResourceURL(String backendResourceURL) {
        this.backendResourceURL = backendResourceURL;
    }

    public String getRemoteIP() {
        return remoteIP;
    }

    public void setRemoteIP(String remoteIP) {
        this.remoteIP = remoteIP;
    }
}
