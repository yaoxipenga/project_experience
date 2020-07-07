package com.medcaptain.productservice.controller;

import com.medcaptain.dto.RestResult;
import com.medcaptain.enums.HttpResponseCode;
import com.medcaptain.exception.ApiRuntimeException;
import com.medcaptain.productservice.dao.mybatis.DeviceinfoMapper;
import com.medcaptain.productservice.dao.mybatis.DevicelogMapper;
import com.medcaptain.productservice.dao.mybatis.ProductinfoMapper;
import com.medcaptain.productservice.dao.mybatis.RunningstatusMapper;
import com.medcaptain.productservice.entity.mybatis.Deviceinfo;
import com.medcaptain.productservice.entity.mybatis.Productinfo;
import com.medcaptain.productservice.feign.ProductTopicService;
import com.medcaptain.productservice.service.OperatePermissionService;
import com.medcaptain.productservice.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TopicController {
    @Autowired
    RunningstatusMapper runningstatusMapper;

    @Autowired
    DeviceinfoMapper deviceinfoMapper;

    @Autowired
    DevicelogMapper devicelogMapper;

    @Autowired
    ProductinfoMapper productinfoMapper;

    @Autowired
    TopicService topicService;

    @Autowired
    ProductTopicService productTopicService;

    @Autowired
    OperatePermissionService operatePermissionService;

    /**
     * 获取产品topic列表
     *
     * @param productKey
     * @return
     */
    @GetMapping("/{product_key}/topics")
    public RestResult getProductTopics(@PathVariable(value = "product_key") String productKey) {
        return topicService.getTopics(productKey);
    }


//    /**
//     * 获取产品topic列表
//     *
//     * @return
//     */
//    @GetMapping("/products/devices/{device_name}/topics")
//    public RestResult getDeviceTopics(
//            @PathVariable(value = "device_name") Integer deviceId
//    ) {
//        if (deviceId == null) {
//            throw new ApiRuntimeException(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
//        }
//        return RestResult.getInstance(HttpResponseCode.SUCCESS, topicService.getDeviceTopics(deviceId));
//    }

    /**
     * 添加产品 topic
     *
     * @param productKey
     * @param topicName
     * @param operationPermission
     * @param topicDesc
     * @return
     */

    @PostMapping("/product_key/{product_key}/topic")
    public RestResult postTopic(
            @RequestHeader(name = "userId") Integer userId,
            @PathVariable(value = "product_key") String productKey,
            @RequestParam(value = "topicName") String topicName,
            @RequestParam(value = "operationPermission") int operationPermission,
            @RequestParam(value = "topicDesc", defaultValue = "") String topicDesc) {
        return topicService.postTopic(userId, productKey, topicName, operationPermission, topicDesc);
    }


    /**
     * 修改产品 topic
     *
     * @param topicId
     * @param topicName
     * @param operation
     * @param topicDesc
     * @return
     */
    @PutMapping("/topic/{topicId}")
    public RestResult putTopic(@PathVariable(value = "topicId") int topicId,
                               @RequestParam(value = "topicName", required = false) String topicName,
                               @RequestParam(value = "operation", defaultValue = "-1") int operation,
                               @RequestParam(value = "topicDesc", required = false) String topicDesc) {
        return topicService.putTopic(topicId, topicName, operation, topicDesc);
    }

    /**
     * 删除产品 topic
     *
     * @param topicId
     * @return
     */
    @DeleteMapping("/topic/{topicId}")
    public RestResult deleteTopic(@PathVariable(value = "topicId") int topicId) {
        return topicService.deleteTopic(topicId);
    }

    /**
     * 获取设备 topic 列表
     *
     * @param deviceName 设备name
     * @return topic 列表
     */
    @GetMapping("/product_key/{product_key}/device/{device_name}/topics")
    public RestResult getDeviceTopicList(
            @RequestHeader(name = "token") String userToken,
            @RequestHeader(name = "userId") Integer userId,
            @RequestHeader(name = "organizationId") Integer organizationId,
            @PathVariable(value = "product_key") String productKey,
            @PathVariable(value = "device_name") String deviceName
    ) {
        if (productKey.isEmpty()) {
            throw new ApiRuntimeException(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }
        Deviceinfo deviceinfo = deviceinfoMapper.selectByProductKeyAndDeviceName(productKey, deviceName);
        if (deviceinfo == null) {
            throw new ApiRuntimeException(HttpResponseCode.ERROR_DEVICE_NOT_EXIST, null);
        }
        switch (operatePermissionService.hasOperatePermission(userToken)) {
            case 1:
                break;
            case 2:
                Productinfo productinfo = productinfoMapper.selectByPrimaryKey(productKey);
                if (productinfo == null) {
                    throw new ApiRuntimeException(HttpResponseCode.ERROR_DEVICE_NOT_EXIST, null);
                }
                if (!productinfo.getOrganizationId().equals(organizationId)) {
                    throw new ApiRuntimeException(HttpResponseCode.ERROR_DEVICE_NOT_EXIST, null);
                }
                break;
            case 3:
                if (!deviceinfo.getUserId().equals(userId)) {
                    throw new ApiRuntimeException(HttpResponseCode.ERROR_DEVICE_NOT_EXIST, null);
                }
                break;
            default:
                throw new ApiRuntimeException(HttpResponseCode.ERROR_NO_POWER, null);
        }
        return RestResult.getInstance(HttpResponseCode.SUCCESS, topicService.postDeviceTopicList(deviceName, productKey));
    }

    /**
     * 向设备发送消息
     *
     * @param productKey
     * @param deviceName
     * @param topic
     * @param message
     * @param qos
     * @return 设备返回消息
     */
    @PostMapping("/product_key/{product_key}/device/{device_name}/topic")
    public RestResult publishMessage(
            @PathVariable("product_key") String productKey,
            @PathVariable("device_name") String deviceName,
            @RequestParam("topicName") String topic,
            @RequestParam("messageContent") String message,
            @RequestParam("Qos") Integer qos
    ) {
        Deviceinfo deviceinfo = deviceinfoMapper.selectByProductKeyAndDeviceName(productKey, deviceName);
        if (!productKey.equals(deviceinfo.getProductKey())) {
            return RestResult.getInstance(HttpResponseCode.ERROR_PARAMETER, null);
        }
        devicelogMapper.insertLog(productKey, deviceName, topic, message);
        return RestResult.getInstance(HttpResponseCode.SUCCESS,
                productTopicService.publishMessage(topic, message, qos));
    }


    /**
     * test 生成测试数据用
     * @return
     */
//    @PostMapping("/topic_test")
//    public RestResult test(){
//        String[] testProject = {"Kaolin","R-Kaolin", "AA-ADP", "HEP", "Control I", "Controll II"};
//        Integer number;
//        String topic;
//        String msg;
//        Integer qos = 0;
//        for(int i = 0; i<1;i++){
//            number = (int)(Math.random()*1000);
//            topic = "/sys/MD66/device6/thing/event/"+number+"/post";
//            number = (int)(Math.random()*6);
//            msg = "{\n" +
//                    "  \"id\": \"3548\",\n" +
//                    "  \"identifier\": \"finish_project_test\",\n" +
//                    "  \"outputData\": {\n" +
//                    "    \"project_name\": \""+testProject[number]+"\",\n" +
//                    "    \"test_end_time\": \""+System.currentTimeMillis()+"\"\n" +
//                    "  }\n" +
//                    "}";
//            productTopicService.publishMessage(topic,msg,qos);
//        }
//        return RestResult.getInstance(HttpResponseCode.SUCCESS,null);
//    }

}
