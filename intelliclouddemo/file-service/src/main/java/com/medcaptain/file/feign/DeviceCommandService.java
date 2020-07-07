package com.medcaptain.file.feign;

import com.medcaptain.file.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
//@FeignClient(value = "eureka-client", configuration = FeignConfig.class)

/**
 * 加上快速失败的处理类。该处理类是作为Feign熔断器的逻辑处理类，必须实现被@FeignClient修饰的接口。例如案例中的HiHystrix类实现了接口EurekaClientFeign，
 * 最后需要以Spring Bean的形式注入IoC容器中
 *
 * @author Adam.Chen
 */
@FeignClient(value = "mqtt-forwarder", configuration = FeignConfig.class)
@Service
public interface DeviceCommandService {
    /**
     * 下达指令
     *
     * @param topic
     * @param payload
     * @param replyTopic
     * @return
     */
    @PostMapping("/orderMessage")
    String issueOrders(@RequestParam("topic") String topic,
                       @RequestParam("payload") String payload,
                       @RequestParam("replyTopic") String replyTopic);

    /**
     * 发送消息
     *
     * @param topic
     * @param payload
     * @return
     */
    @PostMapping("/publish")
    String publishMessage(@RequestParam("topic") String topic,
                          @RequestParam("payload") String payload);
}
