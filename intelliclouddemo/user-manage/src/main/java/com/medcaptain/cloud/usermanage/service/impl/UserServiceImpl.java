package com.medcaptain.cloud.usermanage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.medcaptain.cloud.usermanage.constant.UserConstant;
import com.medcaptain.cloud.usermanage.entity.UserInfo;
import com.medcaptain.cloud.usermanage.entity.UserInfoExample;
import com.medcaptain.cloud.usermanage.entity.UserToken;
import com.medcaptain.cloud.usermanage.feign.MessagePushService;
import com.medcaptain.cloud.usermanage.mapper.UserInfoMapper;
import com.medcaptain.cloud.usermanage.redis.RedisService;
import com.medcaptain.cloud.usermanage.service.UserService;
import com.medcaptain.dto.RestResult;
import com.medcaptain.enums.HttpResponseCode;
import com.medcaptain.utils.CaptchaUtil;
import com.medcaptain.utils.FileUtil;
import com.medcaptain.utils.encrypt.AESUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


/**
 * 关于用户接口的实现类
 *
 * @author Medcaptain
 */
@Service
public class UserServiceImpl implements UserService {
    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private static String VERIFY_CODE_EMAIL_TEMPLATE = null;

    static {
        VERIFY_CODE_EMAIL_TEMPLATE = readHtmlMailTemplate("ResetPassword.html");
    }

    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    RedisService redisService;

    @Autowired
    MessagePushService messagePushService;

    @Override
    public List<UserInfo> login(int organizationID, String userName, String password) throws Exception {
        // 加密传入的密码到数据库获取用户信息
        String encryptedPassword = AESUtil.encrypt(password, UserConstant.ENCRYPT_KEY_PREFIX + userName);
        Map<String, String> params = new HashMap<>(5);
        params.put("organizationID", String.valueOf(organizationID));
        params.put("userName", userName);
        params.put("password", encryptedPassword);
        params.put("isEnable", "1");
        params.put("isDeleted", "0");
        return userInfoMapper.listUsers(params);
    }

    @Override
    public boolean exist(int organizationID, String userName) {
        Map<String, String> queryCondition = new HashMap<>(2);
        queryCondition.put("organizationID", String.valueOf(organizationID));
        queryCondition.put("userName", userName);
        return userInfoMapper.countByUserNameAndOrganizationID(queryCondition) > 0;
    }

    @Override
    public boolean addUser(int roleID, int departmentID, String userName, String password, String nickname,
                           String gender, String icon, String email) throws Exception {
        String encryptedPassword = AESUtil.encrypt(password, UserConstant.ENCRYPT_KEY_PREFIX + userName);
        UserInfo userInfo = new UserInfo();
        userInfo.setRoleId(roleID);
        userInfo.setDepartmentId(departmentID);
        userInfo.setUserName(userName);
        userInfo.setPassword(encryptedPassword);
        userInfo.setGender(gender);
        userInfo.setIcon(icon);
        userInfo.setNickname(nickname);
        userInfo.setEmail(email);
        return userInfoMapper.insertSelective(userInfo) > 0;
    }

    @Override
    public UserInfo getUserInfo(int userID) {
        Map<String, String> params = new HashMap<>(1);
        params.put("currentUserID", String.valueOf(userID));
        List<UserInfo> users = userInfoMapper.listUsers(params);
        if (users.size() > 0) {
            return users.get(0);
        } else {
            return null;
        }
    }

    @Override
    public boolean exist(int userID) {
        UserInfoExample example = new UserInfoExample();
        example.createCriteria().andUserIdEqualTo(userID);
        return userInfoMapper.countByExample(example) > 0;
    }

    @Override
    public boolean existRole(int roleID) {
        UserInfoExample example = new UserInfoExample();
        example.createCriteria().andRoleIdEqualTo(roleID).andIsDeletedEqualTo((byte) 0);
        return userInfoMapper.countByExample(example) > 0;
    }

