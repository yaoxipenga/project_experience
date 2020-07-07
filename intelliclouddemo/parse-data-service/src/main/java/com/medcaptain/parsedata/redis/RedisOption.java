package com.medcaptain.parsedata.redis;

import com.medcaptain.dto.RestResult;
import com.medcaptain.parsedata.constant.CommonConstant;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 文件服务器中对redis使用的方法类
 *
 * @author Adam.Chen
 */

@Component
public class RedisOption {
    /**
     * 载入模板供静态方法调用
     */
    @Autowired
    private RedisTemplate<String, String> redisTemplateHelp;
    static private RedisTemplate<String, String> redisTemplate;

    @PostConstruct
    void init() {
        redisTemplate = redisTemplateHelp;
    }

    static public Integer increment(String key) {
        RedisAtomicLong count = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        Long increment = count.incrementAndGet();
        Integer id = increment.intValue();
        if (id.equals(CommonConstant.DEVICE_MESSAGE_ID_MAX)) {
            return RedisOption.KeyReset(key);
        }
        return id;
    }

    static public Integer decrement(String key) {
        RedisAtomicLong count = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        Long increment = count.decrementAndGet();
        return increment.intValue();
    }

    static public Boolean keyCheck(String key) {
        if (!redisTemplate.hasKey(key)) {
            decrement(key);
            return false;
        }
        return true;
    }

    static public Integer KeyReset(String key) {
        try {
            redisTemplate.delete(key);
            keyCheck(key);
            return increment(key).intValue();
        } catch (Exception e) {
            return -1;
        }
    }

    static public Boolean checkMassageID(String key, String id, Double score) {
        ZSetOperations<String, String> redis = redisTemplate.opsForZSet();
        if (null != redis.score(key, id)) {
//            System.out.println("1");
            return false;
        }
        Boolean temp = redis.add(key, id, score);
//        System.out.println("2" + temp);
        return temp;
    }

    static public Boolean addDevice(String productKey, String deviceName, Double score) {
        String key = CommonConstant.ONLINE_PRODUCT_DEVICE.replace("${productKey}", productKey);
        ZSetOperations<String, String> redis = redisTemplate.opsForZSet();
        return redis.add(key, deviceName, score);
    }

    static public Long delDevice(String productKey, String deviceName) {
        String key = CommonConstant.ONLINE_PRODUCT_DEVICE.replace("${productKey}", productKey);
        ZSetOperations<String, String> redis = redisTemplate.opsForZSet();
        return redis.remove(key, deviceName);
    }

    static public void limitMessageTime(String key, Double min, Double max) {
        ZSetOperations<String, String> redis = redisTemplate.opsForZSet();
        redis.removeRangeByScore(key, min, max);
    }

    static public void setOtaProgress(String productKey, String deviceName, Integer progress) {
        ValueOperations<String, String> redis = redisTemplate.opsForValue();
        redis.set(productKey + "." + deviceName + ".ota.progress", progress.toString(), 1800, TimeUnit.SECONDS);
    }

    static public void setFileProgress(Integer deviceTripleId, Integer progress, String fileName) {
        if (100 == progress) {
            redisTemplate.delete("deviceUploadLogProgress:" + deviceTripleId + "." + fileName);
            return;
        }
        ValueOperations<String, String> redis = redisTemplate.opsForValue();
        redis.set("deviceUploadLogProgress:" + deviceTripleId + "." + fileName, progress.toString(), 1800, TimeUnit.SECONDS);
    }

    static public Integer getUpgradingFirmwareId(String productKey, String deviceName) {
        ZSetOperations<String, String> redis = redisTemplate.opsForZSet();
        Double score = redis.score(CommonConstant.ONLINE_PRODUCT_DEVICE.replace("${productKey}", productKey), deviceName);
        return Integer.valueOf(score.intValue());
    }

    static public boolean deleteDeviceMessageId(String key) {
        return redisTemplate.delete(key);
    }
}
