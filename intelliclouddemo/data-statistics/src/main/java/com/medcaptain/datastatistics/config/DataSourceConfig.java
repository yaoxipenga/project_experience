package com.medcaptain.datastatistics.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class DataSourceConfig {

    @Value("${Druid.jdbcDriver}")
    String statisticsJdbcDriverName;

    @Value("${Druid.dbURL}")
    String statisticsDburl;

    @Value("${Druid.DBUserName}")
    String statisticsUserName;

    @Value("${Druid.DBPassword}")
    String statisticsPassword;

    @Value("${Druid.Filter}")
    String statisticsFilter;

    @Value("${Druid.MaxActive}")
    int statisticsMaxActive;

    @Value("${Druid.MaxWait}")
    int statisticsMaxWait;

    @Value("${Druid.MinIdle}")
    int statisticsMinIdle;

    @Value("${Druid.TimeBetweenEvictionRunsMillis}")
    int statisticsTimeBetweenEvictionRunsMillis;

    @Value("${Druid.MinEvictableIdleTimeMillis}")
    int statisticsMinEvictableIdleTimeMillis;

    @Value("${Druid.TestWhileIdle}")
    boolean statisticsTestWhileIdle;

    @Value("${Druid.TestOnBorrow}")
    boolean statisticsTestOnBorrow;

    @Value("${Druid.TestOnReturn}")
    boolean statisticsTestOnReturn;

    @Value("${Druid.PoolPreparedStatements}")
    boolean statisticsPoolPreparedStatements;
    @Value("${Druid.MaxOpenPreparedStatements}")
    int statisticsMaxOpenPreparedStatements;

    @Value("${Druid.ValidationQuery}")
    String statisticsValidationQuery;

    @Bean(name = "statisticsDataSource")
    public DataSource statisticsDataSource() throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(statisticsJdbcDriverName);
        dataSource.setUrl(statisticsDburl);
        dataSource.setUsername(statisticsUserName);
        dataSource.setPassword(statisticsPassword);
        dataSource.setFilters(statisticsFilter);
        dataSource.setMaxActive(statisticsMaxActive);
        dataSource.setMaxWait(statisticsMaxWait);
        dataSource.setMinIdle(statisticsMinIdle);
        dataSource.setTimeBetweenEvictionRunsMillis(statisticsTimeBetweenEvictionRunsMillis);
        dataSource.setMinEvictableIdleTimeMillis(statisticsMinEvictableIdleTimeMillis);
        dataSource.setTestWhileIdle(statisticsTestWhileIdle);
        dataSource.setValidationQuery(statisticsValidationQuery);
        dataSource.setTestOnBorrow(statisticsTestOnBorrow);
        dataSource.setTestOnReturn(statisticsTestOnReturn);
        dataSource.setPoolPreparedStatements(statisticsPoolPreparedStatements);
        dataSource.setMaxOpenPreparedStatements(statisticsMaxOpenPreparedStatements);
        dataSource.setRemoveAbandoned(false);
        dataSource.setRemoveAbandonedTimeout(180);
        dataSource.init();
        return dataSource;
    }
}
