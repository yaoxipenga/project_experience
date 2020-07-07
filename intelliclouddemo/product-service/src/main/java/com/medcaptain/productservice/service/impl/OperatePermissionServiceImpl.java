package com.medcaptain.productservice.service.impl;

import com.medcaptain.enums.OperatePermissionEnum;
import com.medcaptain.productservice.feign.UserManageService;
import com.medcaptain.productservice.service.OperatePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class OperatePermissionServiceImpl implements OperatePermissionService {

    @Autowired
    UserManageService userManageService;


    @Override
    public int hasOperatePermission(String userToken) {
        if (userManageService.hasOperatePermission(userToken, OperatePermissionEnum.PLATFORM_MANAGE.getMsg())) {
            return OperatePermissionEnum.PLATFORM_MANAGE.getCode();
        } else if (userManageService.hasOperatePermission(userToken, OperatePermissionEnum.ORGANIZATION_MANAGE.getMsg())) {
            return OperatePermissionEnum.ORGANIZATION_MANAGE.getCode();
        } else if (userManageService.hasOperatePermission(userToken, OperatePermissionEnum.OPERATE_DEVICE.getMsg())) {
            return OperatePermissionEnum.OPERATE_DEVICE.getCode();
        }
        return OperatePermissionEnum.ERROR.getCode();
    }
}