    @Override
    public boolean deleteUser(int userID) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userID);
        userInfo.setIsDeleted((byte) 1);
        return userInfoMapper.updateByPrimaryKeySelective(userInfo) > 0;
    }

    @Override
    public boolean updateUserInfo(UserInfo userInfo) {
        Byte isDeleted = userInfo.getIsDeleted();
        Byte isEnable = userInfo.getIsEnable();
        boolean setUserOffline = false;
        boolean isUserOffline = (isDeleted != null && isDeleted == 1) || (isEnable != null && isEnable == 0);
        if (isUserOffline) {
            userInfo.setStatus(1);
            setUserOffline = true;
        }
        int effectedRows = userInfoMapper.updateByPrimaryKeySelective(userInfo);
        if (effectedRows > 0 && userInfo.getUserId() != null) {
            if (setUserOffline) {
                // 禁用或删除用户后，清空redis中的token，用户需重新登录
                redisService.removeToken(userInfo.getUserId());
            } else {
                UserInfo updateUserInfo = getUserInfo(userInfo.getUserId());
                updateUserInfo.setPassword(null);
                redisService.updateUserToken(updateUserInfo);
            }
        }
        return effectedRows > 0;
    }

    @Override
    public PageInfo<UserInfo> listUsers(int organizationID, int departmentID, int userID, int pageIndex, int pageSize,
                                        boolean onlineOnly, String nickname, String roleName, String departmentName) {
        PageHelper.startPage(pageIndex, pageSize);
        Map<String, String> params = new HashMap<>(8);
        if (organizationID > 0) {
            params.put("organizationID", String.valueOf(organizationID));
        }
        if (departmentID > 0) {
            params.put("departmentID", String.valueOf(departmentID));
        }
        params.put("isDeleted", String.valueOf(0));
        if (onlineOnly) {
            params.put("onlineOnly", "1");
        }
        if (userID > 0) {
            params.put("userID", String.valueOf(userID));
        }
        if (StringUtils.isNotBlank(nickname)) {
            params.put("nickname", nickname);
        }
        if (StringUtils.isNotBlank(roleName)) {
            params.put("roleName", roleName);
        }
        if (StringUtils.isNotBlank(departmentName)) {
            params.put("departmentName", departmentName);
        }

        String orderByClause = " d.organization_id,u.department_id,user_id asc";
        params.put("orderByClause", orderByClause);
        List<UserInfo> users = userInfoMapper.listUsers(params);
        PageInfo<UserInfo> pageInfo = new PageInfo<>(users);
        for (UserInfo userInfo : pageInfo.getList()) {
            userInfo.setPassword(null);
        }
        return pageInfo;
    }

    @Override
    public boolean updateStatus(int userID, boolean isLogin) {
        //TODO 暂时忽略第三方账户token续期
//        if (!isLogin && redisService.existUserToken(userID)) {
//            return false;
//        }
        UserInfo userInfo = new UserInfo();
        if (isLogin) {
            userInfo.setStatus(0);
        } else {
            userInfo.setStatus(1);
        }
        UserInfoExample example = new UserInfoExample();
        example.createCriteria().andUserIdEqualTo(userID);
        return userInfoMapper.updateByExampleSelective(userInfo, example) > 0;
    }

    @Override
    public UserInfo getCurrentUser(String userToken) {
        UserToken currentUserToken = redisService.getCacheUser(userToken);
        return currentUserToken == null ? null : currentUserToken.getUserInfo();
    }

    @Override
    public boolean resetPassword(int organizationID, String userName, String newPassword) {
        String encryptedPassword = null;
        try {
            encryptedPassword = AESUtil.encrypt(newPassword, UserConstant.ENCRYPT_KEY_PREFIX + userName);
        } catch (Exception ex) {
        }
        if (encryptedPassword == null) {
            return false;
        }
        UserInfo userInfo = getUser(organizationID, userName);
        if (userInfo != null) {
            UserInfo newUser = new UserInfo();
            newUser.setUserId(userInfo.getUserId());
            newUser.setPassword(encryptedPassword);
            return userInfoMapper.updateByPrimaryKeySelective(newUser) > 0;
        }
        return false;
    }

    @Override
    public boolean sendResetToken(int organizationID, String userName, int resetType) {
        UserInfo userInfo = getUser(organizationID, userName);
        if (userInfo != null) {
            String email = userInfo.getEmail();
            String verifyCode = CaptchaUtil.generateCaptcha(6);
            if (redisService.saveUserResetToken(organizationID, userName, verifyCode)) {
                // 发送电子邮件通知用户
                if (resetType == 1 && StringUtils.isNotEmpty(email)) {
                    String htmlMailMessage = verifyCode;
                    boolean isHtml = false;
                    if (StringUtils.isNotBlank(VERIFY_CODE_EMAIL_TEMPLATE)) {
                        isHtml = true;
                        htmlMailMessage = String.format(VERIFY_CODE_EMAIL_TEMPLATE, userName, verifyCode);
                    }
                    RestResult sendResult = messagePushService.sendEmail(email, "账号密码重置", htmlMailMessage, isHtml);
                    return sendResult.getCode() == HttpResponseCode.SUCCESS.getCode();
                }
            }
        }
        return false;
    }

    @Override
    public PageInfo<UserInfo> listUsersHasPermission(int pageIndex, int pageSize, int organizationID, int frontendResourceID) {
        PageHelper.startPage(pageIndex, pageSize);
        Map<String, String> params = new HashMap<>(8);
        if (organizationID > 0) {
            params.put("organizationID", String.valueOf(organizationID));
        }
        params.put("frontendResourceID", String.valueOf(frontendResourceID));
        List<UserInfo> users = userInfoMapper.listUsersHasPermission(params);
        PageInfo<UserInfo> pageInfo = new PageInfo<>(users);
        for (UserInfo userInfo : pageInfo.getList()) {
            userInfo.setPassword(null);
        }
        return pageInfo;
    }

    private static String readHtmlMailTemplate(String fileName) {
        try {
            InputStream mailTemplate = UserServiceImpl.class.getClassLoader().getResourceAsStream(fileName);
            return FileUtil.readContent(mailTemplate);
        } catch (Exception ex) {
            return null;
        }
    }

    private UserInfo getUser(int organizationID, String userName) {
        Map<String, String> parameters = new HashMap<>(4);
        parameters.put("organizationID", String.valueOf(organizationID));
        parameters.put("userName", userName);
        parameters.put("isDeleted", "0");
        parameters.put("isEnable", "1");
        List<UserInfo> userInfoList = userInfoMapper.listUsers(parameters);
        if (userInfoList != null && userInfoList.size() == 1) {
            return userInfoList.get(0);
        }
        return null;
    }
}
