package com.medcaptain.gateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TestController {

//    @Autowired
//    DynamicRouteService dynamicRouteService;

//    @Autowired
//    TestGatewayService testGatewayService;

    @GetMapping("test_add")
    public String test_add(
            @RequestParam("id") String id
    ) {
//        dynamicRouteService.test_add(id);
//        testGatewayService.save();
        return "成功";
    }

    @GetMapping("test_del")
    public String tes_del(
            @RequestParam("id") String id
    ) {
//        dynamicRouteService.test_del(id);
        return "成功";
    }

}
