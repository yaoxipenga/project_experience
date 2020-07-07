package com.medcaptain.file.controller;

import com.medcaptain.dto.RestResult;
import com.medcaptain.file.service.OptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 平台操作日志的Controller
 * @author Adam.Chen
 */
@RestController
public class OptController {
    @Autowired
    private OptService optService;
    @GetMapping("/logs/operations")
    public RestResult getOptLogs(@RequestHeader(value = "UserId")Integer userId,
                                @RequestParam(value = "page",defaultValue = "0")int page,
                                @RequestParam(value = "per",defaultValue = "10")int per){
        //int userId = 111;

        return optService.getOptLogList(page, per, userId);
    }
}
