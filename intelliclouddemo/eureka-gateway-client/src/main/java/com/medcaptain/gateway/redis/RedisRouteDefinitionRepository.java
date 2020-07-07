package com.medcaptain.gateway.redis;

import com.alibaba.fastjson.JSON;
import com.medcaptain.gateway.entity.UserToken;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
//
//@Component
//public class RedisRouteDefinitionRepository  implements RouteDefinitionRepository {
//
//    public static final String GATEWAY_ROUTES = "gateway_routes";
//
//    @Resource
//    private StringRedisTemplate redisTemplate;
//
//    @Override
//    public Flux<RouteDefinition> getRouteDefinitions() {
////        System.out.println("getRouteDefinitions");
////        List<RouteDefinition> routeDefinitions = new ArrayList<>();
////        return Flux.fromIterable(routeDefinitions);
//        List<RouteDefinition> routeDefinitions = new ArrayList<>();
//        redisTemplate.opsForHash().values(GATEWAY_ROUTES).stream()
//                .forEach(routeDefinition -> routeDefinitions.add(JSON.parseObject(routeDefinition.toString(), RouteDefinition.class)));
//        return Flux.fromIterable(routeDefinitions);
//
//    }
//
//    @Override
//    public Mono<Void> save(Mono<RouteDefinition> route) {
//        return null;
//    }
//
//    @Override
//    public Mono<Void> delete(Mono<String> routeId) {
//        return null;
//    }
//}


/**
 * 使用Redis保存自定义路由配置（代替默认的InMemoryRouteDefinitionRepository）
 * <p/>
 * 存在问题：每次请求都会调用getRouteDefinitions，当网关较多时，会影响请求速度，考虑放到本地Map中，使用消息通知Map更新。
 *
 * @since 2018年7月9日 下午2:39:02
 */
@Component
public class RedisRouteDefinitionRepository implements RouteDefinitionRepository {

    private Logger logger = LoggerFactory.getLogger(RedisRouteDefinitionRepository.class);

    public static final String GATEWAY_ROUTES = "gateway_routes";
    /**
     * 用户信息
     */
    public static final String KEY_WEB_USER_TOKEN_PREFIX = "user.token.web:";
    /**
     * 网关黑名单url
     */
    public static final String URL_BLACKLIST = "gateway.url:blacklist";
    /**
     * 网关黑名单url,前缀
     */
    public static final String URL_BLACKLIST_PREFIX = "gateway.url:blacklist.prefix";
    /**
     * 网关白名单url
     */
    public static final String URL_WHITELIST = "gateway.url:whitelist";
    /**
     * 网关白名单url, 前缀
     */
    public static final String URL_WHITELIST_PREFIX = "gateway.url:whitelist.prefix";


    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        List<RouteDefinition> routeDefinitions = new ArrayList<>();
        redisTemplate.opsForHash().values(GATEWAY_ROUTES).stream().forEach(routeDefinition -> {
            routeDefinitions.add(JSON.parseObject(routeDefinition.toString(), RouteDefinition.class));
        });
        return Flux.fromIterable(routeDefinitions);
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return route
                .flatMap(routeDefinition -> {
                    redisTemplate.opsForHash().put(GATEWAY_ROUTES, routeDefinition.getId(),
                            JSON.toJSONString(routeDefinition));
                    return Mono.empty();
                });
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return routeId.flatMap(id -> {
            if (redisTemplate.opsForHash().hasKey(GATEWAY_ROUTES, id)) {
                redisTemplate.opsForHash().delete(GATEWAY_ROUTES, id);
                return Mono.empty();
            }
            return Mono.defer(() -> Mono.error(new NotFoundException("RouteDefinition not found: " + routeId)));
        });
    }

    /**
     * 获取Redis中的用户信息
     * 并续期
     *
     * @param token 用户token
     * @return 存储在REDIS中的用户信息
     */
    public UserToken getCacheUser(String token) {
        UserToken userToken = null;
        try {
            if (StringUtils.isEmpty(token)) {
                return null;
            }
            String tokenPattern = KEY_WEB_USER_TOKEN_PREFIX + "*:" + token;
            Set<String> tokenKeys = redisTemplate.keys(tokenPattern);
            if (tokenKeys != null && tokenKeys.size() > 0) {
                String userTokenKey = tokenKeys.iterator().next();
                userToken = getUserTokenFromRedis(userTokenKey);
            }
            // 网关层面，既然可以获取到用户信息，那么就将其用户续期。
            if (tokenKeys != null && tokenKeys.size() > 0) {
                long userTokenExpireSeconds = 30 * 60;
                if (userTokenExpireSeconds > 0) {
                    for (String key : tokenKeys) {
                        UserToken userInfo = getUserTokenFromRedis(key);
                        boolean isNotThirdPartUser = userInfo != null && userInfo.getUserInfo() != null
                                && userInfo.getUserInfo().getIsThirdPart() != 1;
                        // 第三方用户token不续期
                        if (isNotThirdPartUser) {
                            Boolean expired = redisTemplate.expire(key, userTokenExpireSeconds, TimeUnit.SECONDS);
                            if (!expired || expired == null) {
                                logger.error("为token续期失败:{}", userToken.toJSON());
                            }
                        }
                    }
                }
            }
            return userToken;
        } catch (Exception ex) {
            logger.error("userToken:{}", userToken);
            return null;
        }
    }


    private UserToken getUserTokenFromRedis(String userTokenKey) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        String userTokenJSON = operations.get(userTokenKey);
        return UserToken.parseFromJSON(userTokenJSON);
    }

    /**
     * 不需要鉴权的url
     *
     * @return true 不鉴权  false 鉴权
     */
    public boolean filterUrlPath(String urlPath) {
        if (urlPath.isEmpty()) {
            return false;
        }
        /**
         *  sadd gateway.url:whitelist /user/login /file /file/file /user/cloud_login /user/captcha /user/logout
         *  sadd gateway.url:whitelist.prefix /websocket /mqtt /parse
         */

        // 白名单
        // 绝对路径相同
        SetOperations<String, String> opsForSet = redisTemplate.opsForSet();
        Set<String> urlWhitelist = opsForSet.members(URL_WHITELIST);
        if (urlWhitelist != null) {
            for (String url : urlWhitelist) {
                if (urlPath.equals(url)) {
                    return true;
                }
            }
        }

        // 前缀路径相同
        Set<String> urlWhitelistPrefix = opsForSet.members(URL_WHITELIST_PREFIX);
        if (urlWhitelistPrefix != null) {
            for (String url : urlWhitelistPrefix) {
                if (url.equals(urlPath.substring(0, url.length()))) {
                    return true;
                }
            }
        }

        // 黑名单
        // 绝对路径相同
        Set<String> urlBlacklist = opsForSet.members(URL_BLACKLIST);
        if (urlBlacklist != null) {
            for (String url : urlBlacklist) {
                if (urlPath.equals(url)) {
                    return false;
                }
            }
        }

        // 前缀路径相同
        Set<String> urlBlacklistPrefix = opsForSet.members(URL_BLACKLIST_PREFIX);
        if (urlBlacklistPrefix != null) {
            for (String url : urlBlacklistPrefix) {
                if (url.equals(urlPath.substring(0, url.length()))) {
                    return false;
                }
            }
        }

        // 其他情况，鉴权
        return false;
    }

}