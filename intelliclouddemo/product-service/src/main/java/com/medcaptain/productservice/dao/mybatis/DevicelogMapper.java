package com.medcaptain.productservice.dao.mybatis;

import com.medcaptain.productservice.entity.mybatis.Devicelog;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DevicelogMapper {
    int deleteByPrimaryKey(Integer deviceLogId);

    int insert(Devicelog record);

    int insertSelective(Devicelog record);

    Devicelog selectByPrimaryKey(Integer deviceLogId);

    int updateByPrimaryKeySelective(Devicelog record);

    int updateByPrimaryKeyWithBLOBs(Devicelog record);

    int updateByPrimaryKey(Devicelog record);


    /**
     * 获取指定 topic 下全部日志
     *
     * @param topic topic
     * @return 设备日志列表
     */
    List<Devicelog> selectByTopic(@Param("topic") String topic);

    /**
     * 获取指定 topic 下全部日志的数量
     * <p>
     * 注意该 topic 参数必须经过校验，使用 TopicUtil.analysisTopic() 方法
     *
     * @param topic topic
     * @return 数量
     */
    Integer selectByTopicCount(String topic);

    List<Devicelog> selectByKeyAndName(String productKey,Integer deviceId, Integer start, Integer size);

    Integer countByProductKeyAndDeviceName(String productKey,Integer deviceId);

    /**
     * 插入日志记录
     * @param productKey
     * @param deviceName
     * @param topic
     * @param data
     * @return
     */
    Integer insertLog(@org.apache.ibatis.annotations.Param("productKey")String productKey,
                      @org.apache.ibatis.annotations.Param("deviceName")String deviceName,
                      @org.apache.ibatis.annotations.Param("topic")String topic,
                      @org.apache.ibatis.annotations.Param("data")String data);

}