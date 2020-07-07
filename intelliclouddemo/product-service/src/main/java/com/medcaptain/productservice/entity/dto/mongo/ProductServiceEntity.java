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
@Document(collection = "service_template")
public class ProductServiceEntity {
    @Nullable
    @Id
    private ObjectId id;

    @Field("name")
    private String name;

    @Field("identifier")
    private String identifier;

    @Field("description")
    private String desc;

    @Field("outputData")
    private List outputData;

    @Field("inputData")
    private List inputData;

    @Field("callType")
    private String callType;

    @Field("required")
    private Boolean required;

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

    public List getInputData() {
        return inputData;
    }

    public void setInputData(List inputData) {
        this.inputData = inputData;
    }

    @VerifyNotEmpty
    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    @Nullable
    public String getAbilityId() {
        return abilityId;
    }

    public void setAbilityId(@Nullable String abilityId) {
        this.abilityId = abilityId;
    }
}
