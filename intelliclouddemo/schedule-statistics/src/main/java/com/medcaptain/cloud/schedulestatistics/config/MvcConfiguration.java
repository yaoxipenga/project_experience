package com.medcaptain.cloud.schedulestatistics.config;

import com.medcaptain.cloud.schedulestatistics.interceptor.AuthorizationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置
 *
 * @author bingwen.ai
 */
public class MvcConfiguration implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        AuthorizationInterceptor authorizationInterceptor = getAuthorityInterceptor();
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/**");
    }

    /**
     * 鉴权拦截器
     */
    @Bean
    public AuthorizationInterceptor getAuthorityInterceptor() {
        return new AuthorizationInterceptor();
    }
}
