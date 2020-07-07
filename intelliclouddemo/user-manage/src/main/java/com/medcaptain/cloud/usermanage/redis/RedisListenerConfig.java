package com.medcaptain.cloud.usermanage.redis;

import io.lettuce.core.RedisURI;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * REDIS监控器配置
 *
 * @author bingwen.ai
 */
@Configuration
public class RedisListenerConfig {
    @Value("${redis.subscribe.cluster}")
    String redisSubURI;

    @Bean(destroyMethod = "shutdown")
    @ConditionalOnProperty(value = "spring.redis.cluster.enable", havingValue = "true")
    ClientResources clientResources() {
        return DefaultClientResources.create();
    }

    @Bean(destroyMethod = "shutdown")
    @ConditionalOnProperty(value = "spring.redis.cluster.enable", havingValue = "true")
    RedisClusterClient redisClusterClient(ClientResources clientResources) {
        RedisURI redisURI = RedisURI.create(redisSubURI);
        return RedisClusterClient.create(clientResources, redisURI);
    }

    @Bean(destroyMethod = "close")
    @ConditionalOnProperty(value = "spring.redis.cluster.enable", havingValue = "true")
    StatefulRedisClusterConnection<String, String> statefulRedisClusterConnection(RedisClusterClient redisClusterClient) {
        return redisClusterClient.connect();
    }

    @Bean
    @ConditionalOnProperty(value = "spring.redis.cluster.enable", havingValue = "false", matchIfMissing = true)
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        return container;
    }
}