package com.medcaptain.pushsocketservice.service.impl;

import com.medcaptain.pushsocketservice.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

/**
 * 电子邮件服务
 *
 * @author bingwen.ai
 */
@Service
@Configuration
public class EmailServiceImpl implements EmailService {
    @Autowired
    JavaMailSender javaMailSender;

    @Value(value = "${spring.mail.username}")
    String fromAccount;

    @Override
    public boolean sendMail(String toAccount, String mailSubject, String mailContent, boolean isHtml) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(fromAccount);
            mimeMessageHelper.setTo(toAccount);
            mimeMessageHelper.setText(mailContent, isHtml);
            mimeMessageHelper.setSubject(mailSubject);
            javaMailSender.send(mimeMessage);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
