package com.medcaptain.forwader.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "mqtt")
@Order(1)
public class MqttSettings {
    private String host;
    private String username;
    private String password;
    private String clientID;
    private String secret;
//    private String ca;
//    private String clientCrt;
//    private String clientKey;


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getSecret() {
        return secret;
    }

//    public String getCa() {
//        return ca;
//    }
//
//    public String getClientCrt() {
//        return clientCrt;
//    }
//
//    public String getClientKey() {
//        return clientKey;
//    }
//
//    public void setCa(String ca) {
//        this.ca = ca;
//    }
//
//    public void setClientCrt(String clientCrt) {
//        this.clientCrt = clientCrt;
//    }
//
//    public void setClientKey(String clientKey) {
//        this.clientKey = clientKey;
//    }
}
