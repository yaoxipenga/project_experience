package com.medcaptain.productservice.dao.mybatis;

import com.medcaptain.productservice.entity.mybatis.Runningstatus;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RunningstatusMapper {
    int deleteByPrimaryKey(Integer indexId);

    int insert(Runningstatus record);

    int insertSelective(Runningstatus record);

    Runningstatus selectByPrimaryKey(Integer indexId);

    int updateByPrimaryKeySelective(Runningstatus record);

    int updateByPrimaryKey(Runningstatus record);


    /**
     * 根据三元组获取设备运行状态
     * @param DeviceTripleId 设备三元组
     * @return 在线运行状态列表
     */
    List<Runningstatus> selectByDeviceTripleId(@Param("DeviceTripleId") Integer DeviceTripleId,
                                               @Param("page") Integer page,
                                               @Param("perPage") Integer perPage);

    /**
     * 根据三元组获取设备运行状态  最新的的一条数据
     * @param DeviceTripleId 设备三元组
     * @return 在线运行状态列表
     */
    Runningstatus selectByDeviceTripleIdNew(@Param("DeviceTripleId") Integer DeviceTripleId);

    Integer countByDeviceTripleId(@Param("deviceTripleId") Integer deviceTripleId);

}