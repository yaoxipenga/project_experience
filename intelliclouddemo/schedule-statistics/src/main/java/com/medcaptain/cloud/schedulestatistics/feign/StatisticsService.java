package com.medcaptain.cloud.schedulestatistics.feign;

import com.medcaptain.dto.RestResult;

/**
 * 通用统计接口
 *
 * @author bingwen.ai
 */
public interface StatisticsService {
    /**
     * 调用各业务服务通用统计接口
     *
     * @param scriptName 统计脚本名称
     * @return 统计结果json数组
     */
    RestResult statistics(String scriptName);
}
