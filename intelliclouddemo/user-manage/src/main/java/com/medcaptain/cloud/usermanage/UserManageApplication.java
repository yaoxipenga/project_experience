package com.medcaptain.cloud.usermanage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


/**
 * 用户管理微服务主程序
 *
 *@author bingwen.ai
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@MapperScan(basePackages = "com.medcaptain.cloud.usermanage.mapper")
public class UserManageApplication {

    public static void main(final String[] args) {
        SpringApplication.run(UserManageApplication.class, args);
    }
}
