package com.medcaptain.cloud.usermanage.redis;

import com.medcaptain.RedisUtilConstant;
import com.medcaptain.cloud.usermanage.service.UserService;
import com.medcaptain.utils.IntegerUtil;
import io.lettuce.core.cluster.models.partitions.RedisClusterNode;
import io.lettuce.core.cluster.pubsub.RedisClusterPubSubAdapter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * REDIS中key过期事件监听器
 *
 * @author bingwen.ai
 */
@Component
@ConditionalOnProperty(value = "spring.redis.cluster.enable", havingValue = "true")
public class RedisClusterKeyExpireListener extends RedisClusterPubSubAdapter {
    private static Logger logger = LoggerFactory.getLogger(RedisClusterKeyExpireListener.class);

    @Autowired
    UserService userService;

    @Override
    public void message(RedisClusterNode node, Object channel, Object message) {
        if (message != null) {
            String expiredKey = message.toString();
            int expireUserID = getExpireUserID(expiredKey);
            if (expireUserID > 0) {
                if (logger.isDebugEnabled()) {
                    logger.debug("用户token已过期,key = {} , userID = {}", expiredKey, expireUserID);
                }
                userTokenExpire(expireUserID);
            }
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

