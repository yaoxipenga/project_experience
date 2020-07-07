package com.medcaptain.cloud.usermanage.service;

import com.medcaptain.cloud.usermanage.entity.OperateLog;

/**
 * 用户操作日志服务
 *
 * @author bingwen.ai
 */
public interface OperateLogService {
    /**
     * 新增日志
     *
     * @param operateLog 操作日志
     * @return true = 新增成功 ; false = 新增失败
     */
    void saveLog(OperateLog operateLog);
}
