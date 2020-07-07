package com.medcaptain.cloud.usermanage.service;

import com.github.pagehelper.PageInfo;
import com.medcaptain.cloud.usermanage.entity.UserInfo;

import java.util.List;

/**
 * 关于用户的Service
 *
 * @author Medcaptain
 */
public interface UserService {
    List<UserInfo> login(int organizationID, String userName, String password) throws Exception;

    boolean exist(int organizationID, String userName);

    boolean exist(int userID);

    boolean existRole(int roleID);

    boolean addUser(int roleID, int departmentID, String userName, String password, String nickname, String gender,
                    String icon, String email) throws Exception;

    UserInfo getUserInfo(int userID);

    boolean deleteUser(int userID);

    boolean updateUserInfo(UserInfo userInfo);

    PageInfo<UserInfo> listUsers(int organization, int departmentID, int userID, int pageIndex, int pageSize,
                                 boolean onlineOnly, String nickname, String roleName, String departmentName);

    boolean updateStatus(int userID, boolean isLogin);

    UserInfo getCurrentUser(String userToken);

    /**
     * 重置用户密码
     *
     * @param organizationID 机构编号
     * @param userName       用户名
     * @param newPassword    新密码
     * @return true = 重置成功 ; false = 重置失败
     */
    boolean resetPassword(int organizationID, String userName, String newPassword);

    /**
     * 发送重置密码验证码给用户
     *
     * @param organizationID 机构编号
     * @param userName       用户名称
     * @param resetType      验证码发送方式: 1 = 电子邮箱; 2 = 手机短信; 3 = both
     * @return true = 发送成功 ; false = 发送失败
     */
    boolean sendResetToken(int organizationID, String userName, int resetType);

    /**
     * 获取拥有前端资源权限的用户列表
     *
     * @param pageIndex          页码
     * @param pageSize           每页数量
     * @param organizationID     机构编号
     * @param frontendResourceID 前端资源编号
     * @return 用户列表
     */
    PageInfo<UserInfo> listUsersHasPermission(int pageIndex, int pageSize, int organizationID, int frontendResourceID);
}
