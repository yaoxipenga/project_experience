package com.medcaptain.cloud.usermanage.mapper;

import com.medcaptain.cloud.usermanage.entity.OperateLog;
import com.medcaptain.cloud.usermanage.entity.OperateLogExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 用户操作日志SQL映射
 *
 * @author bingwen.ai
 */
@Component
public interface OperateLogMapper {
    int countByExample(OperateLogExample example);

    int deleteByExample(OperateLogExample example);

    int deleteByPrimaryKey(Integer logId);

    int insert(OperateLog record);

    int insertSelective(OperateLog record);

    List<OperateLog> selectByExampleWithBLOBs(OperateLogExample example);

    List<OperateLog> selectByExample(OperateLogExample example);

    OperateLog selectByPrimaryKey(Integer logId);

    int updateByExampleSelective(@Param("record") OperateLog record, @Param("example") OperateLogExample example);

    int updateByExampleWithBLOBs(@Param("record") OperateLog record, @Param("example") OperateLogExample example);

    int updateByExample(@Param("record") OperateLog record, @Param("example") OperateLogExample example);

    int updateByPrimaryKeySelective(OperateLog record);

    int updateByPrimaryKeyWithBLOBs(OperateLog record);

    int updateByPrimaryKey(OperateLog record);
}