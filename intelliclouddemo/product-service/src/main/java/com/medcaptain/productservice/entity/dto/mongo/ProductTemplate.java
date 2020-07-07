package com.medcaptain.productservice.entity.dto.mongo;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "product_template")
public class ProductTemplate {
    @Id
    private ObjectId id;

    @Field("productCategory")
    private String productCategory;

    @Field("properties")
    private List<ProductProperty> propertyList;

    @Field("services")
    private List<ProductServiceEntity> serviceList;

    @Field("events")
    private List<ProductEvent> eventList;

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public List<ProductProperty> getPropertyList() {
        return propertyList;
    }

    public void setPropertyList(List<ProductProperty> propertyList) {
        this.propertyList = propertyList;
    }

    public List<ProductServiceEntity> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<ProductServiceEntity> serviceList) {
        this.serviceList = serviceList;
    }

    public List<ProductEvent> getEventList() {
        return eventList;
    }

    public void setEventList(List<ProductEvent> eventList) {
        this.eventList = eventList;
    }
}
