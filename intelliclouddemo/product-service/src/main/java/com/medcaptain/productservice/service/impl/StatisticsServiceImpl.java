package com.medcaptain.productservice.service.impl;

import com.medcaptain.entity.ProductinfoCountEntity;
import com.medcaptain.entity.ProductinfoEntity;
import com.medcaptain.enums.DelEnum;
import com.medcaptain.productservice.dao.mybatis.DeviceinfoMapper;
import com.medcaptain.productservice.dao.mybatis.ProductinfoMapper;
import com.medcaptain.productservice.entity.mybatis.Productinfo;
import com.medcaptain.productservice.service.StatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private static Logger logger = LoggerFactory.getLogger(StatisticsServiceImpl.class);

    @Autowired
    ProductinfoMapper productinfoMapper;

    @Autowired
    DeviceinfoMapper deviceinfoMapper;


    @Override
    public List<ProductinfoEntity> organizationDeviceCountLog(Date timeStartDate, Date timeEndDate, Integer page) {
        /*
         * 机构位置表主键
         * 产品Key
         ** 分析的开始时间
         ** 分析的结束时间
         * 在这个时间段，这个医院，这个产品，新增的设备数量
         * 数据类型（0新增   1删除数量）
         ** 创建时间（查询最新一条的创建时间数据中的结束时间，为下一次统计开始的时间）
         */
        Integer PAGE_LENGTH = 10;
        List<Productinfo> productinfoList = productinfoMapper.getProductTime(timeStartDate, timeEndDate, page * PAGE_LENGTH, PAGE_LENGTH);
        List<ProductinfoEntity> returnList = new ArrayList<>(productinfoList.size() + 3);
        for (Productinfo productinfo : productinfoList) {
            for (DelEnum delEnum : DelEnum.values()) {
                ProductinfoEntity productinfoEntity = new ProductinfoEntity();
                logger.info("ProductKey={} , code={}", productinfo.getProductKey(), delEnum.getCode());
                productinfoEntity.setCountType(delEnum.getCode());
                productinfoEntity.setOrganizationRegionId(productinfo.getOrganizationId());
                productinfoEntity.setProductKey(productinfo.getProductKey());
                Integer deviceCount = deviceinfoMapper.selectByProductKeyAndDel(productinfo.getProductKey(), delEnum.getCode());
                if (deviceCount == null || deviceCount.equals(0)) {
                    productinfoEntity.setDeviceCount(0);
                } else {
                    productinfoEntity.setDeviceCount(deviceCount);
                    // 只有 统计数据不为 0 ，才返回结果
                    returnList.add(productinfoEntity);
                }
            }
        }
        return returnList;
    }

    @Override
    public List<ProductinfoCountEntity> organizationDeviceCount(Integer parentId, Integer page) {
        logger.info("organizationRegionId:{}", parentId);
        List<Map> regionCountMap = productinfoMapper.selectByRegionCount(parentId);
        List<ProductinfoCountEntity> productinfoCountEntityList = new ArrayList<>();
        for (Map regMap : regionCountMap) {
            ProductinfoCountEntity productinfoCountEntity = new ProductinfoCountEntity();
            productinfoCountEntity.setCountType((byte) 2);
            productinfoCountEntity.setDeviceCount(Integer.valueOf(regMap.get("count").toString()));
            productinfoCountEntity.setOrganizationId(Integer.valueOf(regMap.get("organizationId").toString()));
            productinfoCountEntity.setProductKey(regMap.get("product_key").toString());
            productinfoCountEntityList.add(productinfoCountEntity);
        }
        return productinfoCountEntityList;
    }

}