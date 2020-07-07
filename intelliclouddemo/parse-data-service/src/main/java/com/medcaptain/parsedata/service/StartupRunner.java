package com.medcaptain.parsedata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value=1)
public class StartupRunner implements CommandLineRunner {

    @Autowired
    RedisCleaner redisCleaner;

    @Override
    public void run(String... args) throws Exception{
        redisCleaner.redisRemove();
    }
}
