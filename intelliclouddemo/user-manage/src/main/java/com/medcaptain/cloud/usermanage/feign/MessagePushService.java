package com.medcaptain.cloud.usermanage.feign;

import com.medcaptain.dto.RestResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 消息推送服务调用
 *
 * @author bingwen.ai
 */
@FeignClient(value = "push-socket-service", configuration = FeignConfig.class)
@Service
public interface MessagePushService {
    /**
     * 发送电子邮件
     *
     * @param toAccount 接收邮箱地址
     * @param subject   邮件主题
     * @param content   邮件内容
     * @param isHtml    邮件是否是html格式
     * @return 接口调用结果
     */
    @PostMapping(value = "/email")
    RestResult sendEmail(@RequestParam(value = "toAccount") String toAccount,
                         @RequestParam(value = "subject") String subject,
                         @RequestParam(value = "content") String content,
                         @RequestParam(name = "isHtml") boolean isHtml);
}
