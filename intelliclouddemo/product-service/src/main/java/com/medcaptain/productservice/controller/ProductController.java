package com.medcaptain.productservice.controller;

import com.medcaptain.dto.RestResult;
import com.medcaptain.enums.HttpResponseCode;
import com.medcaptain.productservice.controller.validator.TransactionValidator;
import com.medcaptain.productservice.entity.dto.mongo.ProductEvent;
import com.medcaptain.productservice.entity.dto.mongo.ProductProperty;
import com.medcaptain.productservice.entity.dto.mongo.ProductServiceEntity;
import com.medcaptain.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@RestController
public class ProductController {
    @Autowired
    ProductService productService;

    //产品列表
    @GetMapping(value = "/products")
    RestResult getProductList(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                              @RequestParam(value = "per_page", required = false, defaultValue = "10") Integer perPage,
                              @RequestParam(value = "product_type", required = false, defaultValue = "") String productType,
                              @RequestParam(value = "product_name", required = false, defaultValue = "") String productName,
                              @RequestHeader(name = "organizationId") Integer organizationId,
                              @RequestHeader(name = "token") String token,
                              @RequestHeader(name = "userId") Integer userId) {
        return RestResult.getInstance(HttpResponseCode.SUCCESS, productService.getProductList(page, perPage, productType, productName, organizationId, userId, token));
    }

    //新增产品
    @PostMapping(value = "/product")
    RestResult createProduct(
            @RequestHeader(name = "userId") Integer userId,
            @RequestParam(value = "productName", required = true) String productName,
            @RequestParam(value = "productCategory", required = true) String productCategory,
            @RequestParam(value = "productDesc", required = false, defaultValue = "") String productDescribe,
            @RequestParam(value = "productModel", required = false, defaultValue = "") String productModel,
            @RequestHeader(name = "organizationId") Integer organizationId,
            @RequestParam(value = "imageUrl", required = false) String imageUrl
    ) {
        return RestResult.getInstance(HttpResponseCode.SUCCESS, productService.addProduct(userId, productName, productCategory, productDescribe, productModel, organizationId, imageUrl));
    }

    //获得产品所有类别
    @GetMapping(value = "/categories")
    RestResult getCategoryList() {
        return RestResult.getInstance(HttpResponseCode.SUCCESS, productService.getCategoryList());
    }

    //删除产品
    @DeleteMapping(value = "/{product_key}")
    RestResult deleteProduct(@PathVariable("product_key") String productKey) {
        productService.deleteProduct(productKey);
        return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
    }

    //查询单个产品信息
    @GetMapping(value = "/{product_key}")
    RestResult getProductInfo(@PathVariable("product_key") String productKey) {
        return RestResult.getInstance(HttpResponseCode.SUCCESS, productService.getProductInfo(productKey));
    }

    //查看产品业务列表
    @GetMapping(value = "/{product_key}/abilities")
    RestResult getProductTransaction(@PathVariable("product_key") String productKey) {
        return RestResult.getInstance(HttpResponseCode.SUCCESS, productService.getProductTransaction(productKey));
    }

    //获取所有业务列表（模板）
    @GetMapping(value = "/abilities/all/{ability_type}")
    RestResult getAllTransactions(@PathVariable(value = "ability_type") Integer abilityType) {
        return RestResult.getInstance(HttpResponseCode.SUCCESS, productService.getAllTransactions(abilityType));
    }

    //新增属性
    @PostMapping(value = "/{product_key}/ability/property")
    RestResult addProperty(
            @RequestBody @Valid ProductProperty productProperty,
            @RequestHeader(name = "userId") Integer userId,
            @PathVariable("product_key") String productKey
    ) {
        productService.addProperty(productKey, userId, productProperty);
        return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
    }

    //新增服务
    @PostMapping(value = "/{product_key}/ability/service")
    RestResult addService(
            @RequestHeader(name = "userId") Integer userId,
            @PathVariable("product_key") String productKey,
            @RequestBody @Valid ProductServiceEntity productServiceEntity
    ) {
        productService.addService(userId, productKey, productServiceEntity);
        return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
    }

