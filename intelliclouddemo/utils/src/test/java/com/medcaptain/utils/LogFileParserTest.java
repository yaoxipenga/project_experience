package com.medcaptain.utils;

import com.medcaptain.logging.ExecuteTimeLogCount;
import com.medcaptain.tool.LogFileParser;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class LogFileParserTest {

    @Test
    public void testParseLogFile() {
        String logFilePath = LogFileParserTest.class.getClassLoader().getResource("user-manage.log.2019-02-25.0.gz").getPath();
        LogFileParser logFileParser = new LogFileParser(logFilePath);
        Map<String, ExecuteTimeLogCount> executeTimeLogMap = logFileParser.getExecuteTimeLogCountMap();
        Map<String, Long> exceptionMap = logFileParser.getExceptionLogMap();
        Map<String, Long> operateMap = logFileParser.getOperateLogMap();
        Assert.assertEquals(executeTimeLogMap.size(),4);
        Assert.assertEquals(exceptionMap.size(),1);
        Assert.assertEquals(operateMap.size(),3);
    }
}
