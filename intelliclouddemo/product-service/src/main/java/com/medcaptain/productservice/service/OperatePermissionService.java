package com.medcaptain.productservice.service;


/**
 * 用户权限
 *
 * @author yang
 */
public interface OperatePermissionService {

    /**
     * 查询用户权限
     *
     * @param userToken 用户token
     * @return 用户的权限code    1、平台管理员   2、机构管理员    3、运营操作员
     */
    int hasOperatePermission(String userToken);

}
