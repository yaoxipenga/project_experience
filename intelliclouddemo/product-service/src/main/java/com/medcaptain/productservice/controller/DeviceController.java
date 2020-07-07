package com.medcaptain.productservice.controller;

import com.alibaba.fastjson.JSON;
import com.medcaptain.annotation.AuthorizationFree;
import com.medcaptain.constant.InterceptorConstant;
import com.medcaptain.dto.RestResult;
import com.medcaptain.enums.HttpResponseCode;
import com.medcaptain.exception.ApiRuntimeException;
import com.medcaptain.productservice.constant.CommonConstant;
import com.medcaptain.productservice.constant.DeviceConstant;
import com.medcaptain.productservice.dao.mongo.AlarmLogRepository;
import com.medcaptain.productservice.dao.mongo.DeviceLogRepository;
import com.medcaptain.productservice.dao.mongo.EventLogRepository;
import com.medcaptain.productservice.dao.mongo.PropertyLogRepository;
import com.medcaptain.productservice.dao.mybatis.DepartmentMapper;
import com.medcaptain.productservice.dao.mybatis.DeviceinfoMapper;
import com.medcaptain.productservice.dao.mybatis.OrganizationMapper;
import com.medcaptain.productservice.dao.mybatis.ProductTransactionIdentifiersMapper;
import com.medcaptain.productservice.dao.mybatis.ProductinfoMapper;
import com.medcaptain.productservice.dao.mybatis.RegionMapper;
import com.medcaptain.productservice.dao.mybatis.RunningstatusMapper;
import com.medcaptain.productservice.entity.dto.DeviceDepartmentEntity;
import com.medcaptain.productservice.entity.dto.DeviceDepartmentUnbindEntity;
import com.medcaptain.productservice.entity.dto.DeviceDtoEntity;
import com.medcaptain.productservice.entity.dto.ParamEntity;
import com.medcaptain.productservice.entity.dto.mongo.AlarmLogEntity;
import com.medcaptain.productservice.entity.dto.mongo.DeviceLogEntity;
import com.medcaptain.productservice.entity.dto.mongo.EventLog;
import com.medcaptain.productservice.entity.dto.mongo.PropertyLog;
import com.medcaptain.productservice.entity.mybatis.Department;
import com.medcaptain.productservice.entity.mybatis.Deviceinfo;
import com.medcaptain.productservice.entity.mybatis.Organization;
import com.medcaptain.productservice.entity.mybatis.ProductTransactionIdentifiers;
import com.medcaptain.productservice.entity.mybatis.Productinfo;
import com.medcaptain.productservice.entity.mybatis.Region;
import com.medcaptain.productservice.enums.DeviceBatchTypeEnum;
import com.medcaptain.productservice.enums.DeviceListEnum;
import com.medcaptain.productservice.feign.ProductTemplateService;
import com.medcaptain.productservice.service.DeviceService;
import com.medcaptain.productservice.service.OperatePermissionService;
import com.medcaptain.utils.DateUtil;
import com.medcaptain.utils.JSONUtil;
import com.medcaptain.utils.StringUtil;
import com.medcaptain.utils.TripleUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yang
 */
@RestController
public class DeviceController {

    private static Logger logger = LoggerFactory.getLogger(DeviceController.class);

    @Autowired
    DeviceService deviceService;
    @Autowired
    OperatePermissionService operatePermissionService;
    @Autowired
    ProductTemplateService productTemplateService;


    @Autowired
    DeviceinfoMapper deviceinfoMapper;
    @Autowired
    DepartmentMapper departmentMapper;
    @Autowired
    ProductinfoMapper productinfoMapper;
    @Autowired
    RunningstatusMapper runningstatusMapper;
    @Autowired
    OrganizationMapper organizationMapper;
    @Autowired
    RegionMapper regionMapper;
    @Autowired
    ProductTransactionIdentifiersMapper productTransactionIdentifiersMapper;


    @Autowired
    PropertyLogRepository propertyLogRepository;
    @Autowired
    EventLogRepository eventLogRepository;
    @Autowired
    AlarmLogRepository alarmLogRepository;
    @Autowired
    DeviceLogRepository deviceLogRepository;

    /**
     * 获取设备信息(厂家)
     *
     * @param deviceName 设备name
     * @return
     */
    @GetMapping(value = "/product_key/{productKey}/device/{deviceName}")
    public RestResult getDeviceInfo(
            @RequestHeader(name = "userId") Integer userId,
            @RequestHeader(name = "token") String userToken,
            @RequestHeader(name = "organizationId") Integer organizationId,
            @PathVariable String deviceName,
            @PathVariable String productKey
    ) {
        Productinfo productinfo = productinfoMapper.selectByPrimaryKey(productKey);
        if (productinfo == null) {
            throw new ApiRuntimeException(HttpResponseCode.ERROR_PRODUCT_NOT_EXIST, null);
        }
        int operatePermissionCode = operatePermissionService.hasOperatePermission(userToken);
        Object returnObject;
        switch (operatePermissionCode) {
            case 1:
                returnObject = deviceService.getDeviceInfo(productKey, deviceName);
                break;
            case 2:
                returnObject = deviceService.getDeviceInfo(productKey, deviceName, organizationId);
                break;
            case 3:
                returnObject = deviceService.getDeviceInfo(productKey, deviceName, organizationId, userId);
                break;
            default:
                return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
        }
        return RestResult.getInstance(HttpResponseCode.SUCCESS, returnObject);
    }

