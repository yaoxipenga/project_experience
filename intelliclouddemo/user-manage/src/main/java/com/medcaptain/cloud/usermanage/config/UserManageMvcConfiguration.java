package com.medcaptain.cloud.usermanage.config;

import com.medcaptain.cloud.usermanage.interceptor.AuthorizationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 添加拦截器
 *
 * @author bingwen.ai
 */
@Configuration
public class UserManageMvcConfiguration implements WebMvcConfigurer {

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
