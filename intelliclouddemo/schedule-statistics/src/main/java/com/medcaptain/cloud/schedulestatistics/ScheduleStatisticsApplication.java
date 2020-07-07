package com.medcaptain.cloud.schedulestatistics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 定时统计任务程序
 *
 * @author bingwen.ai
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableScheduling
public class ScheduleStatisticsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ScheduleStatisticsApplication.class, args);
    }
}
