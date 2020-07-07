package com.medcaptain.cloud.producttemplate.mongodb;

import com.medcaptain.cloud.producttemplate.entity.ProductEvent;
import com.medcaptain.cloud.producttemplate.entity.ProductProperty;
import com.medcaptain.cloud.producttemplate.entity.ProductService;
import com.medcaptain.cloud.producttemplate.entity.ShadowProperty;
import com.medcaptain.logging.ExceptionLog;
import com.mongodb.client.result.DeleteResult;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <h2>MongoDB操作接口</h2>
 * <p>物模型功能定义及影子设备数据的增删改查</p>
 *
 * @author bingwen.ai
 */
@Service
public class MongoDbService {
    private Logger logger = LoggerFactory.getLogger(MongoDbService.class);

    private static final String PRODUCT_MODEL_COLLECTION_PREFIX = "ProductModel.";

    private static final String SERVICE = "service";

    private static final String PROPERTY = "property";

    private static final String EVENT = "event";

    private static final String SHADOW_DEVICE_PROPERTY = "ShadowDeviceProperty";

    @Autowired
    MongoTemplate mongoTemplate;

    /**
     * 保存服务定义到mongoDB
     * <br>已存在相同标识符数据则更新原数据
     *
     * @param productService 物模型服务定义
     */
    public void saveProductServiceModel(ProductService productService) {
        if (productService != null) {
            String collectionName = PRODUCT_MODEL_COLLECTION_PREFIX + SERVICE;
            Criteria criteria = Criteria.where("productKey").is(productService.getProductKey())
                    .and("identifier").is(productService.getIdentifier());
            Query query = new Query(criteria);
            if (mongoTemplate.exists(query, ProductService.class, collectionName)) {
                Update update = getServiceUpdate(productService);
                mongoTemplate.updateMulti(query, update, ProductService.class, collectionName);
            } else {
                mongoTemplate.save(productService, collectionName);
            }
        }
    }

    /**
     * 获取物模型服务定义
     *
     * @param productKey        产品编码
     * @param serviceIdentifier 服务标识符
     * @return 产品服务定义
     */
    public ProductService getProductServiceModel(String productKey, String serviceIdentifier) {
        Criteria criteria = Criteria.where("productKey").is(productKey).and("identifier").is(serviceIdentifier);
        Query query = new Query(criteria);
        String collectionName = PRODUCT_MODEL_COLLECTION_PREFIX + SERVICE;
        return mongoTemplate.findOne(query, ProductService.class, collectionName);
    }

    /**
     * 删除物模型服务定义
     *
     * @param productKey        产品编码
     * @param serviceIdentifier 服务标识符
     * @return true = 删除成功 ； false = 删除失败
     */
    public boolean deleteProductServiceModel(String productKey, String serviceIdentifier) {
        Criteria criteria = Criteria.where("productKey").is(productKey).and("identifier").is(serviceIdentifier);
        Query query = new Query(criteria);
        String collectionName = PRODUCT_MODEL_COLLECTION_PREFIX + SERVICE;
        DeleteResult deleteResult = mongoTemplate.remove(query, collectionName);
        return deleteResult.getDeletedCount() >= 0;
    }

    /**
     * 保存物模型属性定义到mongoDB
     * <br>已存在相同标识符则更新原数据
     *
     * @param productProperty 物模型属性定义
     */
    public void saveProductPropertyModel(ProductProperty productProperty) {
        if (productProperty != null) {
            String collectionName = PRODUCT_MODEL_COLLECTION_PREFIX + PROPERTY;
            Criteria criteria = Criteria.where("productKey").is(productProperty.getProductKey())
                    .and("identifier").is(productProperty.getIdentifier());
            Query query = new Query(criteria);
            if (mongoTemplate.exists(query, ProductProperty.class, collectionName)) {
                Update update = getPropertyUpdate(productProperty);
                mongoTemplate.updateMulti(query, update, ProductProperty.class, collectionName);
            } else {
                mongoTemplate.save(productProperty, collectionName);
            }
        }
    }

