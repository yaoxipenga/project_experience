package com.medcaptain.cloud.producttemplate.service;

import com.alibaba.fastjson.JSON;
import com.medcaptain.cloud.producttemplate.entity.ProductEvent;
import com.medcaptain.cloud.producttemplate.entity.ProductProperty;
import com.medcaptain.cloud.producttemplate.entity.ProductService;
import com.medcaptain.cloud.producttemplate.mongodb.MongoDbService;
import com.medcaptain.cloud.producttemplate.redis.RedisService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 产品模板服务
 *
 * @author bingwen.ai
 */
@Service
public class ProductModelService {
    private Logger logger = LoggerFactory.getLogger(ProductModelService.class);

    @Autowired
    private RedisService redisService;

    @Autowired
    private MongoDbService mongoDbService;

    public boolean saveService(ProductService productService) {
        if (productService != null) {
            mongoDbService.saveProductServiceModel(productService);
            redisService.addService(productService);
            return true;
        }
        return false;
    }

    public boolean saveProperty(ProductProperty productProperty) {
        if (productProperty != null) {
            mongoDbService.saveProductPropertyModel(productProperty);
            redisService.addProperty(productProperty);
            return true;
        }
        return false;
    }

    public boolean saveEvent(ProductEvent productEvent) {
        if (productEvent != null) {
            mongoDbService.saveProductEventModel(productEvent);
            redisService.addEvent(productEvent);
            return true;
        }
        return false;
    }

    public ProductService getService(String productKey, String serviceIdentifier) {
        if (redisService.existService(productKey, serviceIdentifier)) {
            return redisService.getService(productKey, serviceIdentifier);
        } else {
            ProductService productService = mongoDbService.getProductServiceModel(productKey, serviceIdentifier);
            if (productService != null) {
                redisService.addService(productService);
            }
            return productService;
        }
    }

    public ProductProperty getProperty(String productKey, String propertyIdentifier) {
        if (redisService.existProperty(productKey, propertyIdentifier)) {
            return redisService.getProperty(productKey, propertyIdentifier);
        } else {
            ProductProperty productProperty = mongoDbService.getProductPropertyModel(productKey, propertyIdentifier);
            if (productProperty != null) {
                redisService.addProperty(productProperty);
            }
            return productProperty;
        }
    }

    public ProductEvent getEvent(String productKey, String eventIdentifier) {
        if (redisService.existEvent(productKey, eventIdentifier)) {
            return redisService.getEvent(productKey, eventIdentifier);
        } else {
            ProductEvent productEvent = mongoDbService.getProductEventModel(productKey, eventIdentifier);
            if (productEvent != null) {
                redisService.addEvent(productEvent);
            }
            return productEvent;
        }
    }

    public boolean deleteService(String productKey, String serviceIdentifier) {
        return mongoDbService.deleteProductServiceModel(productKey, serviceIdentifier)
                && redisService.deleteService(productKey, serviceIdentifier);
    }

    public boolean deleteProperty(String productKey, String propertyIdentifier) {
        return mongoDbService.deleteProductPropertyModel(productKey, propertyIdentifier)
                && redisService.deleteProperty(productKey, propertyIdentifier);
    }

    public boolean deleteEvent(String productKey, String eventIdentifier) {
        return mongoDbService.deleteProductEventModel(productKey, eventIdentifier)
                && redisService.deleteEvent(productKey, eventIdentifier);
    }

    public boolean clearModel(String productKey) {
        return mongoDbService.clearModel(productKey) && redisService.clearModel(productKey);
    }

    public List<ProductProperty> getProperties(String productKey) {
        if (redisService.existProperties(productKey)) {
            List<ProductProperty> propertyList = new ArrayList<>();
            Map<String, String> propertyStrings = redisService.getProperties(productKey);
            for (String propertyIdentifier : propertyStrings.keySet()) {
                String propertyJson = propertyStrings.get(propertyIdentifier);
                if (StringUtils.isNotBlank(propertyJson)) {
                    ProductProperty productProperty = JSON.parseObject(propertyJson, ProductProperty.class);
                    propertyList.add(productProperty);
                }
            }
            return propertyList;
        } else {
            return mongoDbService.getProductPropertyModels(productKey);
        }
    }
}
