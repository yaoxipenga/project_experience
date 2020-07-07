package com.medcaptain.productservice.service.impl;


import com.alibaba.fastjson.JSON;
import com.medcaptain.constant.InterceptorConstant;
import com.medcaptain.dto.RestResult;
import com.medcaptain.enums.HttpResponseCode;
import com.medcaptain.exception.ApiRuntimeException;
import com.medcaptain.productservice.constant.CommonConstant;
import com.medcaptain.productservice.constant.DeviceConstant;
import com.medcaptain.productservice.dao.mongo.EventLogRepository;
import com.medcaptain.productservice.dao.mongo.PropertyLogRepository;
import com.medcaptain.productservice.dao.mongo.ServiceLogRepository;
import com.medcaptain.productservice.dao.mybatis.DepartmentMapper;
import com.medcaptain.productservice.dao.mybatis.DeviceBatchMapper;
import com.medcaptain.productservice.dao.mybatis.DeviceDepartmentRelatedMapper;
import com.medcaptain.productservice.dao.mybatis.DeviceEventLogMapper;
import com.medcaptain.productservice.dao.mybatis.DevicePropertyLogMapper;
import com.medcaptain.productservice.dao.mybatis.DeviceServiceLogMapper;
import com.medcaptain.productservice.dao.mybatis.DeviceinfoMapper;
import com.medcaptain.productservice.dao.mybatis.DevicelocationMapper;
import com.medcaptain.productservice.dao.mybatis.OrganizationMapper;
import com.medcaptain.productservice.dao.mybatis.ProductinfoMapper;
import com.medcaptain.productservice.dao.mybatis.RegionMapper;
import com.medcaptain.productservice.dao.mybatis.RunningstatusMapper;
import com.medcaptain.productservice.entity.dto.DeviceDepartmentUnbindEntity;
import com.medcaptain.productservice.entity.dto.DeviceDtoEntity;
import com.medcaptain.productservice.entity.dto.ParamEntity;
import com.medcaptain.productservice.entity.dto.mongo.EventLog;
import com.medcaptain.productservice.entity.dto.mongo.PropertyLog;
import com.medcaptain.productservice.entity.dto.mongo.ServiceLog;
import com.medcaptain.productservice.entity.mybatis.Department;
import com.medcaptain.productservice.entity.mybatis.DeviceBatch;
import com.medcaptain.productservice.entity.mybatis.DeviceDepartmentRelated;
import com.medcaptain.productservice.entity.mybatis.Deviceinfo;
import com.medcaptain.productservice.entity.mybatis.Devicelocation;
import com.medcaptain.productservice.entity.mybatis.Organization;
import com.medcaptain.productservice.entity.mybatis.Productinfo;
import com.medcaptain.productservice.entity.mybatis.Region;
import com.medcaptain.productservice.entity.mybatis.Runningstatus;
import com.medcaptain.productservice.enums.DeviceBatchTypeEnum;
import com.medcaptain.productservice.enums.DeviceListEnum;
import com.medcaptain.productservice.enums.DeviceRunningStatusEnum;
import com.medcaptain.productservice.feign.FileDFSClientFeign;
import com.medcaptain.productservice.feign.ProductTemplateService;
import com.medcaptain.productservice.feign.UserManageService;
import com.medcaptain.productservice.redis.RedisService;
import com.medcaptain.productservice.service.DeviceService;
import com.medcaptain.productservice.service.OperatePermissionService;
import com.medcaptain.productservice.util.DataJsonValueProcessor;
import com.medcaptain.utils.JSONUtil;
import com.medcaptain.utils.LonLatUtil;
import com.medcaptain.utils.Time.DateFormaterUtil;
import com.medcaptain.utils.TripleUtil;
import com.medcaptain.utils.UUIDUtil;
import net.sf.ezmorph.bean.MorphDynaBean;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static feign.Util.toByteArray;

@Service
public class DeviceServiceImpl implements DeviceService {
    private static Logger logger = LoggerFactory.getLogger(DeviceServiceImpl.class);

    @Autowired
    DeviceEventLogMapper deviceEventLogMapper;
    @Autowired
    DeviceServiceLogMapper deviceServiceLogMapper;
    @Autowired
    DeviceinfoMapper deviceinfoMapper;
    @Autowired
    DevicePropertyLogMapper devicePropertyLogMapper;
    @Autowired
    ProductTemplateService productTemplateService;
    @Autowired
    PropertyLogRepository propertyLogRepository;
    @Autowired
    EventLogRepository eventLogRepository;
    /**
     * 产品
     */
    @Autowired
    ProductinfoMapper productinfoMapper;

    /**
     * redis
     */
    @Autowired
    RedisService redisService;

    /**
     * 设备运行在线状态表
     */
    @Autowired
    RunningstatusMapper runningstatusMapper;

    @Autowired
    FileDFSClientFeign fileDFSClientFeign;

    @Autowired
    UserManageService userManageService;

    @Autowired
    DevicelocationMapper devicelocationMapper;

    @Autowired
    DeviceDepartmentRelatedMapper deviceDepartmentRelatedMapper;

    @Autowired
    OrganizationMapper organizationMapper;

    @Autowired
    RegionMapper regionMapper;

    @Autowired
    DepartmentMapper departmentMapper;

    @Autowired
    DeviceBatchMapper deviceBatchMapper;

    @Autowired
    OperatePermissionService operatePermissionService;

    @Autowired
    private ServiceLogRepository serviceLogRepository;

    public static String stampToDate(String timeStamp) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(timeStamp);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    public JSONObject setReturnValue(Object object, String key) {
        //设定返回值中的时间格式
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Timestamp.class, new DataJsonValueProcessor());

