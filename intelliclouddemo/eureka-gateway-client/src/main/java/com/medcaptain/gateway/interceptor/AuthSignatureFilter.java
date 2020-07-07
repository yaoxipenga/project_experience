package com.medcaptain.gateway.interceptor;

import com.medcaptain.gateway.entity.UserToken;
import com.medcaptain.gateway.redis.RedisRouteDefinitionRepository;
import org.apache.commons.lang.StringUtils;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.UUID;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;


/**
 * @Description 全局过滤器 在这里可以实现记录日志和访问权限校验等
 * @Author yangzhixiong
 * @ClassName AuthSignatureFilter
 * @Version: 1.0
 */
@Component
public class AuthSignatureFilter implements GlobalFilter, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(AuthSignatureFilter.class);

    @Value("${user.jwtSecret}")
    String jwtSecret;

    @Autowired
    RedisRouteDefinitionRepository redisRouteDefinitionRepository;

    /**
     * 全局过滤器 核心方法
     * 前端给的值，会已 - 减号为单词分割，传给后端服务
     * 后端的值，会已小驼峰，传给后端服务
     *
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String cloudUuid = UUID.randomUUID().toString().replaceAll("-", "");
        ServerHttpRequest requestHost;
        ServerHttpRequest request = exchange.getRequest();
        String ip = "0.0.0.0";
        List x_ip = request.getHeaders().get("X-Real-IP");
        if (x_ip != null && x_ip.size() > 0) {
            ip = request.getHeaders().get("X-Real-IP").get(0);
        }
//        if (logger.isDebugEnabled()) {
        logger.info("请求入参 - cloud_uuid={} - path={} - method={} - Headers={} - Cookies={} - Body={} - RemoteAddress={} - host = {}",
                cloudUuid, request.getURI().getPath(), request.getMethod(), request.getHeaders().toString(),
                request.getCookies().toString(), request.getBody(), request.getRemoteAddress(), request.getHeaders().get("Host")
        );
//        }
        if (request.getHeaders().get("Host") != null) {
            List<String> hostList = request.getHeaders().get("Host");
            if (hostList != null && hostList.size() > 0) {
                String host = hostList.get(0);
                // 是否鉴权
                if (redisRouteDefinitionRepository.filterUrlPath(request.getURI().getPath())) {
                    // TODO 添加 uuid
                    requestHost = exchange.getRequest().mutate()
                            .header("host", host)
                            .header("cloudUuid", cloudUuid)
                            .header("ip", ip)
                            .build();
                    return chain.filter(exchange.mutate().request(requestHost).build());
                }
                String token = getToken(request, cloudUuid);
                UserToken userToken = authorization(token, request.getURI().getPath(), cloudUuid);
                if (userToken != null) {
                    //向 headers 中放文件，记得 build
                    requestHost = exchange.getRequest().mutate()
                            .header("host", host)
                            .header("cloudUuid", cloudUuid)
                            .header("token", token)
                            .header("ip", ip)
                            .header("userId", String.valueOf(userToken.getUserID()))
                            .header("roleId", String.valueOf(userToken.getRoleID()))
                            .header("organizationId", String.valueOf(userToken.getOrganizationID()))
                            .header("departmentId", String.valueOf(userToken.getDepartmentID()))
                            .build();
                    if (logger.isDebugEnabled()) {
                        ServerHttpResponseDecorator decoratedResponse = response(exchange, request, cloudUuid);
                        return chain.filter(exchange.mutate().request(requestHost).response(decoratedResponse).build());
                    } else {
                        return chain.filter(exchange.mutate().request(requestHost).build());
                    }
                }
            }
        }
        return Mono.error(new RuntimeException(cloudUuid));
    }

    /**
     * 鉴权获取 userToken
     *
     * @param request   请求信息
     * @param cloudUuid uuid
     * @return userToken
     */
    private String getToken(ServerHttpRequest request, String cloudUuid) {
        String authorization = request.getHeaders().getFirst("Authorization");
        if (authorization == null) {
            logger.error("鉴权不通过 - cloud_uuid={} - path={}", cloudUuid, request.getURI().getPath());
            return null;
        }
        return authorization.replace("Bearer", "").trim();
    }

    /**
     * 鉴权获取 userToken
     *
     * @param token     token
     * @param path      路径
     * @param cloudUuid uuid
     * @return userToken
     */
    private UserToken authorization(String token, String path, String cloudUuid) {
        if (StringUtils.isEmpty(token)) {
            logger.error("鉴权不通过 - cloud_uuid={} - path={}", cloudUuid, path);
            return null;
        }
        UserToken userToken = redisRouteDefinitionRepository.getCacheUser(token);
        logger.debug("用户信息 - cloud_uuid={} - token={} - 用户信息={}", cloudUuid, token, userToken.toJSON());
        return userToken;
    }

    private ServerHttpResponseDecorator response(ServerWebExchange exchange, ServerHttpRequest request, String cloudUuid) {
        ServerHttpResponse originalResponse = exchange.getResponse();
        DataBufferFactory bufferFactory = originalResponse.bufferFactory();
        return new ServerHttpResponseDecorator(originalResponse) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                if (body instanceof Flux) {
                    Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
                    return super.writeWith(fluxBody.map(dataBuffer -> {
                        byte[] content = new byte[dataBuffer.readableByteCount()];
                        dataBuffer.read(content);
                        //释放掉内存
                        DataBufferUtils.release(dataBuffer);
                        logger.info("请求出参 - cloud_uuid={} - Path={} - 返回值={}  ",
                                cloudUuid, request.getURI().getPath(), new String(content, Charset.forName("UTF-8"))
                        );
                        // s 就是 response 的值，可以修改、查看
                        return bufferFactory.wrap(content);
                        // 可以修改 new String(content, Charset.forName("UTF-8"))
//                        byte[] uppedContent = new String(content, Charset.forName("UTF-8")).getBytes();
//                        return bufferFactory.wrap(uppedContent);
                    }));
                }
                return super.writeWith(body);
            }
        };
    }

    /**
     * 跳转url
     *
     * @param exchange
     * @param chain
     * @return
     */
    public Object jumpUrl(ServerWebExchange exchange, GatewayFilterChain chain) {
        try {
            exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, new URI("http://tetool.cn"));
            return chain.filter(null);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return Mono.error(new RuntimeException("跳转url失败"));
    }


    @Override
    public int getOrder() {
        // 注意 order 要小于-1.通过上面的类，就能查看服务端响应的值了。
        return -200;
    }
}
