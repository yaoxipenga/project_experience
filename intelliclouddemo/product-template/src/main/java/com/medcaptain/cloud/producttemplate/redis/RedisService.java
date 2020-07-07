package com.medcaptain.cloud.producttemplate.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.medcaptain.cloud.producttemplate.config.ProductTemplateProperties;
import com.medcaptain.cloud.producttemplate.entity.ProductEvent;
import com.medcaptain.cloud.producttemplate.entity.ProductProperty;
import com.medcaptain.cloud.producttemplate.entity.ProductService;
import com.medcaptain.cloud.producttemplate.entity.ShadowProperty;
import com.medcaptain.logging.ExceptionLog;
import com.medcaptain.utils.CharsetUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * REDIS操作服务
 *
 * @author bingwen.ai
 */
@Component
public class RedisService {
    private Logger logger = LoggerFactory.getLogger(RedisService.class);

    private static final String KEY_SERVICE_PREFIX = "productTemplate.service:";

    private static final String KEY_PROPERTY_PREFIX = "productTemplate.property:";

    private static final String KEY_EVENT_PREFIX = "productTemplate.event:";

    private static final String KEY_SHADOW_DEVICE_PREFIX = "shadowDevice:";

    @Autowired
    ProductTemplateProperties productTemplateProperties;

    @Autowired
    private RedisTemplate<String, String> redis;

    /**
     * 添加产品物模型服务定义
     *
     * @param productService 服务定义
     */
    public void addService(ProductService productService) {
        if (productService != null) {
            String key = KEY_SERVICE_PREFIX + productService.getProductKey() + ":" + productService.getIdentifier();
            String serviceJSon = JSONObject.toJSONString(productService);
            addValue(key, serviceJSon);
            long productModelExpireDays = productTemplateProperties.getProductModelExpireDays();
            if (productModelExpireDays > 0) {
                redis.expire(key, productModelExpireDays, TimeUnit.DAYS);
            }
        }
    }

    public ProductService getService(String productKey, String serviceIdentifier) {
        String key = KEY_SERVICE_PREFIX + productKey + ":" + serviceIdentifier;
        String serviceJSON = getValue(key);
        if (StringUtils.isNotEmpty(serviceJSON)) {
            int productModelExpireDays = productTemplateProperties.getProductModelExpireDays();
            if (productModelExpireDays > 0) {
                redis.expire(key, productModelExpireDays, TimeUnit.DAYS);
            }
        }
        try {
            return JSON.parseObject(serviceJSON, ProductService.class);
        } catch (Exception ex) {
            ExceptionLog exceptionLog = new ExceptionLog(ex, "从redis获取服务定义");
            logger.error(exceptionLog.toString());
            return null;
        }
    }

    public Map<String, String> getProperties(String productKey) {
        String key = KEY_PROPERTY_PREFIX + productKey;
        HashOperations<String, String, String> hashOperations = redis.opsForHash();
        return hashOperations.entries(key);
    }

    public ProductProperty getProperty(String productKey, String propertyIdentifier) {
        String key = KEY_PROPERTY_PREFIX + productKey;
        HashOperations<String, String, String> hashOperations = redis.opsForHash();
        String propertyJSon = hashOperations.get(key, propertyIdentifier);
        if (StringUtils.isNotEmpty(propertyJSon)) {
            int productModelExpireDays = productTemplateProperties.getProductModelExpireDays();
            if (productModelExpireDays > 0) {
                redis.expire(key, productModelExpireDays, TimeUnit.DAYS);
            }
        }
        if (StringUtils.isNotBlank(propertyJSon)) {
            return JSON.parseObject(propertyJSon, ProductProperty.class);
        } else {
            return null;
        }
    }

