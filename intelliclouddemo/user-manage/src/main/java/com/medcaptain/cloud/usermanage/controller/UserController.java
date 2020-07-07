package com.medcaptain.cloud.usermanage.controller;


import com.github.pagehelper.PageInfo;
import com.medcaptain.annotation.AuthorizationFree;
import com.medcaptain.annotation.Log;
import com.medcaptain.cloud.usermanage.constant.FrontendResourceType;
import com.medcaptain.cloud.usermanage.constant.UserConstant;
import com.medcaptain.cloud.usermanage.entity.FrontendResource;
import com.medcaptain.cloud.usermanage.entity.RoleInfo;
import com.medcaptain.cloud.usermanage.entity.UserInfo;
import com.medcaptain.cloud.usermanage.entity.UserToken;
import com.medcaptain.cloud.usermanage.redis.RedisService;
import com.medcaptain.cloud.usermanage.service.*;
import com.medcaptain.dto.RestResult;
import com.medcaptain.enums.HttpResponseCode;
import com.medcaptain.enums.OperatePermissionEnum;
import com.medcaptain.helper.HttpServletHelper;
import com.medcaptain.logging.ExceptionLog;
import com.medcaptain.logging.OperateLogLevel;
import com.medcaptain.utils.IntegerUtil;
import com.medcaptain.utils.RegUtil;
import com.medcaptain.utils.UUIDUtil;
import com.medcaptain.utils.encrypt.AESUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.medcaptain.cloud.usermanage.constant.UserConstant.*;

/**
 * 用户相关资源
 *
 * @author yangzhixiong
 */
