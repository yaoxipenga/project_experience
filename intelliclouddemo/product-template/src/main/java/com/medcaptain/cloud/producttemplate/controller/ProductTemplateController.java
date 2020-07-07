package com.medcaptain.cloud.producttemplate.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.medcaptain.annotation.Log;
import com.medcaptain.cloud.producttemplate.entity.ProductEvent;
import com.medcaptain.cloud.producttemplate.entity.ProductProperty;
import com.medcaptain.cloud.producttemplate.entity.ProductService;
import com.medcaptain.cloud.producttemplate.entity.ProductTemplate;
import com.medcaptain.cloud.producttemplate.entity.Profile;
import com.medcaptain.cloud.producttemplate.entity.ShadowProperty;
import com.medcaptain.cloud.producttemplate.processor.MessageProcess;
import com.medcaptain.cloud.producttemplate.processor.MessageProcessorFactory;
import com.medcaptain.cloud.producttemplate.service.ProductModelService;
import com.medcaptain.cloud.producttemplate.service.ShadowDeviceService;
import com.medcaptain.dto.RestResult;
import com.medcaptain.enums.HttpResponseCode;
import com.medcaptain.logging.ExceptionLog;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 产品模板访问控制器
 *
 * @author bingwen.ai
 */
@RestController
public class ProductTemplateController {
    private Logger logger = LoggerFactory.getLogger(ProductTemplateController.class);

    private static final String DEFAULT_MESSAGE_VERSION = "1.0.0";

    private static final int BYTE_CACHE_SIZE = 1024;

    private static final String UNKNOWN_MESSAGE_VERSION = "未知的设备上报消息版本";

    @Autowired
    ShadowDeviceService shadowDeviceService;

    @Autowired
    MessageProcessorFactory messageProcessorFactory;

    @Autowired
    ProductModelService productModelService;

    /**
     * 添加产品模板
     *
     * @param request http请求
     * @return 操作结果json
     */
    @PostMapping(value = "/producttemplate")
    public RestResult parseProductTemplateFile(HttpServletRequest request) {
        String content = readStreamContent(request);
        try {
            ProductTemplate productTemplate = JSON.parseObject(content, ProductTemplate.class);
            if (productTemplate == null) {
                return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
            }
            Profile profile = productTemplate.getProfile();
            String productKey = profile.getProductKey();
            if (!productModelService.clearModel(productKey)) {
                return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
            }
            List<ProductService> productServiceList = productTemplate.getServices();
            List<ProductProperty> productPropertyList = productTemplate.getProperties();
            List<ProductEvent> productEventList = productTemplate.getEvents();
            logTemplateParseInfo(productKey, productServiceList, productPropertyList, productEventList);
            for (ProductService productService : productServiceList) {
                productModelService.saveService(productService);
            }
            for (ProductProperty productProperty : productPropertyList) {
                productModelService.saveProperty(productProperty);
            }
            for (ProductEvent productEvent : productEventList) {
                productModelService.saveEvent(productEvent);
            }
            return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
        } catch (Exception ex) {
            HashMap<String, Object> parameters = new HashMap<>(1);
            parameters.put("content", content);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "解析产品模板", parameters);
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }

    /**
     * 验证产品服务参数规范性
     *
     * @param productKey 产品key
     * @param paramJson  参数json
     * @return 验证结果
     */
    @Log
    @PostMapping(value = "/producttemplate/service/{productKey}")
    public RestResult validateService(@PathVariable(value = "productKey") String productKey,
                                      @RequestParam(value = "paramJson") String paramJson) {
        if (StringUtils.isBlank(productKey) || StringUtils.isBlank(paramJson)) {
            return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }
        String messageVersion = getMessageVersion(paramJson);
        JSONObject inputServiceParameter = getInputParameter(paramJson);
        if (StringUtils.isBlank(messageVersion) || inputServiceParameter == null) {
            return RestResult.getInstance(HttpResponseCode.ERROR_PARAMETER_FORMAT, null);
        }
        try {
            MessageProcess messageProcess = messageProcessorFactory.getMessageProcessor(messageVersion);
            if (messageProcess == null) {
                return RestResult.getInstance(409, UNKNOWN_MESSAGE_VERSION, null);
            }
            return messageProcess.validateService(productKey, inputServiceParameter);
        } catch (Exception ex) {
            HashMap<String, Object> parameters = new HashMap<>(3);
            parameters.put("messageVersion", messageVersion);
            parameters.put("productKey", productKey);
            parameters.put("paramJson", paramJson);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "验证产品服务参数规范性", parameters);
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }

