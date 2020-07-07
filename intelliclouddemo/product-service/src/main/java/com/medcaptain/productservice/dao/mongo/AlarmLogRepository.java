package com.medcaptain.productservice.dao.mongo;

import com.medcaptain.productservice.entity.dto.ParamEntity;
import com.medcaptain.productservice.entity.dto.mongo.AlarmLogEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 设备告警
 *
 * @author yangzhixiong
 */
@Repository
public interface AlarmLogRepository extends MongoRepository<AlarmLogEntity, Long> {

    /**
     * 通用设备告警查询
     */
    List<AlarmLogEntity> findCommonAlarmLog(ParamEntity paramEntity);

    /**
     * 通用设备告警查询
     */
    Long findCommonAlarmLogCount(ParamEntity paramEntity);

}
