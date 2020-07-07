package com.medcaptain.cloud.usermanage.service.impl;

import com.medcaptain.cloud.usermanage.entity.OperateLog;
import com.medcaptain.cloud.usermanage.mapper.OperateLogMapper;
import com.medcaptain.cloud.usermanage.service.OperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户操作日志服务实现
 *
 * @author bingwen.ai
 */
@Service
public class OperateLogServiceImpl implements OperateLogService {
    @Autowired
    OperateLogMapper operateLogMapper;

    @Override
    public void saveLog(OperateLog operateLog) {
        operateLogMapper.insertSelective(operateLog);
    }
}
