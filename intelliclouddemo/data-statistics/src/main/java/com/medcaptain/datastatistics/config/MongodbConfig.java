package com.medcaptain.datastatistics.config;

import com.mongodb.MongoClientOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * MongoDB数据库配置
 *
 * @author bingwen.ai
 */
@Component
public class MongodbConfig {
    @Bean
    MongoClientOptions.Builder getBuilder() {
        return new MongoClientOptions.Builder();
    }

    @Bean
    @Autowired
    MongoClientOptions getClientOptions(MongoClientOptions.Builder builder) {
        //设置连接的最大空闲时间，超过则关闭连接
        builder.maxConnectionIdleTime(30000);
        return builder.build();
    }
}