    /**
     * 更新设备基本信息
     * 设备信息更新 接口（不包含设备绑定数据）
     *
     * @return
     */
    @PutMapping(value = "/product_key/{product_key}/device/{device_name}")
    RestResult relatedDeviceDepartment(
            @RequestHeader(name = "token") String userToken,
            @RequestHeader(name = "roleId") Integer roleId,
            @RequestHeader(name = "userId") Integer userId,
            @RequestHeader(name = "organizationId") Integer organizationId,
            @PathVariable(value = "product_key") String productKey,
            @PathVariable(value = "device_name") String deviceName,
            @RequestParam(value = "manager", required = false) String manager,
            @RequestParam(value = "deviceAlias", required = false) String deviceAlias,
            @RequestParam(value = "isEnabled", required = false) Integer isEnabled
    ) {
        Deviceinfo deviceinfo = deviceinfoMapper.selectByProductKeyAndDeviceName(productKey, deviceName);
        if (null == deviceinfo) {
            throw new ApiRuntimeException(HttpResponseCode.ERROR_DEVICE_NOT_EXIST, null);
        }
        Deviceinfo deviceinfoTemp = new Deviceinfo();
        deviceinfoTemp.setDeviceTripleId(deviceinfo.getDeviceTripleId());
        if (manager != null && manager.length() <= CommonConstant.DEVICE_LENGTH) {
            deviceinfoTemp.setManager(manager);
        }
        if (deviceAlias != null && deviceAlias.length() <= CommonConstant.DEVICE_LENGTH) {
            deviceinfoTemp.setDeviceAlias(deviceAlias);
        } else if (deviceAlias == null) {
            deviceinfoTemp.setDeviceAlias("");
        }
        if (isEnabled != null) {
            if (deviceinfo.getIsOnline()) {
                // 设备在线
                throw new ApiRuntimeException(HttpResponseCode.ERROR_DEVICE_IS_ONLINE, null);
            }
            boolean enabledBool = (isEnabled == 0) ? false : ((isEnabled == 1) ? true : null);
            deviceinfoTemp.setIsEnabled(enabledBool);
        }
        int operatePermissionCode = operatePermissionService.hasOperatePermission(userToken);
        switch (operatePermissionCode) {
            case 1:
                deviceService.putDeviceInfo(deviceinfoTemp);
                break;
            case 2:
                Deviceinfo deviceinfoOrganization = deviceinfoMapper.selectByOrganizationAndPrimaryKey(productKey, deviceName, organizationId);
                if (null != deviceinfoOrganization) {
                    deviceService.putDeviceInfo(deviceinfoTemp);
                }
                break;
            case 3:
                Deviceinfo deviceinfoUser = deviceinfoMapper.selectByUser(userId, productKey, deviceName);
                if (null != deviceinfoUser) {
                    deviceService.putDeviceInfo(deviceinfoTemp);
                }
                break;
            default:
                break;
        }
        return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
    }


