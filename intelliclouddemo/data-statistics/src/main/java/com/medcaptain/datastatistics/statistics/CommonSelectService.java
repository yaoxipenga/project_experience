package com.medcaptain.datastatistics.statistics;


import com.alibaba.fastjson.JSONObject;
import com.medcaptain.annotation.Log;

import java.util.List;

/**
 * 通用数据库查询接口
 *
 * @author bingwen.ai
 */
public interface CommonSelectService {
    /**
     * 查询
     *
     * @param sql        需执行的查询语句
     * @param parameters 传入参数
     * @return 查询结果
     * @throws Exception 执行异常
     */
    @Log
    List select(String sql, JSONObject parameters) throws Exception;
}
