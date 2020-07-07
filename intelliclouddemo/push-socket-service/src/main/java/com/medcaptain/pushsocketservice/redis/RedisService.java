package com.medcaptain.pushsocketservice.redis;

import com.medcaptain.RedisUtilConstant;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Component
@Configurable
public class RedisService {

    private static final String KEY_DEVICE_STATUS = "device.status";
    private static final String KEY_SOCKET = "Socket";
    /**
     * 热力图
     */
    public static final String REDIS_THERMAL_MAP = "statistics:thermalMap";
    /**
     * 统计数据
     */
    public static final String STATISTICS = "statistics:statistics";

    public static final String STATISTICS_SCHEDULED_TASKS = "statistics:scheduled.tasks";

    @Autowired
    private RedisTemplate<String, String> template;

    // key = 登录用户名称， value=websocket的sessionId
    private ConcurrentHashMap<String,String> redisHashMap = new ConcurrentHashMap<>(32);



    /**
     * 根据用户id获取用户对应的sessionId值
     * @param name
     * @return
     */
    public String getUserInfo(String name){
        BoundValueOperations<String,String> boundValueOperations = template.boundValueOps(name);
        return boundValueOperations.get();
    }



    /**
     * 在缓存中保存用户和websocket sessionid的信息
     * @param name
     * @param wsSessionId
     */
    public void add(String name, String wsSessionId){
        BoundValueOperations<String,String> boundValueOperations = template.boundValueOps(name);
        boundValueOperations.set(wsSessionId,24 * 3600, TimeUnit.SECONDS);
    }

    /**
     * 从缓存中删除用户的信息
     * @param name
     */
    public boolean del(String name){
        return template.execute(new RedisCallback<Boolean>() {

            @Override
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] rawKey = template.getStringSerializer().serialize(name);
                return connection.del(rawKey) > 0;
            }
        }, true);
    }

    /**
     * 根据用户id获取用户对应的sessionId值
     * @param name
     * @return
     */
    public String get(String name){
        BoundValueOperations<String,String> boundValueOperations = template.boundValueOps(name);
        return boundValueOperations.get();
    }


    /**
     * 获取Redis中的用户信息
     *
     * @param token 用户token
     * @return 存储在REDIS中的用户信息
     */
    public String getCacheUser(String token) {
        if (StringUtils.isEmpty(token)) return null;
        String tokenPattern = RedisUtilConstant.KEY_WEB_USER_TOKEN_PREFIX + "*:" + token;
        Set<String> tokenKeys = template.keys(tokenPattern);
        if (tokenKeys != null && tokenKeys.size() > 0) {
            String userTokenKey = tokenKeys.iterator().next();
            return getUserTokenFromRedis(userTokenKey);
        }
        return null;
    }

    private String getUserTokenFromRedis(String userTokenKey) {
        ValueOperations<String, String> operations = template.opsForValue();
        return operations.get(userTokenKey);
    }

}