        Map<String, Object> returnMap = new HashMap<>(1);
        returnMap.put(key, object);
        JSONObject returnJson = JSONObject.fromObject(returnMap, jsonConfig);
        return returnJson;
    }

    @Override
    public Map<String, Object> eventManagementList(String productKey, String deviceName, Integer page, Integer perPage, String identifier, String eventType, String startTime, String endTime) {
        //如果page为第0页，则强制转为第1页
        page = (page == 0) ? 1 : page;
        //你每页至少得有一条数据吧
        perPage = (perPage == 0) ? 1 : perPage;
        //分页
        List<EventLog> eventLogList = eventLogRepository.findEventLog(productKey, deviceName, identifier, page, perPage, startTime, endTime);
        List<Map> eventListResult = new ArrayList<>();
        for (EventLog eventLog : eventLogList) {
            RestResult result = productTemplateService.parseEventData(productKey, JSON.toJSON(eventLog.getLogContent()).toString());
            //解析通过
            if (InterceptorConstant.HTTP_CODE_SUCCESS == result.getCode()) {
                Map resultData = JSONUtil.toHashMap(result.getData());
                Map<String, Object> returnJson = new HashMap<>(8);
                returnJson.put("createTime", DateFormaterUtil.formatTimestamp(eventLog.getLogContent().getTimestamp()));
                returnJson.put("eventName", resultData.get("name"));
                returnJson.put("eventType", resultData.get("type"));
                returnJson.put("outputParams", resultData.get("outputData"));
                eventListResult.add(returnJson);
            }
            //解析不通过
            else {
                com.alibaba.fastjson.JSONObject errorJson = new com.alibaba.fastjson.JSONObject();
                errorJson.put("createTime", DateFormaterUtil.formatTimestamp(eventLog.getLogContent().getTimestamp()));
                errorJson.put("eventName", "error");
                errorJson.put("eventType", "error");
                errorJson.put("outputParams", eventLog.getLogContent());
                eventListResult.add(errorJson);
            }
        }
        Map<String, Object> eventMap = new HashMap<>();
        eventMap.put("total", eventLogRepository.eventLogAmount(productKey, deviceName, identifier, page, perPage, startTime, endTime));
        eventMap.put("eventLogList", eventListResult);
        return eventMap;

    }

    @Override
    public Map serviceTransferList(String productKey, String deviceName, Integer page, Integer perPage, String identifier, String startTime, String endTime) {
        //如果page为第0页，则强制转为第1页
        page = (page == 0) ? 1 : page;
        //你每页至少得有一条数据吧
        perPage = (perPage == 0) ? 1 : perPage;
        //分页
        List<ServiceLog> serviceLogList = serviceLogRepository.findServbiceLog(productKey, deviceName, identifier, page, perPage, startTime, endTime);
        List<com.alibaba.fastjson.JSONObject> serviceListResult = new ArrayList<>();
        for (ServiceLog serviceLog : serviceLogList) {
            String result = productTemplateService.parseServiceData(productKey, JSON.toJSON(serviceLog.getLogContent()).toString());
            com.alibaba.fastjson.JSONObject resultJson = JSON.parseObject(result);
            //解析通过
            if (InterceptorConstant.HTTP_CODE_SUCCESS == (int) resultJson.get("code")) {
                com.alibaba.fastjson.JSONObject resultData = resultJson.getJSONObject("data");
                com.alibaba.fastjson.JSONObject returnJson = new com.alibaba.fastjson.JSONObject();
                resultJson.put("createTime", DateFormaterUtil.formatTimestamp(serviceLog.getLogContent().getTimestamp()));
                resultJson.put("serviceName", resultData.get("name"));
                resultJson.put("serviceType", resultData.get("type"));
                resultJson.put("outputParams", resultData.get("outputData"));
                resultJson.put("inputParams", resultData.get("inputData"));
                serviceListResult.add(resultJson);
            }
            //解析不通过
            else {
                com.alibaba.fastjson.JSONObject errorJson = new com.alibaba.fastjson.JSONObject();
                errorJson.put("createTime", DateFormaterUtil.formatTimestamp(serviceLog.getLogContent().getTimestamp()));
                errorJson.put("serviceName", "error");
                errorJson.put("serviceType", "error");
                errorJson.put("outputParams", serviceLog.getLogContent());
                serviceListResult.add(errorJson);
            }
        }
        Map<String, Object> serviceMap = new HashMap<>();
        serviceMap.put("total", serviceLogRepository.serviceLogAmount(productKey, deviceName, identifier, page, perPage, startTime, endTime));
        serviceMap.put("serviceLogList", serviceListResult);
        return serviceMap;
    }

    @Override
    public Map propertyHistory(String productKey, String deviceName, Integer page, Integer perPage, String identifier, String startTime, String endTime) {
        //如果page为第0页，则强制转为第1页
        page = (page == 0) ? 1 : page;
        //你每页至少得有一条数据吧
        perPage = (perPage == 0) ? 1 : perPage;

        //将日志内容存下来并进行解析得到具体内容
        List<PropertyLog> propertyLogList = propertyLogRepository.findPropertyLog(productKey, deviceName, identifier, page, perPage, startTime, endTime);
        Boolean hasNextValue = propertyLogList.size() > perPage;
        List<com.alibaba.fastjson.JSONObject> propertyList = new ArrayList<>();
        String dataType = "";
        for (PropertyLog propertyLog : propertyLogList) {
            //得到解析结果
            String result = productTemplateService.parsePropertyData(productKey, propertyLog.getLogContent().toString());
            JSONObject resultJson = JSONObject.fromObject(result);
            JSONArray resultArray = JSONArray.fromObject(resultJson.get("data"));
            //后面是否还有数据
            for (Object propertyContent : resultArray) {
                com.alibaba.fastjson.JSONObject resultDataJson = JSON.parseObject(propertyContent.toString());
                propertyList.add(resultDataJson);
                dataType = resultDataJson.getString("dataType");
            }
        }
        Map<String, Object> returnMap = new HashMap<>();
        if (hasNextValue) {
            returnMap.put("propertyList", propertyList.subList(0, perPage));
        } else {
            returnMap.put("propertyList", propertyList);
        }
        returnMap.put("dataType", dataType);
        returnMap.put("hasNextValue", hasNextValue);
        return returnMap;
    }

    @Override
    public Map getRealTimeData(String productKey, String deviceName) {
        //得到三元组id
        Integer deviceTripleId = deviceinfoMapper.getDeviceTripleId(productKey, deviceName);
        if (deviceTripleId == null) {
            throw new ApiRuntimeException(HttpResponseCode.ERROR_DEVICE_NOT_EXIST, null);
        }
        //调用读取影子设备接口
        RestResult resultJson = productTemplateService.realTimeData(productKey, deviceTripleId.toString(), null);
        Map<String, Object> returnMap = new HashMap<>();
        //如果请求成功，读取data
        if (InterceptorConstant.HTTP_CODE_SUCCESS == resultJson.getCode()) {
            if (null != resultJson.getData()) {
                //转换时间格式
                try {
                    returnMap.put("currentData", resultJson.getData());
                    JSONArray currentData = JSONArray.fromObject(returnMap.get("currentData"));
                    for (int i = 0; i < currentData.size(); i++) {
                        JSONObject tempJson = JSONObject.fromObject(currentData.get(i));
                        tempJson.put("updateTime", stampToDate(tempJson.get("deviceUploadTime").toString()));
                        tempJson.remove("deviceUploadTime");
                        tempJson.remove("serverReceiveTime");
                        currentData.set(i, tempJson);
                        returnMap.put("currentData", currentData);
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } finally {
                    return returnMap;
                }
            }
        }
        return returnMap;
    }


    @Override
    public List<Map> getDeviceInfoListDeviceInfoListV2(
            String userToken, int organizationId, int userId, DeviceListEnum deviceListEnum) {
        List<Map> deviceInfoList;
        Integer isEnabled = null;
        Integer isDistribution = null;
        if (null != deviceListEnum.getFilterParam()) {
            if (deviceListEnum.getFilterParam().equals(1)) {
                // 禁用
                isEnabled = 1;
            }
            if (deviceListEnum.getFilterParam().equals(2)) {
                // 未分配
                isDistribution = 0;
            }
        }
        List<Integer> organizationIdList = new ArrayList<>();
        List<Integer> departmentIdList = new ArrayList<>();
        if (null != deviceListEnum.getHospital()) {
            for (DeviceListEnum.HospitalBean hospitalBean : deviceListEnum.getHospital()) {
                if (null != hospitalBean.getOrganizationId()) {
                    organizationIdList.add(hospitalBean.getOrganizationId());
                }
                if (null != hospitalBean.getDepartment() && null != hospitalBean.getDepartment().getDepartmentId()) {
                    departmentIdList.add(hospitalBean.getDepartment().getDepartmentId());
                }
            }
        }
        if (organizationIdList.size() > CommonConstant.SEARCH_CONDITION_LENGTH || departmentIdList.size() > CommonConstant.SEARCH_CONDITION_LENGTH) {
            throw new ApiRuntimeException(HttpResponseCode.ERROR_PARAMETER_EXTRA_LONG, null);
        }
        int page = (deviceListEnum.getPage() - 1) * deviceListEnum.getPerPage();
        switch (operatePermissionService.hasOperatePermission(userToken)) {
            case 1:
                deviceInfoList = deviceinfoMapper.selectByOrganizationId(
                        page, deviceListEnum.getPerPage(), null, null,
                        deviceListEnum.getProductKey(), deviceListEnum.getDeviceName(), deviceListEnum.getProvinceId(),
                        deviceListEnum.getOnlineStatus(), isEnabled, isDistribution,
                        deviceListEnum.getSearchDevice(), organizationIdList, departmentIdList
                );
                break;
            case 2:
                deviceInfoList = deviceinfoMapper.selectByOrganizationId(
                        page, deviceListEnum.getPerPage(), organizationId, null,
                        deviceListEnum.getProductKey(), deviceListEnum.getDeviceName(), deviceListEnum.getProvinceId(),
                        deviceListEnum.getOnlineStatus(), isEnabled, isDistribution,
                        deviceListEnum.getSearchDevice(), organizationIdList, departmentIdList
                );
                break;
            case 3:
                deviceInfoList = deviceinfoMapper.selectByOrganizationId(
                        page, deviceListEnum.getPerPage(), organizationId, userId,
                        deviceListEnum.getProductKey(), deviceListEnum.getDeviceName(), deviceListEnum.getProvinceId(),
                        deviceListEnum.getOnlineStatus(), isEnabled, isDistribution,
                        deviceListEnum.getSearchDevice(), organizationIdList, departmentIdList
                );
                break;
            default:
                throw new ApiRuntimeException(409, "这个用户没有权限访问，建议对这个用户菜单，详情联系艾工(bingwen.ai@medcaptain.com)  - yang", null, null);
        }
        return deviceInfoList;
    }

    @Override
    public Integer getDeviceInfoListDeviceInfoCountV2(
            String userToken, int organizationId, int userId, DeviceListEnum deviceListEnum) {
        Integer count;
        Integer isEnabled = null;
        Integer isDistribution = null;
        if (null != deviceListEnum.getFilterParam()) {
            if (deviceListEnum.getFilterParam().equals(1)) {
                // 禁用
                isEnabled = 1;
            }
            if (deviceListEnum.getFilterParam().equals(2)) {
                // 未分配
                isDistribution = 0;
            }
        }
        List<Integer> organizationIdList = new ArrayList<>();
        List<Integer> departmentIdList = new ArrayList<>();
        if (null != deviceListEnum.getHospital()) {
            for (DeviceListEnum.HospitalBean hospitalBean : deviceListEnum.getHospital()) {
                if (null != hospitalBean.getOrganizationId()) {
                    organizationIdList.add(hospitalBean.getOrganizationId());
                }
                if (null != hospitalBean.getDepartment() && null != hospitalBean.getDepartment().getDepartmentId()) {
                    departmentIdList.add(hospitalBean.getDepartment().getDepartmentId());
                }
            }
        }
        if (organizationIdList.size() > CommonConstant.SEARCH_CONDITION_LENGTH || departmentIdList.size() > CommonConstant.SEARCH_CONDITION_LENGTH) {
            throw new ApiRuntimeException(HttpResponseCode.ERROR_PARAMETER_EXTRA_LONG, null);
        }
        switch (operatePermissionService.hasOperatePermission(userToken)) {
            case 1:
                count = deviceinfoMapper.selectByOrganizationIdCount(null, null,
                        deviceListEnum.getProductKey(), deviceListEnum.getDeviceName(), deviceListEnum.getProvinceId(),
                        deviceListEnum.getOnlineStatus(), isEnabled, isDistribution,
                        deviceListEnum.getSearchDevice(), organizationIdList, departmentIdList);
                break;
            case 2:
                count = deviceinfoMapper.selectByOrganizationIdCount(organizationId, null,
                        deviceListEnum.getProductKey(), deviceListEnum.getDeviceName(), deviceListEnum.getProvinceId(),
                        deviceListEnum.getOnlineStatus(), isEnabled, isDistribution,
                        deviceListEnum.getSearchDevice(), organizationIdList, departmentIdList);
                break;
            case 3:
                count = deviceinfoMapper.selectByOrganizationIdCount(organizationId, userId,
                        deviceListEnum.getProductKey(), deviceListEnum.getDeviceName(), deviceListEnum.getProvinceId(),
                        deviceListEnum.getOnlineStatus(), isEnabled, isDistribution,
                        deviceListEnum.getSearchDevice(), organizationIdList, departmentIdList);
                break;
            default:
                throw new ApiRuntimeException(409, "这个用户没有权限访问，建议对这个用户菜单，详情联系艾工(bingwen.ai@medcaptain.com)  - yang", null, null);
        }
        return count;
    }


    @Override
    @Transactional(rollbackFor = ApiRuntimeException.class)
    public Object addDevice(DeviceDtoEntity deviceDtoEntity, String deviceName, Integer roleId, Integer organizationId) {
        Productinfo productinfo = productinfoMapper.selectByPrimaryKey(deviceDtoEntity.getProductKey());
        if (!productinfo.getOrganizationId().equals(organizationId)) {
            throw new ApiRuntimeException(HttpResponseCode.ERROR_DEVICE_NOT_ORGANIZATION_ID, null);
        }
        if (productinfo == null) {
            throw new ApiRuntimeException(HttpResponseCode.ERROR_PRODUCT_NOT_EXIST, null);
        }
        Deviceinfo deviceinfo = deviceinfoMapper.selectByProductKeyAndDeviceName(productinfo.getProductKey(), deviceName);
        if (deviceinfo == null) {
            Deviceinfo deviceinfoDB = new Deviceinfo();
            deviceinfoDB.setProductKey(productinfo.getProductKey());
            deviceinfoDB.setDeviceName(deviceName);
            deviceinfoDB.setDeviceSecret(TripleUtil.getTriple(32));
//            deviceinfoDB.setDeviceSN(deviceDtoEntity.getDeviceSN());
            deviceinfoDB.setDeviceAlias(deviceDtoEntity.getDeviceAlias());
            deviceinfoDB.setIsEnabled(false);
            deviceinfoDB.setIsDel(0);
            deviceinfoDB.setUserId(1);
            deviceinfoDB.setCreatTime(new Date());
            // 设备所属部门
//            deviceinfoDB.setDepartmentId(deviceDtoEntity.getDepartmentBelongId());
//            deviceinfoDB.setManager(deviceDtoEntity.getManager());
//            deviceinfoDB.setOrganizationId(deviceDtoEntity.getHospitalId());

            Integer devInt = deviceinfoMapper.insertSelective(deviceinfoDB);
            deviceinfo = deviceinfoMapper.selectByProductKeyAndDeviceName(productinfo.getProductKey(), deviceName);
            // 添加到角色关联表上
            if (devInt != 1 && deviceinfo != null) {
                throw new ApiRuntimeException(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
            }

            // 添加设备运行状态（未激活）
            Runningstatus runningstatus = new Runningstatus();
            runningstatus.setDeviceStatus(DeviceRunningStatusEnum.NOT_ACTIVE);
            runningstatus.setDeviceIp("0.0.0.0");
            runningstatus.setDeviceTripleId(deviceinfo.getDeviceTripleId());
            runningstatus.setFirmwareVersion("");
//            runningstatus.setLastOnlineTime(new Date());
            runningstatusMapper.insertSelective(runningstatus);

            // 添加设备的地理位置
            Devicelocation devicelocation = new Devicelocation();
            devicelocation.setProductKey(deviceinfo.getProductKey());
            devicelocation.setDeviceName(deviceinfo.getDeviceName());
            devicelocation.setDeviceTripleId(deviceinfo.getDeviceTripleId());
            // 设备ip
            devicelocation.setDeviceIp("0.0.0.0");
            // 纬度
            devicelocation.setLatitude(LonLatUtil.getChinaLonLat(false));
            // 经度
            devicelocation.setLongitude(LonLatUtil.getChinaLonLat(true));
            // 高度
            devicelocation.setAltitude("0");
            devicelocation.setCreattime(new Date());
            devicelocation.setRemark("");
            devicelocationMapper.insertSelective(devicelocation);

            // 设备关联查询权限的部门
//            deviceBindingDepartmentBatch(deviceinfo.getDeviceTripleId(), deviceDtoEntity.getHospitalId(), deviceDtoEntity.getDepartmentCheckList());
        } else {
            logger.error("当前生成设备三元的方式有问题，考虑需要换一种算法");
            throw new ApiRuntimeException(HttpResponseCode.ERROR_DEVICE_EXIST, null);
        }
        return null;
    }

    @Transactional(rollbackFor = ApiRuntimeException.class)
    @Override
    public void deviceBatch(
            String[] deviceNameList,
            String[] productKeyList,
            Integer organizationId,
            Integer userId,
            int operatePermissionCode,
            DeviceBatchTypeEnum type
    ) {
        switch (operatePermissionCode) {
            case 1:
                // 循环删除当前设备
                for (int i = 0; i < deviceNameList.length; i++) {
                    Deviceinfo deviceinfo = deviceinfoMapper.selectByProductKeyAndDeviceName(productKeyList[i], deviceNameList[i]);
                    if (deviceinfo == null) {
                        throw new ApiRuntimeException(HttpResponseCode.ERROR_DEVICE_NOT_EXIST, null);
                    }
                    deviceBatch(deviceinfo, type);
                }
                break;
            case 2:
                // 获取当前厂家下面设备
                List<String> organizationProductInfoList = productinfoMapper.getOrganizationProductInfoList(organizationId);
                // 循环删除当前设备
                for (int i = 0; i < deviceNameList.length; i++) {
                    Deviceinfo deviceinfo = deviceinfoMapper.selectByProductKeyAndDeviceName(productKeyList[i], deviceNameList[i]);
                    if (deviceinfo == null) {
                        throw new ApiRuntimeException(HttpResponseCode.ERROR_DEVICE_NOT_EXIST, null);
                    }
                    // 设备是否属于厂家 判断当前全部设备是否是厂家的
                    if (!organizationProductInfoList.contains(deviceinfo.getProductKey())) {
                        throw new ApiRuntimeException(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
                    }
                    deviceBatch(deviceinfo, type);
                }
                break;
            case 3:
                // 循环删除当前设备
                for (int i = 0; i < deviceNameList.length; i++) {
                    Deviceinfo deviceinfo = deviceinfoMapper.selectByProductKeyAndDeviceName(productKeyList[i], deviceNameList[i]);
                    if (deviceinfo == null) {
                        throw new ApiRuntimeException(HttpResponseCode.ERROR_DEVICE_NOT_EXIST, null);
                    }
                    if (userId.equals(deviceinfo.getUserId())) {
                        throw new ApiRuntimeException(HttpResponseCode.ERROR_DEVICE_NOT_EXIST, null);
                    }
                    deviceBatch(deviceinfo, type);
                }
                break;
            default:
                throw new ApiRuntimeException(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }
    }

    private void deviceBatch(Deviceinfo deviceinfo, DeviceBatchTypeEnum type) {
        switch (type) {
            case DEL:
                if (deviceinfo.getIsOnline()) {
                    // 设备在线
                    throw new ApiRuntimeException(HttpResponseCode.ERROR_DEVICE_IS_ONLINE, null);
                }
                deviceinfoMapper.deleteByIsDelKey(deviceinfo.getDeviceTripleId());
                break;
            case DISABLE:
                if (deviceinfo.getIsOnline()) {
                    // 设备在线
                    throw new ApiRuntimeException(HttpResponseCode.ERROR_DEVICE_IS_ONLINE, null);
                }
                Deviceinfo deviceinfoTempDisable = new Deviceinfo();
                deviceinfoTempDisable.setDeviceTripleId(deviceinfo.getDeviceTripleId());
                deviceinfoTempDisable.setIsEnabled(false);
                deviceinfoMapper.updateByPrimaryKeySelective(deviceinfoTempDisable);
                break;
            case ENABLED:
                Deviceinfo deviceinfoTempEnabled = new Deviceinfo();
                deviceinfoTempEnabled.setDeviceTripleId(deviceinfo.getDeviceTripleId());
                deviceinfoTempEnabled.setIsEnabled(false);
                deviceinfoMapper.updateByPrimaryKeySelective(deviceinfoTempEnabled);
                break;
            default:
                throw new ApiRuntimeException(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }
    }

    @Override
    public void putDeviceInfo(Deviceinfo deviceinfo) {
        deviceinfoMapper.updateByPrimaryKeySelective(deviceinfo);
    }

    @Override
    public Object getDeviceInfo(String productKey, String deviceName) {
        Deviceinfo deviceinfo = deviceinfoMapper.selectByProductKeyAndDeviceName(productKey, deviceName);
        return getDeviceInfo(deviceinfo);
    }

    @Override
    public Object getDeviceInfo(String productKey, String deviceName, Integer organizationId) {
        Deviceinfo deviceinfo = deviceinfoMapper.selectByOrganizationAndPrimaryKey(productKey, deviceName, organizationId);
        return getDeviceInfo(deviceinfo);
    }

    @Override
    public Object getDeviceInfo(String productKey, String deviceName, Integer organizationId, Integer userId) {
        Deviceinfo deviceinfo = deviceinfoMapper.selectByUser(userId, productKey, deviceName);
        return getDeviceInfo(deviceinfo);
    }

    private Object getDeviceInfo(Deviceinfo deviceinfo) {
        if (deviceinfo == null) {
            throw new ApiRuntimeException(HttpResponseCode.ERROR_DEVICE_NOT_EXIST, null);
        }
        Productinfo productinfo = productinfoMapper.selectByPrimaryKey(deviceinfo.getProductKey());
        if (productinfo == null) {
            throw new ApiRuntimeException(HttpResponseCode.ERROR_PRODUCT_NOT_EXIST, null);
        }
        Map<String, Object> returnMap = new HashMap<>(30);
        returnMap.put("productName", productinfo.getProductName());
        returnMap.put("productModel", productinfo.getProductModel());
        if (deviceinfo.getCreatTime() == null) {
            returnMap.put("createAt", "");
        } else {
            returnMap.put("createAt", (new SimpleDateFormat("yyyy-MM-dd")).format(deviceinfo.getCreatTime()));
        }
        if (deviceinfo.getActTime() == null) {
            returnMap.put("activeAt", "");
        } else {
            returnMap.put("activeAt", (new SimpleDateFormat("yyyy-MM-dd")).format(deviceinfo.getActTime()));
        }
        // TODO 最后在线时间
        returnMap.put("deviceIP", "127.0.0.1");
        returnMap.put("productKey", deviceinfo.getProductKey());
        returnMap.put("deviceAlias", deviceinfo.getDeviceAlias());
        returnMap.put("deviceSecret", deviceinfo.getDeviceSecret());
        returnMap.put("deviceSN", deviceinfo.getDeviceSN());
        returnMap.put("deviceName", deviceinfo.getDeviceName());
        returnMap.put("isEnable", deviceinfo.getIsEnabled());
        Runningstatus runningstatu = runningstatusMapper.selectByDeviceTripleIdNew(deviceinfo.getDeviceTripleId());
        returnMap.put("status", runningstatu.getDeviceStatus().getCode());
        returnMap.put("firmwareVersion", runningstatu.getFirmwareVersion());
        returnMap.put("DeviceFirmwareVersion", runningstatu.getFirmwareVersion());
        returnMap.put("lastOnlineAt", runningstatu.getLastOnlineTime().toString());
        Organization organization = organizationMapper.selectByPrimaryKey(deviceinfo.getOrganizationId());
        if (organization != null) {
            returnMap.put("hospitalName", organization.getOrganizationName());
            returnMap.put("hospitalId", organization.getOrganizationId());
            Region regionCity = regionMapper.selectOrganizationId(organization.getOrganizationId());
            if (regionCity != null) {
                returnMap.put("cityName", regionCity.getRegionName());
                returnMap.put("cityId", regionCity.getRegionId());
                Region regionProvince = regionMapper.selectOrganizationId(regionCity.getParentId());
                if (regionProvince != null) {
                    returnMap.put("provinceId", regionProvince.getRegionId());
                    returnMap.put("provinceName", regionProvince.getRegionName());
                }
            }
        }
        Department department = departmentMapper.selectByPrimaryKey(deviceinfo.getDepartmentId());
        if (department != null) {
            returnMap.put("departmentName", department.getDepartmentName());
            returnMap.put("departmentId", department.getDepartmentId());
        }
        List<Department> departmentList = departmentMapper.selectByDeviceTripleId(deviceinfo.getDeviceTripleId(), deviceinfo.getOrganizationId());
        List<String> departmentViewNameList = new ArrayList<>();
        List<Integer> departmentViewIdList = new ArrayList<>();
        if (departmentList != null) {
            for (Department a : departmentList) {
                departmentViewNameList.add(a.getDepartmentName());
                departmentViewIdList.add(a.getDepartmentId());
            }
        }
        RestResult restResult = productTemplateService.realTimeData(deviceinfo.getProductKey(), deviceinfo.getDeviceTripleId().toString(), "RestStorageSpace");
        if (InterceptorConstant.HTTP_CODE_SUCCESS == restResult.getCode()) {
            returnMap.put("RestStorageSpace", restResult.getData());
        }
        returnMap.put("departmentViewName", departmentViewNameList);
        returnMap.put("departmentViewId", departmentViewIdList);
        return returnMap;
    }


    @Override
    public Object getDeviceRole(String productKey, String deviceName, Integer roleid) {
        Deviceinfo deviceinfo = deviceinfoMapper.selectByProductKeyAndDeviceName(productKey, deviceName);
        if (deviceinfo == null) {
            throw new ApiRuntimeException(HttpResponseCode.ERROR_DEVICE_NOT_EXIST, null);
        }
        // TODO 查询角色是否存在
        deviceinfo.setRoleId(roleid);
        return deviceinfoMapper.updateByPrimaryKeySelective(deviceinfo);
    }


    private Deviceinfo addDevice2(String productKey, String deviceName, String deviceAlias, Integer userId, Integer roleId, String ip) {
        Deviceinfo deviceinfo = deviceinfoMapper.selectByProductKeyAndDeviceName(productKey, deviceName);
        if (deviceinfo == null) {
            Deviceinfo deviceinfoDB = new Deviceinfo();
            deviceinfoDB.setProductKey(productKey);
            deviceinfoDB.setDeviceAlias(deviceAlias);
            deviceinfoDB.setDeviceName(deviceName);
            deviceinfoDB.setDeviceSecret(TripleUtil.getTriple(32));
            deviceinfoDB.setIsEnabled(false);
            deviceinfoDB.setIsDel(0);
            deviceinfoDB.setUserId(userId);
            deviceinfoDB.setCreatTime(new Date());
            Integer devInt = deviceinfoMapper.insertSelective(deviceinfoDB);
            deviceinfo = deviceinfoMapper.selectByProductKeyAndDeviceName(productKey, deviceName);
            if (deviceinfo == null) {
                return null;
            }
            // 添加设备运行状态（未激活）
            Runningstatus runningstatus = new Runningstatus();
            runningstatus.setDeviceStatus(DeviceRunningStatusEnum.NOT_ACTIVE);
            runningstatus.setDeviceIp(ip);
            runningstatus.setDeviceTripleId(deviceinfo.getDeviceTripleId());
            runningstatus.setFirmwareVersion("");
//            runningstatus.setLastOnlineTime(new Date());
            runningstatusMapper.insertSelective(runningstatus);
            // 添加设备的地理位置
            Devicelocation devicelocation = new Devicelocation();
            devicelocation.setProductKey(deviceinfo.getProductKey());
            devicelocation.setDeviceName(deviceinfo.getDeviceName());
            devicelocation.setDeviceTripleId(deviceinfo.getDeviceTripleId());
            devicelocation.setDeviceIp(ip);
            devicelocation.setLatitude(LonLatUtil.getChinaLonLat(false));
            devicelocation.setLongitude(LonLatUtil.getChinaLonLat(true));
            // 高度
            devicelocation.setAltitude("0");
            devicelocation.setCreattime(new Date());
            devicelocation.setRemark("");
            devicelocationMapper.insertSelective(devicelocation);
            return deviceinfoDB;
        }
        return null;
    }

    /***
     * 创建表头
     * @param workbook
     * @param sheet
     */
    private void createTitle(HSSFWorkbook workbook, HSSFSheet sheet) {
        HSSFRow row = sheet.createRow(0);
        //设置列宽，setColumnWidth 的第二个参数要乘以256，这个参数的单位是1/256个字符宽度
        sheet.setColumnWidth(0, 20 * 256);
        sheet.setColumnWidth(1, 40 * 256);
        sheet.setColumnWidth(2, 20 * 256);

        //设置为居中加粗
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setFont(font);

        HSSFCell cell;
        cell = row.createCell(0);
        cell.setCellValue("productKey");
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue("deviceName");
        cell.setCellStyle(style);

        cell = row.createCell(2);
        cell.setCellValue("secret");
        cell.setCellStyle(style);

    }

    @Transactional(rollbackFor = ApiRuntimeException.class)
    @Override
    public Object deviceBatchForExcel(String productKey, String deviceAlias, Integer sum, Integer userId, Integer roleId, Integer organizationId, String ip) {
        Map<String, Object> returnMap = new HashMap<>();
        Productinfo productinfo = productinfoMapper.selectByPrimaryKey(productKey);
        if (productinfo == null) {
            throw new ApiRuntimeException(HttpResponseCode.ERROR_PRODUCT_NOT_EXIST, null);
        }
        if (!productinfo.getOrganizationId().equals(organizationId)) {
            throw new ApiRuntimeException(HttpResponseCode.ERROR_DEVICE_NOT_ORGANIZATION_ID, null);
        }
        if (StringUtils.isBlank(deviceAlias)) {
            deviceAlias = UUIDUtil.getUUID().substring(0, 8);
        }
        String fileName = UUIDUtil.getUUID() + ".xls";
        byte[] bytes = excel(sum, productinfo.getProductKey(), deviceAlias, userId, roleId, fileName);
        RestResult fileDFSMap = JSONUtil.toBean(fileDFSClientFeign.fileDFS(bytes, fileName), RestResult.class);
        if (HttpResponseCode.SUCCESS.getCode() == fileDFSMap.getCode()) {
            MorphDynaBean fileDFSDataMap = (MorphDynaBean) fileDFSMap.getData();
            DeviceBatch deviceBatch = new DeviceBatch();
            deviceBatch.setBatch(UUIDUtil.getUUID());
            deviceBatch.setProductName(productinfo.getProductName());
            deviceBatch.setProductKey(productinfo.getProductKey());
            deviceBatch.setDeviceCount(sum);
            deviceBatch.setFileId(fileDFSDataMap.get("url").toString());
            deviceBatch.setCreatTime(new Date());
            deviceBatch.setOrganizationId(organizationId);
            deviceBatch.setUserId(userId);
            deviceBatchMapper.insertSelective(deviceBatch);
            returnMap.put("url", fileDFSDataMap.get("url"));
            returnMap.put("token", fileDFSDataMap.get("token"));
            // 删除 fileName
            return returnMap;
        } else {
            throw new ApiRuntimeException(HttpResponseCode.RESOURCE_NOT_FOUND, null);
        }
    }

    public byte[] excel(Integer sum, String productKey, String deviceAlias, int userId, int roleId, String fileName) {
        File file = new File(fileName);
        try (
                HSSFWorkbook workbook = new HSSFWorkbook();
                FileOutputStream fos = new FileOutputStream(new File(fileName));
                InputStream in = new FileInputStream(fileName);
        ) {
            HSSFSheet sheet = workbook.createSheet("Triad");
            //设置日期格式
            HSSFCellStyle style = workbook.createCellStyle();
            style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
            createTitle(workbook, sheet);
            int rowNum = 1;
            // 新增数据行，并且设置单元格数据
            while (rowNum <= sum) {
                String deviceName = TripleUtil.getTriple(DeviceConstant.DEVICE_NAME_LENG);
                String alias = deviceAlias;
                if (sum > 1) {
                    alias = deviceAlias + "-" + sum;
                }
                Deviceinfo deviceinfo = addDevice2(productKey, deviceName, alias, userId, roleId, "0.0.0.0");
                if (deviceinfo == null) {
                    logger.debug("生成错误");
                    throw new ApiRuntimeException(HttpResponseCode.ERROR_RESOURCE_ALREADY_EXIST, "生成错误");
                }
                HSSFRow row = sheet.createRow(rowNum);
                row.createCell(0).setCellValue(productKey);
                row.createCell(1).setCellValue(deviceinfo.getDeviceName());
                row.createCell(2).setCellValue(deviceinfo.getDeviceSecret());
//            HSSFCell cell = row.createCell(3);
//            cell.setCellValue(deviceinfo.getCreatTime());
//            cell.setCellStyle(style);
                rowNum++;
            }
            //输出到磁盘中
            workbook.write(fos);
            byte[] data = toByteArray(in);
//        DiskFileItem fileItem = (DiskFileItem) new DiskFileItemFactory().createItem("file", MediaType.TEXT_PLAIN_VALUE, true, file.getName());
//            InputStream input = new FileInputStream(file);
//            InputStream in = new FileInputStream(fileName);
//            DiskFileItem fileItem = new DiskFileItem(fileName, "application/vnd.ms-excel", false, file.getName(), (int) file.length(), file.getParentFile());
//            fileItem.getOutputStream();
//            MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
//            IOUtils.copy(input, os);
//            FileInputStream input = new FileInputStream(file);
//            MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "application/vnd.ms-excel", IOUtils.toByteArray(input));
//                OutputStream os = fileItem.getOutputStream()
//            return new CommonsMultipartFile(fileItem);

            return data;
        } catch (IOException e) {
            e.printStackTrace();
            throw new ApiRuntimeException(HttpResponseCode.ERROR_IO, null);
        }
    }

    @Override
    public Object history(Integer userId, Integer organizationId, String userToken) {
        Map<String, Object> returnMap = new HashMap<>(3);
        // 验证是什么权限
        List<Map> returnList = new ArrayList<>();
        int adminCode = operatePermissionService.hasOperatePermission(userToken);
        if (adminCode != 0) {
            for (DeviceBatch deviceBatch : getDeviceBatchList(adminCode, userId, organizationId, null)) {
                Map<String, Object> deviceBatchMap = new HashMap<>(3);
                RestResult restResult = userManageService.getUserInfo(deviceBatch.getUserId());
                if (restResult == null || restResult.getCode() != 200) {
                    deviceBatchMap.put("userName", "");
                } else {
                    deviceBatchMap.put("userName", JSONUtil.toHashMap(restResult.getData()).get("userName"));
                }
                deviceBatchMap.put("createAt", deviceBatch.getCreatTime());
                deviceBatchMap.put("deviceCount", deviceBatch.getDeviceCount());
                deviceBatchMap.put("batch", deviceBatch.getBatch());
                deviceBatchMap.put("productName", deviceBatch.getProductName());
                returnList.add(deviceBatchMap);
            }
        } else {
            throw new ApiRuntimeException(409, "这个用户没有权限访问，建议对这个用户隐藏按钮，详情联系艾工(bingwen.ai@medcaptain.com)  - yang", null, null);
        }
        returnMap.put("historyList", returnList);
        return returnMap;
    }

    @Override
    public Object historyDownload(Integer userId, Integer organizationId, String batch, String userToken) {
        int adminCode = operatePermissionService.hasOperatePermission(userToken);
        if (adminCode != 0) {
            if (getDeviceBatchList(adminCode, userId, organizationId, batch).size() > 0) {
                // 验证userId 是否有权限下载 batch
                DeviceBatch deviceBatch = deviceBatchMapper.selectByBatch(batch, null, null);
                String token = UUIDUtil.getUUID();
                String fileName = token + ".xls";
                fileDFSClientFeign.downloadFile(token, fileName, deviceBatch.getFileId());
                Map<String, Object> returnMap = new HashMap<>(3);
                returnMap.put("url", deviceBatch.getFileId());
                returnMap.put("token", token);
                return returnMap;
            } else {
                throw new ApiRuntimeException(HttpResponseCode.ERROR_FILE_NOT_FOUND, "这个用户没有下载权限访问");
            }
        } else {
            throw new ApiRuntimeException(HttpResponseCode.ERROR_FILE_NOT_FOUND, "这个用户没有权限访问，详情联系艾工(bingwen.ai@medcaptain.com)");
        }
    }


    /**
     * @param adminCode      用户的权限code
     * @param userId         用户id
     * @param organizationId 机构id
     * @param batch          文件id
     * @return 历史文件列表
     */
    public List<DeviceBatch> getDeviceBatchList(Integer adminCode, Integer userId, Integer organizationId, String batch) {
        switch (adminCode) {
            case 1:
                return deviceBatchMapper.selectByUserIdAndOrganizationId(null, null, batch);
            case 2:
                return deviceBatchMapper.selectByUserIdAndOrganizationId(null, organizationId, batch);
            case 3:
                return deviceBatchMapper.selectByUserIdAndOrganizationId(userId, organizationId, batch);
            default:
                return null;
        }
    }


    @Override
    public void deviceBindingDepartmentBatch(
            Deviceinfo deviceinfo,
            Integer organizationId,
            Integer departmentId,
            List<Integer> departmentIdAry
    ) {
        logger.info("设备'{}'绑定到医院'{}'以及部门id为'{}'有查看权限", deviceinfo.getDeviceTripleId(), organizationId, departmentIdAry);
        // 设备绑定到 DeviceDepartmentEntity 中的医院
        Deviceinfo deviceInfoUpdate = new Deviceinfo();
        deviceInfoUpdate.setDeviceTripleId(deviceinfo.getDeviceTripleId());
        deviceInfoUpdate.setOrganizationId(organizationId);
        deviceInfoUpdate.setDepartmentId(departmentId);
        deviceinfoMapper.updateByPrimaryKeySelective(deviceInfoUpdate);
        // 添加设备到关联表
        if (departmentIdAry != null) {
            for (Integer departmentAryId : departmentIdAry) {
                Department department = departmentMapper.selectByPrimaryKey(departmentAryId);
                if (department == null) {
                    throw new ApiRuntimeException(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
                }
                DeviceDepartmentRelated deviceDepartmentRelated = new DeviceDepartmentRelated();
                deviceDepartmentRelated.setGmtModified(new Date());
                deviceDepartmentRelated.setDepartmentId(departmentAryId);
                deviceDepartmentRelated.setDeviceTripleId(deviceinfo.getDeviceTripleId());
                addDeviceDepartmentRelated(deviceDepartmentRelated);
            }
        }
        // 设备激活
        Deviceinfo deviceinfoActivation = new Deviceinfo();
        deviceinfoActivation.setActTime(new Date());
        deviceinfoActivation.setDeviceTripleId(deviceinfo.getDeviceTripleId());
        deviceinfoMapper.updateByPrimaryKeySelective(deviceinfoActivation);
    }


    @Transactional(rollbackFor = ApiRuntimeException.class)
    @Override
    public void untiedDeviceDepartmentBatch(Integer organizationId, DeviceDepartmentUnbindEntity[] deviceDepartmentUnbindEntitiesList) {
        for (DeviceDepartmentUnbindEntity deviceDepartmentUnbindEntity : deviceDepartmentUnbindEntitiesList) {
            // 设备是否存在
            Deviceinfo deviceinfo = deviceinfoMapper.selectByProductKeyAndDeviceName(deviceDepartmentUnbindEntity.getProductKey(), deviceDepartmentUnbindEntity.getDeviceName());
            if (deviceinfo == null) {
                throw new ApiRuntimeException(HttpResponseCode.ERROR_DEVICE_NOT_EXIST, null);
            }
            // 设备是否属于厂家 判断当前全部设备是否是厂家的
            if (productinfoMapper.selectByProductKey(deviceinfo.getProductKey(), organizationId) < 1) {
                throw new ApiRuntimeException(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
            }
            untiedDeviceDepartment(deviceinfo.getDeviceTripleId());
        }
    }

    @Transactional(rollbackFor = ApiRuntimeException.class)
    @Override
    public void untiedDeviceDepartmentBatch(DeviceDepartmentUnbindEntity[] deviceDepartmentUnbindEntitiesList) {
        for (DeviceDepartmentUnbindEntity deviceDepartmentUnbindEntity : deviceDepartmentUnbindEntitiesList) {
            // 设备是否存在
            Deviceinfo deviceinfo = deviceinfoMapper.selectByProductKeyAndDeviceName(deviceDepartmentUnbindEntity.getProductKey(), deviceDepartmentUnbindEntity.getDeviceName());
            if (deviceinfo == null) {
                throw new ApiRuntimeException(HttpResponseCode.ERROR_DEVICE_NOT_EXIST, null);
            }
            untiedDeviceDepartment(deviceinfo.getDeviceTripleId());
        }
    }

    /**
     * 解绑 设备和科室的关联
     *
     * @param deviceTripleId
     */
    private void untiedDeviceDepartment(int deviceTripleId) {
        Deviceinfo deviceInfoUpdate = new Deviceinfo();
        deviceInfoUpdate.setDeviceTripleId(deviceTripleId);
        deviceInfoUpdate.setOrganizationId(0);
        deviceInfoUpdate.setDepartmentId(0);
        deviceinfoMapper.updateByPrimaryKeySelective(deviceInfoUpdate);
        // 删除设备在关联表
        deviceDepartmentRelatedMapper.deleteBydeviceTripleId(deviceTripleId);
    }

    @Override
    public void hasOperatePermission(ParamEntity paramEntity, Integer organizationId, String userToken, Integer userId) {
        switch (operatePermissionService.hasOperatePermission(userToken)) {
            case 1:
                if (null != paramEntity.getDeviceName() && null != paramEntity.getProductKey()) {
                    Deviceinfo deviceinfo = deviceinfoMapper.selectByProductKeyAndDeviceName(paramEntity.getProductKey(), paramEntity.getDeviceName());
                    if (null == deviceinfo) {
                        throw new ApiRuntimeException(HttpResponseCode.ERROR_DEVICE_NOT_EXIST, null);
                    }
                } else if (null != paramEntity.getProductKey()) {
                    Productinfo productinfo = productinfoMapper.selectByPrimaryKey(paramEntity.getProductKey());
                    if (null == productinfo) {
                        throw new ApiRuntimeException(HttpResponseCode.ERROR_DEVICE_NOT_EXIST, null);
                    }
                } else if (null != paramEntity.getDeviceName()) {
                    throw new ApiRuntimeException(HttpResponseCode.ERROR_PARAMETER, null);
                }
                break;
            case 2:
                if (null != paramEntity.getDeviceName() && null != paramEntity.getProductKey()) {
                    // deviceName 和 productKey 都不为空的时候
                    if (1 > deviceinfoMapper.selectByRoleIdCount(organizationId, null, paramEntity.getProductKey(), paramEntity.getDeviceName())) {
                        throw new ApiRuntimeException(HttpResponseCode.ERROR_DEVICE_NOT_EXIST, null);
                    }
                } else if (null != paramEntity.getProductKey()) {
                    // productKey 都不为空的时候，当前产品属性当前机构的
                    Productinfo productinfo = productinfoMapper.selectByPrimaryKey(paramEntity.getProductKey());
                    if (null == productinfo || null == productinfo.getOrganizationId() || !productinfo.getOrganizationId().equals(organizationId)) {
                        throw new ApiRuntimeException(HttpResponseCode.ERROR_DEVICE_NOT_EXIST, null);
                    }
                } else {
                    throw new ApiRuntimeException(HttpResponseCode.ERROR_PARAMETER, null);
                }
                break;
            case 3:
                if (1 > deviceinfoMapper.selectByRoleIdCount(organizationId, userId, paramEntity.getProductKey(), paramEntity.getDeviceName())) {
                    throw new ApiRuntimeException(HttpResponseCode.ERROR_DEVICE_NOT_EXIST, null);
                }
                break;
            default:
                throw new ApiRuntimeException(HttpResponseCode.ERROR_NO_POWER, null);
        }
        if (paramEntity.getPerPage() == null) {
            paramEntity.setPerPage(10);
        } else if (paramEntity.getPerPage() < 1) {
            paramEntity.setPerPage(10);
        }
        if (paramEntity.getPage() == null) {
            paramEntity.setPage(1);
        } else if (paramEntity.getPage() < 1) {
            paramEntity.setPage(1);
        }
    }

    @Override
    public Map deviceLogRecord(String productKey, String deviceName, int page, int perPage) {
        if (0 >= page) {
            page = 1;
        }
        if (0 >= perPage) {
            perPage = 10;
        }
        List<Map> recordList = new ArrayList<>();
        Map<String, Object> returnMap = new HashMap<>();
        Integer deviceTripleId = deviceinfoMapper.getDeviceTripleId(productKey, deviceName);
        if (null == deviceTripleId) {
            throw new ApiRuntimeException(HttpResponseCode.ERROR_DEVICE_NOT_EXIST, null);
        }
        List<Runningstatus> runningStatusList = runningstatusMapper.selectByDeviceTripleId(deviceTripleId, (page - 1) * perPage, perPage);
        int total = runningstatusMapper.countByDeviceTripleId(deviceTripleId);
        for (Runningstatus r : runningStatusList) {
            Map<String, Object> recordMap = new HashMap<>();
            recordMap.put("status", r.getDeviceStatus().getCode());
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            recordMap.put("time", df.format(r.getLastOnlineTime()));
            recordList.add(recordMap);
        }
        returnMap.put("recordList", recordList);
        returnMap.put("total", total);
        return returnMap;
    }

    @Override
    public List<Integer> getDevicePermissionRoleList(String productKey, String deviceName) {
        /**
         * TODO 获取超级管理员的角色
         */

        Deviceinfo deviceinfo = deviceinfoMapper.selectByProductKeyAndDeviceName(productKey, deviceName);
        Productinfo productinfo = productinfoMapper.selectByPrimaryKey(productKey);
        if (deviceinfo == null || productinfo == null) {
            return new ArrayList<>();
        }
        /**
         * 获取当前产品的管理员角色们
         */
        RestResult restResult = userManageService.getManagerRole(0, 100, productinfo.getOrganizationId());
        List<Integer> returnList = new ArrayList<>();
        if (restResult.getCode() == 200) {
            if (restResult.getData() != null) {
                Map dataMap = (Map) restResult.getData();
                List<Map> dataList = (List) dataMap.get("list");
                if (dataList != null) {
                    for (Map userInfo : dataList) {
                        returnList.add(new Integer(userInfo.get("roleId").toString()));
                    }
                }
            }
        }
        return returnList;
    }

    @Override
    public List<Integer> getDevicePermissionUserList(String productKey, String deviceName) {
        Deviceinfo deviceinfo = deviceinfoMapper.selectByProductKeyAndDeviceName(productKey, deviceName);
        Productinfo productinfo = productinfoMapper.selectByPrimaryKey(productKey);
        if (deviceinfo == null || productinfo == null) {
            return new ArrayList<>();
        }
        /**
         * 获取当前产品的管理员用户们
         */
        RestResult restResult = userManageService.listManagers(0, 100, productinfo.getOrganizationId());
        List<Integer> returnList = new ArrayList<>();
        if (restResult.getCode() == 200) {
            if (restResult.getData() != null) {
                Map dataMap = (Map) restResult.getData();
                List<Map> dataList = (List) dataMap.get("list");
                if (dataList != null) {
                    for (Map userInfo : dataList) {
                        returnList.add(new Integer(userInfo.get("userId").toString()));
                    }
                }
            }
        }
        /**
         * 获取创建产品的用户
         */
        returnList.add(deviceinfo.getUserId());
        return returnList;
    }

    /**
     * 添加可查看设备的科室（存在跳过，不存在添加）
     */
    public int addDeviceDepartmentRelated(DeviceDepartmentRelated deviceDepartmentRelated) {
        // 关联数据是否存在
        DeviceDepartmentRelated departmentRelated =
                deviceDepartmentRelatedMapper.selectByDeviceTripleIdAndDepartmentId(
                        deviceDepartmentRelated.getDeviceTripleId(),
                        deviceDepartmentRelated.getDepartmentId()
                );
        if (departmentRelated == null) {
            // 添加关联数据
            deviceDepartmentRelatedMapper.insert(deviceDepartmentRelated);
        }
        return 0;
    }
}