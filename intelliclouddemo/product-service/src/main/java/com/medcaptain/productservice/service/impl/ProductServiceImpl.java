package com.medcaptain.productservice.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.medcaptain.dto.RestResult;
import com.medcaptain.enums.HttpResponseCode;
import com.medcaptain.exception.ApiRuntimeException;
import com.medcaptain.productservice.constant.CommonConstant;
import com.medcaptain.productservice.dao.mongo.EventTemplateRepository;
import com.medcaptain.productservice.dao.mongo.PhysicalModelRepository;
import com.medcaptain.productservice.dao.mongo.ProductTemplateRepository;
import com.medcaptain.productservice.dao.mongo.PropertyTemplateRepository;
import com.medcaptain.productservice.dao.mongo.ServiceTemplateRepository;
import com.medcaptain.productservice.dao.mybatis.BoolDataMapper;
import com.medcaptain.productservice.dao.mybatis.DeviceinfoMapper;
import com.medcaptain.productservice.dao.mybatis.DoubleDataMapper;
import com.medcaptain.productservice.dao.mybatis.EnumDataMapper;
import com.medcaptain.productservice.dao.mybatis.FloatDataMapper;
import com.medcaptain.productservice.dao.mybatis.Int32DataMapper;
import com.medcaptain.productservice.dao.mybatis.ProductTransTemplateMapper;
import com.medcaptain.productservice.dao.mybatis.ProductbasicinfotempMapper;
import com.medcaptain.productservice.dao.mybatis.ProductbasictopicMapper;
import com.medcaptain.productservice.dao.mybatis.ProductcategoryMapper;
import com.medcaptain.productservice.dao.mybatis.ProductinfoMapper;
import com.medcaptain.productservice.dao.mybatis.ProducttopicMapper;
import com.medcaptain.productservice.dao.mybatis.ProducttransrelatMapper;
import com.medcaptain.productservice.dao.mybatis.ProducttripleMapper;
import com.medcaptain.productservice.dao.mybatis.TablelistMapper;
import com.medcaptain.productservice.dao.mybatis.TransParaIoMapper;
import com.medcaptain.productservice.dao.mybatis.UnitMapper;
import com.medcaptain.productservice.entity.dto.mongo.PhysicalModel;
import com.medcaptain.productservice.entity.dto.mongo.ProductEvent;
import com.medcaptain.productservice.entity.dto.mongo.ProductProperty;
import com.medcaptain.productservice.entity.dto.mongo.ProductServiceEntity;
import com.medcaptain.productservice.entity.dto.mongo.ProductTemplate;
import com.medcaptain.productservice.entity.productpojo.Template;
import com.medcaptain.productservice.feign.FileDFSClientFeign;
import com.medcaptain.productservice.feign.ProductTemplateService;
import com.medcaptain.productservice.feign.ProductTopicService;
import com.medcaptain.productservice.service.OperatePermissionService;
import com.medcaptain.productservice.service.ProductService;
import com.medcaptain.productservice.util.DataJsonValueProcessor;
import com.medcaptain.utils.TripleUtil;
import com.medcaptain.utils.UUIDUtil;
import com.medcaptain.utils.encrypt.MD5Util;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.*;

