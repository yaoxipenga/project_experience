package com.medcaptain.cloud.producttemplate.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 产品模板服务相关配置
 *
 * @author bingwen.ai
 */
@Component
@ConfigurationProperties(prefix = "product-template")
public class ProductTemplateProperties {
    private int productModelExpireDays = 7;

    private int shadowDevicePropertyExpireDays = 7;

    /**
     * @return 产品物模型定义老化时间
     */
    public int getProductModelExpireDays() {
        return productModelExpireDays;
    }

    public void setProductModelExpireDays(int productModelExpireDays) {
        this.productModelExpireDays = productModelExpireDays;
    }

    /**
     * @return 影子设备属性老化时间
     */
    public int getShadowDevicePropertyExpireDays() {
        return shadowDevicePropertyExpireDays;
    }

    public void setShadowDevicePropertyExpireDays(int shadowDevicePropertyExpireDays) {
        this.shadowDevicePropertyExpireDays = shadowDevicePropertyExpireDays;
    }
}