    /**
     * 厂家添加设备接口，此接口不应该涉及医院方面的处理逻辑
     *
     * @param roleId          角色id
     * @param deviceDtoEntity 新增设备实体
     * @return
     */
    @PostMapping(value = "/device")
    public RestResult addDevice(
            @RequestHeader(name = "roleId") Integer roleId,
            @RequestHeader(name = "token") String userToken,
            @RequestHeader(name = "organizationId") Integer organizationId,
            @RequestBody() DeviceDtoEntity deviceDtoEntity
    ) {
        if (deviceDtoEntity == null) {
            throw new ApiRuntimeException(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }
        if (StringUtil.isEmpty(deviceDtoEntity.getProductKey())) {
            throw new ApiRuntimeException(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }
        switch (operatePermissionService.hasOperatePermission(userToken)) {
            case 1:
            case 2:
            case 3:
                break;
            default:
                throw new ApiRuntimeException(HttpResponseCode.ERROR_NO_POWER, null);
        }
        // 添加设备
        // 随机生成deviceName
        String deviceName = TripleUtil.getTriple(DeviceConstant.DEVICE_NAME_LENG);
        deviceService.addDevice(deviceDtoEntity, deviceName, roleId, organizationId);
        return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
    }

    /**
     * 删除设备
     * 删除设备只有厂家拥有权限
     *
     * @param deviceName 设备name
     * @return
     */
    @DeleteMapping(value = "/product_key/{productKey}/device/{deviceName}")
    public RestResult delDevice(
            @RequestHeader(name = "organizationId") Integer organizationId,
            @RequestHeader(name = "token") String userToken,
            @RequestHeader(name = "userId") Integer userId,
            @PathVariable String productKey,
            @PathVariable String deviceName
    ) {
        if (StringUtil.isEmpty(productKey) || StringUtil.isEmpty(deviceName)) {
            throw new ApiRuntimeException(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }
        String[] deviceNameList = {deviceName};
        String[] productKeyList = {productKey};
        int operatePermissionCode = operatePermissionService.hasOperatePermission(userToken);
        deviceService.deviceBatch(deviceNameList, productKeyList, organizationId, userId, operatePermissionCode, DeviceBatchTypeEnum.DEL);
        return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
    }


    private Map<String, Object> getDeviceList(List<Map> deviceInfoList) {
        Map<String, Object> returnMap = new HashMap<>(4);
        List<Map> deviceList = new ArrayList<>();
        for (Map deviceinfo : deviceInfoList) {
            Map<String, Object> deviceInfoMap = new HashMap<>(13);
            String productKey = deviceinfo.get(DeviceConstant.PRODUCT_KEY).toString();
            Integer deviceTripleId = Integer.parseInt(deviceinfo.get(DeviceConstant.DEVICE_TRIPLE_ID).toString());
            if (productKey == null) {
                break;
            }
            List<ProductTransactionIdentifiers> productTransactionIdentifiersMapperList = productTransactionIdentifiersMapper.selectByProductKey(productKey, 0);
            Map<String, Object> identifiersList = new HashMap<>(productTransactionIdentifiersMapperList.size() + 1);
            for (ProductTransactionIdentifiers identifiers : productTransactionIdentifiersMapperList) {
                RestResult restResult = productTemplateService.realTimeData(
                        deviceinfo.get(DeviceConstant.PRODUCT_KEY).toString(),
                        deviceinfo.get(DeviceConstant.DEVICE_TRIPLE_ID).toString(),
                        identifiers.getIdentifiers());
                if (InterceptorConstant.HTTP_CODE_SUCCESS == restResult.getCode() && null != restResult.getData()) {
                    JSONArray restResultList = JSONUtil.toJSONArray(restResult.getData());
                    if (null != restResultList && restResultList.size() > 0) {
                        identifiersList.put(identifiers.getIdentifiers(), ((Map) restResultList.get(0)).get("deviceUploadValue"));
                    }
                }
            }
            deviceInfoMap.put("identifiers", identifiersList);
            // 是否分配给医院
            deviceInfoMap.put("distribution", true);
            // 累计在线时间
            deviceInfoMap.put("totalOnlineTime", 1553148090);
            // 累计测试次数
            deviceInfoMap.put("totalTextTime", 1500);

            deviceInfoMap.put("deviceAlias", deviceinfo.get(DeviceConstant.DEVICE_ALIAS));
            deviceInfoMap.put("productName", deviceinfo.get(DeviceConstant.PRODUCT_NAME));
            deviceInfoMap.put("productModel", deviceinfo.get(DeviceConstant.PRODUCT_MODEL));
            deviceInfoMap.put("deviceName", deviceinfo.get(DeviceConstant.DEVICE_NAME));
            deviceInfoMap.put("deviceId", deviceTripleId);
            deviceInfoMap.put("firmwareVersion", "最新固件版本");
            deviceInfoMap.put("DeviceFirmwareVersion", "当前固件版本");
            if (Boolean.parseBoolean(deviceinfo.get(DeviceConstant.IS_ONLINE).toString())) {
                deviceInfoMap.put("isOnline", 1);
            } else {
                deviceInfoMap.put("isOnline", 0);
            }
            if (deviceinfo.get(DeviceConstant.ACT_TIME) == null) {
                deviceInfoMap.put("isStatus", 0);
            } else {
                deviceInfoMap.put("isStatus", 1);
            }
            if (deviceinfo.get(DeviceConstant.ENABLED) != null) {
                Boolean enabledBool = (Boolean) deviceinfo.get(DeviceConstant.ENABLED);
                deviceInfoMap.put("isEnabled", (enabledBool) ? 0 : 1);
            }
            deviceInfoMap.put("createAt", DateUtil.getDateTimeFormat(String.valueOf(deviceinfo.get(DeviceConstant.CREAT_TIME))));
            deviceInfoMap.put("productKey", deviceinfo.get(DeviceConstant.PRODUCT_KEY));
            deviceInfoMap.put("deviceSN", deviceinfo.get(DeviceConstant.DEVICE_SN));
            // 所属医院、所属科室
            deviceInfoMap.put("organizationName", "");
            deviceInfoMap.put("departmentName", "");
            Object temp1 = deviceinfo.get(DeviceConstant.ORGANIZATION_ID);
            Object temp2 = deviceinfo.get(DeviceConstant.DEPARTMENT_ID);
            if (deviceinfo.get(DeviceConstant.ORGANIZATION_ID) != null) {
                int organizationId = Integer.parseInt(deviceinfo.get(DeviceConstant.ORGANIZATION_ID).toString());
                if (organizationId > 0) {
                    Organization organization = organizationMapper.selectByPrimaryKey(organizationId);
                    if (organization != null) {
                        deviceInfoMap.put("organizationName", organization.getOrganizationName());
                        //  TODO 可查询科室  （仅开发查询，上线删除）
                        List<Department> departmentList = departmentMapper.selectByDeviceTripleId(deviceTripleId, organization.getOrganizationId());
                        if (departmentList != null && departmentList.size() != 0) {
                            String[] departmentNameArray = new String[departmentList.size()];
                            for (int i = 0; i < departmentList.size(); i++) {
                                departmentNameArray[i] = departmentList.get(i).getDepartmentName();
                            }
                            deviceInfoMap.put("可查询科室", StringUtils.join(departmentNameArray, " , "));
                        }
                        // 有查询的科室 结束
                    }
                }
            }
            if (deviceinfo.get(DeviceConstant.DEPARTMENT_ID) != null) {
                int departmentId = Integer.parseInt(deviceinfo.get(DeviceConstant.DEPARTMENT_ID).toString());
                if (departmentId > 0) {
                    Department department = departmentMapper.selectByPrimaryKey(departmentId);
                    if (department != null) {
                        deviceInfoMap.put("departmentName", department.getDepartmentName());
                    }
                }
            }
            // 所属区域
            Region region = regionMapper.selectOrganizationId(Integer.parseInt(deviceinfo.get(DeviceConstant.ORGANIZATION_ID).toString()));
            if (region == null) {
                deviceInfoMap.put("regionName", "");
            } else {
                deviceInfoMap.put("regionName", region.getRegionName());
            }
            deviceList.add(deviceInfoMap);
        }
        returnMap.put(DeviceConstant.DEVICE_LIST, deviceList);
        return returnMap;
    }

    /**
     * 获取当前角色下全部列表
     *
     * @return
     */
    @PostMapping(value = "/v2/devices")
    public RestResult getDeviceList(
            @RequestHeader(name = "userId") Integer userId,
            @RequestHeader(name = "roleId") Integer roleId,
            @RequestHeader(name = "organizationId") Integer userorganizationId,
            @RequestHeader(name = "departmentId") Integer userDepartmentId,
            @RequestHeader(name = "token") String userToken,
            @RequestBody() DeviceListEnum deviceListEnum
    ) {
        if (deviceListEnum == null) {
            deviceListEnum = new DeviceListEnum();
            deviceListEnum.setPerPage(CommonConstant.PERPAGE_SIZE);
            deviceListEnum.setPage(1);
        } else {
            if (deviceListEnum.getPerPage() == null || deviceListEnum.getPerPage() > CommonConstant.PERPAGE_SIZE) {
                deviceListEnum.setPerPage(CommonConstant.PERPAGE_SIZE);
            }
            if (deviceListEnum.getPage() == null || deviceListEnum.getPage() < 1) {
                deviceListEnum.setPage(1);
            }
        }
        if (null != deviceListEnum.getProductKey() && deviceListEnum.getProductKey().size() > CommonConstant.SEARCH_CONDITION_LENGTH) {
            throw new ApiRuntimeException(HttpResponseCode.ERROR_PARAMETER_EXTRA_LONG, null);
        }
        if (null != deviceListEnum.getDeviceName() && deviceListEnum.getDeviceName().size() > CommonConstant.SEARCH_CONDITION_LENGTH) {
            throw new ApiRuntimeException(HttpResponseCode.ERROR_PARAMETER_EXTRA_LONG, null);
        }
        // 厂家角色，可以看到当前厂家下产品的全部设备，
        // 权限说的是，是否可以对产品和设备的操作权限，不会分厂家的某个角色只能看某个产品
        List<Map> deviceInfoList = deviceService.getDeviceInfoListDeviceInfoListV2(
                userToken, userorganizationId, userId, deviceListEnum);
        Map<String, Object> returnMap = getDeviceList(deviceInfoList);
        returnMap.put(CommonConstant.TOTAL, deviceService.getDeviceInfoListDeviceInfoCountV2(
                userToken, userorganizationId, userId, deviceListEnum));
        return RestResult.getInstance(HttpResponseCode.SUCCESS, returnMap);
    }


    /**
     * 设备绑定角色
     *
     * @return
     */
    @PostMapping(value = "/device/bindRole")
    public RestResult getDeviceRole(
            @RequestHeader(name = "roleId") Integer roleId,
            @RequestParam(value = "deviceName") String deviceName,
            @RequestParam(value = "productKey") String productKey
    ) {
        return RestResult.getInstance(HttpResponseCode.SUCCESS, deviceService.getDeviceRole(productKey, deviceName, roleId));
    }

    /**
     * 批量操作设备
     * 1、删除设备
     * 2、禁用
     * 3、启用
     * 设备批量删除（厂家）
     * 删除设备只有厂家拥有全新
     */
    @PutMapping(value = "/device/batch")
    public RestResult deviceBatch(
            @RequestHeader(name = "token") String userToken,
            @RequestHeader(name = "userId") Integer userId,
            @RequestHeader(name = "organizationId") Integer organizationId,
            @RequestParam(value = "type") DeviceBatchTypeEnum type,
            @RequestParam(value = "deviceName") String deviceName,
            @RequestParam(value = "productKey") String productKey
    ) {
        if (StringUtil.isEmpty(productKey) || StringUtil.isEmpty(deviceName)) {
            throw new ApiRuntimeException(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }
        String[] deviceNameList = deviceName.split(CommonConstant.VARIABLE_SEGMENTATION);
        String[] productKeyList = productKey.split(CommonConstant.VARIABLE_SEGMENTATION);
        if (deviceNameList.length != productKeyList.length) {
            throw new ApiRuntimeException(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }
        if (deviceNameList.length == 0) {
            return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
        }
        int operatePermissionCode = operatePermissionService.hasOperatePermission(userToken);
        deviceService.deviceBatch(deviceNameList, productKeyList, organizationId, userId, operatePermissionCode, type);
        return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
    }

    /**
     * 批量设备分配 医院、科室（厂家）
     *
     * @param deviceDepartmentEntity 前端传入json 的DTO
     * @return
     */
    @Transactional(rollbackFor = ApiRuntimeException.class)
    @PostMapping(value = "/organization/bind")
    RestResult bindingDeviceOrganizationAndDepartmentBatch(
            @RequestHeader(name = "roleId") Integer roleId,
            @RequestHeader(name = "userId") Integer userId,
            @RequestHeader(name = "token") String userToken,
            @RequestHeader(name = "organizationId") Integer organizationId,
            @RequestBody() DeviceDepartmentEntity deviceDepartmentEntity
    ) {
        if (deviceDepartmentEntity == null) {
            throw new ApiRuntimeException(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }
        if (deviceDepartmentEntity.getDeviceList() == null && 1 > deviceDepartmentEntity.getDeviceList().size()) {
            throw new ApiRuntimeException(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }
        if (deviceDepartmentEntity.getDepartmentId() != null) {
            Department department = departmentMapper.selectByPrimaryKey(deviceDepartmentEntity.getDepartmentId());
            if (department == null) {
                throw new ApiRuntimeException(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
            }
        }
        if (deviceDepartmentEntity.getOrganizationId() != null) {
            Organization organization = organizationMapper.selectByPrimaryKey(deviceDepartmentEntity.getOrganizationId());
            if (organization == null) {
                throw new ApiRuntimeException(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
            }
        } else {
            deviceDepartmentEntity.setOrganizationId(0);
            deviceDepartmentEntity.setDepartmentId(0);
            deviceDepartmentEntity.setDepartmentCheckId(new ArrayList<>());
        }
        // departmentAryId 是否是 organizationId 下的部门
        List<Integer> depList = departmentMapper.getOrganizationList(organizationId);
        for (Integer departmentAryId : deviceDepartmentEntity.getDepartmentCheckId()) {
            if (!depList.contains(departmentAryId)) {
                // 可查询的科室，不在当前机构\医院下面，参数无效。
                throw new ApiRuntimeException(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
            }
        }
        boolean admin = false;
        switch (operatePermissionService.hasOperatePermission(userToken)) {
            case 1:
                admin = true;
                break;
            case 2:
                break;
            default:
                throw new ApiRuntimeException(HttpResponseCode.ERROR_NO_POWER, null);
        }
        // 根据 deviceDepartmentEntity 循环添加设备关联
        for (DeviceDepartmentEntity.DeviceListBean deviceListBean : deviceDepartmentEntity.getDeviceList()) {
            // 设备是否存在
            Deviceinfo deviceinfo = deviceinfoMapper.selectByProductKeyAndDeviceName(deviceListBean.getProductKey(), deviceListBean.getDeviceName());
            if (deviceinfo == null) {
                throw new ApiRuntimeException(HttpResponseCode.ERROR_DEVICE_NOT_EXIST, null);
            }
            // 管理员不鉴此权限, 设备是否属于厂家 判断当前全部设备是否是厂家的
            if (admin) {
                if (productinfoMapper.selectByProductKey(deviceinfo.getProductKey(), organizationId) < 1) {
                    throw new ApiRuntimeException(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
                }
            }
            // 将设备授权到某个医院，并添加一批改医院下的部门查询权限
            deviceService.deviceBindingDepartmentBatch(
                    deviceinfo,
                    deviceDepartmentEntity.getOrganizationId(),
                    deviceDepartmentEntity.getDepartmentId(),
                    deviceDepartmentEntity.getDepartmentCheckId()
            );
        }
        return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
    }


    /**
     * 批量设备 医院解绑 接口（解绑当前设备的医院和科室）
     * （只有销售人员和我们的超级管理员有权限）
     */
    @PutMapping(value = "/organization/unbind")
    RestResult unbindDeviceDepartmentBatch(
            @RequestHeader(name = "roleId") Integer roleId,
            @RequestHeader(name = "userId") Integer userId,
            @RequestHeader(name = "token") String userToken,
            @RequestHeader(name = "organizationId") Integer organizationId,
            @RequestBody() DeviceDepartmentUnbindEntity[] deviceDepartmentUnbindEntity
    ) {
        if (deviceDepartmentUnbindEntity == null || deviceDepartmentUnbindEntity.length == 0) {
            throw new ApiRuntimeException(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }
        // 当前角色是在该医院中有权限管理设备关联权限
        switch (operatePermissionService.hasOperatePermission(userToken)) {
            case 1:
                deviceService.untiedDeviceDepartmentBatch(deviceDepartmentUnbindEntity);
                break;
            case 2:
                deviceService.untiedDeviceDepartmentBatch(organizationId, deviceDepartmentUnbindEntity);
                break;
            default:
                throw new ApiRuntimeException(HttpResponseCode.ERROR_NO_POWER, null);
        }
        return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
    }

    /**
     * 获取批量设备历史记录
     */
    @GetMapping(value = "/device/history")
    RestResult history(
            @RequestHeader(name = "token") String userToken,
            @RequestHeader(name = "roleId") Integer roleId,
            @RequestHeader(name = "userId") Integer userId,
            @RequestHeader(name = "organizationId") Integer organizationId
    ) {
        return RestResult.getInstance(HttpResponseCode.SUCCESS, deviceService.history(userId, organizationId, userToken));
    }

    /**
     * 实时数据
     */
    @GetMapping(value = "/product_key/{product_key}/device/{device_name}/data")
    RestResult getRealTimeData(
            @PathVariable(value = "product_key", required = true) String productKey,
            @PathVariable(value = "device_name", required = true) String deviceName
    ) {
        return RestResult.getInstance(HttpResponseCode.SUCCESS, deviceService.getRealTimeData(productKey, deviceName));
    }

    /**
     * 批量设备历史文件下载
     */
    @PostMapping(value = "/device/download")
    RestResult historyDownload(
            @RequestHeader(name = "token") String userToken,
            @RequestHeader(name = "roleId") Integer roleId,
            @RequestHeader(name = "userId") Integer userId,
            @RequestHeader(name = "organizationId") Integer organizationId,
            @RequestParam(value = "batch") String batch
    ) {
        return RestResult.getInstance(HttpResponseCode.SUCCESS, deviceService.historyDownload(userId, organizationId, batch, userToken));
    }


    /**
     * 历史属性
     */
    @GetMapping(value = "/product_key/{product_key}/device/{device_name}/property")
    RestResult propertyHistory(
            @PathVariable(value = "product_key") String productKey,
            @PathVariable(value = "device_name") String deviceName,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "per_page", required = false, defaultValue = "10") Integer perPage,
            @RequestParam(value = "identifier", required = true) String identifier,
            @RequestParam(value = "start_time", required = true) String startTime,
            @RequestParam(value = "end_time", required = true) String endTime
    ) {
        return RestResult.getInstance(HttpResponseCode.SUCCESS, deviceService.propertyHistory(productKey, deviceName, page, perPage, identifier, startTime, endTime));
    }


    /**
     * 事件管理
     */
    @AuthorizationFree
    @GetMapping(value = "/product_key/{product_key}/device/{device_name}/event")
    RestResult eventManagementList(
            @PathVariable(value = "product_key") String productKey,
            @PathVariable(value = "device_name") String deviceName,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "per_page", required = false, defaultValue = "10") Integer perPage,
            @RequestParam(value = "identifier", required = false, defaultValue = "") String identifier,
            @RequestParam(value = "event_type", required = false, defaultValue = "") String eventType,
            @RequestParam(value = "start_time", required = false, defaultValue = "") String startTime,
            @RequestParam(value = "end_time", required = false, defaultValue = "") String endTime
    ) {
        return RestResult.getInstance(HttpResponseCode.SUCCESS, deviceService.eventManagementList(productKey, deviceName, page, perPage, identifier, eventType, startTime, endTime));
    }

    /**
     * 服务调用记录
     */
    @GetMapping(value = "/product_key/{product_key}/device/{device_name}/service")
    RestResult serviceTransferList(
            @PathVariable(value = "product_key") String productKey,
            @PathVariable(value = "device_name") String deviceName,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "per_page", required = false, defaultValue = "10") Integer perPage,
            @RequestParam(value = "identifier", required = false, defaultValue = "") String identifier,
            @RequestParam(value = "start_time", required = false, defaultValue = "") String startTime,
            @RequestParam(value = "end_time", required = false, defaultValue = "") String endTime
    ) {
        return RestResult.getInstance(HttpResponseCode.SUCCESS, deviceService.serviceTransferList(productKey, deviceName, page, perPage, identifier, startTime, endTime));
    }

    /**
     * 设备上下线记录
     *
     * @param productKey
     * @param deviceName
     * @param page
     * @param perPage
     * @return
     */
    @GetMapping("/product_key/{product_key}/device/{device_name}/online_record")
    RestResult deviceLogRecord(@PathVariable(value = "product_key") String productKey,
                               @PathVariable(value = "device_name") String deviceName,
                               @RequestParam(value = "page", defaultValue = "1") int page,
                               @RequestParam(value = "per_page", defaultValue = "10") int perPage) {
        return RestResult.getInstance(HttpResponseCode.SUCCESS, deviceService.deviceLogRecord(productKey, deviceName, page, perPage));
    }


    /**
     * 批量生成设备 ，获取 excel 下载地址
     *
     * @param productKey 产品key
     * @param count      生成数量
     */
    @GetMapping(value = "/device/excel")
    public RestResult excel(
            @RequestHeader(name = "roleId") Integer roleId,
            @RequestHeader(name = "userId") Integer userId,
            @RequestHeader(value = "organizationId") Integer organizationId,
            @RequestHeader(value = "ip") String ip,
            @RequestParam(value = "productKey") String productKey,
            @RequestParam(value = "deviceAlias") String deviceAlias,
            @RequestParam(value = "sum") Integer count
    ) {
        if (count > DeviceConstant.DEVICE_LIST_COUNT) {
            throw new ApiRuntimeException(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }
        return RestResult.getInstance(HttpResponseCode.SUCCESS, deviceService.deviceBatchForExcel(productKey, deviceAlias, count, userId, roleId, organizationId, ip));
    }

    /**
     * 获取属性列表
     *
     * @param paramEntity 参数
     * @return
     */
    @PostMapping(value = "/properties")
    public RestResult properties(
            @RequestHeader(name = "token") String userToken,
            @RequestHeader(name = "roleId") Integer roleId,
            @RequestHeader(name = "userId") Integer userId,
            @RequestHeader(name = "organizationId") Integer organizationId,
            @RequestHeader(name = "departmentId") Integer departmentId,
            @RequestBody(required = false) ParamEntity paramEntity
    ) {
        // 查询的设备 和 产品是否有权限
        // 1. 设备可以为空，但是为空的时候只有权限是超级管理员和产品是当前厂家且是厂家管理员
        // 2. 产品为空，只有是超级管理员才可以
        deviceService.hasOperatePermission(paramEntity, organizationId, userToken, userId);
        List<PropertyLog> commonPropertyLogList = propertyLogRepository.findCommonPropertyLog(paramEntity);
        List<JSONObject> propertyLogList = new ArrayList<>();
        for (PropertyLog propertyLog : commonPropertyLogList) {
            propertyLogList.add(propertyLog.getLogContent());
        }
        Long count = propertyLogRepository.findCommonPropertyLogCount(paramEntity);
        Map<String, Object> returnMap = new HashMap<>(2);
        returnMap.put("propertyLogList", propertyLogList);
        returnMap.put("count", count);
        return RestResult.getInstance(HttpResponseCode.SUCCESS, returnMap);
    }

    /**
     * 获取事件列表
     *
     * @param paramEntity 参数
     * @return
     */
    @PostMapping(value = "/events")
    public RestResult events(
            @RequestHeader(name = "token") String userToken,
            @RequestHeader(name = "roleId") Integer roleId,
            @RequestHeader(name = "userId") Integer userId,
            @RequestHeader(name = "organizationId") Integer organizationId,
            @RequestHeader(name = "departmentId") Integer departmentId,
            @RequestBody(required = false) ParamEntity paramEntity
    ) {
        // 查询的设备 和 产品是否 有权限 （按照当前用户权限和机构等参数判断）
        deviceService.hasOperatePermission(paramEntity, organizationId, userToken, userId);
        List<EventLog> commonEventLogList = eventLogRepository.findCommonEventLog(paramEntity);
        List<Object> eventLogList = new ArrayList<>();
        for (EventLog eventLog : commonEventLogList) {
            RestResult result = productTemplateService.parseEventData(paramEntity.getProductKey(), JSON.toJSON(eventLog.getLogContent()).toString());
            if (InterceptorConstant.HTTP_CODE_SUCCESS == result.getCode()) {
                eventLogList.add(eventLog.getLogContent());
            } else {
                Map<String, Object> eventLogErr = new HashMap<>(4);
                eventLogErr.put("data", eventLog.getLogContent().getParams());
                eventLogErr.put("params", null);
                eventLogErr.put("version", eventLog.getLogContent().getTimestamp());
                eventLogErr.put("timestamp", eventLog.getLogContent().getTimestamp());
                eventLogList.add(eventLogErr);
//                eventLogList.add(eventLog.getLogContent());
            }
        }
        Long count = eventLogRepository.findCommonEventLogCount(paramEntity);
        Map<String, Object> returnMap = new HashMap<>(2);
        returnMap.put("eventLogList", eventLogList);
        returnMap.put("count", count);
        return RestResult.getInstance(HttpResponseCode.SUCCESS, returnMap);
    }

    /**
     * 获取事件列表
     *
     * @param paramEntity 参数
     * @return
     */
    @PostMapping(value = "/alerts")
    public RestResult alert(
            @RequestHeader(name = "token") String userToken,
            @RequestHeader(name = "roleId") Integer roleId,
            @RequestHeader(name = "userId") Integer userId,
            @RequestHeader(name = "organizationId") Integer organizationId,
            @RequestHeader(name = "departmentId") Integer departmentId,
            @RequestBody(required = false) ParamEntity paramEntity
    ) {
        // 查询的设备 和 产品是否 有权限 （按照当前用户权限和机构等参数判断）
        deviceService.hasOperatePermission(paramEntity, organizationId, userToken, userId);
        List<AlarmLogEntity> commonEventLogList = alarmLogRepository.findCommonAlarmLog(paramEntity);
        List<Object> eventLogList = new ArrayList<>();
        for (AlarmLogEntity alarmLogEntity : commonEventLogList) {
            eventLogList.add(alarmLogEntity.getLogContent());
        }
        Long count = alarmLogRepository.findCommonAlarmLogCount(paramEntity);
        Map<String, Object> returnMap = new HashMap<>(2);
        returnMap.put("alertsList", eventLogList);
        returnMap.put("count", count);
        return RestResult.getInstance(HttpResponseCode.SUCCESS, returnMap);
    }

    /**
     * 获取事件列表
     *
     * @param paramEntity 参数
     * @return
     */
    @PostMapping(value = "/logs")
    public RestResult log(
            @RequestHeader(name = "token") String userToken,
            @RequestHeader(name = "roleId") Integer roleId,
            @RequestHeader(name = "userId") Integer userId,
            @RequestHeader(name = "organizationId") Integer organizationId,
            @RequestHeader(name = "departmentId") Integer departmentId,
            @RequestBody(required = false) ParamEntity paramEntity
    ) {
        // 查询的设备 和 产品是否 有权限 （按照当前用户权限和机构等参数判断）
        deviceService.hasOperatePermission(paramEntity, organizationId, userToken, userId);
        List<DeviceLogEntity> commonEventLogList = deviceLogRepository.findCommonDeviceLog(paramEntity);
        List<Object> eventLogList = new ArrayList<>();
        for (DeviceLogEntity deviceLogEntity : commonEventLogList) {
            eventLogList.add(deviceLogEntity.getLogContent());
        }
        Long count = deviceLogRepository.findCommonDeviceLogCount(paramEntity);
        Map<String, Object> returnMap = new HashMap<>(2);
        returnMap.put("logsList", eventLogList);
        returnMap.put("count", count);
        return RestResult.getInstance(HttpResponseCode.SUCCESS, returnMap);
    }


    /**
     * 更新设备基本信息
     * 设备信息更新 接口（不包含设备绑定数据）
     *
     * @return
     */
    @AuthorizationFree
    @PutMapping(value = "/device/binding/deviceSN")
    RestResult bindingDeviceSN(
            @RequestParam(value = "product_key") String productKey,
            @RequestParam(value = "device_name") String deviceName,
            @RequestParam(value = "deviceSN") String deviceSN
    ) {
        if (productKey == null || productKey.length() >= CommonConstant.DEVICE_LENGTH) {
            throw new ApiRuntimeException(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }
        if (deviceName == null || deviceName.length() >= CommonConstant.DEVICE_LENGTH) {
            throw new ApiRuntimeException(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }
        if (deviceSN == null || deviceSN.length() >= CommonConstant.DEVICE_LENGTH) {
            throw new ApiRuntimeException(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }
        Deviceinfo deviceinfo = deviceinfoMapper.selectByProductKeyAndDeviceName(productKey, deviceName);
        Deviceinfo deviceinfoUpdate = new Deviceinfo();
        deviceinfoUpdate.setDeviceTripleId(deviceinfo.getDeviceTripleId());
        deviceinfoUpdate.setDeviceSN(deviceSN);
        deviceService.putDeviceInfo(deviceinfoUpdate);
        return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
    }


    /**
     * 获取有访问改设备的角色列表
     *
     * @return
     */
    @AuthorizationFree
    @GetMapping(value = "/getDevicePermissionRoleList")
    List<Integer> getDevicePermissionRoleList(
            @RequestParam(value = "product_key") String productKey,
            @RequestParam(value = "device_name") String deviceName
    ) {
        if (productKey == null || productKey.length() >= CommonConstant.DEVICE_LENGTH) {
            return null;
        }
        if (deviceName == null || deviceName.length() >= CommonConstant.DEVICE_LENGTH) {
            return null;
        }
        return deviceService.getDevicePermissionRoleList(productKey, deviceName);
    }

    /**
     * 获取有访问改设备的角色列表
     *
     * @return
     */
    @AuthorizationFree
    @GetMapping(value = "/getDevicePermissionUserList")
    List<Integer> getDevicePermissionUserList(
            @RequestParam(value = "product_key") String productKey,
            @RequestParam(value = "device_name") String deviceName
    ) {
        if (productKey == null || productKey.length() >= CommonConstant.DEVICE_LENGTH) {
            return null;
        }
        if (deviceName == null || deviceName.length() >= CommonConstant.DEVICE_LENGTH) {
            return null;
        }
        return deviceService.getDevicePermissionUserList(productKey, deviceName);
    }


}
