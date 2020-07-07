package com.medcaptain.productservice.dao.mybatis;

import com.medcaptain.productservice.entity.mybatis.Deviceinfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface DeviceinfoMapper {
    Integer getDeviceTripleId(String productKey, String deviceName);

    Map<String, String> getProductKeyAndDeviceName(Integer deviceTripleId);

    String getProductKey(Integer deviceTripleId);

    int deleteByProductKey(String productKey);

    int deleteByPrimaryKey(Integer deviceTripleId);

    int insert(Deviceinfo record);

    int insertSelective(Deviceinfo record);

    Deviceinfo selectByPrimaryKey(Integer deviceTripleId);

    int updateByPrimaryKeySelective(Deviceinfo record);

    int updateByPrimaryKey(Deviceinfo record);


    /**
     * 获取未删除的设备
     *
     * @param deviceTripleId 主键，设备三元组id
     * @return 实体
     */
    Deviceinfo selectBydeviceTripleId(@Param("deviceTripleId") Integer deviceTripleId);

    /**
     * 查询产品下设备总数
     *
     * @param productKey 设备key
     * @param isEnabled  是否激活（0未激活  1已激活）  空查询全部
     * @return 设备数量
     */
    int selectByProductCount(@Param("productKey") String productKey, @Param("isEnabled") Integer isEnabled);

    /**
     * 查询当前厂家下产品中全部未删除设备
     *
     * @param page           分页
     * @param perPage        分页
     * @param organizationId 需要查询厂家的id
     * @param userId         创建用户的id
     * @param productKey     产品key
     * @param deviceName     设备name
     * @return 设备列表
     */
    List<Map> selectByRoleIdPage(
            @Param("page") Integer page,
            @Param("perPage") Integer perPage,
            @Param("organizationId") Integer organizationId,
            @Param("userId") Integer userId,
            @Param("productKey") String productKey,
            @Param("deviceName") String deviceName
    );

    /**
     * 查询当前厂家下产品中全部设备数量
     *
     * @param organizationId 需要查询厂家的id
     * @return 数量
     */
    int selectByRoleIdCount(
            @Param("organizationId") Integer organizationId,
            @Param("userId") Integer userId,
            @Param("productKey") String productKey,
            @Param("deviceName") String deviceName
    );


    /**
     * 获取厂家的指定设备
     *
     * @param productKey
     * @param deviceName
     * @param organizationId
     * @return
     */
    Deviceinfo selectByOrganizationAndPrimaryKey(
            @Param("productKey") String productKey,
            @Param("deviceName") String deviceName,
            @Param(value = "organizationId") Integer organizationId
    );

    /**
     * 查询当前厂家下产品中全部未删除设备
     *
     * @param page               分页
     * @param perPage            分页
     * @param organizationId     需要查询厂家的id
     * @param userId             用户id
     * @param productKeyList     查询的设备列表
     * @param deviceNameList     查询的设备列表
     * @param provinceId         查询设备归属城市
     * @param onlineStatus       是否在线 0 不在线  1 在线
     * @param isEnabled          是否禁用  0 未禁用   1禁用
     * @param isDistribution     是否已分配   0 未分配    1 分配
     * @param searchDevice       搜索字段 sn 和 key
     * @param organizationIdList
     * @param departmentIdList
     * @return 设备列表
     */
    List<Map> selectByOrganizationId(
            @Param("page") Integer page,
            @Param("perPage") Integer perPage,
            @Param("organizationId") Integer organizationId,
            @Param("userId") Integer userId,
            @Param("productKeyList") List<String> productKeyList,
            @Param("deviceNameList") List<String> deviceNameList,
            @Param("provinceIdList") List<Integer> provinceId,
            @Param("onlineStatus") Integer onlineStatus,
            @Param("isEnabled") Integer isEnabled,
            @Param("isDistribution") Integer isDistribution,
            @Param("searchDevice") String searchDevice,
            @Param("organizationIdList") List<Integer> organizationIdList,
            @Param("departmentIdList") List<Integer> departmentIdList

    );

    /**
     * 查询当前厂家下产品中全部设备数量
     *
     * @param organizationId 需要查询厂家的id
     * @param userId
     * @param productKeyList
     * @param deviceNameList
     * @param provinceId
     * @param onlineStatus
     * @param isEnabled
     * @param isDistribution
     * @return 数量
     */
    int selectByOrganizationIdCount(
            @Param("organizationId") Integer organizationId,
            @Param("userId") Integer userId,
            @Param("productKeyList") List<String> productKeyList,
            @Param("deviceNameList") List<String> deviceNameList,
            @Param("provinceIdList") List<Integer> provinceId,
            @Param("onlineStatus") Integer onlineStatus,
            @Param("isEnabled") Integer isEnabled,
            @Param("isDistribution") Integer isDistribution,
            @Param("searchDevice") String searchDevice,
            @Param("organizationIdList") List<Integer> organizationIdList,
            @Param("departmentIdList") List<Integer> departmentIdList
    );

    /**
     * 使用产品key和设备的名称  查询  设备
     * 包括已删除的设备
     *
     * @param productKey 设备key
     * @param deviceName 产品名称
     * @return 设备
     */
    List<Deviceinfo> selectByProductKeyAndDeviceNameIsNotDel(@Param("productKey") String productKey, @Param("deviceName") String deviceName);

    /**
     * 获取未删除的设备
     *
     * @param deviceName 索引，设备name
     * @return 实体
     */
    Deviceinfo selectByProductKeyAndDeviceName(
            @Param("productKey") String productKey,
            @Param("deviceName") String deviceName
    );

    /**
     * 获取指定用户创建的设备
     *
     * @param userId
     * @return 实体
     */
    Deviceinfo selectByUser(
            @Param("userId") Integer userId,
            @Param("productKey") String productKey,
            @Param("deviceName") String deviceName
    );

    Integer selectByProductKeyAndDel(
            @Param("productKey") String productKey,
            @Param("del") Integer del
    );

    /**
     * 删除设备 ， 软删除
     * 1 表示删除  0 表示没删除
     *
     * @param deviceTripleId 设备三元组id
     * @return 1
     */
    int deleteByIsDelKey(Integer deviceTripleId);

}