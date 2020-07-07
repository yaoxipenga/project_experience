package com.medcaptain.cloud.usermanage.controller;

import com.medcaptain.annotation.AuthorizationFree;
import com.medcaptain.annotation.Log;
import com.medcaptain.cloud.usermanage.constant.FrontendResourceType;
import com.medcaptain.cloud.usermanage.entity.FrontendResourcePermission;
import com.medcaptain.cloud.usermanage.entity.UserInfo;
import com.medcaptain.cloud.usermanage.service.FrontendResourcePermissionService;
import com.medcaptain.cloud.usermanage.service.UserService;
import com.medcaptain.dto.RestResult;
import com.medcaptain.enums.HttpResponseCode;
import com.medcaptain.helper.HttpServletHelper;
import com.medcaptain.logging.ExceptionLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜单信息控制器
 *
 * @author bingwen.ai
 */
@RestController
public class MenuController {
    @Autowired
    UserService userService;

    @Autowired
    FrontendResourcePermissionService frontendResourcePermissionService;

    private Logger logger = LoggerFactory.getLogger(MenuController.class);

    /**
     * 按用户获取菜单列表
     *
     * @param request HTTP请求
     * @return 包含菜单列表的json
     */
    @Log
    @AuthorizationFree
    @GetMapping(value = "/menu_list")
    public RestResult listMenus(HttpServletRequest request) {
        try {
            String token = HttpServletHelper.getRequestToken(request);
            UserInfo currentUser = userService.getCurrentUser(token);
            if (currentUser == null) {
                return RestResult.getInstance(HttpResponseCode.ERROR_USER_INFO, null);
            }
            List<FrontendResourcePermission> menuPermissions =
                    frontendResourcePermissionService.listPermissions(currentUser.getRoleId(), FrontendResourceType.MENU);
            List<Map> menuList = formatMenuList(menuPermissions);
            Map<String, Object> returnMap = new HashMap<>(2);
            returnMap.put("menuList", menuList);
            List<Map> subpageList = new ArrayList<>();
            addSubMenu(subpageList, "/product-info", "产品信息", "", "");
            addSubMenu(subpageList, "/device-info", "设备信息", "", "");
            addSubMenu(subpageList, "/firmware-info", "固件升级", "", "");
            addSubMenu(subpageList, "/manage/hospital-info", "医院信息", "", "");
            returnMap.put("subpageList", subpageList);
            return RestResult.getInstance(HttpResponseCode.SUCCESS, returnMap);
        } catch (Exception ex) {
            ExceptionLog exceptionLog = new ExceptionLog(ex, "获取菜单列表");
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }

    private void addSubMenu(List<Map> parentMenu, String path, String name, String permission, String icon) {
        Map<String, String> subMenu = new HashMap<>(4);
        subMenu.put("path", path);
        subMenu.put("name", name);
        subMenu.put("permission", permission);
        subMenu.put("icon", icon);
        parentMenu.add(subMenu);
    }

    private List<Map> formatMenuList(List<FrontendResourcePermission> menuPermissions) {
        List<Map> menuList = new ArrayList<>();
        addSubMenu(menuList, "/home", "首页", "", "iconfont icon-home");
        List<FrontendResourcePermission> firstLayerMenus = filterMenus(menuPermissions, 0);
        for (FrontendResourcePermission firstLayerMenu : firstLayerMenus) {
            int resourceID = firstLayerMenu.getFrontendResourceId();
            Map<String, Object> menu = new HashMap<>(4);
            menu.put("name", firstLayerMenu.getFrontendResourceName());
            menu.put("icon", firstLayerMenu.getMenuIcon());
            menu.put("path", firstLayerMenu.getMenuURL());
            menu.put("permission", "");
            List<FrontendResourcePermission> secondLayerMenus = filterMenus(menuPermissions, resourceID);
            if (secondLayerMenus.size() > 0) {
                List<Map> subMenuList = new ArrayList<>();
                for (FrontendResourcePermission secondLayerMenu : secondLayerMenus) {
                    addSubMenu(subMenuList, secondLayerMenu.getMenuURL(),
                            secondLayerMenu.getFrontendResourceName(), "", secondLayerMenu.getMenuIcon());
                }
                if (subMenuList.size() > 0) {
                    menu.put("child", subMenuList);
                }
            }
            menuList.add(menu);
        }
        return menuList;
    }

    private List<FrontendResourcePermission> filterMenus(List<FrontendResourcePermission> menuPermissions,
                                                         int parentMenuID) {
        List<FrontendResourcePermission> permissionList = new ArrayList<>();
        for (FrontendResourcePermission menuPermission : menuPermissions) {
            if (menuPermission.getParentResourceID() == parentMenuID) {
                permissionList.add(menuPermission);
            }
        }
        return permissionList;
    }
}
