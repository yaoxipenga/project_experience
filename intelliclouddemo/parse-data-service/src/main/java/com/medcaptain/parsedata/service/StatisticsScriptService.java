package com.medcaptain.parsedata.service;

import com.medcaptain.parsedata.entity.mysql.StatisticsScript;

/**
 * 统计脚本服务
 *
 * @author bingwen.ai
 */
public interface StatisticsScriptService {

    /**
     * 添加通用查询脚本
     *
     * @param scriptName    脚本名称
     * @param scriptContent 脚本查询语句
     * @param remark        脚本说明
     * @param databaseType  数据库类型：SQL/MONGODB/HBASE/REDIS
     * @return true = 添加成功 ； false = 添加失败
     */
    boolean addScript(String scriptName, String scriptContent, String databaseType, String remark);

    /**
     * 按脚本名称查询对应的脚本详情
     *
     * @param scriptName 脚本名称
     * @return 脚本详情
     */
    StatisticsScript getScript(String scriptName);
}
