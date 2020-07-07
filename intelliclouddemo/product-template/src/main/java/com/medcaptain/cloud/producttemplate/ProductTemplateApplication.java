package com.medcaptain.cloud.producttemplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 产品模板微服务主程序
 *
 * @author bingwen.ai
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class ProductTemplateApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductTemplateApplication.class, args);
    }
}
