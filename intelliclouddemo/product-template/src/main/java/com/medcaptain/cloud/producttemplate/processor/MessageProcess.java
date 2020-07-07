package com.medcaptain.cloud.producttemplate.processor;

import com.alibaba.fastjson.JSONObject;
import com.medcaptain.cloud.producttemplate.entity.ShadowProperty;
import com.medcaptain.dto.RestResult;

/**
 * 产品模型消息处理接口
 *
 * @author bingwen.ai
 */
public interface MessageProcess {
    /**
     * 验证产品服务参数是否符合物模型规范
     *
     * @param productKey            产品编号
     * @param inputServiceParameter 精简模式的服务参数
     * @return 参数符合规范：返回完整参数信息 ；参数不符合规范：返回错误信息
     */
    RestResult validateService(String productKey, JSONObject inputServiceParameter);

    /**
     * 验证产品事件参数是否符合物模型规范
     *
     * @param productKey          产品编号
     * @param inputEventParameter 精简模式的事件参数
     * @return 参数符合规范：返回完整参数信息 ；参数不符合规范：返回错误信息
     */
    RestResult validateEvent(String productKey, JSONObject inputEventParameter);

    /**
     * 解析产品属性
     *
     * @param productKey 产品编号
     * @param paramJson  精简模式的属性json
     * @return 属性符合规范：返回完整属性信息 ；属性不符合规范：返回错误信息
     */
    RestResult parseProperties(String productKey, String paramJson);

    /**
     * 验证设备上报属性格式，并保存到影子设备
     *
     * @param productKey     产品编号
     * @param deviceTripleID 设备三元组编号
     * @param paramJson      精简格式的属性json
     * @return 属性格式不符合规范：返回错误信息 ;属性格式符合规范：保存到影子设备
     */
    RestResult uploadDeviceProperty(String productKey, String deviceTripleID, String paramJson);

    /**
     * 设置设备属性，并保存到影子设备
     *
     * @param productKey     产品编号
     * @param deviceTripleID 设备三元组编号
     * @param paramJson      精简格式的属性json
     * @return 属性格式不符合规范：返回错误信息 ;属性格式符合规范：保存到影子设备
     */
    RestResult setDeviceProperties(String productKey, String deviceTripleID, String paramJson);

    /**
     * 格式化影子设备中的属性
     *
     * @param productKey         产品编号
     * @param propertyIdentifier 属性标识符
     * @param shadowProperty     影子设备属性
     * @return 格式化后的属性
     */
    JSONObject formatProperty(String productKey, String propertyIdentifier, ShadowProperty shadowProperty);
}
