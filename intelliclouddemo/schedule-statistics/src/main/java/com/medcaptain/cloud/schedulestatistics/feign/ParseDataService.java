package com.medcaptain.cloud.schedulestatistics.feign;

import com.medcaptain.dto.RestResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 调用用户管理服务的后端接口鉴权
 *
 * @author bingwen.ai
 */

@FeignClient(value = "parse-data-service", configuration = FeignConfig.class)
@Service
public interface ParseDataService extends StatisticsService {
    /**
     * 调用设备日志管理服务的通用统计脚本
     *
     * @param scriptName 统计脚本名称
     * @return 统计结果
     */
    @Override
    @GetMapping(value = "/statistics/{scriptName}")
    RestResult statistics(@PathVariable(name = "scriptName") String scriptName);
}
