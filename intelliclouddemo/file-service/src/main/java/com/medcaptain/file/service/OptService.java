package com.medcaptain.file.service;

import com.medcaptain.dto.RestResult;

/**
 * 操作日志的service
 * @author Adam.Chen
 */
public interface OptService {
    /**
     * 获取操作日志
     * @param page
     * @param per
     * @param userId
     * @return
     */
    RestResult getOptLogList(int page,int per, int userId);
}
