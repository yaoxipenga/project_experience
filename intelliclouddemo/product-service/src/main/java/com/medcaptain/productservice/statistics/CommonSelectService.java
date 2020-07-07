package com.medcaptain.productservice.statistics;


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
     * @param sql 需执行的查询语句
     * @return 查询结果
     */
    List select(String sql) throws Exception;
}
