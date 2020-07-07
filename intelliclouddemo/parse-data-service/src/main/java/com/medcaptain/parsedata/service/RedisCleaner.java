package com.medcaptain.parsedata.service;

import com.medcaptain.parsedata.constant.CommonConstant;
import com.medcaptain.parsedata.redis.RedisOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class RedisCleaner {
    private static final Logger log = LoggerFactory.getLogger(RedisCleaner.class);

    @Async("DeviceMessageRemove")
    public void redisRemove() {
        log.debug("线程名称：{} be ready to read data!", Thread.currentThread().getName());
        while (true) {
            try {
                Thread.sleep(CommonConstant.FLUSH_TASK_SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Date date = new Date();
            Long time_now = date.getTime();
            Long end= time_now-CommonConstant.DEVICE_MESSAGE_FLUSH_TIME;
            RedisOption.limitMessageTime(CommonConstant.DEVICE_POST_MESSAGE + "*",
                    CommonConstant.DEVICE_MESSAGE_FLUSH_TIME_START,
                    end.doubleValue()
                    );
        }
    }
}
