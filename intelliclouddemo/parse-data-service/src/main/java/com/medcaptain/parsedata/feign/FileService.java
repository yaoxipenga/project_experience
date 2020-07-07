package com.medcaptain.parsedata.feign;

import com.medcaptain.dto.RestResult;
import com.medcaptain.parsedata.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@FeignClient(value = "file-service", configuration = FeignConfig.class)
public interface FileService {
    @PostMapping("/firmware/progress")
    String reflush(@RequestParam(value = "firmwareId") Integer firmwareId,
                   @RequestParam(value = "deviceName") String deviceName,
                   @RequestParam(value = "progress") Integer progress);

    /**
     * 设备升级完成上报版本号，检查升级结果
     * @param deviceTripleId
     * @param firmwareId
     * @param pushVersion
     * @return
     */
    @PostMapping("/firmware/version")
    String checkUpgradeResult(@RequestParam(value = "deviceTripleId") Integer deviceTripleId,
                              @RequestParam(value = "firmwareId") Integer firmwareId,
                              @RequestParam(value = "pushVersion") String pushVersion);
}
