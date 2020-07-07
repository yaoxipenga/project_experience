package com.medcaptain.pushsocketservice.service;

public interface EmailService {


    boolean sendMail(String toAccount, String mailSubject, String mailContent, boolean isHtml);
}
