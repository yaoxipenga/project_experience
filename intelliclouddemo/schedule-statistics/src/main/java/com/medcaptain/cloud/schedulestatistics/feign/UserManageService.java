package com.medcaptain.cloud.schedulestatistics.feign;

import com.medcaptain.authorization.MethodRequest;
import com.medcaptain.dto.RestResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

/**
 * 调用用户管理服务的后端接口鉴权
 *
 * @author bingwen.ai
 */

@FeignClient(value = "user-manage", configuration = FeignConfig.class)
@Service
public interface UserManageService extends StatisticsService {
    /**
     * 检查是否有后端接口访问权限
     *
     * @param methodRequest 接口访问信息
     * @return true = 有访问权限； false = 无访问权限
     */
    @PostMapping(value = "/authorization")
    boolean checkAuthorization(@RequestBody MethodRequest methodRequest);

    /**
     * 调用用户管理服务的通用统计脚本
     *
     * @param scriptName 统计脚本名称
     * @return 统计结果
     */
    @Override
    @GetMapping(value = "/statistics/{scriptName}")
    RestResult statistics(@PathVariable(name = "scriptName") String scriptName);
}
