package com.medcaptain.cloud.usermanage.redis;

import com.medcaptain.RedisUtilConstant;
import com.medcaptain.cloud.usermanage.config.UserManageProperties;
import com.medcaptain.cloud.usermanage.constant.FrontendResourceType;
import com.medcaptain.cloud.usermanage.entity.*;
import com.medcaptain.cloud.usermanage.service.*;
import com.medcaptain.logging.ExceptionLog;
import com.medcaptain.utils.CharsetUtil;
import com.medcaptain.utils.IntegerUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.medcaptain.constant.TimeConstant.SECONDS_ONE_DAY;

/**
 * REDIS缓存操作服务
 *
 * @author bingwen.ai
 */
@Component
public class RedisService {
    private Logger logger = LoggerFactory.getLogger(RedisService.class);

    private static final String KEY_WEB_USER_LOGIN_FAILURE_PREFIX = "user.login.failure:";

    private static final String KEY_WEB_LOGIN_VERIFY_CODE_PREFIX = "user.login.verifyCode:";

    private static final String KEY_ROLE_PERMISSION = "role.permission:";

    private static final String RESET_PASSWORD_CODE_PREFIX = "user.resetPassword.token:";

    @Autowired
    private UserManageProperties userManageProperties;

    @Autowired
    private RedisTemplate<String, String> redis;

    @Autowired
    private ResourceMappingService resourceMappingService;

    @Autowired
    private BackendResourceService backendResourceService;

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private FrontendResourcePermissionService frontendResourcePermissionService;

    @Autowired
    private FrontendResourceService frontendResourceService;

    /**
     * 获取Redis中的用户信息
     *
     * @param token 用户token
     * @return 存储在REDIS中的用户信息
     */
    public UserToken getCacheUser(String token) {
        try {
            UserToken userToken = null;
            if (StringUtils.isBlank(token)) {
                return null;
            }
            String tokenPattern = RedisUtilConstant.KEY_WEB_USER_TOKEN_PREFIX + "*:" + token;
            Set<String> tokenKeys = redis.keys(tokenPattern);
            if (tokenKeys != null && tokenKeys.size() > 0) {
                String userTokenKey = tokenKeys.iterator().next();
                userToken = getUserTokenFromRedis(userTokenKey);
            }
            return userToken;
        } catch (Exception ex) {
            ExceptionLog exceptionLog = new ExceptionLog(ex, "获取缓存用户信息");
            logger.error(exceptionLog.toString());
            return null;
        }
    }

