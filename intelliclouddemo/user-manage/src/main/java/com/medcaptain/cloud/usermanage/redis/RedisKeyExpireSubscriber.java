package com.medcaptain.cloud.usermanage.redis;


import com.medcaptain.logging.ExceptionLog;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.pubsub.StatefulRedisClusterPubSubConnection;
import io.lettuce.core.cluster.pubsub.api.async.NodeSelectionPubSubAsyncCommands;
import io.lettuce.core.cluster.pubsub.api.async.PubSubAsyncNodeSelection;
import io.lettuce.core.pubsub.RedisPubSubAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * REDIS中key过期话题订阅
 *
 * @author bingwen.ai
 */
@Component
@ConditionalOnProperty(value = "spring.redis.cluster.enable", havingValue = "true")
public class RedisKeyExpireSubscriber extends RedisPubSubAdapter {
    private static Logger logger = LoggerFactory.getLogger(RedisKeyExpireSubscriber.class);

    private static final String EXPIRED_CHANNEL = "__keyevent@0__:expired";

    @Resource
    RedisClusterClient clusterClient;

    @Autowired
    private RedisClusterKeyExpireListener redisKeyExpireListener;

    @PostConstruct
    public void subscribe() {
        try {
            StatefulRedisClusterPubSubConnection<String, String> subscribeConnection = clusterClient.connectPubSub();
            subscribeConnection.setNodeMessagePropagation(true);
            subscribeConnection.addListener(redisKeyExpireListener);
            PubSubAsyncNodeSelection<String, String> masters = subscribeConnection.async().masters();
            NodeSelectionPubSubAsyncCommands<String, String> commands = masters.commands();
            commands.subscribe(EXPIRED_CHANNEL);
        } catch (Exception ex) {
            ExceptionLog exceptionLog = new ExceptionLog(ex, "订阅redis键过期事件", null);
            logger.error(exceptionLog.toString());
        }
    }
}