    /**
     * 验证事件参数规范
     *
     * @param productKey 产品key
     * @param paramJson  参数json
     * @return 验证结果
     */
    @Log
    @PostMapping(value = "/producttemplate/event/{productKey}")
    public RestResult validateEvent(@PathVariable(value = "productKey") String productKey,
                                    @RequestParam(value = "paramJson") String paramJson) {
        if (StringUtils.isBlank(productKey) || StringUtils.isBlank(paramJson)) {
            return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }
        try {
            String messageVersion = getMessageVersion(paramJson);
            JSONObject inputEventParameter = getInputParameter(paramJson);
            if (StringUtils.isBlank(messageVersion) || inputEventParameter == null) {
                return RestResult.getInstance(HttpResponseCode.ERROR_PARAMETER_FORMAT, null);
            }
            MessageProcess messageProcess = messageProcessorFactory.getMessageProcessor(messageVersion);
            if (messageProcess == null) {
                return RestResult.getInstance(409, UNKNOWN_MESSAGE_VERSION, null);
            }
            return messageProcess.validateEvent(productKey, inputEventParameter);
        } catch (Exception ex) {
            HashMap<String, Object> parameters = new HashMap<>(2);
            parameters.put("productKey", productKey);
            parameters.put("paramJson", paramJson);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "验证事件参数规范", parameters);
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }

    /**
     * 解析产品属性历史记录
     *
     * @param productKey 产品编号
     * @param paramJson  历史属性json串
     * @return 格式化后的属性json
     */
    @Log
    @PostMapping(value = "/producttemplate/property/{productKey}")
    public RestResult parseProperties(@PathVariable(value = "productKey") String productKey,
                                      @RequestParam(value = "paramJson") String paramJson) {
        if (StringUtils.isBlank(productKey) || StringUtils.isBlank(paramJson)) {
            return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }
        String messageVersion = getMessageVersion(paramJson);
        if (StringUtils.isBlank(messageVersion)) {
            return RestResult.getInstance(HttpResponseCode.ERROR_PARAMETER_FORMAT, null);
        }
        try {
            MessageProcess messageProcess = messageProcessorFactory.getMessageProcessor(messageVersion);
            if (messageProcess == null) {
                return RestResult.getInstance(409, UNKNOWN_MESSAGE_VERSION, null);
            }
            return messageProcess.parseProperties(productKey, paramJson);
        } catch (Exception ex) {
            HashMap<String, Object> parameters = new HashMap<>(3);
            parameters.put("messageVersion", messageVersion);
            parameters.put("productKey", productKey);
            parameters.put("paramJson", paramJson);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "解析产品属性历史记录", parameters);
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }

    /**
     * 上报设备属性
     *
     * @param productKey     产品key
     * @param deviceTripleID 设备三元组
     * @param paramJson      参数json
     * @return 操作结果
     */
    @Log
    @PostMapping(value = "/producttemplate/property/{productKey}/upload")
    public RestResult uploadDeviceProperty(@PathVariable(value = "productKey") String productKey,
                                           @RequestParam(value = "deviceTripleId") String deviceTripleID,
                                           @RequestParam(value = "paramJson") String paramJson) {
        if (StringUtils.isBlank(productKey) || StringUtils.isBlank(paramJson)) {
            return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }
        String messageVersion = getMessageVersion(paramJson);
        if (StringUtils.isBlank(messageVersion)) {
            return RestResult.getInstance(HttpResponseCode.ERROR_PARAMETER_FORMAT, null);
        }
        try {
            MessageProcess messageProcess = messageProcessorFactory.getMessageProcessor(messageVersion);
            if (messageProcess == null) {
                return RestResult.getInstance(409, UNKNOWN_MESSAGE_VERSION, null);
            }
            return messageProcess.uploadDeviceProperty(productKey, deviceTripleID, paramJson);
        } catch (Exception ex) {
            HashMap<String, Object> parameters = new HashMap<>(4);
            parameters.put("messageVersion", messageVersion);
            parameters.put("productKey", productKey);
            parameters.put("deviceTripleID", deviceTripleID);
            parameters.put("paramJson", paramJson);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "上报设备属性", parameters);
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }

