package com.medcaptain.gateway;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.InMemoryRouteDefinitionRepository;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class EurekaGatewayClientApplication {


    public static void main(String[] args) {
        SpringApplication.run(EurekaGatewayClientApplication.class, args);
    }


//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route(r -> r.path("/jianshu")
//                        .uri("http://www.jianshu.com/u/128b6effde53")
//                ).build();
//    }

    @Bean
    @ConditionalOnMissingBean(RouteDefinitionRepository.class)
    public InMemoryRouteDefinitionRepository inMemoryRouteDefinitionRepository(){
        return new InMemoryRouteDefinitionRepository();
    }
}
