package com.medcaptain.productservice.feign;

import com.medcaptain.authorization.MethodRequest;
import com.medcaptain.dto.RestResult;
import com.medcaptain.productservice.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

/**
 * 加上快速失败的处理类。该处理类是作为Feign熔断器的逻辑处理类，必须实现被@FeignClient修饰的接口。例如案例中的HiHystrix类实现了接口EurekaClientFeign，
 * 最后需要以Spring Bean的形式注入IoC容器中
 */
@FeignClient(value = "user-manage", configuration = FeignConfig.class)
@Service
public interface UserManageService {
    @RequestMapping(method = RequestMethod.POST, value = "/rolepermission/{roleId}/{permissinName}")
    String parseServiceData(@PathVariable(value = "roleId") Integer roleId,
                            @PathVariable(value = "permissinName") String permissinName);

    /**
     * 检查是否有后端接口访问权限
     *
     * @param methodRequest 接口访问信息
     * @return true = 有访问权限； false = 无访问权限
     */
    @PostMapping(value = "/authorization")
    boolean checkAuthorization(@RequestBody MethodRequest methodRequest);


    @GetMapping(value = "/authorization/{token}/{permissionName}")
    boolean hasOperatePermission(
            @PathVariable(name = "token") String token,
            @PathVariable(name = "permissionName") String permissionName
    );


    @GetMapping(value = "/user/{userId}")
    RestResult getUserInfo(@PathVariable(value = "userId") int userID);




    /**
     * 获取指定机构的管理员用户列表
     * <p>只用于服务间调用</p>
     *
     * @return 操作结果
     */
    @GetMapping(value = "/user/managers")
    RestResult listManagers(
            @RequestParam(value = "page") int page,
            @RequestParam(value = "per_page") int perPage,
            @RequestParam(value = "organizationId") int organizationId
    ) ;

    /**
     * 获取指定机构的管理员角色信息
     * <p>只用于服务间调用</p>
     *
     * @return 操作结果
     */
    @GetMapping(value = "/role/managers")
    RestResult getManagerRole(
            @RequestParam(value = "page") int page,
            @RequestParam(value = "per_page") int perPage,
            @RequestParam(value = "organizationId") int organizationId
    ) ;



}
