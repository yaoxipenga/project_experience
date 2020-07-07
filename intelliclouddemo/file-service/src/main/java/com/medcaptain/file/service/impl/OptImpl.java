package com.medcaptain.file.service.impl;

import com.medcaptain.dto.RestResult;
import com.medcaptain.enums.HttpResponseCode;
import com.medcaptain.file.entity.mysql.OptLogExample;
import com.medcaptain.file.mapper.OptLogMapper;
import com.medcaptain.file.mapper.OptMapper;
import com.medcaptain.file.service.OptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 操作日志服务
 * @author Adam.Chen
 */
@Service
public class OptImpl implements OptService {
    @Autowired
    private OptMapper optMapper;
    @Autowired
    private OptLogMapper optLogMapper;

    @Override
    public RestResult getOptLogList(int page,int per, int userId){
        OptLogExample optLogExample = new OptLogExample();
        optLogExample.createCriteria().andUserIdEqualTo(userId);
        Map<String,Object> returnData = new HashMap<String,Object>();
        returnData.put("operationLogList",optMapper.getOptLogList(per*page,per,userId));
        returnData.put("total",optLogMapper.countByExample(optLogExample));
        return RestResult.getInstance(HttpResponseCode.SUCCESS, returnData);
    }
}