    //新增事件
    @PostMapping(value = "/{product_key}/ability/event")
    RestResult addEvent(
            @RequestHeader(name = "userId") Integer userId,
            @PathVariable("product_key") String productKey,
            @RequestBody @Valid ProductEvent productEvent
    ) {

        productService.addEvent(userId, productKey, productEvent);
        return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
    }

    //编辑属性
    @PutMapping(value = "/{product_key}/ability/property")
    RestResult updateProperty(
            @RequestHeader(name = "userId") Integer userId,
            @PathVariable(value = "product_key", required = true) String productKey,
            @RequestBody @Valid ProductProperty productProperty

    ) {

        productService.updateProperty(userId, productKey, productProperty);
        return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
    }

    //编辑服务
    @PutMapping(value = "/{product_key}/ability/service")
    RestResult updateService(
            @RequestHeader(name = "userId") Integer userId,
            @PathVariable("product_key") String productKey,
            @RequestBody @Valid ProductServiceEntity productServiceEntity
    ) {
        productService.updateService(userId, productKey, productServiceEntity);
        return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
    }

    //编辑事件
    @PutMapping(value = "/{product_key}/ability/event")
    RestResult updateEvent(
            @RequestHeader(name = "userId") Integer userId,
            @PathVariable(value = "product_key", required = true) String productKey,
            @RequestBody @Valid ProductEvent productEvent
    ) {
        productService.updateEvent(userId, productKey, productEvent);
        return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
    }

    //删除功能
    @DeleteMapping(value = "/{product_key}/ability/{ability_id}/{transaction_type}")
    RestResult deleteAbility(@PathVariable("product_key") String productKey,
                             @PathVariable("ability_id") String transactionId,
                             @PathVariable("transaction_type") String transactionType) {
        productService.deleteAbility(productKey, transactionType, transactionId);
        return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
    }

    //重置productSecret
    @PutMapping(value = "/{product_key}/secret")
    RestResult resetProductSecret(@PathVariable("product_key") String productKey) {
        return RestResult.getInstance(HttpResponseCode.SUCCESS, productService.resetProductSecret(productKey));
    }

    //编辑产品信息
    @PutMapping(value = "/{product_key}")
    RestResult updateProduct(@PathVariable("product_key") String productKey,
                             @RequestParam(value = "productName", required = true) String productName,
                             @RequestParam(value = "productModel", required = true) String productModel,
                             @RequestParam(value = "productDesc", required = false) String productDescribe,
                             @RequestHeader(name = "organizationId") Integer organizationId) {
        return RestResult.getInstance(HttpResponseCode.SUCCESS, productService.updateProduct(productKey, productModel, productDescribe, productName, organizationId));
    }

    //查看所有单位
    @GetMapping(value = "/ability/units")
    RestResult getUnit() {
        return RestResult.getInstance(HttpResponseCode.SUCCESS, productService.getUnit());
    }

    //查看物理模型
    @GetMapping(value = "/{product_key}/physical")
    RestResult getPhysicalModel(@PathVariable(value = "product_key", required = true) String productKey) {
        return RestResult.getInstance(HttpResponseCode.SUCCESS, productService.getPhysicalModel(productKey));
    }

    //导出证书
    @GetMapping(value = "/product/cert")
    RestResult downloadClientCert(@RequestParam(value = "productKey", required = true) String productKey) {
//        return RestResult.getInstance(HttpResponseCode.SUCCESS, productService.downloadClientCert(productKey));
        return productService.downloadClientCert(productKey);
    }

    //导入物模型
    @PostMapping(value = "/file")
    RestResult importPhysicalModel(@RequestParam(value = "productKey", required = true) String productKey,
                                   @RequestParam("file") MultipartFile file,
                                   @RequestParam(value = "MD5", required = true) String MD5) {
        productService.importPhysicalModel(productKey, file, MD5);
        return RestResult.getInstance(HttpResponseCode.SUCCESS, null);
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.setValidator(new TransactionValidator());
    }
}
