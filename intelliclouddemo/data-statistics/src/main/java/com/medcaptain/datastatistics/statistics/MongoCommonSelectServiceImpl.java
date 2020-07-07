package com.medcaptain.datastatistics.statistics;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * MongoDB通用查询
 *
 * @author bingwen.ai
 */
@Service
public class MongoCommonSelectServiceImpl implements CommonSelectService {
    private Logger logger = LoggerFactory.getLogger(MongoCommonSelectServiceImpl.class);

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List select(String sql, JSONObject parameters) throws Exception {
        sql = replaceCondition(sql, parameters);
        Document document = Document.parse(sql);
        logger.debug("执行mongodb通用查询语句 =  {}", document.toJson());
        Document resultDocument = mongoTemplate.executeCommand(document);
        if (resultDocument != null && resultDocument.size() > 0) {
            return (ArrayList) ((Document) resultDocument.get("cursor")).get("firstBatch");
        }
        return null;
    }

    /**
     * 替换传入的查询条件
     * <p>希望查询某一项条件的全部数据，参数传入all(不区分大小写)</p>
     * <p>脚本模板中做使用or操作符做特殊处理</p>
     * @param sql        脚本语句
     * @param parameters 传入的参数
     * @return 替换后的脚本
     */
    private String replaceCondition(String sql, JSONObject parameters) {
        if (parameters != null) {
            for (String conditionName : parameters.keySet()) {
                String conditionValue = parameters.getString(conditionName);
                if (StringUtils.isNotBlank(conditionValue)) {
                    if("all".equals(conditionValue.toLowerCase())) {
                        sql = sql.replaceAll("%" + conditionName + "%", "all");
                    } else {
                        sql = sql.replaceAll("%" + conditionName + "%", conditionValue);
                    }
                }
            }
        }
        return sql;
    }
}