    /**
     * 设置设备属性(前端或其他服务调用)
     *
     * @param productKey     产品key
     * @param deviceTripleID 设备三元组
     * @param paramJson      参数json
     * @return 操作结果
     */
    @Log
    @PostMapping(value = "/producttemplate/property/{productKey}/download")
    public RestResult setDeviceProperties(@PathVariable(value = "productKey") String productKey,
                                          @RequestParam(value = "deviceTripleId") String deviceTripleID,
                                          @RequestParam(value = "paramJson") String paramJson) {
        if (StringUtils.isBlank(productKey) || StringUtils.isBlank(paramJson)) {
            return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }
        //TODO 设备鉴权
        String messageVersion = getMessageVersion(paramJson);
        if (StringUtils.isBlank(messageVersion)) {
            return RestResult.getInstance(HttpResponseCode.ERROR_PARAMETER_FORMAT, null);
        }
        try {
            MessageProcess messageProcess = messageProcessorFactory.getMessageProcessor(messageVersion);
            if (messageProcess == null) {
                return RestResult.getInstance(409, UNKNOWN_MESSAGE_VERSION, null);
            }
            return messageProcess.setDeviceProperties(productKey, deviceTripleID, paramJson);
        } catch (Exception ex) {
            HashMap<String, Object> parameters = new HashMap<>(4);
            parameters.put("messageVersion", messageVersion);
            parameters.put("productKey", productKey);
            parameters.put("deviceTripleID", deviceTripleID);
            parameters.put("paramJson", paramJson);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "设置设备属性", parameters);
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }

    /**
     * 读取影子设备属性
     *
     * @param productKey          产品key
     * @param deviceTripleID      设备三元组
     * @param propertyIdentifiers 属性列表
     * @return 属性列表json
     */
    @Log
    @GetMapping(value = "/product/{productKey}/device/{deviceTripleId}/properties")
    public RestResult getShadowDeviceProperties(@PathVariable(value = "productKey") String productKey,
                                                @PathVariable(value = "deviceTripleId") String deviceTripleID,
                                                @RequestParam(value = "propertyIdentifiers", defaultValue = "") String propertyIdentifiers) {
        try {
            List<ShadowProperty> shadowProperties = shadowDeviceService.getShadowProperties(deviceTripleID, propertyIdentifiers);
            if (shadowProperties != null && shadowProperties.size() > 0) {
                //规范数据，填充单位、名称等信息
                List<JSONObject> formatPropertyList = formatProperties(productKey, shadowProperties);
                return RestResult.getInstance(HttpResponseCode.SUCCESS, formatPropertyList);
            } else {
                return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
            }
        } catch (Exception ex) {
            HashMap<String, Object> parameters = new HashMap<>(4);
            parameters.put("productKey", productKey);
            parameters.put("deviceTripleID", deviceTripleID);
            parameters.put("propertyIdentifiers", propertyIdentifiers);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "读取影子设备属性", parameters);
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }

    private String getMessageVersion(String paramJson) {
        try {
            JSONObject inputMessage = JSON.parseObject(paramJson);
            return inputMessage.getString("version");
        } catch (Exception ex) {
            return null;
        }
    }

    private JSONObject getInputParameter(String inputJson) {
        try {
            JSONObject inputObject = JSON.parseObject(inputJson);
            return inputObject.getJSONObject("params");
        } catch (Exception ex) {
            return null;
        }
    }

    private List<JSONObject> formatProperties(String productKey, List<ShadowProperty> shadowPropertyList) {
        List<JSONObject> formatPropertyList = new ArrayList<>();
        for (ShadowProperty shadowProperty : shadowPropertyList) {
            String propertyIdentifier = shadowProperty.getIdentifier();
            String propertyVersion = shadowProperty.getVersion();
            MessageProcess messageProcess = messageProcessorFactory.getMessageProcessor(propertyVersion);
            if (messageProcess == null) {
                messageProcess = messageProcessorFactory.getMessageProcessor(DEFAULT_MESSAGE_VERSION);
            }
            JSONObject formatProperty = messageProcess.formatProperty(productKey, propertyIdentifier, shadowProperty);
            if (formatProperty != null) {
                formatPropertyList.add(formatProperty);
            }
        }
        return formatPropertyList;
    }

    private String readStreamContent(HttpServletRequest request) {
        try {
            ServletInputStream inputStream = request.getInputStream();
            StringBuilder contentBuffer = new StringBuilder();
            byte[] contentBytes = new byte[BYTE_CACHE_SIZE];
            int readLength;
            while ((readLength = inputStream.readLine(contentBytes, 0, contentBytes.length)) > 0) {
                contentBuffer.append(new String(contentBytes, 0, readLength));
            }
            String content = contentBuffer.toString();
            inputStream.close();
            return content;
        } catch (Exception ex) {
            logger.error("读取产品模板流内容异常，详细信息:{}", ex);
            return null;
        }
    }

    private void logTemplateParseInfo(String productKey, List<ProductService> productServiceList,
                                      List<ProductProperty> productPropertyList, List<ProductEvent> productEventList) {
        int serviceCount = productServiceList == null ? 0 : productServiceList.size();
        int propertyCount = productPropertyList == null ? 0 : productPropertyList.size();
        int eventCount = productEventList == null ? 0 : productEventList.size();
        logger.info("解析产品模板,productKey = {}, 服务数量 = {}, 事件数量 = {}, 属性数量 = {}",
                productKey, serviceCount, eventCount, propertyCount);
    }
}
