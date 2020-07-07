package com.medcaptain.pushsocketservice.config;


import com.medcaptain.pushsocketservice.constant.SocketConstant;
import com.medcaptain.pushsocketservice.redis.RedisService;
import com.medcaptain.pushsocketservice.socket.Authentication;
import com.medcaptain.utils.JSONUtil;
import com.medcaptain.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;
import java.util.Map;


@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    static Logger logger = LoggerFactory.getLogger(WebSocketConfig.class);

    @Autowired
    RedisService redisService;


//    @Bean
//    public ServerEndpointExporter serverEndpointExporter() {
//        return new ServerEndpointExporter();
//    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {

        /**
         * 用户可以订阅来自"/topic"和"/user"的消息，
         * 在Controller中，可通过@SendTo注解指明发送目标，这样服务器就可以将消息发送到订阅相关消息的客户端
         *
         * 在本Demo中，使用topic来达到群发效果，使用user进行一对一发送
         *
         * 客户端只可以订阅这两个前缀的主题
         */
        config.enableSimpleBroker("/topic");
        /**
         * 客户端发送过来的消息，需要以"/app"为前缀，再经过Broker转发给响应的Controller
         */
//        config.setApplicationDestinationPrefixes("/app");


        /**
         * 一对一发送的前缀
         * 订阅主题：/user/{userID}//demo3/greetings
         * 推送方式：1、@SendToUser("/demo3/greetings")
         *          2、messagingTemplate.convertAndSendToUser(destUsername, "/demo3/greetings", greeting);
         */
//        config.setUserDestinationPrefix("/user");

    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        /**
         * 路径"/webSocketEndPoint"被注册为STOMP端点，对外暴露，客户端通过该路径接入WebSocket服务
         */
        // TODO 跨域
        registry.addEndpoint("/websocket")  //开启/bullet端点
//                .setAllowedOrigins("*")             //允许跨域访问
                .withSockJS();                      //使用sockJS
    }


    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.setInterceptors(new ChannelInterceptorAdapter() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor =
                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

                //1. 判断是否首次连接请求
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {

                    //2. 验证是否登录
                    String userId = accessor.getNativeHeader("userId").get(0);
                    String token = accessor.getNativeHeader("password").get(0);
                    if (!StringUtil.isEmpty(userId) && !StringUtil.isEmpty(token)) {
                        String userToken = redisService.getCacheUser(token);
                        if (!ObjectUtils.isEmpty(userToken)) {
                            Map a = JSONUtil.toHashMap(userToken);
                            Map aMap = (Map) a.get("userInfo");
                            Integer userTokenId = new Integer(aMap.get("userId").toString());
                            String userTokenName = aMap.get("userName").toString();
                            if (userTokenId.toString().equals(userId)) {
                                Integer roleId = new Integer(aMap.get("roleId").toString());
                                Authentication user = new Authentication(userTokenName, userTokenId, roleId);
                                accessor.setUser(user);
                                logger.info("用户上线  --> userName = {} , simpSessionId = {}", user.getName(), message.getHeaders().get("simpSessionId"));
                                return message;
                            }
                        }
                    }
                    logger.info("上线失败  --> userId = {} , simpSessionId = {}", userId, message.getHeaders().get("simpSessionId"));
                    return null;
                } else {

                    //不是首次连接，已经成功登陆
//                logger.info("订阅时有内容 simpHeartbeat --> {}", message.getHeaders().get("simpHeartbeat"));
//                // simpMessageType   stompCommand  状态
//                logger.info("状态 simpMessageType --> {}", message.getHeaders().get("simpMessageType"));
//                logger.info("状态 stompCommand --> {}", message.getHeaders().get("stompCommand"));
//                StompCommand stompCommand = (StompCommand) message.getHeaders().get("stompCommand");
//                logger.info("状态 getMessageType --> {}", stompCommand.getMessageType());
//                logger.info("状态 ---> isBodyAllowed --> {}", stompCommand.isBodyAllowed());
//                logger.info("状态 ---> requiresContentLength --> {}", stompCommand.requiresContentLength());
//                logger.info("状态 ---> requiresDestination --> {}", stompCommand.requiresDestination());
//                logger.info("状态 ---> requiresSubscriptionId --> {}", stompCommand.requiresSubscriptionId());
//                logger.info("不知道啥 目前全是空 simpSessionAttributes --> {}", message.getHeaders().get("simpSessionAttributes"));
//                logger.info("订阅topic（id 和 destination） nativeHeaders --> {}", message.getHeaders().get("nativeHeaders"));
//                logger.info("全是空 destination --> {}", message.getHeaders().get("destination"));
//                logger.info("用户名 simpUser --> {}", message.getHeaders().get("simpUser"));
//                logger.info("simpSessionId --> {}", message.getHeaders().get("simpSessionId"));
//                logger.info("全部内容 getHeaders --> {}", message.getHeaders().toString());
                    Authentication user = (Authentication) message.getHeaders().get("simpUser");
                    if (user == null) {
                        return null;
                    }
                    if (message.getHeaders().get("simpHeartbeat") == null) {
                        logger.info("用户下线  --> userName = {} , simpSessionId = {}", user.getName(), message.getHeaders().get("simpSessionId"));
                    } else {
                        LinkedMultiValueMap linkedMultiValueMap = (LinkedMultiValueMap) message.getHeaders().get("nativeHeaders");
                        if (linkedMultiValueMap == null) {
                            logger.info("  ---  用户即将下线  ---   --> userName = {} , simpSessionId = {}", user.getName(), message.getHeaders().get("simpSessionId"));
                        } else {
                            if ("SEND".equals(message.getHeaders().get("stompCommand").toString())) {
                                // 发布消息
                                logger.info("用户发布  --> userName = {} , simpSessionId = {} , topic = {}", user.getName(), message.getHeaders().get("simpSessionId"), linkedMultiValueMap.get("destination"));
                                return null;
                            } else {
                                List<String> topicLsit = linkedMultiValueMap.get("destination");
                                if (topicLsit == null) {
                                    return null;
                                }
                                boolean socketIsEmpty = false;
                                for (String topic : topicLsit) {
                                    if (topic.contains(SocketConstant.SOCKET_ROLE_URL)) {
                                        String roleString = topic.substring(SocketConstant.SOCKET_ROLE_URL.length());
                                        Integer roleId = Integer.valueOf(roleString);
                                        if (roleId.equals(user.getRoleId())) {
                                            socketIsEmpty = true;
                                        }
                                    } else if (topic.contains(SocketConstant.SOCKET_USER_URL)) {
                                        String userString = topic.substring(SocketConstant.SOCKET_USER_URL.length());
                                        Integer userId = Integer.valueOf(userString);
                                        if (userId.equals(user.getUserId())) {
                                            socketIsEmpty = true;
                                        }
                                    }
                                    if (SocketConstant.SOCKET_BROADCAST_URL.equals(topic)) {
                                        socketIsEmpty = true;
                                    }
                                }
                                if (!socketIsEmpty) {
                                    logger.info("用户订阅(失败)  --> userName = {} , simpSessionId = {} , topic = {}", user.getName(), message.getHeaders().get("simpSessionId"), linkedMultiValueMap.get("destination"));
                                    return null;
                                }
                                logger.info("用户订阅(成功)  --> userName = {} , simpSessionId = {} , topic = {}", user.getName(), message.getHeaders().get("simpSessionId"), linkedMultiValueMap.get("destination"));
                            }
                        }
                    }
                    return message;
                }
            }
        });
    }

}
