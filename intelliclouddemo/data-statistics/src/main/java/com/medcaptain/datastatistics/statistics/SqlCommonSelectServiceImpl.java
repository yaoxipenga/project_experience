package com.medcaptain.datastatistics.statistics;

import com.alibaba.fastjson.JSONObject;
import com.medcaptain.utils.SqlUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;


/**
 * 关系型数据库通用查询
 *
 * @author bingwen.ai
 */
@Service
public class SqlCommonSelectServiceImpl implements CommonSelectService {
    private Logger logger = LoggerFactory.getLogger(SqlCommonSelectServiceImpl.class);

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    @Override
    public synchronized List select(String sql, JSONObject parameters) throws SQLException {
        sql = replaceParameters(sql, parameters);
        logger.debug("执行SQL通用查询语句： {}", sql);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Connection connection = sqlSession.getConnection();
        List result = SqlUtil.commonSelect(connection, sql);
        sqlSession.close();
        return result;
    }

    private String replaceParameters(String sql, JSONObject parameters) {
        if (parameters != null) {
            Set<String> parameterNames = parameters.keySet();
            for (String parameterName : parameterNames) {
                sql = sql.replaceAll("%" + parameterName + "%", parameters.getString(parameterName));
            }
        }
        return sql;
    }
}
