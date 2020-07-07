package com.medcaptain.productservice.statistics;

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
    public synchronized List select(String sql) throws SQLException {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Connection connection = sqlSession.getConnection();
        List result = SqlUtil.commonSelect(connection, sql);
        sqlSession.close();
        return result;
    }
}
