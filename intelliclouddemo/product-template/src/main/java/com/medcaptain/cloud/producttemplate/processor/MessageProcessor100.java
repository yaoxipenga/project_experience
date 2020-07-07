package com.medcaptain.cloud.producttemplate.processor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.medcaptain.cloud.producttemplate.entity.*;
import com.medcaptain.cloud.producttemplate.service.ProductModelService;
import com.medcaptain.cloud.producttemplate.service.ShadowDeviceService;
import com.medcaptain.dto.RestResult;
import com.medcaptain.enums.HttpResponseCode;
import com.medcaptain.logging.ExceptionLog;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;


/**
 * 1.0.0版本json消息处理器
 *
 * @author bingwen.ai
 */

public class MessageProcessor100 implements MessageProcess {
    private static final String READ_ONLY = "r";

    private static final String READ_AND_WRITE = "rw";

    private Logger logger = LoggerFactory.getLogger(MessageProcessor100.class);

    private ProductModelService productModelService;

    private ShadowDeviceService shadowDeviceService;

    public MessageProcessor100(ProductModelService productModelService, ShadowDeviceService shadowDeviceService) {
        this.productModelService = productModelService;
        this.shadowDeviceService = shadowDeviceService;
    }

    @Override
    public RestResult validateService(String productKey, JSONObject inputServiceParameter) {
        String serviceIdentifier = inputServiceParameter.getString("identifier");
        if (StringUtils.isBlank(serviceIdentifier)) {
            logger.warn("在传入json中未找到服务标识");
            return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }

        ProductService productService = productModelService.getService(productKey, serviceIdentifier);
        if (productService == null) {
            logger.warn("未找到产品服务模板,服务标识:{}.{}", productKey, serviceIdentifier);
            return RestResult.getInstance(HttpResponseCode.RESOURCE_NOT_FOUND, null);
        }
        try {
            JSONObject formatedService = formatService(productService, inputServiceParameter);
            return RestResult.getInstance(HttpResponseCode.SUCCESS, formatedService);
        } catch (IllegalArgumentException ex) {
            HashMap<String, Object> parameters = new HashMap<>(2);
            parameters.put("productKey", productKey);
            parameters.put("inputServiceParameter", JSON.toJSONString(inputServiceParameter));
            ExceptionLog exceptionLog = new ExceptionLog(ex, "验证产品服务", parameters);
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_PARAMETER_FORMAT, null);
        }
    }

    @Override
    public RestResult validateEvent(String productKey, JSONObject inputEventParameter) {
        String eventIdentifier = inputEventParameter.getString("identifier");
        if (StringUtils.isBlank(eventIdentifier)) {
            logger.warn("未在传入json中找到事件标识");
            return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }

        ProductEvent productEvent = productModelService.getEvent(productKey, eventIdentifier);
        if (productEvent == null) {
            logger.warn("未找到产品事件模板,事件标识:{}.{}", productKey, eventIdentifier);
            return RestResult.getInstance(HttpResponseCode.RESOURCE_NOT_FOUND, null);
        }
        try {
            JSONObject formatEvent = formatEvent(productEvent, inputEventParameter);
            if (formatEvent == null) {
                return RestResult.getInstance(HttpResponseCode.ERROR, null);
            } else {
                return RestResult.getInstance(HttpResponseCode.SUCCESS, formatEvent);
            }
        } catch (IllegalArgumentException ex) {
            HashMap<String, Object> parameters = new HashMap<>(2);
            parameters.put("productKey", productKey);
            parameters.put("paramJson", JSON.toJSONString(inputEventParameter));
            ExceptionLog exceptionLog = new ExceptionLog(ex, "验证产品事件", parameters);
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_PARAMETER_FORMAT, null);
        }
    }

    @Override
    public RestResult parseProperties(String productKey, String paramJson) {
        try {
            List<JSONObject> returnPropertyList = new ArrayList<>();
            JSONObject propertyParams = JSON.parseObject(paramJson);
            JSONObject propertyValueMap = propertyParams.getJSONObject("params");
            Object updateTime = propertyParams.get("timestamp");
            for (String propertyIdentifier : propertyValueMap.keySet()) {
                ProductProperty productProperty = productModelService.getProperty(productKey, propertyIdentifier);
                // 忽略不存在物模型定义的属性
                if (productProperty == null) {
                    break;
                }
                Object propertyValue = propertyValueMap.get(propertyIdentifier);
                try {
                    JSONObject formatedProperty = formatParameter(productProperty, propertyValue,false);
                    if (updateTime != null) {
                        formatedProperty.put("deviceUploadTime", updateTime);
                    }
                    returnPropertyList.add(formatedProperty);
                } catch (IllegalArgumentException ex) {
                    return RestResult.getInstance(400, ex.getMessage(), null);
                }
            }
            return RestResult.getInstance(HttpResponseCode.SUCCESS, returnPropertyList);
        } catch (Exception ex) {
            HashMap<String, Object> parameters = new HashMap<>(2);
            parameters.put("productKey", productKey);
            parameters.put("paramJson", paramJson);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "解析产品属性", parameters);
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }

    @Override
    public RestResult uploadDeviceProperty(String productKey, String deviceTripleID, String paramJson) {
        Map<String, ProductProperty> propertyMap = getProperties(productKey);
        RestResult validateResult = validateProperties(paramJson, propertyMap, false);
        if (validateResult.getCode() != HttpResponseCode.SUCCESS.getCode()) {
            return validateResult;
        }
        if (shadowDeviceService.saveShadowProperties(deviceTripleID, paramJson)) {
            return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
        } else {
            return RestResult.getInstance(HttpResponseCode.ERROR, null);
        }
    }

    @Override
    public RestResult setDeviceProperties(String productKey, String deviceTripleID, String paramJson) {
        Map<String, ProductProperty> propertyMap = getProperties(productKey);
        RestResult validateResult = validateProperties(paramJson, propertyMap, true);
        if (validateResult.getCode() == HttpResponseCode.SUCCESS.getCode()) {
            if (shadowDeviceService.saveShadowProperties(deviceTripleID, paramJson)) {
                return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
            } else {
                return RestResult.getInstance(HttpResponseCode.ERROR, null);
            }
        } else {
            logger.warn("验证设置设备属性参数格式异常，productKey = {}, deviceTripleID = {}, params = '{}' ,异常详情:{}",
                    productKey, deviceTripleID, paramJson, validateResult.getMsg());
            return validateResult;
        }
    }

    @Override
    public JSONObject formatProperty(String productKey, String propertyIdentifier, ShadowProperty shadowProperty) {
        ProductProperty productProperty = productModelService.getProperty(productKey, propertyIdentifier);
        if (productProperty != null && productProperty.getAccessMode().contains(READ_ONLY)) {
            Object propertyValue = shadowProperty.getValue();
            try {
                JSONObject formatProperty = formatParameter(productProperty, propertyValue,false);
                formatProperty.put("serverReceiveTime", shadowProperty.getServerReceiveTime());
                formatProperty.put("deviceUploadTime", shadowProperty.getDeviceUploadTime());
                String version = shadowProperty.getVersion();
                if (StringUtils.isNotEmpty(version)) {
                    formatProperty.put("version", version);
                }
                return formatProperty;
            } catch (IllegalArgumentException ex) {
                throw ex;
            }
        }
        return null;
    }

    private JSONObject formatService(ProductService productService, JSONObject inputServiceParameter) throws IllegalArgumentException {
        try {
            JSONObject formatService = new JSONObject();
            JSONObject outputData = inputServiceParameter.getJSONObject("outputData");
            JSONObject inputData = inputServiceParameter.getJSONObject("inputData");
            formatService.put("identifier", productService.getIdentifier());
            formatService.put("name", productService.getName());
            formatService.put("method", productService.getMethod());
            formatService.put("desc", productService.getDesc());
            formatService.put("callType", productService.getCallType());
            try {
                List<JSONObject> outputDataList = formatParameters(productService.getOutputData(), outputData);
                formatService.put("outputData", outputDataList);
                List<JSONObject> inputDataList = formatParameters(productService.getInputData(), inputData);
                formatService.put("inputData", inputDataList);
            } catch (IllegalArgumentException ex) {
                logger.error("格式化服务参数异常,异常详情：{}", ex.getMessage());
                throw ex;
            }
            return formatService;
        } catch (Exception ex) {
            HashMap<String, Object> parameters = new HashMap<>(3);
            parameters.put("productKey", productService.getProductKey());
            parameters.put("serviceIdentifier", productService.getIdentifier());
            parameters.put("inputServiceParameter", JSON.toJSONString(inputServiceParameter));
            ExceptionLog exceptionLog = new ExceptionLog(ex, "格式化产品服务", parameters);
            logger.error(exceptionLog.toString());
            return null;
        }
    }

    private JSONObject formatEvent(ProductEvent productEvent, JSONObject inputEventParameter) {
        try {
            JSONObject formatedEvent = new JSONObject();
            JSONObject outputData = inputEventParameter.getJSONObject("outputData");
            formatedEvent.put("identifier", productEvent.getIdentifier());
            formatedEvent.put("name", productEvent.getName());
            formatedEvent.put("method", productEvent.getMethod());
            formatedEvent.put("desc", productEvent.getDesc());
            formatedEvent.put("type", productEvent.getType());
            try {
                List<JSONObject> outputDataList = formatParameters(productEvent.getOutputData(), outputData);
                formatedEvent.put("outputData", outputDataList);
            } catch (IllegalArgumentException ex) {
                HashMap<String, Object> parameters = new HashMap<>(3);
                parameters.put("productKey", productEvent.getProductKey());
                parameters.put("eventIdentifier", productEvent.getIdentifier());
                parameters.put("inputServiceParameter", JSON.toJSONString(inputEventParameter));
                ExceptionLog exceptionLog = new ExceptionLog(ex, "格式化事件参数", parameters);
                logger.error(exceptionLog.toString());
                return null;
            }
            return formatedEvent;
        } catch (Exception ex) {
            logger.error("格式化事件异常,异常详情：{}", ex.getMessage());
            return null;
        }
    }

    private List<JSONObject> formatParameters(List<Parameter> parameterList, JSONObject parameterValues) throws IllegalArgumentException {
        List<JSONObject> formatList = new ArrayList<>();
        for (Parameter inputParameter : parameterList) {
            String parameterIdentifier = inputParameter.getIdentifier();
            Object inputValue = parameterValues.get(parameterIdentifier);
            if (inputValue == null) {
                throw new IllegalArgumentException("未找到指定的参数:" + parameterIdentifier);
            }
            try {
                JSONObject formatParameter = formatParameter(inputParameter, inputValue,false);
                formatList.add(formatParameter);
            } catch (IllegalArgumentException ex) {
                throw ex;
            }
        }
        return formatList;
    }

    private JSONObject formatParameter(Parameter parameter, Object propertyValue, boolean isArrayItem) throws IllegalArgumentException {
        DataType parameterType = parameter.getDataType();
        if (matchDataType(parameterType, propertyValue, isArrayItem) != 0) {
            throw new IllegalArgumentException("传入参数(" + parameter.getIdentifier() + ")不符合模板规范");
        }
        if (parameterType.getIsArray() && !isArrayItem) {
            JSONObject formatParameter = new JSONObject();
            formatParameter.put("identifier", parameter.getIdentifier());
            formatParameter.put("name", parameter.getName());
            formatParameter.put("dataType", parameterType.getType());
            formatParameter.put("isArray", parameterType.getIsArray());
            List<Object> arrayItems = (List<Object>) propertyValue;
            List<JSONObject> formatArrayList = new ArrayList<>();
            for (Object arrayItem : arrayItems) {
                JSONObject formatItem = formatParameter(parameter, arrayItem, true);
                formatArrayList.add(formatItem);
            }
            formatParameter.put("value", formatArrayList);
            return formatParameter;
        }
        JSONObject formatParameter = new JSONObject();
        formatParameter.put("identifier", parameter.getIdentifier());
        formatParameter.put("name", parameter.getName());
        formatParameter.put("deviceUploadValue", propertyValue);
        formatParameter.put("value", propertyValue);
        formatParameter.put("dataType", parameterType.getType());
        switch (parameterType.getType()) {
            case "int": {
                String spec = parameterType.getSpecs();
                IntegerSpec integerSpec = JSON.parseObject(spec, IntegerSpec.class);
                formatParameter.put("unit", integerSpec.getUnit());
                formatParameter.put("unitName", integerSpec.getUnitName());
                break;
            }
            case "float":
            case "double": {
                String spec = parameterType.getSpecs();
                DoubleSpec integerSpec = JSON.parseObject(spec, DoubleSpec.class);
                formatParameter.put("unit", integerSpec.getUnit());
                formatParameter.put("unitName", integerSpec.getUnitName());
                break;
            }
            case "enum":
            case "bool": {
                HashMap<String, String> spec = JSON.parseObject(parameterType.getSpecs(), HashMap.class);
                String value = propertyValue.toString();
                String valueDescription = spec.get(value);
                formatParameter.put("value", valueDescription);
                break;
            }
            case "struct": {
                List<JSONObject> structParameters = JSONObject.parseObject(parameterType.getSpecs(), List.class);
                List<JSONObject> fieldJsons = new ArrayList<>();
                for (JSONObject structProperty : structParameters) {
                    Parameter fieldParameter = JSON.parseObject(structProperty.toJSONString(), Parameter.class);
                    String parameterIdentifier = fieldParameter.getIdentifier();
                    Object fieldValue = getFieldValue(propertyValue, parameterIdentifier);
                    if (fieldValue != null) {
                        JSONObject jsonObject = formatParameter(fieldParameter, fieldValue, false);
                        fieldJsons.add(jsonObject);
                    }
                }
                formatParameter.put("value", fieldJsons);
                break;
            }
            case "string": {
                break;
            }
            default: {
                logger.error("未知的属性类型:" + parameterType.getType());
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug("格式化参数 {}，传入值:{},返回结果:{}", parameter.getIdentifier(), propertyValue.toString(), formatParameter.toJSONString());
        }
        return formatParameter;
    }

    private Object getFieldValue(Object parameterValues, String parameterIdentifier) {
        if (parameterValues instanceof HashMap) {
            Map<String, Object> structValueList = (HashMap) parameterValues;
            return structValueList.get(parameterIdentifier);
        } else {
            JSONObject structValueList = (JSONObject) parameterValues;
            return structValueList.get(parameterIdentifier);
        }
    }

    /**
     * 验证属性值是否符合模板设置
     *
     * @param dataType       数据类型
     * @param parameterValue 传入的属性值
     * @param isArrayItem    是否是数组类型
     * @return 0-符合格式；1-小于属性下限;2-大于属性上限;3-不符合属性步长;4-布尔值匹配异常；5-属性值不在备选项中;-1-其它异常
     */
    private int matchDataType(DataType dataType, Object parameterValue, boolean isArrayItem) {
        String type = dataType.getType();
        // 处理数组
        if (!isArrayItem) {
            boolean isArray = dataType.getIsArray();
            if (isArray) {
                List<JSONObject> arrayItems = (List<JSONObject>) parameterValue;
                for (Object arrayItem : arrayItems) {
                    int matchResult = matchDataType(dataType, arrayItem, true);
                    if (matchResult != 0) {
                        return matchResult;
                    }
                }
                return 0;
            }
        }

        switch (type) {
            case "int":
                return matchInt(dataType, parameterValue);
            case "float":
            case "double":
                return matchDouble(dataType, parameterValue);
            case "enum":
                return matchEnum(dataType, parameterValue);
            case "bool":
                return matchBool(dataType, parameterValue);
            case "struct": {
                List<JSONObject> structFieldList = JSONObject.parseObject(dataType.getSpecs(), List.class);
                for (JSONObject fieldJSON : structFieldList) {
                    Parameter parameter = JSON.parseObject(fieldJSON.toJSONString(), Parameter.class);
                    String parameterIdentifier = parameter.getIdentifier();
                    DataType parameterDataType = parameter.getDataType();
                    Object value = getFieldValue(parameterValue, parameterIdentifier);
                    int matchResult = matchDataType(parameterDataType, value, false);
                    if (matchResult != 0) {
                        return matchResult;
                    }
                }
                return 0;
            }
            case "string": {
                break;
            }
            default: {
                logger.error("未知的属性类型:" + type);
            }
        }
        return 0;
    }

    private int matchInt(DataType dataType, Object parameterValue) {
        try {
            long inputValue = Long.valueOf(parameterValue.toString());
            IntegerSpec spec = JSON.parseObject(dataType.getSpecs(), IntegerSpec.class);
            if (inputValue < spec.getMin()) {
                return 1;
            } else if (inputValue > spec.getMax()) {
                return 2;
            } else if (inputValue % spec.getStep() != 0) {
                return 3;
            } else {
                return 0;
            }
        } catch (Exception ex) {
            return -1;
        }
    }

    private int matchDouble(DataType dataType, Object parameterValue) {
        try {
            double inputValue = Double.valueOf(parameterValue.toString());
            DoubleSpec spec = JSON.parseObject(dataType.getSpecs(), DoubleSpec.class);
            if (inputValue < spec.getMin()) {
                return 1;
            } else if (inputValue > spec.getMax()) {
                return 2;
            } else {
                BigDecimal step = BigDecimal.valueOf(spec.getStep());
                BigDecimal value = BigDecimal.valueOf(inputValue);
                if (value.remainder(step).doubleValue() != 0) {
                    return 3;
                } else {
                    return 0;
                }
            }
        } catch (Exception ex) {
            return -1;
        }
    }

    private int matchEnum(DataType dataType, Object parameterValue) {
        HashMap<String, String> spec = JSON.parseObject(dataType.getSpecs(), HashMap.class);
        if (spec.keySet().contains(parameterValue.toString())) {
            return 0;
        } else {
            return 5;
        }
    }

    private int matchBool(DataType dataType, Object parameterValue) {
        HashMap<String, String> spec = JSON.parseObject(dataType.getSpecs(), HashMap.class);
        if (spec.keySet().contains(parameterValue.toString())) {
            return 0;
        } else {
            return 4;
        }
    }

    private Map<String, ProductProperty> getProperties(String productKey) {
        List<ProductProperty> propertyList = productModelService.getProperties(productKey);
        Map<String, ProductProperty> properties = new HashMap<>(8);
        for (ProductProperty productProperty : propertyList) {
            String propertyIdentifier = productProperty.getIdentifier();
            properties.put(propertyIdentifier, productProperty);
        }
        return properties;
    }

    /**
     * <h1>验证上报或设置的属性是否符合模板规范</h1>
     *
     * @param parameterJson      参数值json
     * @param templateProperties 模板属性列表
     * @param userSetData        用户设置属性
     * @return 验证结果
     */
    private RestResult validateProperties(String parameterJson, Map<String, ProductProperty> templateProperties, boolean userSetData) {
        try {
            JSONObject outputJson = JSON.parseObject(parameterJson);
            if (outputJson != null) {
                JSONObject params = outputJson.getJSONObject("params");
                Set<String> identifiers = params.keySet();
                for (String identifier : identifiers) {
                    ProductProperty productProperty = templateProperties.get(identifier);
                    //TODO  未定义属性是否忽略
                    if (productProperty == null) {
                        return RestResult.getInstance(HttpResponseCode.ERROR_PROPERTY_NOT_FOUND, null);
                    }
                    DataType templateDataType = productProperty.getDataType();
                    String propertyAccessMode = productProperty.getAccessMode();

                    //用户设置不可写属性返回错误
                    if (userSetData && !propertyAccessMode.contains("w")) {
                        return RestResult.getInstance(HttpResponseCode.ERROR_PROPERTY_ACESS_DENIED, null);
                    }
                    Object inputParameterValue = params.get(identifier);
                    int formatMatchResult = matchDataType(templateDataType, inputParameterValue, false);
                    RestResult matchResult = parserMatchError(productProperty, formatMatchResult);
                    if (matchResult != null) {
                        return matchResult;
                    }
                }
                return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
            }
            return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        } catch (Exception ex) {
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, ex.getMessage());
        }
    }

    private RestResult parserMatchError(ProductProperty productProperty, int formatMatchResult) {
        switch (formatMatchResult) {
            case -1: {
                String errorMessage = "属性值格式异常：[" + productProperty.getName() + "]格式异常";
                return RestResult.getInstance(400, errorMessage, null);
            }
            case 1: {
                String errorMessage = "属性值格式异常：[" + productProperty.getName() + "]小于设定范围下限";
                return RestResult.getInstance(400, errorMessage, null);
            }
            case 2: {
                String errorMessage = "属性值格式异常：[" + productProperty.getName() + "]大于设定范围上限";
                return RestResult.getInstance(400, errorMessage, null);
            }
            case 3: {
                String errorMessage = "属性值格式异常：[" + productProperty.getName() + "]不符合设定步长";
                return RestResult.getInstance(400, errorMessage, null);
            }
            case 4: {
                String errorMessage = "属性值格式异常：[" + productProperty.getName() + "]不符合布尔值设定值";
                return RestResult.getInstance(400, errorMessage, null);
            }
            case 5: {
                String errorMessage = "属性值格式异常：[" + productProperty.getName() + "]不在预置备选项中";
                return RestResult.getInstance(400, errorMessage, null);
            }
            default:
                return null;
        }
    }
}
