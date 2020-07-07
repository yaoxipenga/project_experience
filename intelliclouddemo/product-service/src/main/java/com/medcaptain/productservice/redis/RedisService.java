package com.medcaptain.productservice.redis;

import com.medcaptain.productservice.config.ProductTemplateProperties;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Configurable
public class RedisService {

    private static final String KEY_DEVICE_STATUS = "device.status";

    private static final String KEY_SOCKET = "Socket";

    @Autowired
    private RedisTemplate<String, String> redis;

    @Autowired
    ProductTemplateProperties productTemplateProperties;

    /**
     * 获取设备在线数量
     *
     * @return 在线数量
     */
    public Long getDeviceNum() {
        return redis.opsForSet().size(KEY_DEVICE_STATUS);
    }

    /**
     * 添加webs 上线
     *
     * @param key   产品id
     * @param value 设备
     */
    public Long saveSocket(String key, String value) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
            throw new IllegalArgumentException("UserID or token can not be null.");
        }
        SetOperations<String, String> opsForSet = redis.opsForSet();
        return opsForSet.add(KEY_SOCKET + key, (value));
    }

    private void setkeyTime(String key, int day) {
        if (day > 0) {
            redis.expire(key, day, TimeUnit.DAYS);
        }
    }
//    @Autowired
//    private ResourceMappingService resourceMappingService;

//
//
//    /**
//     * 为角色添加前端权限
//     *
//     * @param roleID             角色编号
//     * @param frontendResourceID 前端资源编号
//     */
//    public void addFrontendResourcePermission(int roleID, int frontendResourceID) {
//        List<ResourceMapping> backendResourceList = resourceMappingService.listBackendResources(frontendResourceID);
//        if (backendResourceList.size() > 0) {
//            String key = KEY_ROLE_PERMISSION + roleID + ":" + frontendResourceID;
//            SetOperations<String, String> operations = redis.opsForSet();
//            for (ResourceMapping resourceMapping : backendResourceList) {
//                BackendResource backendResource = resourceMapping.getBackendResource();
//                if (backendResource != null) {
//                    String backendResourceURL = backendResource.getResourceurl();
//                    operations.add(key, backendResourceURL);
//                }
//            }
//        }
//    }
//
//    /**
//     * 移除角色的前端资源权限
//     *
//     * @param roleID             角色编号
//     * @param frontendResourceID 前端资源编号
//     */
//    public void removeFrontendResourcePermission(int roleID, int frontendResourceID) {
//        List<ResourceMapping> backendResourceList = resourceMappingService.listBackendResources(frontendResourceID);
//        if (backendResourceList.size() > 0) {
//            String key = KEY_ROLE_PERMISSION + roleID + ":" + frontendResourceID;
//            SetOperations<String, String> operations = redis.opsForSet();
//            for (ResourceMapping resourceMapping : backendResourceList) {
//                BackendResource backendResource = resourceMapping.getBackendResource();
//                if (backendResource != null) {
//                    String backendResourceURL = backendResource.getResourceurl();
//                    operations.remove(key, backendResourceURL);
//                }
//            }
//        }
//    }
//
//    /**
//     * 添加前后端资源映射关系后更新权限缓存
//     *
//     * @param frontendResouceID 前端资源ID
//     * @param backendResourceID 后端资源ID
//     */
//    public void addResourceMapping(int frontendResouceID, int backendResourceID) {
//        String keyPattern = KEY_ROLE_PERMISSION + "\\d+:" + frontendResouceID;
//        Set<String> keys = redis.keys(keyPattern);
//        if (keys.size() > 0) {
//            SetOperations<String, String> operations = redis.opsForSet();
//            for (String key : keys) {
//                operations.add(key, Integer.toString(backendResourceID));
//            }
//        }
//    }
//
//    /**
//     * 移除前后端资源映射关系后更新权限缓存
//     *
//     * @param frontendResouceID 前端资源ID
//     * @param backendResourceID 后端资源ID
//     */
//    public void removeResourceMapping(int frontendResouceID, int backendResourceID) {
//        String keyPattern = KEY_ROLE_PERMISSION + "\\d+:" + frontendResouceID;
//        Set<String> keys = redis.keys(keyPattern);
//        if (keys.size() > 0) {
//            SetOperations<String, String> operations = redis.opsForSet();
//            for (String key : keys) {
//                operations.remove(key, Integer.toString(backendResourceID));
//            }
//        }
//    }
//
//    /**
//     * 检查角色是否有操作后端接口的权限
//     *
//     * @param roleID             角色编号
//     * @param backendResourceURL 后端资源URL
//     * @return true - 有操作权限 ； false -无操作权限
//     */
//    public boolean checkRolePermission(int roleID, String backendResourceURL) {
//        String keyPattern = KEY_ROLE_PERMISSION + roleID + ":\\d+";
//        Set<String> keys = redis.keys(keyPattern);
//        SetOperations<String, String> operations = redis.opsForSet();
//        boolean result = false;
//        for (String key : keys) {
//            result = result && operations.isMember(key, backendResourceURL);
//            if (result)
//                return result;
//        }
//        return false;
//    }
}
