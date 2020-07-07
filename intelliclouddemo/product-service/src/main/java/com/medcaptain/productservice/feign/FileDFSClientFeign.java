package com.medcaptain.productservice.feign;

import com.medcaptain.dto.RestResult;
import com.medcaptain.productservice.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
//@FeignClient(value = "eureka-client", configuration = FeignConfig.class)

/**
 * 加上快速失败的处理类。该处理类是作为Feign熔断器的逻辑处理类，必须实现被@FeignClient修饰的接口。例如案例中的HiHystrix类实现了接口EurekaClientFeign，
 * 最后需要以Spring Bean的形式注入IoC容器中
 */
@Service
@FeignClient(value = "fast-dfs", configuration = FeignConfig.class)
public interface FileDFSClientFeign {
    @PostMapping(value = "/file/excel")
    String fileDFS(
            @RequestParam(value = "data") byte[] fileByte,
            @RequestParam(value = "fileName") String fileName
    );

    @PostMapping(value = "/file/downloadToken")
    RestResult downloadFile(
            @RequestParam("token") String token,
            @RequestParam("fileName") String fileName,
            @RequestParam("path") String path
    );

    @PostMapping(value = "/file", produces = "application/json;charset=UTF-8")
    public RestResult recFile(@RequestParam("file") MultipartFile file, @RequestParam("token") String token,
                              @RequestParam(value = "type", required = false) String type);
}
