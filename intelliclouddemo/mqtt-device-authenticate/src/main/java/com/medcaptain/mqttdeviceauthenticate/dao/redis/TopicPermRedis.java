package com.medcaptain.mqttdeviceauthenticate.dao.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

/***
 * Redis查询话题读写权限接口
 *
 * @author hengyuluo
 * @date 2018/11/1
 *
 */
@Repository
public class TopicPermRedis {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);

    }

    public Object getTopic(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    public boolean setTopic(String key, Map topicList) {
        try {
            redisTemplate.opsForHash().put(key, topicList.get("topic").toString(), topicList.get("permission").toString());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
