package com.medcaptain.productservice.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.medcaptain.dto.RestResult;
import com.medcaptain.productservice.entity.dto.mongo.PhysicalModel;
import com.medcaptain.productservice.entity.dto.mongo.ProductEvent;
import com.medcaptain.productservice.entity.dto.mongo.ProductProperty;
import com.medcaptain.productservice.entity.dto.mongo.ProductServiceEntity;
import org.bson.types.ObjectId;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface ProductService {

    /***
     * 查询产品列表
     * @param page 页码
     * @param perPage 每页条数
     * @param productType 产品类别
     * @param productName 产品名称
     * @return 返回产品列表
     */
    public Map getProductList(int page, int perPage, String productType, String productName, Integer organizationId, Integer userId, String token);

    /***
     * 创建产品
     * @param userId 创建者id
     * @param productName 产品名称
     * @param productCategory 产品描述代码
     * @param productDescribe 产品描述
     * @return 返回数据
     */
    public Object addProduct(int userId, String productName, String productCategory, String productDescribe, String productModel, Integer organizationId, String imageUrl);

    /***
     * 查询产品类别列表
     * @return 产品类别列表
     */
    public Map getCategoryList();

    /***
     * 删除产品
     * @param productKey 删除产品的Key
     * @return 返回数据
     */
    public void deleteProduct(String productKey);

    /***
     * 查询产品完整信息
     * @param productKey 产品key
     * @return 产品信息
     */
    public Map getProductInfo(String productKey);

    /***
     * 获取产品功能列表
     * @param productKey 产品Key
     * @return 返回产品功能列表
     */
    public PhysicalModel getProductTransaction(String productKey);

    /***
     * 获取所有功能列表（模板）
     * @return 功能列表
     */
    Object getAllTransactions(Integer abilityType);

    /***
     * 新增属性
     * @param productKey 产品key
     * @param propertyName 属性名称
     * @param identifier 属性标识符
     * @param propertyDesc 属性代号
     * @param rwFlag 属性读写权限
     * @param dataType 数据类型
     * @param userId 创建者
     * @return 返回数据
     */
    public void addProperty(String productKey, Integer userId, ProductProperty productProperty);

    /***
     * 新增服务
     * @param serviceName 服务名
     * @param identifier 服务标识符
     * @param serviceDescription 服务描述
     * @param userId 创建者
     * @param productKey 产品Key
     * @param templateId 所属模板
     * @param outputParams 输出参数
     * @param inputParams 输入参数
     * @return 返回数据
     */
    void addService(Integer userId, String productKey, ProductServiceEntity productServiceEntity);


    /***
     * 新增事件
     * @param eventName 事件名
     * @param identifier 事件标识符
     * @param eventDescription 事件描述
     * @param userId 用户id
     * @param productKey 产品key
     * @param templateId 所属模板
     * @param outputParams 输出参数
     * @param eventType 事件类型
     * @return
     */
    void addEvent(Integer userId, String productKey, ProductEvent productEvent);

    /***
     * 编辑属性
     * @param propertyName 属性名
     * @param identifier 属性标识符
     * @param propertyDescribe 属性描述
     * @param rwFlag 读写标识
     * @param dataType 数据类型
     * @param dataSpecJson 数据详情
     * @param templateId 所属模板
     * @param propertyId 属性id
     * @return
     */
    void updateProperty(Integer userId, String productKey, ProductProperty productProperty);

    /***
     * 编辑服务
     * @param serviceName 服务名
     * @param identifier 服务标识符
     * @param serviceDescribe 服务描述
     * @param serviceId 服务id
     * @param userId 用户id
     * @param templateId 所属模板
     * @param productKey 产品key
     * @param outputParams 输出参数
     * @param inputParams 输入参数
     * @return
     */
    void updateService(Integer userId,  String productKey, ProductServiceEntity productServiceEntity);

    /***
     * 编辑事件
     * @param eventName 事件名
     * @param identifier 事件标识符
     * @param eventDescription 事件描述
     * @param eventId 事件id
     * @param userId 用户id
     * @param templateId 所属模板
     * @param productKey 产品key
     * @param outputParams 输出参数
     * @param eventType 事件类型
     * @return
     */
    void updateEvent(Integer userId, String productKey, ProductEvent productEvent);

    /***
     * 删除产品功能
     * @param productKey 产品Key
     * @param transactionId 功能Id
     * @return
     */
    public void deleteAbility(String productKey, String transactionType, String transactionId);

    /***
     * 重置productSecret
     * @param productKey 产品key
     * @return
     */
    Object resetProductSecret(String productKey);

    /***
     * 编辑产品信息
     * @param productKey 产品key
     * @param productModel 产品型号
     * @param productDescribe产品描述
     * @return
     */
    Object updateProduct(String productKey, String productModel, String productDescribe, String productName, Integer organizationId);

    /***
     * 查看所有单位列表
     * @return
     */
    Map getUnit();

    /***
     * 查看物理模型
     * @param productKey 产品key
     * @return
     */
    PhysicalModel getPhysicalModel(String productKey);

    /***
     * 导出客户端证书
     * @param productKey 用户id
     *
     */
    RestResult downloadClientCert(String productKey);

    /**
     * 导入物模型
     * @param productKey
     * @param file
     * @return
     */
    RestResult importPhysicalModel(String productKey, MultipartFile file, String MD5);
}
