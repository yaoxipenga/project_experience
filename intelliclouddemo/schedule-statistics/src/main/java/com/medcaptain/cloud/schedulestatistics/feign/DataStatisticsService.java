package com.medcaptain.cloud.schedulestatistics.feign;

import com.medcaptain.dto.RestResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 调用数据统计服务的通用脚本
 *
 * @author bingwen.ai
 */
@FeignClient(value = "data-statistics", configuration = FeignConfig.class)
@Service
public interface DataStatisticsService extends StatisticsService {

    /**
     * 调用数据统计服务的通用统计脚本
     *
     * @param scriptName 统计脚本名称
     * @return 统计结果
     */
    @Override
    @GetMapping(value = "/internalStatistics/{scriptName}")
    RestResult statistics(@PathVariable(name = "scriptName") String scriptName);
}
