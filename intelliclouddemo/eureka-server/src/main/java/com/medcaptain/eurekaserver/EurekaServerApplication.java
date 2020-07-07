package com.medcaptain.eurekaserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaServer
@RestController
public class EurekaServerApplication {
    private static Logger log = LoggerFactory.getLogger(EurekaServerApplication.class);


    @RequestMapping("/eureka")
    public String eureka() {
        return "forward:/";
    }

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
