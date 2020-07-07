package com.medcaptain.cloud.usermanage.controller;

import com.medcaptain.cloud.usermanage.service.LogService;
import com.medcaptain.logging.OperateLogRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 数据库日志记录接口
 * <p>其它服务调用记录关键操作</p>
 *
 * @author bingwen.ai
 */
@RestController
public class LogController {

    @Autowired
    LogService logService;

    @PostMapping("/log")
    public boolean log(HttpServletRequest request, @RequestBody OperateLogRecord operateLogRecord) {
        return logService.logOperate(request, operateLogRecord, null);
    }
}
