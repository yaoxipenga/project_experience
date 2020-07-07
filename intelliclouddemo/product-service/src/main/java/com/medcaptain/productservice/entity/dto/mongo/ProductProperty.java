package com.medcaptain.productservice.entity.dto.mongo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.medcaptain.productservice.annotation.VerifyNotEmpty;
import com.medcaptain.productservice.entity.productpojo.DataType;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.annotation.Nullable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "property_template")
public class ProductProperty {
    @Nullable
    @Id
    private ObjectId id;

    @Field("name")
    private String name;

    @Field("identifier")
    private String identifier;

    @Field("dataType")
    private DataType dataType;

    @Field("accessMode")
    private String accessMode;

    @Field("required")
    private Boolean required;

    @Field("description")
    private String desc;

    @Nullable
    @Field("id")
    private String abilityId;

    @VerifyNotEmpty
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @VerifyNotEmpty
    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    @VerifyNotEmpty
    public String getAccessMode() {
        return accessMode;
    }

    public void setAccessMode(String accessMode) {
        this.accessMode = accessMode;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Nullable
    public String getAbilityId() {
        return abilityId;
    }

    public void setAbilityId(@Nullable String abilityId) {
        this.abilityId = abilityId;
    }
}
