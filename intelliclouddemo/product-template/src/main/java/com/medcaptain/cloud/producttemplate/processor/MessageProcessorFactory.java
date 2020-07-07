package com.medcaptain.cloud.producttemplate.processor;

import com.medcaptain.cloud.producttemplate.service.ProductModelService;
import com.medcaptain.cloud.producttemplate.service.ShadowDeviceService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 消息处理器工厂
 *
 * @author bingwen.ai
 */
@Component
public class MessageProcessorFactory {
    @Autowired
    ProductModelService productModelService;

    @Autowired
    ShadowDeviceService shadowDeviceService;

    /**
     * 根据上报消息版本获取消息处理器
     *
     * @param messageVersion 消息版本
     * @return 消息处理器
     */
    public MessageProcess getMessageProcessor(String messageVersion) {
        if (StringUtils.isBlank(messageVersion)) {
            return null;
        }
        switch (messageVersion) {
            case "1.0.0":
                return new MessageProcessor100(productModelService, shadowDeviceService);
            default:
                return null;
        }
    }
}
