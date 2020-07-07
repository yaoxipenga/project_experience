package com.medcaptain.productservice.entity.dto.mongo;

import com.alibaba.fastjson.JSONObject;
import com.medcaptain.utils.UUIDUtil;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;

@Document(collection = "physical_model")
public class PhysicalModel implements Serializable {
    @Id
    private ObjectId id;

    @Field(value = "profile")
    private JSONObject profile;

    @Field(value = "services")
    private List<ProductServiceEntity> services;

    @Field(value = "events")
    private List<ProductEvent> events;

    @Field(value = "properties")
    private List<ProductProperty> properties;

    public JSONObject getProfile() {
        return profile;
    }

    public void setProfile(JSONObject profile) {
        this.profile = profile;
    }

    public List<ProductServiceEntity> getServices() {
        return services;
    }

    public void setServices(List<ProductServiceEntity> services) {
        this.services = services;
    }

    public List<ProductEvent> getEvents() {
        return events;
    }

    public void setEvents(List<ProductEvent> events) {
        this.events = events;
    }

    public List<ProductProperty> getProperties() {
        return properties;
    }

    public void setProperties(List<ProductProperty> properties) {
        this.properties = properties;
    }

    public void addId() {
        for (ProductProperty property : properties) {
            property.setAbilityId(UUIDUtil.getUUID());
        }
        for (ProductServiceEntity service : services) {
            service.setAbilityId(UUIDUtil.getUUID());
        }
        for (ProductEvent event : events) {
            event.setAbilityId(UUIDUtil.getUUID());
        }
    }
}