    /**
     * 登录后保存用户的token
     *
     * @param loginUser 登录用户信息
     * @param token     用户token
     * @param clientID  客户端ID
     * @return token有效期（秒）
     */
    public long saveUserToken(UserInfo loginUser, String token, String clientID) {
        if (loginUser == null || loginUser.getUserId() <= 0
                || StringUtils.isBlank(token) || StringUtils.isBlank(clientID)) {
            throw new IllegalArgumentException("UserID/ClientID/token can not be null.");
        }
        int userID = loginUser.getUserId();
        UserToken loginUserToken = new UserToken();
        loginUserToken.setUserInfo(loginUser);
        loginUserToken.setClientID(clientID);
        //TODO(优化) 区分各中客户端类型（web/app/pc)时使用抽象工厂模式
        try {
            String userPattern = RedisUtilConstant.KEY_WEB_USER_TOKEN_PREFIX + userID + ":" + "*";
            Set<String> userTokenKeys = redis.keys(userPattern);
            if (loginUser.getIsThirdPart() == 1) {
                // 第三方用户的token特殊处理
                deleteThirdPartKeys(userTokenKeys);
            } else {
                deleteKeys(userTokenKeys);
            }
            String key = RedisUtilConstant.KEY_WEB_USER_TOKEN_PREFIX + userID + ":" + token;
            saveUserTokenToRedis(loginUserToken, key);
            return expireUserToken(loginUserToken, key);
        } catch (Exception ex) {
            Map<String, Object> parameters = new HashMap<>(4);
            parameters.put("userID", loginUser.getUserId());
            parameters.put("userName", loginUser.getUserName());
            parameters.put("token", token);
            parameters.put("clientID", clientID);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "保存用户信息缓存", parameters);
            logger.error(exceptionLog.toString());
            return -1;
        }
    }

    /**
     * 用户信息变更后，修改已登录用户缓存信息
     *
     * @param loginUser 已登录用户
     */
    public void updateUserToken(UserInfo loginUser) {
        try {
            if (loginUser == null || loginUser.getUserId() == null || loginUser.getUserId() <= 0) {
                return;
            }
            String userPattern = RedisUtilConstant.KEY_WEB_USER_TOKEN_PREFIX + loginUser.getUserId() + ":" + "*";
            Set<String> userTokenKeys = redis.keys(userPattern);
            if (userTokenKeys != null && userTokenKeys.size() > 0) {
                for (String userTokenKey : userTokenKeys) {
                    UserToken userToken = getUserTokenFromRedis(userTokenKey);
                    if (userToken != null) {
                        userToken.setUserInfo(loginUser);
                        saveUserTokenToRedis(userToken, userTokenKey);
                    }
                }
            }
        } catch (Exception ex) {
            Map<String, Object> parameters = new HashMap<>(2);
            parameters.put("userID", loginUser.getUserId());
            parameters.put("userName", loginUser.getUserName());
            ExceptionLog exceptionLog = new ExceptionLog(ex, "更新缓存用户信息", parameters);
            logger.error(exceptionLog.toString());
        }
    }

    /**
     * 移除用户token
     *
     * @param token 用户token
     */
    public boolean removeToken(String token) {
        try {
            if (StringUtils.isNotBlank(token)) {
                String userPattern = RedisUtilConstant.KEY_WEB_USER_TOKEN_PREFIX + "*:" + token;
                Set<String> userTokenKeys = redis.keys(userPattern);
                deleteKeys(userTokenKeys);
            }
            return true;
        } catch (Exception ex) {
            Map<String, Object> parameters = new HashMap<>(1);
            parameters.put("token", token);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "移除用户信息缓存", parameters);
            logger.error(exceptionLog.toString());
            return false;
        }
    }

    /**
     * 按用户移除token
     *
     * @param userID 用户编号
     * @return 移除结果
     */
    public boolean removeToken(int userID) {
        try {
            if (userID > 0) {
                String userPattern = RedisUtilConstant.KEY_WEB_USER_TOKEN_PREFIX + userID + ":*";
                Set<String> userTokenKeys = redis.keys(userPattern);
                deleteKeys(userTokenKeys);
            }
            return true;
        } catch (Exception ex) {
            Map<String, Object> parameters = new HashMap<>(1);
            parameters.put("userID", userID);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "按用户编号移除用户信息缓存", parameters);
            logger.error(exceptionLog.toString());
            return false;
        }
    }

    /**
     * 检查token、clientID对是否过期
     *
     * @param token    用户token
     * @param clientID HTTP请求回话编号
     * @return true - 已过期; false - 未过期
     */
    public boolean isTokenExpired(String token, String clientID) {
        try {
            UserToken userToken = getCacheUser(token);
            return userToken == null || !userToken.getClientID().equals(clientID);
        } catch (Exception ex) {
            Map<String, Object> parameters = new HashMap<>(2);
            parameters.put("token", token);
            parameters.put("clientID", clientID);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "判断token是否过期", parameters);
            logger.error(exceptionLog.toString());
            return true;
        }
    }

    /**
     * 检查token、clientID对是否过期
     *
     * @param token 用户token
     * @return true - 已过期; false - 未过期
     */
    public boolean isTokenExpired(String token) {
        try {
            String tokenPattern = RedisUtilConstant.KEY_WEB_USER_TOKEN_PREFIX + "*:" + token;
            Set<String> keys = redis.keys(tokenPattern);
            return keys == null;
        } catch (Exception ex) {
            Map<String, Object> parameters = new HashMap<>(1);
            parameters.put("token", token);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "判断token是否过期", parameters);
            logger.error(exceptionLog.toString());
            return true;
        }
    }

    /**
     * 增加登录失败次数
     *
     * @param clientID 客户端ID
     */
    public int increaseLoginFailTimes(String clientID) {
        if (StringUtils.isBlank(clientID)) {
            return 0;
        }
        try {
            String key = KEY_WEB_USER_LOGIN_FAILURE_PREFIX + clientID;
            ValueOperations<String, String> operations = redis.opsForValue();
            operations.increment(key, 1);
            long loginFailExpireSeconds = userManageProperties.getLoginFailExpireSeconds();
            if (loginFailExpireSeconds > 0) {
                redis.expire(key, loginFailExpireSeconds, TimeUnit.SECONDS);
            }
            return IntegerUtil.tryParse(operations.get(key), 0);
        } catch (Exception ex) {
            Map<String, Object> parameters = new HashMap<>(1);
            parameters.put("clientID", clientID);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "增加登录失败次数", parameters);
            logger.error(exceptionLog.toString());
            return 0;
        }
    }

    public void removeLoginFailTimes(String clientID) {
        try {
            String key = KEY_WEB_USER_LOGIN_FAILURE_PREFIX + clientID;
            redis.delete(key);
        } catch (Exception ex) {
            Map<String, Object> parameters = new HashMap<>(1);
            parameters.put("clientID", clientID);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "移除登录失败次数", parameters);
            logger.error(exceptionLog.toString());
        }
    }

    /**
     * 获取用户登录失败次数
     *
     * @param clientID 客户端ID
     * @return 登录失败次数
     */
    public int getLoginFailTimes(String clientID) {
        try {
            String key = KEY_WEB_USER_LOGIN_FAILURE_PREFIX + clientID;
            return IntegerUtil.tryParse(getRedisValue(key), 0);
        } catch (Exception ex) {
            Map<String, Object> parameters = new HashMap<>(1);
            parameters.put("clientID", clientID);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "获取登录失败次数", parameters);
            logger.error(exceptionLog.toString());
            return 0;
        }
    }

    /**
     * <h1>获取存在REDIS中的验证码，获取后即从REDIS中删除</h1>
     *
     * @param clientID 客户端ID
     * @return REDIS中的验证码
     */
    public String getCaptcha(String clientID) {
        try {
            String key = KEY_WEB_LOGIN_VERIFY_CODE_PREFIX + clientID;
            String captcha = getRedisValue(key);
            redis.delete(key);
            return captcha;
        } catch (Exception ex) {
            Map<String, Object> parameters = new HashMap<>(1);
            parameters.put("clientID", clientID);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "获取缓存验证码", parameters);
            logger.error(exceptionLog.toString());
            return null;
        }
    }

    /**
     * <h1>将验证码存入REDIS</h1>
     *
     * @param clientID   客户端ID
     * @param verifyCode 验证码
     */
    public boolean saveCaptcha(String clientID, String verifyCode) {
        try {
            if (StringUtils.isBlank(clientID) || StringUtils.isBlank(verifyCode)) {
                return false;
            }
            String key = KEY_WEB_LOGIN_VERIFY_CODE_PREFIX + clientID;
            setRedisValue(key, verifyCode);
            long captchaExpireSeconds = userManageProperties.getCaptchaExpireSeconds();
            if (captchaExpireSeconds > 0) {
                redis.expire(key, captchaExpireSeconds, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception ex) {
            Map<String, Object> parameters = new HashMap<>(2);
            parameters.put("clientID", clientID);
            parameters.put("verifyCode", verifyCode);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "缓存验证码", parameters);
            logger.error(exceptionLog.toString());
            return false;
        }
    }

    /**
     * 为用户token续期
     *
     * @param userToken 用户token
     * @return true - 成功 ; false - 失败
     */
    public boolean extendWebUserTokenExpire(String userToken) {
        try {
            if (StringUtils.isBlank(userToken)) {
                logger.error("用户token为空");
                return false;
            }
            String tokenPattern = RedisUtilConstant.KEY_WEB_USER_TOKEN_PREFIX + "*:" + userToken;
            Set<String> tokenKeys = redis.keys(tokenPattern);
            boolean expireComplete = true;
            if (tokenKeys != null && tokenKeys.size() > 0) {
                long userTokenExpireSeconds = userManageProperties.getUserTokenExpireSeconds();
                if (userTokenExpireSeconds > 0) {
                    for (String key : tokenKeys) {
                        UserToken userInfo = getUserTokenFromRedis(key);
                        boolean isNotThirdPartUser = userInfo != null && userInfo.getUserInfo() != null
                                && userInfo.getUserInfo().getIsThirdPart() != 1;
                        // 第三方用户token不续期
                        if (isNotThirdPartUser) {
                            Boolean expired = redis.expire(key, userTokenExpireSeconds, TimeUnit.SECONDS);
                            if (expired != null) {
                                expireComplete = expireComplete && expired;
                            }
                        }
                    }
                }
            }
            return expireComplete;
        } catch (Exception ex) {
            Map<String, Object> parameters = new HashMap<>(1);
            parameters.put("userToken", userToken);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "为token续期", parameters);
            logger.error(exceptionLog.toString());
            return false;
        }
    }

    /**
     * 为角色添加前端权限
     *
     * @param roleID             角色编号
     * @param frontendResourceID 前端资源编号
     */
    public void addFrontendResourcePermission(int roleID, int frontendResourceID) {
        try {
            FrontendResource frontendResource = frontendResourceService.getResource(frontendResourceID);
            if (frontendResource != null && frontendResource.getIsEnable() == 1) {
                if (frontendResource.getResourceType() == FrontendResourceType.RESOURCE.ordinal()) {
                    addBackendResourcePermissions(roleID, frontendResourceID);
                } else {
                    addOperatePermission(roleID, frontendResource);
                }
                long rolePermissionExpireDays = userManageProperties.getRolePermissionExpireDays();
                if (rolePermissionExpireDays > 0) {
                    String key = KEY_ROLE_PERMISSION + roleID + ":" + frontendResourceID;
                    redis.expire(key, rolePermissionExpireDays, TimeUnit.DAYS);
                }
            }
        } catch (Exception ex) {
            Map<String, Object> parameters = new HashMap<>(2);
            parameters.put("roleID", roleID);
            parameters.put("frontendResourceID", frontendResourceID);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "添加前端资源权限缓存", parameters);
            logger.error(exceptionLog.toString());
        }
    }

    /**
     * 将前端资源对应的后端资源列表加载到redis
     *
     * @param roleID             角色编号
     * @param frontendResourceID 前端资源编号
     */
    private void addBackendResourcePermissions(int roleID, int frontendResourceID) {
        try {
            List<ResourceMapping> backendResourceList = resourceMappingService.listResourceMappings(frontendResourceID);
            if (backendResourceList.size() > 0) {
                String key = KEY_ROLE_PERMISSION + roleID + ":" + frontendResourceID;
                redis.executePipelined((RedisConnection connection) -> {
                    connection.openPipeline();
                    try {
                        for (ResourceMapping resourceMapping : backendResourceList) {
                            BackendResource backendResource = resourceMapping.getBackendResource();
                            if (backendResource != null) {
                                String backendResourceURL = backendResource.getResourceUrl() + "|"
                                        + backendResource.getRequestType();
                                connection.sAdd(key.getBytes(CharsetUtil.UTF8),
                                        backendResourceURL.getBytes(CharsetUtil.UTF8));
                            }
                        }
                    } finally {
                        connection.closePipeline();
                    }
                    return null;
                });
            }
        } catch (Exception ex) {
            Map<String, Object> parameters = new HashMap<>(2);
            parameters.put("roleID", roleID);
            parameters.put("frontendResourceID", frontendResourceID);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "添加前端对应的后端资源到缓存", parameters);
            logger.error(exceptionLog.toString());
        }
    }

    /**
     * 将前端功能权限加载到redis
     *
     * @param roleID           角色编号
     * @param frontendResource 前端资源
     */
    private void addOperatePermission(int roleID, FrontendResource frontendResource) {
        try {
            int frontendResourceType = (int) frontendResource.getResourceType();
            if (frontendResource != null && frontendResourceType != FrontendResourceType.RESOURCE.ordinal()) {
                int operatePermissionID = frontendResource.getFrontendResourceId();
                String key = KEY_ROLE_PERMISSION + roleID + ":" + operatePermissionID;
                setRedisValue(key, "");
            }
        } catch (Exception ex) {
            Map<String, Object> parameters = new HashMap<>(2);
            parameters.put("roleID", roleID);
            parameters.put("frontendResourceID", frontendResource.getFrontendResourceId());
            ExceptionLog exceptionLog = new ExceptionLog(ex, "添加前端功能权限", parameters);
            logger.error(exceptionLog.toString());
        }
    }

    /**
     * 移除角色的前端资源权限
     *
     * @param roleID             角色编号
     * @param frontendResourceID 前端资源编号
     */
    public void removeFrontendResourcePermission(int roleID, int frontendResourceID) {
        String key = KEY_ROLE_PERMISSION + roleID + ":" + frontendResourceID;
        redis.delete(key);
    }

    /**
     * 添加前后端资源映射关系后更新权限缓存
     *
     * @param frontendResourceID 前端资源ID
     * @param backendResourceID  后端资源ID
     */
    public void addResourceMapping(int frontendResourceID, int backendResourceID) {
        try {
            BackendResource backendResource = backendResourceService.getResource(backendResourceID);
            if (backendResource == null) {
                return;
            } else {
                if (StringUtils.isBlank(backendResource.getResourceUrl())) {
                    return;
                }
            }

            final String backendResourceURL = backendResource.getResourceUrl() + "|" + backendResource.getRequestType();
            String keyPattern = KEY_ROLE_PERMISSION + "*:" + frontendResourceID;
            Set<String> keys = redis.keys(keyPattern);
            if (keys != null && keys.size() > 0) {
                redis.executePipelined((RedisConnection connection) -> {
                    connection.openPipeline();
                    try {
                        for (String key : keys) {
                            connection.sAdd(key.getBytes(CharsetUtil.UTF8),
                                    backendResourceURL.getBytes(CharsetUtil.UTF8));
                            long rolePermissionExpireDays = userManageProperties.getRolePermissionExpireDays();
                            if (rolePermissionExpireDays > 0) {
                                connection.expire(key.getBytes(CharsetUtil.UTF8),
                                        rolePermissionExpireDays * SECONDS_ONE_DAY);
                            }
                        }
                    } finally {
                        connection.closePipeline();
                    }
                    return null;
                });
            }
        } catch (Exception ex) {
            Map<String, Object> parameters = new HashMap<>(2);
            parameters.put("frontendResourceID", frontendResourceID);
            parameters.put("backendResourceID", backendResourceID);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "添加前后端资源映射关系后更新权限缓存", parameters);
            logger.error(exceptionLog.toString());
        }
    }

    /**
     * 移除前后端资源映射关系后更新权限缓存
     *
     * @param frontendResourceID 前端资源ID
     * @param backendResourceID  后端资源ID
     */
    public void removeResourceMapping(int frontendResourceID, int backendResourceID) {
        try {
            BackendResource backendResource = backendResourceService.getResource(backendResourceID);
            if (backendResource == null || StringUtils.isBlank(backendResource.getResourceUrl())) {
                return;
            }

            final String backendResourceURL = backendResource.getResourceUrl() + "|" + backendResource.getRequestType();
            String keyPattern = KEY_ROLE_PERMISSION + "*:" + frontendResourceID;
            Set<String> keys = redis.keys(keyPattern);
            if (keys != null && keys.size() > 0) {
                redis.executePipelined((RedisConnection connection) -> {
                    connection.openPipeline();
                    try {
                        for (String key : keys) {
                            connection.sRem(key.getBytes(CharsetUtil.UTF8),
                                    backendResourceURL.getBytes(CharsetUtil.UTF8));
                        }
                    } finally {
                        connection.closePipeline();
                    }
                    return null;
                });
            }
        } catch (Exception ex) {
            Map<String, Object> parameters = new HashMap<>(2);
            parameters.put("frontendResourceID", frontendResourceID);
            parameters.put("backendResourceID", backendResourceID);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "移除前后端资源映射关系后更新权限缓存", parameters);
            logger.error(exceptionLog.toString());
        }
    }

    /**
     * 检查角色是否有操作后端接口的权限
     * 先检查redis是否有权限相关的key，没有则从数据库加载
     *
     * @param roleID             角色编号
     * @param backendResourceURL 后端资源URL
     * @return true - 有操作权限 ； false -无操作权限
     */
    public boolean checkBackendResourcePermission(int roleID, String backendResourceURL, int requestType) {
        try {
            SetOperations<String, String> operations = redis.opsForSet();
            String backendResource = backendResourceURL + "|" + requestType;
            boolean hasPermission = false;
            List<RolePermission> rolePermissionList =
                    rolePermissionService.listPermissionByBackendResource(backendResourceURL, requestType);
            for (RolePermission frontendResourcePermission : rolePermissionList) {
                int frontendResourceID = frontendResourcePermission.getFrontendResourceId();
                String permissionKey = KEY_ROLE_PERMISSION + roleID + ":" + Integer.toString(frontendResourceID);
                Boolean hasKey = redis.hasKey(permissionKey);
                if (hasKey != null && hasKey) {
                    long rolePermissionExpireDays = userManageProperties.getRolePermissionExpireDays();
                    if (rolePermissionExpireDays > 0) {
                        redis.expire(permissionKey, rolePermissionExpireDays, TimeUnit.DAYS);
                    }
                } else {
                    // 重新加载权限到redis
                    addFrontendResourcePermission(roleID, frontendResourceID);
                }
                Boolean isMember = operations.isMember(permissionKey, backendResource);
                if (isMember != null) {
                    hasPermission = hasPermission || isMember;
                }
                if (hasPermission) {
                    return hasPermission;
                }
            }
            return hasPermission;
        } catch (Exception ex) {
            Map<String, Object> parameters = new HashMap<>(3);
            parameters.put("roleID", roleID);
            parameters.put("backendResourceURL", backendResourceURL);
            parameters.put("requestType", requestType);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "检查角色是否有后端资源权限", parameters);
            logger.error(exceptionLog.toString());
            return false;
        }
    }

    /**
     * 检查角色的功能操作权限
     *
     * @param roleID      角色编号
     * @param operateName 操作权限名称
     * @return true - 有权限 ；false - 无权限
     */
    public boolean hasOperatePermission(int roleID, String operateName) {
        try {
            FrontendResource frontendResource = frontendResourceService.getResourceByName(operateName,
                    (byte) FrontendResourceType.OPERATE_FUNCTION.ordinal());
            if (frontendResource != null) {
                String key = KEY_ROLE_PERMISSION + roleID + ":" + Integer.toString(frontendResource.getFrontendResourceId());
                Boolean hasKey = redis.hasKey(key);
                if (hasKey == null || !hasKey) {
                    FrontendResourcePermission permission =
                            frontendResourcePermissionService.getPermission(frontendResource.getFrontendResourceId(), roleID);
                    if (permission != null) {
                        setRedisValue(key, "");
                    }
                }
                hasKey = redis.hasKey(key);
                if (hasKey != null && hasKey) {
                    long rolePermissionExpireDays = userManageProperties.getRolePermissionExpireDays();
                    if (rolePermissionExpireDays > 0) {
                        redis.expire(key, rolePermissionExpireDays, TimeUnit.DAYS);
                    }
                    return true;
                }
                return false;
            }
            return false;
        } catch (Exception ex) {
            Map<String, Object> parameters = new HashMap<>(2);
            parameters.put("roleID", roleID);
            parameters.put("operateName", operateName);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "判断角色是否有操作权限", parameters);
            logger.error(exceptionLog.toString());
            return false;
        }
    }

    /**
     * 初始化REDIS中的角色权限信息
     */
    public void initRolePermission() {
        try {
            clearRolePermission();  // 先清空REDIS中的权限缓存
            List<RolePermission> rolePermissions = rolePermissionService.listAllPermissions();
            if (rolePermissions.size() > 0) {
                redis.executePipelined((RedisConnection connection) -> {
                    connection.openPipeline();
                    try {
                        for (RolePermission rolePermission : rolePermissions) {
                            if (rolePermission == null) {
                                continue;
                            }
                            int roleID = rolePermission.getRoleId();
                            int frontendResourceID = rolePermission.getFrontendResourceId();
                            String backendResourceURL = rolePermission.getResourceUrl() + "|" +
                                    (int) rolePermission.getRequestType();
                            if (roleID <= 0 || frontendResourceID <= 0 || StringUtils.isBlank(backendResourceURL)) {
                                continue;
                            }
                            String key = KEY_ROLE_PERMISSION + roleID + ":" + frontendResourceID;
                            connection.sAdd(key.getBytes(CharsetUtil.UTF8), backendResourceURL.getBytes(CharsetUtil.UTF8));
                            long rolePermissionExpireDays = userManageProperties.getRolePermissionExpireDays();
                            if (rolePermissionExpireDays > 0) {
                                connection.expire(key.getBytes(CharsetUtil.UTF8), rolePermissionExpireDays * SECONDS_ONE_DAY);
                            }
                        }
                    } finally {
                        connection.closePipeline();
                    }
                    return null;
                });
            }
        } catch (Exception ex) {
            ExceptionLog exceptionLog = new ExceptionLog(ex, "初始化角色权限信息");
            logger.error(exceptionLog.toString());
        }
    }

    public boolean removeRolePermissionByBackendResource(int backendResourceID) {
        try {
            List<RolePermission> rolePermissions = rolePermissionService.getPermissionsByBackendResource(backendResourceID);
            if (rolePermissions.size() > 0) {
                redis.executePipelined((RedisConnection connection) -> {
                    connection.openPipeline();
                    try {
                        for (RolePermission rolePermission : rolePermissions) {
                            String key = KEY_ROLE_PERMISSION + Integer.toString(rolePermission.getRoleId()) + ":"
                                    + Integer.toString(rolePermission.getFrontendResourceId());
                            String backendResourceURL = rolePermission.getResourceUrl() + "|"
                                    + rolePermission.getRequestType();
                            connection.sRem(key.getBytes(CharsetUtil.UTF8),
                                    backendResourceURL.getBytes(CharsetUtil.UTF8));
                        }
                    } finally {
                        connection.closePipeline();
                    }
                    return null;
                });
            }
            return true;
        } catch (Exception ex) {
            Map<String, Object> parameters = new HashMap<>(1);
            parameters.put("backendResourceID", backendResourceID);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "移除后端资源授权", parameters);
            logger.error(exceptionLog.toString());
            return false;
        }
    }

    public boolean removeRolePermissionByFrontendResource(int frontendResourceID) {
        try {
            String keyPattern = KEY_ROLE_PERMISSION + "*:" + frontendResourceID;
            Set<String> keys = redis.keys(keyPattern);
            deleteKeys(keys);
            return true;
        } catch (Exception ex) {
            Map<String, Object> parameters = new HashMap<>(1);
            parameters.put("frontendResourceID", frontendResourceID);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "移除前端资源授权", parameters);
            logger.error(exceptionLog.toString());
            return false;
        }
    }

    /**
     * 保存用户重置密码token
     *
     * @param organizationID 机构编号
     * @param userName       管理员用户登录名
     * @param token          初始token
     */
    public boolean saveUserResetToken(int organizationID, String userName, String token) {
        try {
            String key = RESET_PASSWORD_CODE_PREFIX + organizationID + ":" + userName;
            setRedisValue(key, token);
            long initializeTokenExpireSeconds = userManageProperties.getUserResetPasswordTokenExpireSeconds();
            if (initializeTokenExpireSeconds > 0) {
                redis.expire(key, initializeTokenExpireSeconds, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public String getUserResetPasswordToken(int organizationID, String userName) {
        String key = RESET_PASSWORD_CODE_PREFIX + organizationID + ":" + userName;
        return getRedisValue(key);
    }

    /**
     * 移除机构管理员初始化密码token
     *
     * @param organizationID 机构编号
     * @param userName       用户名
     */
    public void removeUserResetPasswordToken(int organizationID, String userName) {
        String key = RESET_PASSWORD_CODE_PREFIX + organizationID + ":" + userName;
        redis.delete(key);
    }

    /**
     * 是否存在用户token
     *
     * @param userID 用户编号
     * @return true = 存在 ； false = 不存在
     */
    public boolean existUserToken(int userID) {
        if (userID > 0) {
            String userTokenKeyPattern = RedisUtilConstant.KEY_WEB_USER_TOKEN_PREFIX + userID + ":*";
            Set<String> keySet = redis.keys(userTokenKeyPattern);
            return keySet != null && keySet.size() > 0;
        }
        return false;
    }

    private void clearRolePermission() {
        Set<String> keys = redis.keys(KEY_ROLE_PERMISSION + "*");
        deleteKeys(keys);
    }

    private void deleteKeys(Set<String> keys) {
        if (keys != null && keys.size() > 0) {
            redis.executePipelined((RedisConnection connection) -> {
                connection.openPipeline();
                try {
                    for (String key : keys) {
                        connection.del(key.getBytes(CharsetUtil.UTF8));
                    }
                } finally {
                    connection.closePipeline();
                }
                return null;
            });
        }
    }

    /**
     * 处理第三方用户的token
     * <p/>只保留老化时间最长 且 老化时间小于初始老化时间三分之一的token
     *
     * @param userTokenKeys 用户token集合
     */
    private void deleteThirdPartKeys(Set<String> userTokenKeys) {
        if (userTokenKeys != null && userTokenKeys.size() > 0) {
            long thirdPartUserTokenExpireSeconds = userManageProperties.getThirdPartUserTokenExpireSeconds();
            long lastKeyTTL = 0;
            String lastKey = null;
            for (String key : userTokenKeys) {
                Long ttl = redis.getExpire(key);
                if (ttl != null) {
                    if (ttl > lastKeyTTL && ttl < thirdPartUserTokenExpireSeconds / 3) {
                        if (StringUtils.isNotEmpty(lastKey)) {
                            redis.delete(lastKey);
                        }
                        lastKeyTTL = ttl;
                        lastKey = key;
                    } else {
                        redis.delete(key);
                    }
                }
            }
        }
    }

    private UserToken getUserTokenFromRedis(String userTokenKey) {
        try {
            String userTokenJSON = getRedisValue(userTokenKey);
            return UserToken.parseFromJSON(userTokenJSON);
        } catch (Exception ex) {
            return null;
        }
    }

    private void saveUserTokenToRedis(UserToken userToken, String key) {
        String userTokenJSON = userToken.toJSON();
        setRedisValue(key, userTokenJSON);
    }

    private long expireUserToken(UserToken userToken, String key) {
        long tokenExpireSeconds = userManageProperties.getUserTokenExpireSeconds();
        if (userToken.getUserInfo().getIsThirdPart() == 1) {
            tokenExpireSeconds = userManageProperties.getThirdPartUserTokenExpireSeconds();
        }
        if (tokenExpireSeconds > 0) {
            redis.expire(key, tokenExpireSeconds, TimeUnit.SECONDS);
            return tokenExpireSeconds;
        } else {
            return -1;
        }
    }

    private void setRedisValue(String key, String value) {
        ValueOperations<String, String> operations = redis.opsForValue();
        operations.set(key, value);
    }

    private String getRedisValue(String key) {
        ValueOperations<String, String> operations = redis.opsForValue();
        return operations.get(key);
    }
}
