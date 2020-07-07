package com.medcaptain.mqttdeviceauthenticate.config;



import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * 配置连接池
 */
@Configuration
@MapperScan(basePackages = "com.medcaptain.mqttdeviceauthenticate.dao", sqlSessionFactoryRef = "deviceSqlSessionFactory")
public class DeviceDataSourceConfig {

    private static final String Mapper_Path = "classpath:mapper/*.xml";
    private static final String Entity_Package = "com.medcaptain.mqttdeviceauthenticate.entity";

    @Value("${Druid.jdbcDriver}")
    String jdbcDriverName;

    @Value("${Druid.dbURL}")
    String dburl;

    @Value("${Druid.DBUserName}")
    String userName;

    @Value("${Druid.DBPassword}")
    String password;

    @Value("${Druid.Filter}")
    String filter;

    @Value("${Druid.MaxActive}")
    int maxActive;

    @Value("${Druid.MaxWait}")
    int maxWait;

    @Value("${Druid.MinIdle}")
    int minIdle;

    @Value("${Druid.TimeBetweenEvictionRunsMillis}")
    int timeBetweenEvictionRunsMillis;

    @Value("${Druid.MinEvictableIdleTimeMillis}")
    int minEvictableIdleTimeMillis;

    @Value("${Druid.TestWhileIdle}")
    boolean testWhileIdle;

    @Value("${Druid.TestOnBorrow}")
    boolean testOnBorrow;

    @Value("${Druid.TestOnReturn}")
    boolean testOnReturn;

    @Value("${Druid.PoolPreparedStatements}")
    boolean poolPreparedStatements;
    @Value("${Druid.MaxOpenPreparedStatements}")
    int maxOpenPreparedStatements;

    @Value("${Druid.ValidationQuery}")
    String validationQuery;

    @Bean(name = "deviceDataSource")
    @Primary
    public DataSource DeviceDataSource() throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(jdbcDriverName);
        dataSource.setUrl(dburl);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        dataSource.setFilters(filter);
        dataSource.setMaxActive(maxActive);
        dataSource.setMaxWait(maxWait);
        dataSource.setMinIdle(minIdle);
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        dataSource.setTestWhileIdle(testWhileIdle);
        dataSource.setValidationQuery(validationQuery);
        dataSource.setTestOnBorrow(testOnBorrow);
        dataSource.setTestOnReturn(testOnReturn);
        dataSource.setPoolPreparedStatements(poolPreparedStatements);
        dataSource.setMaxOpenPreparedStatements(maxOpenPreparedStatements);
        dataSource.setRemoveAbandoned(false);
        dataSource.setRemoveAbandonedTimeout(180);
        dataSource.init();
        return dataSource;
    }


    @Bean(name = "masterTransactionManager")
    @Primary
    public DataSourceTransactionManager masterTransactionManager() throws SQLException {
        return new DataSourceTransactionManager(DeviceDataSource());
    }


    @Bean(name = "deviceSqlSessionFactory")
    @Primary
    public SqlSessionFactory deviceSqlSessionFactory(@Qualifier("deviceDataSource") DataSource DeviceDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(DeviceDataSource);
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources(Mapper_Path));
        sessionFactory.setTypeAliasesPackage(Entity_Package);
        return sessionFactory.getObject();
    }
}