@RestController
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    RoleService roleService;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    OrganizationService organizationService;

    @Autowired
    CaptchaController captchaController;

    @Autowired
    FrontendResourceService frontendResourceService;

    @Autowired
    LogService logService;

    @Autowired
    OrganizationDomainService organizationDomainService;

    @AuthorizationFree
    @Log(logParameterNames = "userName")
    @PostMapping(value = "/login")
    public RestResult login(HttpServletRequest request, HttpServletResponse response,
                            @RequestParam(value = "userName") String userName,
                            @RequestHeader(value = "host") String requestHost) {
        String password = request.getParameter("password");
        int organizationID = organizationDomainService.getOrganizationID(requestHost);
        try {
            if (organizationID <= 0) {
                return RestResult.getInstance(HttpResponseCode.ERROR_DOMAIN_NOT_BINDING, null);
            }
            if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {
                return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
            }
            String clientID = HttpServletHelper.getClientID(request, response);
            boolean needVerifyCaptcha = redisService.getLoginFailTimes(clientID) > MAX_LOGIN_FAILURE_TIMES
                    && !captchaController.isVerifyCodeMatch(request);
            if (needVerifyCaptcha) {
                Map<String, Boolean> showCaptcha = new HashMap<>(1);
                showCaptcha.put("showCaptcha", true);
                return RestResult.getInstance(HttpResponseCode.ERROR_VERIFY_CODE, showCaptcha);
            }
            List<UserInfo> userInfoList = userService.login(organizationID, userName, password);
            if (userInfoList != null && userInfoList.size() == 1) {
                UserInfo userInfo = userInfoList.get(0);
                return loginSuccess(userInfo, clientID, request);
            } else {
                return loginFail(userName, clientID, organizationID, request);
            }
        } catch (Exception ex) {
            HashMap<String, Object> parameters = new HashMap<>(3);
            parameters.put("userName", userName);
            parameters.put("organizationID", organizationID);
            parameters.put("requestHost", requestHost);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "用户登录", parameters);
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }

    /**
     * 用户登出
     *
     * @param request HTTP请求
     * @return 处理结果
     */
    @AuthorizationFree
    @PostMapping(value = "/logout")
    public RestResult logout(HttpServletRequest request, HttpServletResponse response) {
        String token = HttpServletHelper.getRequestToken(request);
        String clientID = HttpServletHelper.getClientID(request, response);
        if (StringUtils.isBlank(token) || StringUtils.isBlank(clientID)) {
            return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }
        try {
            UserToken userToken = redisService.getCacheUser(token);
            int userID = userToken == null ? 0 : userToken.getUserID();
            boolean hasRemoveTokenAndSetUserOffline = userID == 0
                    || (redisService.removeToken(token) && userService.updateStatus(userID, false));
            if (hasRemoveTokenAndSetUserOffline) {
                logService.logLogout(logger, request, userToken);
                return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
            } else {
                return RestResult.getInstance(HttpResponseCode.ERROR, null);
            }
        } catch (Exception ex) {
            HashMap<String, Object> parameters = new HashMap<>(2);
            parameters.put("token", token);
            parameters.put("clientID", clientID);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "用户登出", parameters);
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }

    @Log
    @Transactional(rollbackFor = Exception.class)
    @PostMapping(value = "/user")
    public RestResult addUser(HttpServletRequest request) {
        // TODO [重要] 考虑高并发冲突，同一机构添加相同用户名
        String userName = request.getParameter("userName");
        if (!isUserNameLegal(userName)) {
            return RestResult.getInstance(400, ILLEGAL_PASSWORD_NOTICE, null);
        }
        String password = request.getParameter("password");
        if (!isPasswordLegal(password)) {
            return RestResult.getInstance(400, ILLEGAL_USERNAME_NOTICE, null);
        }
        int roleID = IntegerUtil.tryParse(request.getParameter("roleId"), 0);
        int departmentID = IntegerUtil.tryParse(request.getParameter("departmentId"), 0);
        String email = request.getParameter("email");
        if (StringUtils.isBlank(userName) || roleID <= 0 || departmentID <= 0 || StringUtils.isBlank(email)) {
            return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }
        RoleInfo roleInfo = roleService.getRole(roleID);
        if (roleInfo == null || !departmentService.exist(departmentID)) {
            return RestResult.getInstance(HttpResponseCode.RESOURCE_NOT_FOUND, null);
        }
        if (userService.exist(roleInfo.getOrganizationId(), userName)) {
            return RestResult.getInstance(HttpResponseCode.ERROR_RESOURCE_ALREADY_EXIST, null);
        }
        String nickName = request.getParameter("nickname");
        String gender = request.getParameter("gender");
        String icon = request.getParameter("icon");

        try {
            if (userService.addUser(roleID, departmentID, userName, password, nickName, gender, icon, email)) {
                String logContent = "用户名称 = " + userName + " , 角色ID = " + roleID;
                logService.logOperate(logger, request, "新增用户", logContent, OperateLogLevel.normal);
                return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
            } else {
                return RestResult.getInstance(HttpResponseCode.ERROR, null);
            }
        } catch (Exception ex) {
            HashMap<String, Object> parameters = new HashMap<>(7);
            parameters.put("userName", userName);
            parameters.put("roleID", roleID);
            parameters.put("departmentID", departmentID);
            parameters.put("nickName", nickName);
            parameters.put("gender", gender);
            parameters.put("icon", icon);
            parameters.put("email", email);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "新增用户", parameters);
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }

    @GetMapping(value = "/user/name/{organizationId}/{userName}")
    public RestResult checkUserName(@PathVariable(value = "organizationId") int organizationID,
                                    @PathVariable(value = "userName") String userName) {
        try {
            if (organizationID <= 0 || StringUtils.isBlank(userName)) {
                return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
            }
            if (!isUserNameLegal(userName)) {
                return RestResult.getInstance(400, ILLEGAL_PASSWORD_NOTICE, null);
            }
            return RestResult.getInstance(HttpResponseCode.SUCCESS, userService.exist(organizationID, userName));
        } catch (Exception ex) {
            ExceptionLog exceptionLog = new ExceptionLog(ex, "检查用户名");
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }

    @Log(logParameterNames = "userID")
    @GetMapping(value = "/user/{userId}")
    public RestResult getUserInfo(@PathVariable(value = "userId") int userID, HttpServletRequest request) {
        try {
            if (userID <= 0) {
                return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
            }
            String token = HttpServletHelper.getRequestToken(request);
            UserInfo userInfo = getUserInfoWithPermissionCheck(userID, token);
            if (userInfo == null) {
                return RestResult.getInstance(HttpResponseCode.RESOURCE_NOT_FOUND, null);
            } else {
                return RestResult.getInstance(HttpResponseCode.SUCCESS, userInfo);
            }
        } catch (Exception ex) {
            HashMap<String, Object> parameters = new HashMap<>(1);
            parameters.put("userID", userID);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "获取用户信息", parameters);
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }

    private UserInfo getUserInfoWithPermissionCheck(int userID, String currentUserToken) {
        UserInfo userInfo = userService.getUserInfo(userID);
        if (userInfo != null) {
            userInfo.setPassword(null);
        }
        UserInfo currentUser = userService.getCurrentUser(currentUserToken);
        if (currentUser == null) {
            return null;
        }
        int organizationID = currentUser.getOrganizationId();
        int departmentID = currentUser.getDepartmentId();
        boolean managePlatform = redisService.hasOperatePermission(currentUser.getRoleId(), OperatePermissionEnum.PLATFORM_MANAGE.getMsg());
        boolean manageOrganization = redisService.hasOperatePermission(currentUser.getRoleId(), OperatePermissionEnum.ORGANIZATION_MANAGE.getMsg());
        if (!managePlatform && userInfo != null) {
            if (manageOrganization) {
                if (organizationID != userInfo.getOrganizationId()) {
                    userInfo = null;
                }
            } else {
                if (departmentID != userInfo.getDepartmentId()) {
                    userInfo = null;
                }
            }
        }
        return userInfo;
    }

    @Log(logParameterNames = "userID")
    @DeleteMapping(value = "/user/{userId}")
    public RestResult deleteUser(@PathVariable(value = "userId") int userID, HttpServletRequest request) {
        try {
            if (userService.deleteUser(userID)) {
                redisService.removeToken(userID);
                logService.logOperate(logger, request, "用户ID：" + userID, "删除用户", OperateLogLevel.normal);
                return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
            } else {
                return RestResult.getInstance(HttpResponseCode.ERROR, null);
            }
        } catch (Exception ex) {
            HashMap<String, Object> parameters = new HashMap<>(1);
            parameters.put("userID", userID);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "删除用户信息", parameters);
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }

    @Log
    @PutMapping(value = "/user")
    public RestResult updateUserInfo(HttpServletRequest request) {
        try {
            UserInfo userInfo = new UserInfo();
            RestResult validateResult = validateUpdateParameter(request, userInfo);
            if (validateResult == null) {
                if (userService.updateUserInfo(userInfo)) {
                    userInfo.setPassword(null);
                    logService.logOperate(logger, request, "更新用户信息", userInfo.getUserId().toString(), OperateLogLevel.critical);
                    return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
                } else {
                    return RestResult.getInstance(HttpResponseCode.ERROR, null);
                }
            }
            return validateResult;
        } catch (Exception ex) {
            ExceptionLog exceptionLog = new ExceptionLog(ex, "更新用户信息");
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }

    /**
     * 验证更新用户信息的输入
     */
    private RestResult validateUpdateParameter(HttpServletRequest request, UserInfo userInfo) {
        String nickName = request.getParameter("nickname");
        String gender = request.getParameter("gender");
        String icon = request.getParameter("icon");
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String enable = request.getParameter("enable");
        String email = request.getParameter("email");
        if (!StringUtils.isEmpty(enable)) {
            if (!ENABLE_INT.equals(enable) && !DISABLE_INT.equals(enable)) {
                return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
            }
        }

        int userID = IntegerUtil.tryParse(request.getParameter("userId"), 0);
        int roleID = IntegerUtil.tryParse(request.getParameter("roleId"), 0);
        int departmentID = IntegerUtil.tryParse(request.getParameter("departmentId"), 0);
        if (userID <= 0) {
            return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }
        if (!userService.exist(userID)) {
            return RestResult.getInstance(HttpResponseCode.RESOURCE_NOT_FOUND, null);
        }
        userInfo.setUserId(userID);
        if (roleID > 0) {
            if (roleService.exist(roleID)) {
                userInfo.setRoleId(roleID);
            } else {
                return RestResult.getInstance(HttpResponseCode.RESOURCE_NOT_FOUND, null);
            }
        }

        if (departmentID > 0) {
            if (departmentService.exist(departmentID)) {
                userInfo.setDepartmentId(departmentID);
            } else {
                return RestResult.getInstance(HttpResponseCode.RESOURCE_NOT_FOUND, null);
            }
        }
        if (nickName != null) {
            userInfo.setNickname(nickName);
        }
        if (gender != null) {
            userInfo.setGender(gender);
        }
        if (icon != null) {
            userInfo.setIcon(icon);
        }
        if (email != null) {
            userInfo.setEmail(email);
        }
        if (StringUtils.isNotBlank(oldPassword)) {
            RestResult validResult = validateChangePassword(userID, userInfo, oldPassword, newPassword);
            if (validResult != null) {
                return validResult;
            }
        }

        int status = IntegerUtil.tryParse(request.getParameter("status"), -1);
        if (status >= 0) {
            userInfo.setStatus(status);
        }

        if (enable != null) {
            if (ENABLE_INT.equals(enable)) {
                userInfo.setIsEnable((byte) 1);
            } else if (DISABLE_INT.equals(enable)) {
                userInfo.setIsEnable((byte) 0);
            }
        }
        return null;
    }

    @Log
    @GetMapping(value = "/users")
    public RestResult listUsers(HttpServletRequest request) {
        int pageIndex = IntegerUtil.tryParse(request.getParameter("page"), 0);
        int pageSize = IntegerUtil.tryParse(request.getParameter("per_page"), 10);
        boolean onlineOnly = IntegerUtil.tryParse(request.getParameter("onlineOnly"), 0) > 0;
        int organizationID = IntegerUtil.tryParse(request.getParameter("organizationId"), 0);
        int departmentID = IntegerUtil.tryParse(request.getParameter("departmentId"), 0);
        if (pageIndex < 0 || pageSize <= 0 || organizationID < 0 || departmentID < 0) {
            return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }
        String token = HttpServletHelper.getRequestToken(request);
        UserInfo currentUser = userService.getCurrentUser(token);
        if (currentUser == null) {
            return RestResult.getInstance(HttpResponseCode.CURRENT_USER_NOT_FOUND, null);
        }
        boolean canManagePlatform = redisService.hasOperatePermission(currentUser.getRoleId(),
                OperatePermissionEnum.PLATFORM_MANAGE.getMsg());
        boolean canManageOrganization = redisService.hasOperatePermission(currentUser.getRoleId(),
                OperatePermissionEnum.ORGANIZATION_MANAGE.getMsg());
        if (!canManagePlatform) {
            organizationID = currentUser.getOrganizationId();
            if (!canManageOrganization) {
                departmentID = currentUser.getDepartmentId();
            }
        }

        try {
            String nickname = request.getParameter("nickname");
            String roleName = request.getParameter("roleName");
            String departmentName = request.getParameter("departmentName");
            PageInfo<UserInfo> users = userService.listUsers(organizationID, departmentID, currentUser.getUserId(),
                    pageIndex, pageSize, onlineOnly, nickname, roleName, departmentName);
            return RestResult.getInstance(HttpResponseCode.SUCCESS, users);
        } catch (Exception ex) {
            HashMap<String, Object> parameters = new HashMap<>(8);
            parameters.put("pageIndex", pageIndex);
            parameters.put("pageSize", pageSize);
            parameters.put("onlineOnly", onlineOnly);
            parameters.put("organizationID", organizationID);
            parameters.put("departmentID", departmentID);
            parameters.put("token", token);
            parameters.put("currentUser", currentUser.getUserId());
            ExceptionLog exceptionLog = new ExceptionLog(ex, "获取用户列表", parameters);
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }

    /**
     * 初始化(重置)用户密码
     *
     * @param resetPasswordToken 初始token
     * @param userName           用户登录名
     * @param password           新的密码
     * @param request            HTTP请求
     * @return 操作结果
     */
    @AuthorizationFree
    @PutMapping(value = "/user/password")
    public RestResult resetPassword(@RequestParam(name = "resetPasswordToken") String resetPasswordToken,
                                    @RequestParam(name = "userName") String userName,
                                    @RequestParam(name = "password") String password,
                                    @RequestHeader(value = "host") String requestHost,
                                    HttpServletRequest request) {
        int organizationID = organizationDomainService.getOrganizationID(requestHost);
        try {
            if (organizationID <= 0) {
                return RestResult.getInstance(HttpResponseCode.ERROR_DOMAIN_NOT_BINDING, null);
            }
            if (StringUtils.isBlank(resetPasswordToken) || StringUtils.isBlank(password) || StringUtils.isBlank(userName)) {
                return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
            }
            String resetTokenInRedis = redisService.getUserResetPasswordToken(organizationID, userName);
            if (StringUtils.isBlank(resetTokenInRedis) || !resetTokenInRedis.equals(resetPasswordToken)) {
                return RestResult.getInstance(400, "验证码不正确", null);
            }
            String logContent = "机构编码 = " + String.valueOf(organizationID) + " ,用户名称 = " + userName;
            if (userService.resetPassword(organizationID, userName, password)) {
                redisService.removeUserResetPasswordToken(organizationID, userName);
                logService.logOperate(logger, request, "重置密码", logContent + " 密码重置成功", OperateLogLevel.critical);
                return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
            } else {
                logService.logOperate(logger, request, "重置密码", logContent + " 密码重置失败", OperateLogLevel.critical);
                return RestResult.getInstance(HttpResponseCode.ERROR, null);
            }
        } catch (Exception ex) {
            HashMap<String, Object> parameters = new HashMap<>(4);
            parameters.put("userName", userName);
            parameters.put("resetPasswordToken", resetPasswordToken);
            parameters.put("organizationID", organizationID);
            parameters.put("requestHost", requestHost);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "重置密码", parameters);
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }

    /**
     * 获取重置密码验证码
     *
     * @param userName  用户名
     * @param resetType 验证码发送方式： 1= 邮箱 ; 2 = 手机 ; 3 = Both
     * @param request   HTTP请求
     * @return 操作结果
     */
    @AuthorizationFree
    @GetMapping(value = "/user/resetPasswordToken")
    public RestResult getResetPasswordToken(@RequestParam(name = "userName") String userName,
                                            @RequestParam(name = "resetType", defaultValue = "1") int resetType,
                                            @RequestHeader(value = "host") String requestHost,
                                            HttpServletRequest request) {
        int organizationID = organizationDomainService.getOrganizationID(requestHost);
        try {
            if (organizationID <= 0) {
                return RestResult.getInstance(HttpResponseCode.ERROR_DOMAIN_NOT_BINDING, null);
            }
            if (resetType <= 0 || StringUtils.isBlank(userName)) {
                return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
            }
            if (userService.sendResetToken(organizationID, userName, resetType)) {
                String logContent = "机构编码 = " + String.valueOf(organizationID) + " ,用户名称 = " + userName;
                logService.logOperate(logger, request, "获取重置密码验证码", logContent, OperateLogLevel.critical);
                return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
            } else {
                return RestResult.getInstance(HttpResponseCode.ERROR, null);
            }
        } catch (Exception ex) {
            HashMap<String, Object> parameters = new HashMap<>(4);
            parameters.put("userName", userName);
            parameters.put("resetType", resetType);
            parameters.put("organizationID", organizationID);
            parameters.put("requestHost", requestHost);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "发送重置密码验证码", parameters);
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }

    /**
     * 获取指定机构的管理员用户列表
     * <p>只用于服务间调用</p>
     *
     * @param request HTTP请求
     * @return 操作结果
     */
    @AuthorizationFree
    @GetMapping(value = "/user/managers")
    public RestResult listManagers(HttpServletRequest request) {
        int pageIndex = IntegerUtil.tryParse(request.getParameter("page"), 0);
        int pageSize = IntegerUtil.tryParse(request.getParameter("per_page"), 10);
        int organizationID = IntegerUtil.tryParse(request.getParameter("organizationId"), 0);
        try {
            if (pageIndex < 0 || pageSize <= 0 || organizationID <= 0) {
                return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
            }
            FrontendResource manageOrganization = frontendResourceService.getResourceByName(OperatePermissionEnum.ORGANIZATION_MANAGE.getMsg(), (byte) FrontendResourceType.OPERATE_FUNCTION.ordinal());
            if (manageOrganization == null) {
                return RestResult.getInstance(HttpResponseCode.RESOURCE_NOT_FOUND, null);
            }
            PageInfo<UserInfo> userList = userService.listUsersHasPermission(pageIndex, pageSize, organizationID, manageOrganization.getFrontendResourceId());
            return RestResult.getInstance(HttpResponseCode.SUCCESS, userList);
        } catch (Exception ex) {
            HashMap<String, Object> parameters = new HashMap<>(3);
            parameters.put("userName", pageIndex);
            parameters.put("resetType", pageSize);
            parameters.put("organizationID", organizationID);
            ExceptionLog exceptionLog = new ExceptionLog(ex, "获取管理员列表", parameters);
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }

    private int increaseLoginFailTimes(String clientID) {
        return redisService.increaseLoginFailTimes(clientID);
    }

    private void removeLoginFailTimes(String clientID) {
        redisService.removeLoginFailTimes(clientID);
    }

    private RestResult validateChangePassword(int userID, UserInfo userInfo, String oldPassword, String
            newPassword) {
        UserInfo existUser = userService.getUserInfo(userID);
        if (existUser == null) {
            return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
        }

        String userName = existUser.getUserName();
        if (StringUtils.isNotBlank(userName)) {
            // 修改密码
            if (!isPasswordLegal(newPassword)) {
                return RestResult.getInstance(400, ILLEGAL_PASSWORD_NOTICE, null);
            }
            String encryptedNewPassword = null;
            String encryptedOldPassword = null;
            try {
                encryptedOldPassword = AESUtil.encrypt(oldPassword, UserConstant.ENCRYPT_KEY_PREFIX + userName);
                encryptedNewPassword = AESUtil.encrypt(newPassword, UserConstant.ENCRYPT_KEY_PREFIX + userName);
            } catch (Exception ex) {
                logger.error("密码加密失败,异常详情：{}", ex.getMessage());
            }
            if (StringUtils.isBlank(newPassword) || StringUtils.isBlank(encryptedOldPassword)
                    || StringUtils.isBlank(encryptedNewPassword)) {
                return RestResult.getInstance(HttpResponseCode.ERROR_ILLEGAL_ARGUMENT, null);
            }
            // 旧密码不匹配
            if (!encryptedOldPassword.equals(existUser.getPassword())) {
                return RestResult.getInstance(400, "原密码不匹配", null);
            }
            userInfo.setPassword(encryptedNewPassword);
        }
        return null;
    }

    private RestResult loginSuccess(UserInfo userInfo, String clientID, HttpServletRequest request) {
        userInfo.setPassword(null);
        String token = UUIDUtil.getUUID();
        long expireSeconds = redisService.saveUserToken(userInfo, token, clientID);
        removeLoginFailTimes(clientID);
        userService.updateStatus(userInfo.getUserId(), true);
        logService.logLoginSucess(logger, request, userInfo);
        Map<String, Object> tokenMap = new HashMap<>(2);
        tokenMap.put("token", token);
        tokenMap.put("expireSeconds", expireSeconds);
        return RestResult.getInstance(HttpResponseCode.SUCCESS, tokenMap);
    }

    private RestResult loginFail(String userName, String clientID, int organizationID, HttpServletRequest request) {
        Map<String, Boolean> showCaptcha = null;
        int loginFailTimes = increaseLoginFailTimes(clientID);
        logService.logLoginFail(logger, request, userName, 0, organizationID);
        if (loginFailTimes > MAX_LOGIN_FAILURE_TIMES) {
            showCaptcha = new HashMap<>(1);
            showCaptcha.put("showCaptcha", true);
        }
        return RestResult.getInstance(HttpResponseCode.ERROR_USER_INFO, showCaptcha);
    }

    private boolean isPasswordLegal(String password) {
        return RegUtil.isMatch(password, LEGAL_PASSWORD_PATTERN);
    }

    private boolean isUserNameLegal(String userName) {
        return RegUtil.isMatch(userName, LEGAL_USERNAME_PATTERN);
    }
}
