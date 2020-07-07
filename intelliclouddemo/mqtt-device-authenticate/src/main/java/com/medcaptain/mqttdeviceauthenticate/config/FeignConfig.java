package com.medcaptain.mqttdeviceauthenticate.config;

import com.medcaptain.helper.HttpServletHelper;
import feign.Request;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class FeignConfig {
    @Bean
    public RequestInterceptor headerInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                requestTemplate.header(HttpServletHelper.REQUEST_HEADER_INNER_INVOKE, HttpServletHelper.INNER_INVOKE_FLAG);
            }
        };
    }

    @Bean
    public Retryer feignRetryer() {
        // maxAttempts 最大尝试次数
        return new Retryer.Default(100, TimeUnit.SECONDS.toMillis(1), 1);
    }

    @Bean
    public Request.Options feignOption() {
        return new Request.Options(5000, 5000);
    }
}
