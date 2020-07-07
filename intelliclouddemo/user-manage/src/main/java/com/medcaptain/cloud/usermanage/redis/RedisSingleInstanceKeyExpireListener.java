package com.medcaptain.cloud.usermanage.redis;

import com.medcaptain.RedisUtilConstant;
import com.medcaptain.cloud.usermanage.service.UserService;
import com.medcaptain.utils.IntegerUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

/**
 * REDIS键过期监控器
 * <p>用于单实例REDIS环境下用户token到期后更新用户状态到离线</p>
 *
 * @author bingwen.ai
 */
@Component
@ConditionalOnProperty(value = "spring.redis.cluster.enable", havingValue = "false", matchIfMissing = true)
public class RedisSingleInstanceKeyExpireListener extends KeyExpirationEventMessageListener {
    private Logger logger = LoggerFactory.getLogger(RedisSingleInstanceKeyExpireListener.class);

    @Autowired
    RedisService redisService;

    @Autowired
    UserService userService;

    public RedisSingleInstanceKeyExpireListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = message.toString();
        int expireUserID = getExpireUserID(expiredKey);
        if (expireUserID > 0) {
            if (logger.isDebugEnabled()) {
                logger.debug("用户token已过期,key = {} , userID = {}", expiredKey, expireUserID);
            }
            userTokenExpire(expireUserID);
        }
    }

    private int getExpireUserID(String expiredKey) {
        int userID = 0;
        if (StringUtils.isNotEmpty(expiredKey) && expiredKey.startsWith(RedisUtilConstant.KEY_WEB_USER_TOKEN_PREFIX)) {
            String userIDString = expiredKey.replace(RedisUtilConstant.KEY_WEB_USER_TOKEN_PREFIX, "");
            userIDString = userIDString.substring(0, userIDString.indexOf(':'));
            userID = IntegerUtil.tryParse(userIDString, 0);
        }
        return userID;
    }

    /**
     * 用户token过期，更新用户状态到离线
     *
     * @param expireUserID token过期用户编号
     */
    private void userTokenExpire(int expireUserID) {
        userService.updateStatus(expireUserID, false);
    }
}