    public ProductProperty getProductPropertyModel(String productKey, String propertyIdentifier) {
        Criteria criteria = Criteria.where("productKey").is(productKey).and("identifier").is(propertyIdentifier);
        Query query = new Query(criteria);
        String collectionName = PRODUCT_MODEL_COLLECTION_PREFIX + PROPERTY;
        return mongoTemplate.findOne(query, ProductProperty.class, collectionName);
    }

    public List<ProductProperty> getProductPropertyModels(String productKey) {
        Criteria criteria = Criteria.where("productKey").is(productKey);
        Query query = new Query(criteria);
        String collectionName = PRODUCT_MODEL_COLLECTION_PREFIX + PROPERTY;
        return mongoTemplate.find(query, ProductProperty.class, collectionName);
    }

    /**
     * 删除物模型属性定义
     *
     * @param productKey         产品编码
     * @param propertyIdentifier 属性标识符
     * @return true = 删除成功 ； false = 删除失败
     */
    public boolean deleteProductPropertyModel(String productKey, String propertyIdentifier) {
        Criteria criteria = Criteria.where("productKey").is(productKey).and("identifier").is(propertyIdentifier);
        Query query = new Query(criteria);
        String collectionName = PRODUCT_MODEL_COLLECTION_PREFIX + PROPERTY;
        DeleteResult deleteResult = mongoTemplate.remove(query, collectionName);
        return deleteResult.getDeletedCount() >= 0;
    }

    /**
     * 保存物模型事件定义到mongoDB
     *
     * @param productEvent 物模型事件定义
     */
    public void saveProductEventModel(ProductEvent productEvent) {
        if (productEvent != null) {
            Criteria criteria = Criteria.where("productKey").is(productEvent.getProductKey())
                    .and("identifier").is(productEvent.getIdentifier());
            Query query = new Query(criteria);
            String collectionName = PRODUCT_MODEL_COLLECTION_PREFIX + EVENT;
            if (mongoTemplate.exists(query, ProductEvent.class, collectionName)) {
                Update update = getEventUpdate(productEvent);
                mongoTemplate.updateMulti(query, update, ProductEvent.class, collectionName);
            } else {
                mongoTemplate.save(productEvent, collectionName);
            }
        }
    }

    public ProductEvent getProductEventModel(String productKey, String eventIdentifier) {
        Criteria criteria = Criteria.where("productKey").is(productKey).and("identifier").is(eventIdentifier);
        Query query = new Query(criteria);
        String collectionName = PRODUCT_MODEL_COLLECTION_PREFIX + EVENT;
        return mongoTemplate.findOne(query, ProductEvent.class, collectionName);
    }

    /**
     * 删除物模型事件定义
     *
     * @param productKey      产品编码
     * @param eventIdentifier 事件标识符
     * @return true = 删除成功 ； false = 删除失败
     */
    public boolean deleteProductEventModel(String productKey, String eventIdentifier) {
        Criteria criteria = Criteria.where("productKey").is(productKey).and("identifier").is(eventIdentifier);
        Query query = new Query(criteria);
        String collectionName = PRODUCT_MODEL_COLLECTION_PREFIX + EVENT;
        DeleteResult deleteResult = mongoTemplate.remove(query, collectionName);
        return deleteResult.getDeletedCount() >= 0;
    }

    public boolean clearModel(String productKey) {
        Criteria criteria = Criteria.where("productKey").is(productKey);
        Query query = new Query(criteria);
        String collectionName = PRODUCT_MODEL_COLLECTION_PREFIX + EVENT;
        DeleteResult deleteEventResult = mongoTemplate.remove(query, collectionName);
        collectionName = PRODUCT_MODEL_COLLECTION_PREFIX + PROPERTY;
        DeleteResult deletePropertyResult = mongoTemplate.remove(query, collectionName);
        collectionName = PRODUCT_MODEL_COLLECTION_PREFIX + SERVICE;
        DeleteResult deleteServiceResult = mongoTemplate.remove(query, collectionName);
        return deleteEventResult.getDeletedCount() + deletePropertyResult.getDeletedCount() + deleteServiceResult.getDeletedCount() >= 0;
    }

