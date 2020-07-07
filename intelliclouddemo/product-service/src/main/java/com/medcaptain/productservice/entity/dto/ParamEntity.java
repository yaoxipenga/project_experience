package com.medcaptain.productservice.entity.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.medcaptain.productservice.enums.ParamDataEnum;

import java.util.List;
import java.util.Map;

/**
 * @author yang
 */
public class ParamEntity {

    /**
     * page : 1
     * per_page : 10
     * product_key : 2c35e7e9f6a
     * device_name : 0292878a2c86467
     * start_time : 1541317790
     * end_time : 1551317790
     * dataType : 0
     * paramType : 0
     * paramName : ["fcc"]
     * paramKV : {"fcc":1}
     */

    private Integer page;
    @JsonProperty("per_page")
    private Integer perPage;
    @JsonProperty("product_key")
    private String productKey;
    @JsonProperty("device_name")
    private String deviceName;
    /**
     * 标识符
     */
    @JsonProperty("identifier")
    private String identifier;
    /**
     * 开始时间
     */
    @JsonProperty("start_time")
    private Long startTime;
    /**
     * 结束时间
     */
    @JsonProperty("end_time")
    private Long endTime;

    /**
     * 参数类型
     * 1 默认  普通模式
     * 2 只返回当前参数的参数
     */
    @JsonProperty("data_type")
    private ParamDataEnum dataType;
    /**
     * 参数类型 （保留字段）
     */
    @JsonProperty("param_type")
    private Integer paramType;
    /**
     * 参数为 k
     * 值为 v
     */
    @JsonProperty("param_kv")
    private Map<String, Object> paramKV;
    /**
     * 1、存在当前的参数
     * 2、查询时会去掉kv中存在的
     */
    @JsonProperty("param_name_and")
    private List<String> paramNameAnd;

    /**
     * 使用or参数，返回结果只有or参数
     */
    @JsonProperty("param_name_or")
    private List<String> paramNameOr;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public ParamDataEnum getDataType() {
        return dataType;
    }

    public void setDataType(ParamDataEnum dataType) {
        this.dataType = dataType;
    }

    public Integer getParamType() {
        return paramType;
    }

    public void setParamType(Integer paramType) {
        this.paramType = paramType;
    }

    public Map<String, Object> getParamKV() {
        return paramKV;
    }

    public void setParamKV(Map<String, Object> paramKV) {
        this.paramKV = paramKV;
    }

    public List<String> getParamNameAnd() {
        return paramNameAnd;
    }

    public void setParamNameAnd(List<String> paramNameAnd) {
        this.paramNameAnd = paramNameAnd;
    }

    public List<String> getParamNameOr() {
        return paramNameOr;
    }

    public void setParamNameOr(List<String> paramNameOr) {
        this.paramNameOr = paramNameOr;
    }

    public static ParamEntity copy(ParamEntity paramEntity){
        ParamEntity paramEntity1 = new ParamEntity();
        paramEntity1.setPage(paramEntity.getPage());
        paramEntity1.setPerPage(paramEntity.getPerPage());
        paramEntity1.setProductKey(paramEntity.getProductKey());
        paramEntity1.setDeviceName(paramEntity.getDeviceName());
        paramEntity1.setIdentifier(paramEntity.getIdentifier());
        paramEntity1.setStartTime(paramEntity.getStartTime());
        paramEntity1.setEndTime(paramEntity.getEndTime());
        paramEntity1.setDataType(paramEntity.getDataType());
        paramEntity1.setParamType(paramEntity.getParamType());
        paramEntity1.setParamKV(paramEntity.getParamKV());
        paramEntity1.setParamNameAnd(paramEntity.getParamNameAnd());
        paramEntity1.setParamNameOr(paramEntity.getParamNameOr());
        return paramEntity1;
    }

}
