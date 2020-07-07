package com.medcaptain.pushsocketservice.service.impl;

import com.medcaptain.annotation.AuthorizationFree;
import com.medcaptain.enums.DeviceAlertEnum;
import com.medcaptain.pushsocketservice.constant.SocketConstant;
import com.medcaptain.pushsocketservice.service.SocketService;
import com.medcaptain.pushsocketservice.socket.Greeting;
import com.medcaptain.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SocketServiceImpl implements SocketService {


    private final SimpMessagingTemplate messagingTemplate;

    /**
     * 实例化Controller的时候，注入SimpMessagingTemplate
     */
    @Autowired
    public SocketServiceImpl(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

//    /**
//     * 推送给一群人
//     * <p>
//     */
//    @MessageMapping("/socket/role/{role}")
//    public void greeting(
//            @DestinationVariable String role,
//            HelloMessage message,
//            @Headers Map<String, Object> headers,
//            StompHeaderAccessor headerAccessor
//    ) throws Exception {
//        Authentication user = (Authentication) headerAccessor.getUser();
//        // 发送者
////            user.getName() //  在群里发送消息
//        // 接收者
//        // 在 typeId 这个群中的
//        System.out.println(SocketConstant.SOCKET_ROLE_URL + role);
//        messagingTemplate.convertAndSendToUser(user.getName(), "/user", new Greeting(1, "系统", "群消息已发送", DateUtil.getDateTimeFormat(new Date())));
//        messagingTemplate.convertAndSend(SocketConstant.SOCKET_ROLE_URL + role, new Greeting(1, "用户id" + role, message.getMessage(), DateUtil.getDateTimeFormat(new Date())));
////        return new Greeting(user.getName(), message.getMessage(), typeId, DateUtil.getDateTimeFormat(new Date()));
//    }

//    /**
//     * 精准发送
//     *
//     * @param destUsername
//     * @param message
//     * @param headerAccessor
//     * @return
//     * @throws Exception
//     */
//    @MessageMapping("/toUser/{destUsername}")
//    public void greeting(@DestinationVariable String destUsername, HelloMessage message, StompHeaderAccessor headerAccessor) throws Exception {
//        Authentication user = (Authentication) headerAccessor.getUser();
//        /// 用户唯一标识符
//        // String sessionId = headerconvertAndSendAccessor.getSessionId();
//        // 发送者
//        // user.getName() //  向接收者发送消息
//        // 接收者
//        // destUsername
//
//        Greeting greeting = new Greeting(1, user.getName(), message.getMessage(), DateUtil.getDateTimeFormat(new Date()));
//        /**
//         * 对目标进行发送信息
//         */
//        messagingTemplate.convertAndSendToUser(destUsername, "/user", greeting);
//        messagingTemplate.convertAndSendToUser(user.getName(), "/user", new Greeting(1, "系统", "消息已被推送。", DateUtil.getDateTimeFormat(new Date())));
//    }

//    /**
//     * 广播
//     * <p>
//     * 这里没用@SendTo注解指明消息目标接收者，消息将默认通过@SendTo("/topic/twoWays")交给Broker进行处理
//     */
//    @MessageMapping("/socket/broadcast")
//    public Greeting twoWays(HelloMessage message) {
//        return new Greeting(1, "广播", message.getMessage(), DateUtil.getDateTimeFormat(new Date()));
//    }

    /**
     * 推送 Rolid
     * TODO 测试接口
     *
     * @param roleId   角色id
     * @param greeting 推动对象
     */
    @AuthorizationFree
    @Override
    public void pullRoleId(String roleId, Greeting greeting) {
        messagingTemplate.convertAndSend(SocketConstant.SOCKET_ROLE_URL + roleId, greeting);
//        return RestResult.getInstance(HttpResponseCode.SUCCESS, "hi 消息发送成功！");
    }

    /**
     * 推送 Rolid
     * TODO 测试接口
     *
     * @param userId   用户id
     * @param greeting 推送对象
     */
    @AuthorizationFree
    @Override
    public void pullUserId(String userId, Greeting greeting) {
        messagingTemplate.convertAndSend(SocketConstant.SOCKET_USER_URL + userId, greeting);
//        return RestResult.getInstance(HttpResponseCode.SUCCESS, "hi 消息发送成功！");
    }

    /**
     * 广播
     * TODO 测试接口
     *
     * @param greeting 推送对象
     */
    @AuthorizationFree
    @Override
    public void pullBroadcast(Greeting greeting) {
        messagingTemplate.convertAndSend(SocketConstant.SOCKET_BROADCAST_URL, greeting);
//        return RestResult.getInstance(HttpResponseCode.SUCCESS, "hi 消息发送成功！");
    }

    @Override
    public Greeting format(
            String productKey, String deviceName,
            String topic, String title, String jsonString
    ) {
        Greeting greeting = new Greeting(
                DeviceAlertEnum.ALARM.getCode(),
                title,
                jsonString,
                DateUtil.getDateTimeFormat(new Date())
        );
        return greeting;
    }


}
