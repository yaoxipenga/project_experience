package com.medcaptain.cloud.schedulestatistics.feign;

import com.medcaptain.constant.BusinessMicroService;
import com.medcaptain.logging.ExceptionLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * 统计调用服务创建工厂
 *
 * @author bingwen.ai
 */
@Component
public class StatisticsServiceFactory implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    private Logger logger = LoggerFactory.getLogger(StatisticsServiceFactory.class);

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 根据服务名获取调用的外部服务实例
     *
     * @param serviceName 业务服务名称
     * @return 业务服务feign调用实例
     */
    public StatisticsService getStatisticsService(String serviceName) {
        try {
            switch (serviceName) {
                case BusinessMicroService.USER_MANAGE: {
                    return applicationContext.getBean(UserManageService.class);
                }
                case BusinessMicroService.PRODUCT_MANAGE: {
                    return applicationContext.getBean(ProductManageService.class);
                }
                case BusinessMicroService.PARSE_DATE: {
                    return applicationContext.getBean(ParseDataService.class);
                }
                case BusinessMicroService.DATA_STATISTIC: {
                    return applicationContext.getBean(DataStatisticsService.class);
                }
                default: {
                    return null;
                }
            }
        } catch (Exception ex) {
            HashMap<String, Object> parameters = new HashMap<>(1);
            parameters.put("serviceName", serviceName);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "获取调用微服务实例");
            exceptionLog.setParameters(parameters);
            logger.error(exceptionLog.toString());
            return null;
        }
    }
}
