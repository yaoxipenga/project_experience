package com.medcaptain.productservice.entity.dto.mongo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.medcaptain.productservice.annotation.VerifyNotEmpty;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.annotation.Nullable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "event_template")
public class ProductEvent {
    @Nullable
    @Id
    private ObjectId id;

    @Field("identifier")
    private String identifier;

    @Field("name")
    private String name;

    @Field("type")
    private String type;

    @Field("description")
    private String desc;

    @Field("outputData")
    private List outputData;

    @Field("required")
    private Boolean required;

    @Field("eventType")
    private String eventType;

    @Nullable
    @Field("id")
    private String abilityId;

    @VerifyNotEmpty
    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @VerifyNotEmpty
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List getOutputData() {
        return outputData;
    }

    public void setOutputData(List outputData) {
        this.outputData = outputData;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    @VerifyNotEmpty
    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    @Nullable
    public String getAbilityId() {
        return abilityId;
    }

    public void setAbilityId(@Nullable String abilityId) {
        this.abilityId = abilityId;
    }
}