    public ProductEvent getEvent(String productKey, String eventIdentifier) {
        String key = KEY_EVENT_PREFIX + productKey + ":" + eventIdentifier;
        String eventJSON = getValue(key);
        if (StringUtils.isNotEmpty(eventJSON)) {
            int productModelExpireDays = productTemplateProperties.getProductModelExpireDays();
            if (productModelExpireDays > 0) {
                redis.expire(key, productModelExpireDays, TimeUnit.DAYS);
            }
        }
        try {
            return JSON.parseObject(eventJSON, ProductEvent.class);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 添加产品属性
     *
     * @param productKey 产品编号
     * @param properties 属性列表
     */
    public void addProperties(String productKey, List<ProductProperty> properties) {
        if (StringUtils.isNotBlank(productKey) && properties.size() > 0) {
            String key = KEY_PROPERTY_PREFIX + productKey;
            redis.delete(key);
            // 批量处理
            redis.executePipelined((RedisConnection connection) -> {
                connection.openPipeline();
                try {
                    for (ProductProperty productProperty : properties) {
                        String propertyJSon = JSONObject.toJSONString(productProperty);
                        String propertyIdentifier = productProperty.getIdentifier();
                        connection.hSet(key.getBytes(CharsetUtil.UTF8), propertyIdentifier.getBytes(CharsetUtil.UTF8), propertyJSon.getBytes(CharsetUtil.UTF8));
                    }
                } finally {
                    connection.closePipeline();
                }
                return null;
            });
        }
    }

    public void addProperty(ProductProperty productProperty) {
        if (productProperty != null) {
            String key = KEY_PROPERTY_PREFIX + productProperty.getProductKey();
            String propertyJSon = JSONObject.toJSONString(productProperty);
            String propertyIdentifier = productProperty.getIdentifier();
            HashOperations<String, String, String> operations = redis.opsForHash();
            operations.put(key, propertyIdentifier, propertyJSon);
            int productModelExpireDays = productTemplateProperties.getProductModelExpireDays();
            if (productModelExpireDays > 0) {
                redis.expire(key, productModelExpireDays, TimeUnit.DAYS);
            }
        }
    }

    /**
     * 添加产品物模型事件定义
     *
     * @param productEvent 事件
     */
    public void addEvent(ProductEvent productEvent) {
        if (productEvent != null) {
            String key = KEY_EVENT_PREFIX + productEvent.getProductKey() + ":" + productEvent.getIdentifier();
            String eventJSon = JSONObject.toJSONString(productEvent);
            addValue(key, eventJSon);
            long productModelExpireDays = productTemplateProperties.getProductModelExpireDays();
            if (productModelExpireDays > 0) {
                redis.expire(key, productModelExpireDays, TimeUnit.DAYS);
            }
        }
    }

    private boolean addValue(String key, String value) {
        ValueOperations<String, String> operations = redis.opsForValue();
        operations.set(key, value);
        return true;
    }

    private String getValue(String key) {
        ValueOperations<String, String> operations = redis.opsForValue();
        return operations.get(key);
    }

    public void addShadowProperties(List<ShadowProperty> propertyList) {
        try {
            if (propertyList != null && propertyList.size() > 0) {
                String deviceTripleID = propertyList.get(0).getDeviceTripleId();
                redis.executePipelined((RedisConnection connection) -> {
                    connection.openPipeline();
                    try {
                        for (ShadowProperty property : propertyList) {
                            String key = KEY_SHADOW_DEVICE_PREFIX + property.getDeviceTripleId();
                            String propertyIdentifier = property.getIdentifier();
                            ShadowProperty propertyInRedis = getShadowProperty(key, propertyIdentifier);
                            if (propertyInRedis != null) {
                                long oldUploadTime = propertyInRedis.getDeviceUploadTime();
                                long currentUploadTime = property.getDeviceUploadTime();
                                if (oldUploadTime > 0 && oldUploadTime >= currentUploadTime) {
                                    logger.info("上报属性生成时间早于已有数据，未修改影子设备属性，属性名称：{} ，属性值：{} ,本次上报时间:{} ,已有数据时间:{}",
                                            propertyIdentifier, property.getValue(), currentUploadTime, oldUploadTime);
                                    continue;
                                }
                            }
                            String propertyJSON = JSON.toJSONString(property);
                            if (propertyJSON != null) {
                                connection.hSet(key.getBytes(CharsetUtil.UTF8), propertyIdentifier.getBytes(CharsetUtil.UTF8), propertyJSON.getBytes(CharsetUtil.UTF8));
                            }
                        }
                    } catch (Exception ex) {
                        HashMap<String, Object> parameters = new HashMap<>(1);
                        parameters.put("deviceTripleID", deviceTripleID);
                        ExceptionLog exceptionLog = new ExceptionLog(ex, "添加影子设备信息到REDIS", parameters);
                        logger.error(exceptionLog.toString());
                    } finally {
                        connection.closePipeline();
                    }
                    return null;
                });
                int shadowDevicePropertyExpireDays = productTemplateProperties.getShadowDevicePropertyExpireDays();
                if (shadowDevicePropertyExpireDays > 0) {
                    String key = KEY_SHADOW_DEVICE_PREFIX + propertyList.get(0).getDeviceTripleId();
                    redis.expire(key, shadowDevicePropertyExpireDays, TimeUnit.DAYS);
                }
            }
        } catch (Exception ex) {
            ExceptionLog exceptionLog = new ExceptionLog(ex, "Redis保存影子设备属性");
            logger.error(exceptionLog.toString());
        }
    }

    private ShadowProperty getShadowProperty(String key, String propertyIdentifier) {
        ShadowProperty redisProperty = null;
        try {
            HashOperations<String, String, String> hashOperations = redis.opsForHash();
            String redisJson = hashOperations.get(key, propertyIdentifier);
            if (StringUtils.isNotEmpty(redisJson)) {
                redisProperty = JSON.parseObject(redisJson, ShadowProperty.class);
            }
        } catch (Exception ex) {
            HashMap<String, Object> parameters = new HashMap<>(2);
            parameters.put("key", key);
            parameters.put("propertyIdentifier", propertyIdentifier);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "获取REDIS中影子设备属性", parameters);
            logger.error(exceptionLog.toString());
        }
        return redisProperty;
    }

    public List<ShadowProperty> getDeviceProperties(String deviceTripleID, String propertyIdentifiers) {
        String key = KEY_SHADOW_DEVICE_PREFIX + deviceTripleID;
        int shadowDevicePropertyExpireDays = productTemplateProperties.getShadowDevicePropertyExpireDays();
        if (shadowDevicePropertyExpireDays > 0) {
            redis.expire(key, shadowDevicePropertyExpireDays, TimeUnit.DAYS);
        }
        HashOperations<String, String, String> hashOperations = redis.opsForHash();
        Map<String, String> propertyMap = hashOperations.entries(key);
        List<ShadowProperty> propertyList = new ArrayList<>();

        String[] propertyIdentifierArray = StringUtils.split(propertyIdentifiers, ",");
        List<String> propertyIdentifierList = Arrays.asList(propertyIdentifierArray);

        for (String identifier : propertyMap.keySet()) {
            if (propertyIdentifierList.size() > 0) {
                if (!propertyIdentifierList.contains(identifier)) {
                    continue;
                }
            }
            String propertyJSon = propertyMap.get(identifier);
            ShadowProperty property = JSON.parseObject(propertyJSon, ShadowProperty.class);
            propertyList.add(property);
        }
        return propertyList;
    }

    public boolean existService(String productKey, String serviceIdentifier) {
        String key = KEY_SERVICE_PREFIX + productKey + ":" + serviceIdentifier;
        return hasKey(key);
    }

    public boolean existProperties(String productKey) {
        String key = KEY_PROPERTY_PREFIX + productKey;
        Boolean hasKey = redis.hasKey(key);
        return hasKey != null && hasKey;
    }

    public boolean existProperty(String productKey, String propertyIdentifier) {
        String key = KEY_PROPERTY_PREFIX + productKey;
        HashOperations<String, String, String> operations = redis.opsForHash();
        Boolean hasKey = operations.hasKey(key, propertyIdentifier);
        return hasKey != null && hasKey;
    }

    public boolean existEvent(String productKey, String eventIdentifier) {
        String key = KEY_EVENT_PREFIX + productKey + ":" + eventIdentifier;
        return hasKey(key);
    }

    public boolean existShadowProperty(String deviceTripleId) {
        String key = KEY_SHADOW_DEVICE_PREFIX + deviceTripleId;
        return hasKey(key);
    }

    public boolean deleteService(String productKey, String serviceIdentifier) {
        String key = KEY_SERVICE_PREFIX + productKey + ":" + serviceIdentifier;
        Boolean deleteResult = redis.delete(key);
        return deleteResult == null ? false : deleteResult;
    }

    public boolean deleteProperty(String productKey, String propertyIdentifier) {
        String key = KEY_PROPERTY_PREFIX + productKey;
        HashOperations<String, String, String> operations = redis.opsForHash();
        return operations.delete(key, propertyIdentifier) >= 0;
    }

    public boolean deleteEvent(String productKey, String eventIdentifier) {
        String key = KEY_EVENT_PREFIX + productKey + ":" + eventIdentifier;
        Boolean deleteResult = redis.delete(key);
        return deleteResult == null ? false : deleteResult;
    }

    public boolean clearModel(String productKey) {
        try {
            String keyPattern = "productTemplate.*:" + productKey + ":*";
            Set<String> keySet = redis.keys(keyPattern);
            deleteKeys(keySet);
            keyPattern = KEY_PROPERTY_PREFIX + productKey;
            keySet = redis.keys(keyPattern);
            deleteKeys(keySet);
            return true;
        } catch (Exception ex) {
            return false;
        }
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

    private boolean hasKey(String key) {
        Boolean hasKey = redis.hasKey(key);
        return hasKey != null && hasKey;
    }
}
