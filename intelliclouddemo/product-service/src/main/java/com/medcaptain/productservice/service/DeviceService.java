package com.medcaptain.productservice.service;

import com.medcaptain.productservice.entity.dto.DeviceDepartmentUnbindEntity;
import com.medcaptain.productservice.entity.dto.DeviceDtoEntity;
import com.medcaptain.productservice.entity.dto.ParamEntity;
import com.medcaptain.productservice.entity.mybatis.Deviceinfo;
import com.medcaptain.productservice.enums.DeviceBatchTypeEnum;
import com.medcaptain.productservice.enums.DeviceListEnum;

import java.util.List;
import java.util.Map;

public interface DeviceService {

    /***
     * 事件管理列表
     * @param productKey
     * @param deviceName
     * @param page
     * @param perPage
     * @param identifier
     * @param eventType
     * @param startTime
     * @param endTime
     * @return
     */
    Map eventManagementList(String productKey, String deviceName, Integer page, Integer perPage, String identifier, String eventType, String startTime, String endTime);

    /***
     * 服务调用列表
     * @param productKey
     * @param deviceName
     * @param page
     * @param perPage
     * @param identifier
     * @param startTime
     * @param endTime
     * @return
     */
    Map serviceTransferList(String productKey, String deviceName, Integer page, Integer perPage, String identifier, String startTime, String endTime);

    /***
     * 属性历史记录
     * @param productKey
     * @param deviceName
     * @param page
     * @param perPage
     * @param identifier
     * @param startTime
     * @param endTime
     * @return
     */
    Map propertyHistory(String productKey, String deviceName, Integer page, Integer perPage, String identifier, String startTime, String endTime);

    /***
     * 实时数据
     * @param productKey
     * @param deviceName
     * @return
     */
    Map getRealTimeData(String productKey, String deviceName);


    /**
     * 根据用户权限，查询设备列表
     *
     * @param userToken
     * @param organizationId
     * @param userId
     * @param deviceListEnum
     * @return
     */
    List<Map> getDeviceInfoListDeviceInfoListV2(
            String userToken,
            int organizationId,
            int userId,
            DeviceListEnum deviceListEnum
    );

    /**
     * 根据用户权限，查询设备列表
     *
     * @param userToken
     * @param organizationId
     * @param userId
     * @param productKey
     * @param deviceName
     * @return
     */
    Integer getDeviceInfoListDeviceInfoCountV2(
            String userToken,
            int organizationId,
            int userId,
            DeviceListEnum deviceListEnum
    );

    /**
     * 添加设备
     *
     * @param deviceDtoEntity 设备实体
     * @return
     */
    Object addDevice(DeviceDtoEntity deviceDtoEntity, String deviceName, Integer roleId, Integer organizationId);

    /**
     * 删除设备，软删除
     * 所指设备必须属于当前医院的产品下的设备
     *
     * @param deviceNameList        和 productKeyList 一一对应一个设备
     * @param productKeyList        和 deviceNameList 一一对应一个设备
     * @param organizationId        操作角色的 organizationId
     * @param userId                操作角色的 userId
     * @param operatePermissionCode 操作角色的权限 operatePermissionCode
     * @return
     */
    void deviceBatch(
            String[] deviceNameList,
            String[] productKeyList,
            Integer organizationId,
            Integer userId,
            int operatePermissionCode,
            DeviceBatchTypeEnum type
    );

    /***
     * 修改设备信息
     * @return 设备信息
     */
    void putDeviceInfo(Deviceinfo deviceinfo);

    /***
     * 获取设备信息（超级管理员，可以查询平台上未删除的设备信息）
     * @param productKey 产品key
     * @param deviceName 设备name
     * @return 设备信息
     */
    Object getDeviceInfo(String productKey, String deviceName);

    /***
     * 获取设备信息（机构管理员，可以查询机构创建的产品且设备未删除的信息）
     * @param productKey 产品key
     * @param deviceName 设备name
     * @return 设备信息
     */
    Object getDeviceInfo(String productKey, String deviceName, Integer organizationId);

    /***
     * 获取设备信息（普通人，只能查询自己创建的设备）
     * @param productKey 产品key
     * @param deviceName 设备name
     * @return 设备信息
     */
    Object getDeviceInfo(String productKey, String deviceName, Integer organizationId, Integer userId);

    /**
     * 角色绑定设备
     *
     * @param productKey 产品key
     * @param deviceName 设备name
     * @param roleid     角色id
     * @return 成功否
     */
    Object getDeviceRole(String productKey, String deviceName, Integer roleid);

    /**
     * 批量生成设备，并上传 excel，返回链接以供下载
     *
     * @param productKey     设备key
     * @param sum            生成数量
     * @param userId         用户id
     * @param roleId         角色id
     * @param organizationId 机构id
     * @return
     */
    Object deviceBatchForExcel(String productKey, String deviceAlias, Integer sum, Integer userId, Integer roleId, Integer organizationId, String ip);

    /**
     * 设备历史记录列表
     *
     * @param organizationId
     */
    Object history(Integer userId, Integer organizationId, String userToken);

    /**
     * 设备历史记录获取下载地址
     *
     * @param userId         用户 id
     * @param organizationId 机构id
     * @param batch          文件id
     * @return
     */
    Object historyDownload(Integer userId, Integer organizationId, String batch, String userToken);


    /**
     * 将设备授权到某个医院，并添加一批改医院下的部门查询权限
     *
     * @param deviceinfo      设备
     * @param organizationId  绑定到某个医院，自行检查医院是否窜
     * @param departmentId    绑定的科室，自行检查科室是否存在
     * @param departmentIdAry 有查看权限的部门 id 数组，自行检查部门科室是否存在
     * @return
     */
    void deviceBindingDepartmentBatch(
            Deviceinfo deviceinfo,
            Integer organizationId,
            Integer departmentId,
            List<Integer> departmentIdAry
    );

    /**
     * 解绑 设备和科室的关联（厂家）
     *
     * @param organizationId         医院id
     * @param deviceDepartmentEntity 设备集合
     */
    void untiedDeviceDepartmentBatch(Integer organizationId, DeviceDepartmentUnbindEntity[] deviceDepartmentEntity);

    /**
     * 解绑 设备和科室的关联（超级管理员）
     *
     * @param deviceDepartmentEntity 设备集合
     */
    void untiedDeviceDepartmentBatch(DeviceDepartmentUnbindEntity[] deviceDepartmentEntity);

    /**
     * @param paramEntity
     * @param organizationId
     * @param userToken
     * @param userId
     * @return
     */
    void hasOperatePermission(ParamEntity paramEntity, Integer organizationId, String userToken, Integer userId);

    /**
     * 设备上下线记录
     *
     * @param productKey
     * @param deviceName
     * @return
     */
    Map deviceLogRecord(String productKey, String deviceName, int page, int perPage);


    /***
     * 获取有访问改设备的角色列表
     * @return 角色id列表
     */
    List<Integer> getDevicePermissionRoleList(String productKey, String deviceName);

    /***
     * 获取有访问改设备的角色列表
     * @return 角色id列表
     */
    List<Integer> getDevicePermissionUserList(String productKey, String deviceName);

}
