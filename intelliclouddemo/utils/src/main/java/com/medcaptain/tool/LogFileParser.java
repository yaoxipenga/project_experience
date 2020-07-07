package com.medcaptain.tool;

import com.alibaba.fastjson.JSON;
import com.medcaptain.logging.*;
import com.medcaptain.utils.FileUtil;
import com.medcaptain.utils.GzipFileUtil;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 解析微服务日志
 * <p>读取微服务日志文件(压缩文件)，统计日志中的异常、方法执行用时、操作日志情况</p>
 *
 * @author bingwen.ai
 */
public class LogFileParser {
    private String logFilePath;

    private boolean fileParsed = false;

    /**
     * 方法执行时间统计信息
     */
    private Map<String, ExecuteTimeLogCount> executeTimeLogCountMap = new HashMap<>();

    /**
     * 异常次数统计
     */
    private Map<String, Long> exceptionLogMap = new HashMap<>();

    /**
     * 操作日志统计
     */
    private Map<String, Long> operateLogMap = new HashMap<>();

    public Map<String, ExecuteTimeLogCount> getExecuteTimeLogCountMap() {
        if (!fileParsed) {
            parse();
        }
        return executeTimeLogCountMap;
    }

    public Map<String, Long> getExceptionLogMap() {
        if (!fileParsed) {
            parse();
        }
        return exceptionLogMap;
    }

    public Map<String, Long> getOperateLogMap() {
        if (!fileParsed) {
            parse();
        }
        return operateLogMap;
    }

    /**
     * 初始化日志解析器
     *
     * @param logFilePath 日志文件路径
     */
    public LogFileParser(String logFilePath) {
        this.logFilePath = logFilePath;
    }

    private void parse() {
        List<String> logLines = readLogLines();
        if (logLines != null) {
            for (String logLine : logLines) {
                try {
                    parseLog(logLine);
                } catch (Exception ex) {
                }
            }
        }
        fileParsed = true;
    }

    private void parseLog(String logJSon) {
        LogRecord logRecord = JSON.parseObject(logJSon, LogRecord.class);
        if (logRecord != null) {
            String logClass = logRecord.getLogClass();
            switch (logClass) {
                case "ExecuteTimeLog": {
                    parseExecuteTimeLog(logJSon);
                    break;
                }
                case "ExceptionLog": {
                    parseExceptionLog(logJSon);
                    break;
                }
                case "OperateLog": {
                    parseOperateLog(logJSon);
                    break;
                }
                default: {
                    break;
                }
            }
        }
    }

    private void parseExecuteTimeLog(String logLine) {
        ExecuteTimeLog executeTimeLog = JSON.parseObject(logLine, ExecuteTimeLog.class);
        String className = executeTimeLog.getClassName();
        String methodName = executeTimeLog.getMethodName();
        String mapKey = className + "." + methodName;
        ExecuteTimeLogCount executeTimeLogCount = executeTimeLogCountMap.get(mapKey);
        if (executeTimeLogCount == null) {
            executeTimeLogCount = new ExecuteTimeLogCount();
            executeTimeLogCount.setClassName(className);
            executeTimeLogCount.setMethodName(methodName);
            executeTimeLogCount.setFirstExecuteTime(executeTimeLog.getExecuteStartTime());
        }
        executeTimeLogCount.setLastExecuteTime(executeTimeLog.getExecuteStartTime());
        long executeTime = executeTimeLog.getExecuteTimeInMs();
        if (executeTime > 0) {
            long finishCount = executeTimeLogCount.getFinishCount();
            long totalTime = executeTimeLogCount.getTotalExecuteTime();
            long minExecuteTime = executeTimeLogCount.getMinimumExecuteTime();
            long maxExecuteTime = executeTimeLogCount.getMaximumExecuteTime();
            executeTimeLogCount.setFinishCount(finishCount + 1);
            if (minExecuteTime == 0 || minExecuteTime > executeTime) {
                executeTimeLogCount.setMinimumExecuteTime(executeTime);
            }
            if (executeTime > maxExecuteTime) {
                executeTimeLogCount.setMaximumExecuteTime(executeTime);
            }
            executeTimeLogCount.setTotalExecuteTime(totalTime + executeTime);
        } else {
            long startCount = executeTimeLogCount.getStartCount();
            executeTimeLogCount.setStartCount(startCount + 1);
        }
        executeTimeLogCountMap.put(mapKey, executeTimeLogCount);
    }

    private void parseExceptionLog(String logLine) {
        ExceptionLog exceptionLog = JSON.parseObject(logLine, ExceptionLog.class);
        String businessDescription = exceptionLog.getBusinessDescription();
        long exceptionCount = 0;
        if (exceptionLogMap.containsKey(businessDescription)) {
            exceptionCount = exceptionLogMap.get(businessDescription);
        }
        exceptionLogMap.put(businessDescription, exceptionCount + 1);
    }

    private void parseOperateLog(String logLine) {
        OperateLog operateLog = JSON.parseObject(logLine, OperateLog.class);
        String logType = operateLog.getLogType();
        long operateCount = 0;
        if (operateLogMap.containsKey(logType)) {
            operateCount = operateLogMap.get(logType);
        }
        operateLogMap.put(logType, operateCount + 1);
    }


    private List<String> readLogLines() {
        if (StringUtils.isNotEmpty(logFilePath)) {
            boolean isCompressedFile = logFilePath.endsWith(".gz");
            List<String> logLines = isCompressedFile ? GzipFileUtil.readCompressLines(logFilePath) : FileUtil.lines(new File(logFilePath));
            if (logLines != null) {
                List<String> filteredLogLines = new ArrayList<>();
                for (String logLine : logLines) {
                    if (logLine.endsWith("}")) {
                        int prefixPosition = logLine.indexOf("{");
                        String filteredLine = logLine.substring(prefixPosition);
                        filteredLogLines.add(filteredLine);
                    }
                }
                return filteredLogLines;
            }
        }
        return null;
    }
}
