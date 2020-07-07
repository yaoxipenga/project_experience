package com.medcaptain.cloud.schedulestatistics.feign;

import feign.Request;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * 服务间调用配置
 *
 * @author bingwen.ai
 */
@Configuration
public class FeignConfig {
    @Bean
    public Retryer feignRetryer() {
        return new Retryer.Default(100, TimeUnit.SECONDS.toMillis(1), 3);
    }

    @Bean
    public Request.Options feignOption() {
        return new Request.Options(5000, 5000);
    }
}
