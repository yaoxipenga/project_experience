package com.medcaptain.productservice.service;

import com.medcaptain.entity.ProductinfoCountEntity;
import com.medcaptain.entity.ProductinfoEntity;

import java.util.Date;
import java.util.List;

public interface StatisticsService {
    List<ProductinfoEntity> organizationDeviceCountLog(Date timeStartDate, Date timeEndDate, Integer page);

    /**
     * 获取某个地区的产生的产品数量
     * @param page
     * @return
     */
    List<ProductinfoCountEntity> organizationDeviceCount(Integer parentId, Integer page);
}
