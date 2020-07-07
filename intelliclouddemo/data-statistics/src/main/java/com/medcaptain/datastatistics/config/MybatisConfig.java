package com.medcaptain.datastatistics.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
public class MybatisConfig {

    /**
     * 第三个数据库 SqlSessionFactory && SqlSessionTemplate 创建
     */
    @MapperScan(basePackages = {"com.medcaptain.datastatistics.dao"},
            sqlSessionFactoryRef = "sqlSessionFactoryThree",
            sqlSessionTemplateRef = "sqlSessionTemplateThree")
    public static class DBThree {

        private static final String MAPPER_PATH_STATISTICS = "classpath:mapper/*.xml";

        @Resource
        DataSource statisticsDataSource;

        @Bean
        public SqlSessionFactory sqlSessionFactoryThree() throws Exception {

            SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
            factoryBean.setDataSource(statisticsDataSource);
            factoryBean.setMapperLocations(
                    new PathMatchingResourcePatternResolver().getResources(MAPPER_PATH_STATISTICS));
            return factoryBean.getObject();
        }

        @Bean
        public SqlSessionTemplate sqlSessionTemplateThree() throws Exception {
            // 使用上面配置的Factory
            SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactoryThree());
            return template;
        }
    }
}
