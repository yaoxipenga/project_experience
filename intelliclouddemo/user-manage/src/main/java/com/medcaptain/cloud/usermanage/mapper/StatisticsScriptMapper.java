package com.medcaptain.cloud.usermanage.mapper;

import com.medcaptain.cloud.usermanage.entity.StatisticsScript;
import com.medcaptain.cloud.usermanage.entity.StatisticsScriptExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 统计脚本查询SQL映射器
 *
 * @author bingwen.ai
 */
@Component
public interface StatisticsScriptMapper {
    int countByExample(StatisticsScriptExample example);

    int deleteByExample(StatisticsScriptExample example);

    int deleteByPrimaryKey(String scriptName);

    int insert(StatisticsScript record);

    int insertSelective(StatisticsScript record);

    List<StatisticsScript> selectByExample(StatisticsScriptExample example);

    StatisticsScript selectByPrimaryKey(String scriptName);

    int updateByExampleSelective(@Param("record") StatisticsScript record, @Param("example") StatisticsScriptExample example);

    int updateByExample(@Param("record") StatisticsScript record, @Param("example") StatisticsScriptExample example);

    int updateByPrimaryKeySelective(StatisticsScript record);

    int updateByPrimaryKey(StatisticsScript record);
}