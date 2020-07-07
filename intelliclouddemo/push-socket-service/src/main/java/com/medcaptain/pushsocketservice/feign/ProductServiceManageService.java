package com.medcaptain.pushsocketservice.feign;

import com.medcaptain.pushsocketservice.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 调用设备管理服务的后端接口
 *
 * @author zhixiong
 */

@FeignClient(value = "product-service", configuration = FeignConfig.class)
@Service
public interface ProductServiceManageService {


    /**
     * 获取有访问改设备的角色列表
     *
     * @param productKey
     * @param deviceName
     * @return
     */
    @GetMapping(value = "/getDevicePermissionRoleList")
    List<Integer> getDevicePermissionRoleList(
            @RequestParam(value = "product_key") String productKey,
            @RequestParam(value = "device_name") String deviceName
    );
    /**
     * 获取有访问改设备的角色列表
     *
     * @param productKey
     * @param deviceName
     * @return
     */
    @GetMapping(value = "/getDevicePermissionUserList")
    List<Integer> getDevicePermissionUserList(
            @RequestParam(value = "product_key") String productKey,
            @RequestParam(value = "device_name") String deviceName
    );
}
