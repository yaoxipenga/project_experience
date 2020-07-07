package com.medcaptain.datastatistics.service.impl;

import com.medcaptain.datastatistics.dao.StatisticsScriptMapper;
import com.medcaptain.datastatistics.entity.StatisticsScript;
import com.medcaptain.datastatistics.entity.StatisticsScriptExample;
import com.medcaptain.datastatistics.service.StatisticsScriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 统计脚本服务实现
 *
 * @author bingwen.ai
 */
@Service
public class StatisticsScriptServiceImpl implements StatisticsScriptService {
    @Autowired
    StatisticsScriptMapper statisticsScriptMapper;

    @Override
    public boolean addScript(String scriptName, String scriptContent, String databaseType, String remark) {
        try {
            StatisticsScript script = new StatisticsScript();
            script.setScriptName(scriptName);
            script.setDatabaseType(databaseType);
            script.setScriptContent(scriptContent);
            script.setRemark(remark);
            return statisticsScriptMapper.insertSelective(script) > 0;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public StatisticsScript getScript(String scriptName) {
        StatisticsScriptExample example = new StatisticsScriptExample();
        example.createCriteria().andScriptNameEqualTo(scriptName).andIsDeletedEqualTo((byte) 0).andIsEnableEqualTo((byte) 1);
        List<StatisticsScript> scriptList = statisticsScriptMapper.selectByExample(example);
        if (scriptList != null && scriptList.size() > 0) {
            return scriptList.get(0);
        }
        return null;
    }
}