//import org.springframework.mock.web.MockMultipartFile;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductinfoMapper productinfoMapper;
    @Autowired
    ProductcategoryMapper productcategoryMapper;
    @Autowired
    DeviceinfoMapper deviceinfoMapper;
    @Autowired
    ProducttransrelatMapper producttransrelatMapper;
    @Autowired
    TransParaIoMapper transParaIoMapper;
    @Autowired
    ProductTransTemplateMapper productTransTemplateMapper;
    @Autowired
    UnitMapper unitMapper;
    @Autowired
    Int32DataMapper int32DataMapper;
    @Autowired
    FloatDataMapper floatDataMapper;
    @Autowired
    DoubleDataMapper doubleDataMapper;
    @Autowired
    EnumDataMapper enumDataMapper;
    @Autowired
    BoolDataMapper boolDataMapper;
    @Autowired
    TablelistMapper tablelistMapper;
    @Autowired
    ProducttripleMapper producttripleMapper;
    @Autowired
    ProductbasicinfotempMapper productbasicinfotempMapper;
    @Autowired
    ProductbasictopicMapper productbasictopicMapper;
    @Autowired
    ProducttopicMapper producttopicMapper;
    @Autowired
    ProductTopicService productTopicService;
    @Autowired
    PhysicalModelRepository physicalModelRepository;
    @Autowired
    PropertyTemplateRepository propertyTemplateRepository;
    @Autowired
    ProductTemplateRepository productTemplateRepository;
    @Autowired
    ServiceTemplateRepository serviceTemplateRepository;
    @Autowired
    EventTemplateRepository eventTemplateRepository;
    @Autowired
    OperatePermissionService operatePermissionService;
    @Autowired
    ProductTemplateService productTemplateService;
    @Autowired
    FileDFSClientFeign fileDFSClientFeign;

    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private JSONObject setReturnValue(Object object, String key) {
        //设定返回值中的时间格式
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(java.sql.Timestamp.class, new DataJsonValueProcessor());

        Map<String, Object> returnMap = new HashMap<>(3);
        returnMap.put(key, object);
        JSONObject returnJson = JSONObject.fromObject(returnMap, jsonConfig);
        return returnJson;
    }

    @Override
    public Map getProductList(int page, int perPage, String productType, String productName, Integer organizationId, Integer userId, String token) {
        int userPermission = operatePermissionService.hasOperatePermission(token);
        if (0 == userPermission) {
            throw new ApiRuntimeException(HttpResponseCode.ERROR_UNAUTHRORIZED_ACCESS, "没有权限");
        }
        //如果page为第0页，则强制转为第1页
        page = (page == 0) ? 1 : page;
        //每页至少有一条数据
        perPage = (perPage == 0) ? 1 : perPage;
        //分页
        int M = (page - 1) * perPage;
        int N = perPage;

        //查询产品List
        Map<String, Object> map = new HashMap<>(4);
        map.put("productType", productType);
        map.put("productName", productName);
        map.put("organizationId", organizationId);
        map.put("userId", userId);
        map.put("userPermission", userPermission);
        map.put("M", M);
        map.put("N", N);
        List<Map<String, Object>> completeProductList = productinfoMapper.getProductList(map);
        for (Map productInfo : completeProductList) {
            //设置时间格式
            try {
                Date time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(productInfo.get("createAt").toString());
                productInfo.put("createAt", formatter.format(time));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        int total = productinfoMapper.getProductCount(productType, productName, organizationId, userId, userPermission);

        Map<String, Object> data = new HashMap<String, Object>(2);
        data.put("productList", completeProductList);
        data.put("total", total);
        return data;
    }

    @Transactional
    @Override
    public Object addProduct(int userId, String productName, String productCategory, String productDescribe, String productModel, Integer organizationId, String imageUrl) {
        //查看产品名和型号是否重复
        if (productinfoMapper.selectByProductName(productName, organizationId) != 0 || productinfoMapper.selectByProductModel(productModel, organizationId) != 0)
            throw new ApiRuntimeException(HttpResponseCode.ERROR_RESOURCE_ALREADY_EXIST, null);

        //生成productKey
        String productKey = TripleUtil.getTriple(11);
        //新建产品信息和产品描述
        Map<String, Object> productMap = new HashMap<>(4);
        productMap.put("productName", productName);
        productMap.put("productCategory", productCategory);
        productMap.put("userId", userId);
        productMap.put("productKey", productKey);
        productMap.put("productDescribe", productDescribe);
        productMap.put("productModel", productModel);
        productMap.put("organizationId", organizationId);
        productMap.put("imageUrl", imageUrl);
        productinfoMapper.createProduct(productMap);

        //新建产品三元组信息
        String productSecret = TripleUtil.getTriple(20);
        Map<String, Object> productTripleMap = new HashMap<>();
        productTripleMap.put("productKey", productKey);
        productTripleMap.put("productSecret", productSecret);
        productTripleMap.put("productName", productName);
        productTripleMap.put("userId", userId);
        producttripleMapper.createTriple(productTripleMap);

        //添加基本产品topic
        List<Map<String, Object>> basicTopicList = productbasictopicMapper.ListBasicTopic();
//        List<String> topicList = new ArrayList<>();
        //将每条基础topic插入产品topic表中
        for (Map<String, Object> topic : basicTopicList) {
            topic.put("productKey", productKey);
            topic.put("userId", userId);
            topic.put("productTopic", topic.get("productTopic").toString().replace("${productKey}", productKey));
//            topicList.add(topic.get("productTopic").toString());
            producttopicMapper.insertProductTopic(topic);
        }
        //新建设备的时候订阅所有topic
//        productTopicService.subscribeTopic(topicList);

        //从模板中复制所有业务功能
        ProductTemplate productTemplate = productTemplateRepository.findByProductCategory(productCategory);

        //生成物理模型
        Map<String, String> profile = new HashMap<>();
        profile.put("productKey", productKey);
        PhysicalModel physicalModel = new PhysicalModel();
        physicalModel.setProfile(JSON.parseObject(JSON.toJSONString(profile)));
        physicalModel.setProperties(productTemplate.getPropertyList());
        physicalModel.setServices(productTemplate.getServiceList());
        physicalModel.setEvents(productTemplate.getEventList());
        physicalModel.addId();
        physicalModelRepository.add(physicalModel);

        Thread thread = new Thread(() -> pushPhysicalModel(getPhysicalModel(productKey)));
        thread.start();
        return new HashMap<>();
    }

    @Override
    public Map getCategoryList() {
        List<Map<String, Object>> categoryList = productcategoryMapper.getAllCategory();
        return setReturnValue(categoryList, "categoryList");
    }

    @Transactional
    @Override
    public void deleteProduct(String productKey) {
        //从产品表中删除该类产品
        productinfoMapper.deleteByProductKey(productKey);
        //删除物理模型
        physicalModelRepository.deleteByProductKey(productKey);
        //从设备表中删除该类产品下的所有设备
        deviceinfoMapper.deleteByProductKey(productKey);
    }

    @Override
    public Map getProductInfo(String productKey) {
        Map returnMap;
        returnMap = productinfoMapper.getProductCompleteInfo(productKey);
        return returnMap;
    }

    @Override
    public PhysicalModel getProductTransaction(String productKey) {
        return physicalModelRepository.completeModel(productKey);
    }

    @Override
    public Object getAllTransactions(Integer abilityType) {
        Map<String, Object> returnMap = new HashMap<>();
        switch (abilityType) {
            case 1:
                returnMap.put("transactionList", propertyTemplateRepository.findAll());
                break;
            case 2:
                returnMap.put("transactionList", serviceTemplateRepository.findAll());
                break;
            case 3:
                returnMap.put("transactionList", eventTemplateRepository.findAll());
                break;
            default:
                throw new ApiRuntimeException(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, "不存在该类业务");
        }
        return returnMap;
    }

    @Override
    public void addProperty(String productKey, Integer userId, ProductProperty productProperty) {
        com.alibaba.fastjson.JSONObject propertyJson = JSON.parseObject(JSON.toJSONString(productProperty));
        String identifier = productProperty.getIdentifier();
        String propertyName = productProperty.getName();
        productProperty.setAbilityId(UUIDUtil.getUUID());
        propertyJson.put("id", productProperty.getAbilityId());
        physicalModelRepository.addTransaction("properties", productKey, identifier, propertyName, propertyJson);
        Thread thread = new Thread(() -> pushPhysicalModel(getPhysicalModel(productKey)));
        thread.start();
    }

    @Override
    public void addService(Integer userId, String productKey, ProductServiceEntity productServiceEntity) {
        com.alibaba.fastjson.JSONObject serviceJson = JSON.parseObject(JSON.toJSONString(productServiceEntity));
        String identifier = productServiceEntity.getIdentifier();
        String serviceName = productServiceEntity.getName();
        productServiceEntity.setAbilityId(UUIDUtil.getUUID());
        serviceJson.put("id", productServiceEntity.getAbilityId());
        physicalModelRepository.addTransaction("services", productKey, identifier, serviceName, serviceJson);
        Thread thread = new Thread(() -> pushPhysicalModel(getPhysicalModel(productKey)));
        thread.start();
    }

    @Override
    public void addEvent(Integer userId, String productKey, ProductEvent productEvent) {
        com.alibaba.fastjson.JSONObject eventJson = JSON.parseObject(JSON.toJSONString(productEvent));
        String identifier = productEvent.getIdentifier();
        String eventName = productEvent.getName();
        productEvent.setAbilityId(UUIDUtil.getUUID());
        eventJson.put("id", productEvent.getAbilityId());
        physicalModelRepository.addTransaction("events", productKey, identifier, eventName, eventJson);
        Thread thread = new Thread(() -> pushPhysicalModel(getPhysicalModel(productKey)));
        thread.start();
    }

    @Override
    public void updateProperty(Integer userId, String productKey, ProductProperty productProperty) {
        String identifier = productProperty.getIdentifier();
        String propertyName = productProperty.getName();
        com.alibaba.fastjson.JSONObject dataTypeJson = JSON.parseObject(JSON.toJSONString(productProperty.getDataType()));
        String propertyId = productProperty.getAbilityId();
        String propertyDescribe = productProperty.getDesc();
        String accessMode = productProperty.getAccessMode();
        physicalModelRepository.updateProperty(productKey, identifier, propertyName, dataTypeJson, propertyId, propertyDescribe, accessMode);
        Thread thread = new Thread(() -> pushPhysicalModel(getPhysicalModel(productKey)));
        thread.start();
    }

    @Override
    public void updateService(Integer userId, String productKey, ProductServiceEntity productServiceEntity) {
        String identifier = productServiceEntity.getIdentifier();
        String serviceName = productServiceEntity.getName();
        JSONArray outputParams = JSON.parseArray(JSON.toJSONString(productServiceEntity.getOutputData()));
        JSONArray inputParams = JSON.parseArray(JSON.toJSONString(productServiceEntity.getInputData()));
        String serviceId = productServiceEntity.getAbilityId();
        String serviceDescribe = productServiceEntity.getDesc();
        String callType = productServiceEntity.getCallType();
        physicalModelRepository.updateService(productKey, identifier, serviceName, outputParams, inputParams, serviceId, serviceDescribe, callType);
        Thread thread = new Thread(() -> pushPhysicalModel(getPhysicalModel(productKey)));
        thread.start();
    }

    @Override
    public void updateEvent(Integer userId, String productKey, ProductEvent productEvent) {
        String identifier = productEvent.getIdentifier();
        String eventName = productEvent.getName();
        JSONArray outputParams = JSON.parseArray(JSON.toJSONString(productEvent.getOutputData()));
        String eventId = productEvent.getAbilityId();
        String eventDescription = productEvent.getDesc();
        String eventType = productEvent.getType();
        physicalModelRepository.updateEvent(productKey, identifier, eventName, outputParams, eventId, eventDescription, eventType);
        Thread thread = new Thread(() -> pushPhysicalModel(getPhysicalModel(productKey)));
        thread.start();
    }

    @Override
    public void deleteAbility(String productKey, String transactionType, String transactionId) {
        transactionType = "property".equals(transactionType) ? "properties" : transactionType + "s";
        physicalModelRepository.deleteTransaction(productKey, transactionType, transactionId);
        Thread thread = new Thread(() -> pushPhysicalModel(getPhysicalModel(productKey)));
        thread.start();
    }

    @Override
    public Object resetProductSecret(String productKey) {
        //生成新的productSecret
        String newProductSecret = TripleUtil.getTriple(11);
        //改表
        producttripleMapper.resetProductSecret(newProductSecret, productKey);
        return new HashMap<>();
    }

    @Override
    public Object updateProduct(String productKey, String productModel, String productDescribe, String productName, Integer organizationId) {
        //查看产品名和型号是否重复
        if (productinfoMapper.selectByProductName(productName, organizationId) != 0 || productinfoMapper.selectByProductModel(productModel, organizationId) != 0)
            throw new ApiRuntimeException(HttpResponseCode.ERROR_RESOURCE_ALREADY_EXIST, null);
        productinfoMapper.updateProductInformation(productKey, productName, productModel, productDescribe);
        return null;
    }

    @Override
    public Map<String, Object> getUnit() {
        List<Map<String, Object>> unitList = unitMapper.getAllUnit();
        return setReturnValue(unitList, "unitList");
    }

    @Override
    public PhysicalModel getPhysicalModel(String productKey) {
        return physicalModelRepository.findByProductKey(productKey);
    }

    @Override
    public RestResult downloadClientCert(String productKey) {
//        String cmd = "/home/key-manager/client_cert_generator.sh " + productKey;
//        System.out.println(cmd);
//        try {
//            Process p = Runtime.getRuntime().exec(cmd);
//            p.waitFor();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        Path path = Paths.get("/home/key-manager/client_certs/" + productKey + ".zip");
//        File file = path.toFile();
//        FileInputStream fileInputStream = null;
//        MultipartFile mf = null;
//        try {
//            fileInputStream = new FileInputStream(file);
//            mf = new MockMultipartFile(file.getName(), file.getName(),
//                    ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("hello");
//        RestResult result = fileDFSClientFeign.recFile(mf, UUIDUtil.getUUID(), "key");
        return null;

//        String fileName = productKey.toString() + ".zip";// 文件名
//        if (fileName != null) {
//            //设置文件路径
//            File file = new File("E:\\" + fileName);
//            //File file = new File(realPath , fileName);
//            if (file.exists()) {
//                // 设置强制下载不打开
//                response.setContentType("application/force-download");
//                // 设置文件名
//                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
//                byte[] buffer = new byte[1024];
//                FileInputStream fis = null;
//                BufferedInputStream bis = null;
//                try {
//                    fis = new FileInputStream(file);
//                    bis = new BufferedInputStream(fis);
//                    OutputStream os = response.getOutputStream();
//                    int i = bis.read(buffer);
//                    while (i != -1) {
//                        os.write(buffer, 0, i);
//                        i = bis.read(buffer);
//                    }
//                    return;
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    if (bis != null) {
//                        try {
//                            bis.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    if (fis != null) {
//                        try {
//                            fis.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//        }
//        //下载失败
//        throw new ApiRuntimeException(HttpResponseCode.ERROR_FILE_DOWNLOAD, "文件不存在");
    }

    @Override
    public RestResult importPhysicalModel(String productKey, MultipartFile file, String MD5) {
        //文件是否为空
        if (file.isEmpty()) throw new ApiRuntimeException(HttpResponseCode.ERROR_FILE_IS_EMPTY, null);
        String fileContent = null;
        try {
            //校验MD5
            if (!MD5.equalsIgnoreCase(MD5Util.md5(file.getBytes())))
                throw new ApiRuntimeException(HttpResponseCode.ERROR_FILE_MD5_MISMATCH, null);
            StringBuilder tempFileContent = new StringBuilder();
            FileInputStream fis = (FileInputStream) file.getInputStream();
            FileChannel fc = fis.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (fc.read(buffer) != -1) {
                buffer.flip();
                byte[] buf = new byte[buffer.limit()];
                buffer.get(buf, buffer.position(), buffer.limit());
                tempFileContent.append(new String(buf));
                buffer.clear();
            }
            fis.close();
            fc.close();

            fileContent = tempFileContent.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        PhysicalModel physicalModel = new PhysicalModel();
        try {
            physicalModel = JSON.parseObject(fileContent, PhysicalModel.class);
        }catch (Exception e){
            throw new ApiRuntimeException(HttpResponseCode.ERROR_PHYSICAL_MODEL_ERROR, null);
        }
        //TODO: 校验合法性
        if (null == physicalModel.getProfile())
            throw new ApiRuntimeException(HttpResponseCode.ERROR_PHYSICAL_MODEL_ERROR, null);
        if (!physicalModel.getProfile().getString("productKey").equals(productKey))
            throw new ApiRuntimeException(HttpResponseCode.ERROR_PRODUCTKEY_MISMATCH, null);

        physicalModelRepository.updatePhysicalModel(physicalModel, productKey);
        Thread thread = new Thread(() -> pushPhysicalModel(getPhysicalModel(productKey)));
        thread.start();
        return null;
    }

    private void pushPhysicalModel(PhysicalModel physicalModel) {
        Template template = JSON.parseObject(JSON.toJSONString(physicalModel), Template.class);
        byte[] productTemplateByte = JSON.toJSON(template).toString().getBytes();
        productTemplateService.pushPhysicalModel(productTemplateByte);
    }
}
