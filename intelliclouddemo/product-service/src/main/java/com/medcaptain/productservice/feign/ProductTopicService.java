package com.medcaptain.productservice.feign;

import com.medcaptain.dto.RestResult;
import com.medcaptain.productservice.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * 加上快速失败的处理类。该处理类是作为Feign熔断器的逻辑处理类，必须实现被@FeignClient修饰的接口。例如案例中的HiHystrix类实现了接口EurekaClientFeign，
 * 最后需要以Spring Bean的形式注入IoC容器中
 */
@FeignClient(value = "mqtt-forwarder", configuration = FeignConfig.class)
@Service
public interface ProductTopicService {


    @RequestMapping(method = RequestMethod.POST, value = "/subscribe")
    Boolean subscribeTopic(@RequestParam("topicList") List<String> topicList);


    @PostMapping("/orderMessage")
    RestResult issueOrders(@RequestParam("topic") String topic,
                           @RequestParam("payload") String payload,
                           @RequestParam("replyTopic") String replyTopic);

    @PostMapping("/publish")
    String publishMessage(@RequestParam("topic") String topic,
                          @RequestParam("payload")String payload,
                          @RequestParam("qos") Integer qos);

}
