package com.medcaptain.productservice.dao.mongo;

import com.medcaptain.productservice.entity.dto.ParamEntity;
import com.medcaptain.productservice.entity.dto.mongo.DeviceLogEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 设备日志
 *
 * @author yangzhixiong
 */
@Repository
public interface DeviceLogRepository extends MongoRepository<DeviceLogEntity, Long> {

    /**
     * 通用设备日志查询
     */
    List<DeviceLogEntity> findCommonDeviceLog(ParamEntity paramEntity);

    /**
     * 通用设备日志查询
     */
    Long findCommonDeviceLogCount(ParamEntity paramEntity);

}
