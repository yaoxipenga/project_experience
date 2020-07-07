package com.medcaptain.cloud.usermanage.service;

import com.medcaptain.cloud.usermanage.entity.OperateLog;
import com.medcaptain.cloud.usermanage.entity.UserInfo;
import com.medcaptain.cloud.usermanage.entity.UserToken;
import com.medcaptain.cloud.usermanage.redis.RedisService;
import com.medcaptain.helper.HttpServletHelper;
import com.medcaptain.logging.LoggerHelper;
import com.medcaptain.logging.OperateLogLevel;
import com.medcaptain.logging.OperateLogRecord;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * 业务操作日志服务
 *
 * @author bingwen.ai
 */
@Service
public class LogService {
    @Autowired
    RedisService redisService;

    @Autowired
    OperateLogService operateLogService;

    public void logOperate(Logger logger, HttpServletRequest request, String logType, String logContent, OperateLogLevel logLevel) {
        OperateLogRecord operateLogRecord = new OperateLogRecord(logType, logContent, logLevel);
        logOperate(request, operateLogRecord, logger);
    }

    public boolean logOperate(HttpServletRequest request, OperateLogRecord operateLogRecord, Logger logger) {
        if (operateLogRecord != null) {
            int userID = 0;
            String clientIP = HttpServletHelper.getHttpClientIPAddress(request);
            String token = HttpServletHelper.getRequestToken(request);
            UserToken currentUser = redisService.getCacheUser(token);
            if (currentUser != null && currentUser.getUserInfo() != null) {
                userID = currentUser.getUserInfo().getUserId();
            }
            log(logger, userID, clientIP, operateLogRecord.getLogContent(), operateLogRecord.getLogType(), operateLogRecord.getLogLevel());
            return true;
        }
        return false;
    }

    public void logLoginSucess(Logger logger, HttpServletRequest request, UserInfo loginUser) {
        if (loginUser != null) {
            String logType = "用户登录";
            String clientIP = HttpServletHelper.getHttpClientIPAddress(request);
            String logContent = "用户登录成功,登录用户名 = " + loginUser.getUserName() + " ,机构名称 = " + loginUser.getOrganizationName() + " ,部门 = " + loginUser.getDepartmentName();
            log(logger, loginUser.getUserId(), clientIP, logContent, logType, OperateLogLevel.normal);
        }
    }

    public void logLoginFail(Logger logger, HttpServletRequest request, String userName, int userID, int organizationID) {
        String logType = "用户登录";
        String clientIP = HttpServletHelper.getHttpClientIPAddress(request);
        String logContent = "用户登录失败,登录用户名 = " + userName + ",机构编号 = " + organizationID;
        log(logger, userID, clientIP, logContent, logType, OperateLogLevel.normal);
    }

    public void logLogout(Logger logger, HttpServletRequest request, UserToken userToken) {
        if (userToken != null && userToken.getUserInfo() != null) {
            String logType = "用户登出";
            String clientIP = HttpServletHelper.getHttpClientIPAddress(request);
            UserInfo logoutUser = userToken.getUserInfo();
            String logContent = "用户登出,用户名 = " + logoutUser.getUserName() + " ,机构名称 = " + logoutUser.getOrganizationName() + " ,部门 = " + logoutUser.getDepartmentName();
            log(logger, logoutUser.getUserId(), clientIP, logContent, logType, OperateLogLevel.normal);
        }
    }

    private void log(Logger logger, int userID, String clientIP, String logContent, String logType, OperateLogLevel logLevel) {
        //TODO 用户操作日志暂时放在用户管理数据库，后续单独存放，并使用MQ发送到目标服务
        if (logger != null) {
            LoggerHelper.logOperate(logger, userID, clientIP, logContent, logType, logLevel);
        }
        OperateLog operateLog = new OperateLog(System.currentTimeMillis(), clientIP, userID, logType, logContent, (byte) logLevel.ordinal());
        operateLogService.saveLog(operateLog);
    }
}
