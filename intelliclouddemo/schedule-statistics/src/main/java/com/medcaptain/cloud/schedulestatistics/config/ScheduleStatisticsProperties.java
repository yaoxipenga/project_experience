package com.medcaptain.cloud.schedulestatistics.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 定时统计任务配置
 *
 * @author bingwen.ai
 */
@Component
@ConfigurationProperties(prefix = "schedule-statistics")
public class ScheduleStatisticsProperties {
    private int coreThreadSize = 6;

    private int maxThreadSize = 100;

    public int getMaxThreadSize() {
        return maxThreadSize;
    }

    public void setMaxThreadSize(int maxThreadSize) {
        this.maxThreadSize = maxThreadSize;
    }

    public int getCoreThreadSize() {
        return coreThreadSize;
    }

    public void setCoreThreadSize(int coreThreadSize) {
        this.coreThreadSize = coreThreadSize;
    }
}
