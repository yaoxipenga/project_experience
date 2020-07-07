package com.medcaptain.productservice.entity.productpojo;

import java.util.List;


/**
 * 产品模板定义
 *
 * @author bingwen.ai
 */
public class Template {
    private Profile profile;

    private List<Service> services;

    private List<Property> properties;

    private List<Event> events;

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public List<Service> getServices() {
        if (profile != null && services != null) {
            for (Service service : services) {
                service.setProductKey(profile.getProductKey());
            }
        }
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public List<Property> getProperties() {
        if (profile != null && properties != null) {
            for (Property property : properties) {
                property.setProductKey(profile.getProductKey());
            }
        }
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public List<Event> getEvents() {
        if (profile != null && events != null) {
            for (Event event : events) {
                event.setProductKey(profile.getProductKey());
            }
        }
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
