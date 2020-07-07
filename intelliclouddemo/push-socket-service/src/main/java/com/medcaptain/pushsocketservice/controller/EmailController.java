package com.medcaptain.pushsocketservice.controller;

import com.medcaptain.dto.RestResult;
import com.medcaptain.enums.HttpResponseCode;
import com.medcaptain.pushsocketservice.service.EmailService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 电子邮件REST接口
 *
 * @author bingwen.ai
 */
@RestController
public class EmailController {
    @Autowired
    EmailService emailService;

    /**
     * 发送电子邮件
     *
     * @param toAccount 目的邮箱地址
     * @param subject   邮件主题
     * @param content   邮件内容
     * @param isHtml    邮件内容是否是HTML格式
     * @return true = 发送成功 ； false = 发送失败
     */
    @PostMapping(value = "/email")
    public RestResult sendEmail(@RequestParam(name = "toAccount") String toAccount,
                                @RequestParam(name = "subject") String subject,
                                @RequestParam(name = "content") String content,
                                @RequestParam(name = "isHtml") boolean isHtml) {
        if (StringUtils.isEmpty(toAccount) || StringUtils.isEmpty(subject) || StringUtils.isEmpty(content)) {
            return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }
        if (emailService.sendMail(toAccount, subject, content, isHtml)) {
            return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
        } else {
            return RestResult.getInstance(HttpResponseCode.ERROR, null);
        }
    }
}
