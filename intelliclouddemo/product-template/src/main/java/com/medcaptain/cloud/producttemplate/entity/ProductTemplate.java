package com.medcaptain.cloud.producttemplate.entity;

import java.util.List;


/**
 * 产品模板定义
 *
 * @author bingwen.ai
 */
public class ProductTemplate {
    private Profile profile;

    private List<ProductService> services;

    private List<ProductProperty> properties;

    private List<ProductEvent> events;

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public List<ProductService> getServices() {
        if (profile != null && services != null) {
            for (ProductService service : services) {
                service.setProductKey(profile.getProductKey());
            }
        }
        return services;
    }

    public void setServices(List<ProductService> services) {
        this.services = services;
    }

    public List<ProductProperty> getProperties() {
        if (profile != null && properties != null) {
            for (ProductProperty productProperty : properties) {
                productProperty.setProductKey(profile.getProductKey());
            }
        }
        return properties;
    }

    public void setProperties(List<ProductProperty> properties) {
        this.properties = properties;
    }

    public List<ProductEvent> getEvents() {
        if (profile != null && events != null) {
            for (ProductEvent productEvent : events) {
                productEvent.setProductKey(profile.getProductKey());
            }
        }
        return events;
    }

    public void setEvents(List<ProductEvent> events) {
        this.events = events;
    }
}