    public void saveShadowDeviceProperties(List<ShadowProperty> shadowPropertyList) {
        if (shadowPropertyList != null) {
            for (ShadowProperty shadowProperty : shadowPropertyList) {
                saveShadowDeviceProperty(shadowProperty);
            }
        }
    }

    public void saveShadowDeviceProperty(ShadowProperty shadowProperty) {
        try {
            if (shadowProperty != null) {
                Criteria criteria = Criteria.where("deviceTripleId").is(shadowProperty.getDeviceTripleId())
                        .and("identifier").is(shadowProperty.getIdentifier());
                Query query = new Query(criteria);
                String collectionName = SHADOW_DEVICE_PROPERTY;
                if (mongoTemplate.exists(query, collectionName)) {
                    ShadowProperty shadowPropertyInDB = getShadowProperty(shadowProperty.getDeviceTripleId(), shadowProperty.getIdentifier());
                    if (shadowPropertyInDB != null) {
                        Update update = getShadowPropertyUpdate(shadowProperty);
                        mongoTemplate.updateMulti(query, update, ShadowProperty.class, collectionName);
                    }
                } else {
                    mongoTemplate.save(shadowProperty, collectionName);
                }
            }
        } catch (Exception ex) {
            ExceptionLog exceptionLog = new ExceptionLog(ex, "mongoDB保存影子设备属性");
            logger.error(exceptionLog.toString());
        }
    }

    public List<ShadowProperty> getShadowProperties(String deviceTripleId, String propertyIdentifiers) {
        if (StringUtils.isBlank(propertyIdentifiers)) {
            Criteria criteria = Criteria.where("deviceTripleId").is(deviceTripleId);
            Query query = new Query(criteria);
            String collectionName = SHADOW_DEVICE_PROPERTY;
            return mongoTemplate.find(query, ShadowProperty.class, collectionName);
        } else {
            List<ShadowProperty> shadowPropertyList = new ArrayList<>();
            String[] propertyIdentifierList = propertyIdentifiers.split(",");
            for (String propertyIdentifier : propertyIdentifierList) {
                shadowPropertyList.add(getShadowProperty(deviceTripleId, propertyIdentifier));
            }
            return shadowPropertyList;
        }
    }

    private ShadowProperty getShadowProperty(String deviceTripleId, String propertyIdentifier) {
        Criteria criteria = Criteria.where("deviceTripleId").is(deviceTripleId).and("identifier").is(propertyIdentifier);
        Query query = new Query(criteria);
        String collectionName = SHADOW_DEVICE_PROPERTY;
        return mongoTemplate.findOne(query, ShadowProperty.class, collectionName);
    }

    private Update getServiceUpdate(ProductService productService) {
        return new Update().set("name", productService.getName())
                .set("method", productService.getMethod())
                .set("required", productService.isRequired())
                .set("callType", productService.getCallType())
                .set("desc", productService.getDesc())
                .set("outputData", productService.getOutputData())
                .set("inputData", productService.getInputData());
    }

    private Update getPropertyUpdate(ProductProperty productProperty) {
        return new Update().set("dataType", productProperty.getDataType())
                .set("name", productProperty.getName())
                .set("accessMode", productProperty.getAccessMode())
                .set("required", productProperty.isRequired());
    }

    private Update getEventUpdate(ProductEvent productEvent) {
        return new Update().set("name", productEvent.getName())
                .set("method", productEvent.getMethod())
                .set("required", productEvent.isRequired())
                .set("type", productEvent.getType())
                .set("desc", productEvent.getDesc())
                .set("outputData", productEvent.getOutputData());
    }

    private Update getShadowPropertyUpdate(ShadowProperty shadowProperty) {
        return new Update().set("deviceUploadTime", shadowProperty.getDeviceUploadTime())
                .set("value", shadowProperty.getValue())
                .set("version", shadowProperty.getVersion())
                .set("serverReceiveTime", shadowProperty.getServerReceiveTime());
    }
}
