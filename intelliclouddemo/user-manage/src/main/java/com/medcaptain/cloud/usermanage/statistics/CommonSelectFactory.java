package com.medcaptain.cloud.usermanage.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 通用查询服务工厂
 *
 * @author bingwen.ai
 */
@Component
public class CommonSelectFactory {
    @Autowired
    SqlCommonSelectServiceImpl sqlCommonSelectService;

    /**
     * 按数据库类型获取通用查询服务实现
     *
     * @param databaseType 数据库类型
     * @return 通用查询服务
     */
    public CommonSelectService getCommonSelectService(String databaseType) {
        switch (databaseType.toLowerCase()) {
            case "sql": {
                return sqlCommonSelectService;
            }
            default: {
                return null;
            }
        }
    }
}
