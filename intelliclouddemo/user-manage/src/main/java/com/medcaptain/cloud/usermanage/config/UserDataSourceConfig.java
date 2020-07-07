package com.medcaptain.cloud.usermanage.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * <h2>MySQL数据库连接池</h2>
 * <p>
 * 使用阿里巴巴数据库开源组件druid
 *
 * @author bingwen.ai
 */
@Configuration
public class UserDataSourceConfig {
    private static final String MAPPER_PATH = "classpath*:/Mapper/*.xml";

    private static final String ENTITY_PACKAGE = "com.medcaptain.cloud.usermanage.entity";

    @Autowired
    DataSourceConfig dataSourceConfig;

    @Primary
    @Bean(name = "userDataSource")
    public DataSource webPageDataSource() throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(dataSourceConfig.getJdbcDriverName());
        dataSource.setUrl(dataSourceConfig.getDbUrl());
        dataSource.setUsername(dataSourceConfig.getUserName());
        dataSource.setPassword(dataSourceConfig.getPassword());
        dataSource.setFilters(dataSourceConfig.getFilter());
        dataSource.setMaxActive(dataSourceConfig.getMaxActive());
        dataSource.setMaxWait(dataSourceConfig.getMaxWait());
        dataSource.setMinIdle(dataSourceConfig.getMinIdle());
        dataSource.setTimeBetweenEvictionRunsMillis(dataSourceConfig.getTimeBetweenEvictionRunsMillis());
        dataSource.setMinEvictableIdleTimeMillis(dataSourceConfig.getMinEvictableIdleTimeMillis());
        dataSource.setTestWhileIdle(dataSourceConfig.isTestWhileIdle());
        dataSource.setValidationQuery(dataSourceConfig.getValidationQuery());
        dataSource.setTestOnBorrow(dataSourceConfig.isTestOnBorrow());
        dataSource.setTestOnReturn(dataSourceConfig.isTestOnReturn());
        dataSource.setPoolPreparedStatements(dataSourceConfig.isPoolPreparedStatements());
        dataSource.setMaxOpenPreparedStatements(dataSourceConfig.getMaxOpenPreparedStatements());
        dataSource.setRemoveAbandoned(dataSourceConfig.isRemoveAbandoned());
        dataSource.setRemoveAbandonedTimeout(dataSourceConfig.getRemoveAbandonedTimeout());
        dataSource.init();
        return dataSource;
    }

    @Primary
    @Bean(name = "userSqlSessionFactory")
    public SqlSessionFactory webPageSqlSessionFactory(@Qualifier("userDataSource") DataSource userDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(userDataSource);
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources(MAPPER_PATH));
        sessionFactory.setTypeAliasesPackage(ENTITY_PACKAGE);
        return sessionFactory.getObject();
    }

    @Primary
    @Bean(name = "userTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("userDataSource") DataSource userDataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(userDataSource);
        return transactionManager;
    }
}
