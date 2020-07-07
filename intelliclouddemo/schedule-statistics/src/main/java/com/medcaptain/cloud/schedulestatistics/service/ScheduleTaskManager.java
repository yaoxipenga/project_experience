package com.medcaptain.cloud.schedulestatistics.service;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.medcaptain.cloud.schedulestatistics.config.ScheduleStatisticsProperties;
import com.medcaptain.cloud.schedulestatistics.entity.ScheduleTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 定时任务管理器
 * <p>管理定时任务线程</p>
 *
 * @author bingwen.ai
 */
@Service
public class ScheduleTaskManager implements ApplicationContextAware {
    private Logger logger = LoggerFactory.getLogger(ScheduleTaskManager.class);

    private ThreadPoolExecutor threadPoolExecutor;

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Autowired
    ScheduleStatisticsProperties scheduleStatisticsProperties;

    /**
     * 初始化线程池
     * <p>核心线程数最小为6</p>
     * <p>最大线程数至少为20</p>
     * <p>缓冲时间固定为10秒</p>
     */
    @PostConstruct
    void initializeThreadPool() {
        int coreThreadSize = scheduleStatisticsProperties.getCoreThreadSize();
        coreThreadSize = coreThreadSize < 6 ? 6 : coreThreadSize;
        int maxThreadSize = scheduleStatisticsProperties.getMaxThreadSize();
        maxThreadSize = coreThreadSize < 20 ? 20 : maxThreadSize;
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("scheduleTaskPool-%d").build();
        threadPoolExecutor = new ThreadPoolExecutor(coreThreadSize, maxThreadSize, 10, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(), namedThreadFactory);
    }

    /**
     * 新增执行任务
     *
     * @param scheduleTask 定时任务
     */
    public void addTask(ScheduleTask scheduleTask) {
        try {
            ScheduleTaskExecutor scheduleTaskExecutor = applicationContext.getBean(ScheduleTaskExecutor.class);
            scheduleTaskExecutor.setScheduleTask(scheduleTask);
            threadPoolExecutor.submit(scheduleTaskExecutor);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }
}
