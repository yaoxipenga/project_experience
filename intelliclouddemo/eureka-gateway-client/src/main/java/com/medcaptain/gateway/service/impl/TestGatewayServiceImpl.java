package com.medcaptain.gateway.service.impl;

import com.medcaptain.gateway.service.TestGatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class TestGatewayServiceImpl implements TestGatewayService {

    @Autowired
    private RouteDefinitionWriter routeDefinitionWriter;
    private ApplicationEventPublisher publisher;

    @Override
    public String save() {
        RouteDefinition definition = new RouteDefinition();
        PredicateDefinition predicate = new PredicateDefinition();
        Map<String, String> predicateParams = new HashMap<>(8);

        // 设置definition的id，需要全局唯一，默认使用UUID。
        definition.setId("baiduRoute");
        // predicate.setName("Path"); 设置predicat名称，这个名称不是乱起的
        // Spring会根据名称去查找对应的FilterFactory，
        // 目前支持的名称有：After、Before、Between、Cookie、Header、Host、Method、Path、Query、RemoteAddr。
        predicate.setName("Path");
        //  每个Route Predicate的参数不同，详情在官网文档查看对应的Route Predicate配置示例，
        //  然而官方文档也很坑，比如Path Route的- Path=/foo/{segment}，把参数给省略了。
        //  还是得看源码，在包org.springframework.cloud.gateway.handler.predicate里有Spring Cloud Gateway所有的Predicate，
        //  打开对应的RoutePredicateFactory，内部类Config就是该Predicate支持的参数。
        predicateParams.put("pattern", "/baidu");
        predicateParams.put("pathPattern", "/baidu");
        predicate.setArgs(predicateParams);
        definition.setPredicates(Arrays.asList(predicate));
        URI uri = UriComponentsBuilder.fromHttpUrl("http://www.baidu.com").build().toUri();
        definition.setUri(uri);

//        RouteDefinition routeDefinition = new RouteDefinition();
//        PredicateDefinition predicateDefinition = new PredicateDefinition();
//        Map<String, String> predicateParams2 = new HashMap<>(8);
//        Map<String, String> filterParams = new HashMap<>(8);
//        FilterDefinition filterDefinition = new FilterDefinition();
//        URI uri2 = UriComponentsBuilder.fromUriString("lb://HELLO-SERVICE").build().toUri();
//
//        routeDefinition.setId("rateLimitTest");
//        // 名称是固定的，spring gateway会根据名称找对应的PredicateFactory
//        predicateDefinition.setName("Path");
//        predicateParams2.put("pattern", "/rate/**");
//        predicateDefinition.setArgs(predicateParams2);
//
//        // 名称是固定的，spring gateway会根据名称找对应的FilterFactory
//        filterDefinition.setName("RequestRateLimiter");
//        // 每秒最大访问次数
//        filterParams.put("redis-rate-limiter.replenishRate", "2");
//        // 令牌桶最大容量
//        filterParams.put("redis-rate-limiter.burstCapacity", "3");
//        // 需要注意的是filterParams.put("key-resolver", "#{@hostAddressKeyResolver}");
//        // hostAddressKeyResolver是我自定义的Spring Bean，
//        // #{@BeanName}是Spring的表达式，用来注入Bean。
//        // 限流策略(#{@BeanName})
//        filterParams.put("key-resolver", "#{@hostAddressKeyResolver}");
//        // 自定义限流器(#{@BeanName})
//        //filterParams.put("rate-limiter", "#{@redisRateLimiter}");
//        filterDefinition.setArgs(filterParams);
//
//        routeDefinition.setPredicates(Arrays.asList(predicateDefinition));
//        routeDefinition.setFilters(Arrays.asList(filterDefinition));
//        routeDefinition.setUri(uri2);
//        routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
//        publisher.publishEvent(new RefreshRoutesEvent(this));


        // 默认的RouteDefinitionWriter实现类是org.springframework.cloud.gateway.route.InMemoryRouteDefinitionRepository。
        // 注意最后一定要调用subscribe()，否则不执行。
        routeDefinitionWriter.save(Mono.just(definition)).subscribe();
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
        return "success";
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
}