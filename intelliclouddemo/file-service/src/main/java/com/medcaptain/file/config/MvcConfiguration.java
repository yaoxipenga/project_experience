package com.medcaptain.file.config;

import com.medcaptain.file.interceptor.AuthorizationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 添加MVC拦截器
 *
 * @author bingwen.ai
 */
@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

    /**
     * 鉴权拦截器
     */
    @Bean
    public AuthorizationInterceptor getAuthorityInterceptor() {
        return new AuthorizationInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getAuthorityInterceptor()).addPathPatterns("/**");
    }
}
