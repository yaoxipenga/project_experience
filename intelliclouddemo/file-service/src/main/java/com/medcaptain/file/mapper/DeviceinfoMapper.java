package com.medcaptain.file.mapper;

import com.medcaptain.file.entity.mysql.Deviceinfo;
import org.springframework.data.repository.query.Param;
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

//    /**
//     * 查询当前角色下的有权限访问的全部设备
//     *
//     * @param roleId  角色id
//     * @param page    分页
//     * @param perPage 分页
//     * @param userId  用户id
//     * @return 设备列表
//     */
//    List<Deviceinfo> selectByRoleIdPage(@Param("roleId") Integer roleId,
//                                        @Param("page") Integer page,
//                                        @Param("perPage") Integer perPage,
//                                        @Param("userId") Integer userId,
//                                        @Param("productKey") String productKey,
//                                        @Param("deviceName") String deviceName
//    );


    /**
     * 查询当前厂家下产品中全部未删除设备
     * TODO 开发后删除 `remark` is null 标志位
     *
     * @param page           分页
     * @param perPage        分页
     * @param organizationId 需要查询厂家的id
     * @return 设备列表
     */
    List<Deviceinfo> selectByRoleIdPage(@Param("page") Integer page,
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
    int selectByRoleIdCount(@Param("organizationId") Integer organizationId,
                            @Param("productKey") String productKey,
                            @Param("deviceName") String deviceName
    );

    /**
     * 使用产品key和设备的名称  查询  设备
     * 包括已删除的设备
     * TODO
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
    Deviceinfo selectByProductKeyAndDeviceName(@Param("productKey") String productKey, @Param("deviceName") String deviceName);

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