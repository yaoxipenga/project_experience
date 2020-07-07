package com.medcaptain.cloud.usermanage.service;

import com.medcaptain.cloud.usermanage.entity.Region;

import java.util.List;

/**
 * 区域信息服务
 *
 * @author bingwen.ai
 */
public interface RegionService {
    List<Region> listRegion(int regionLevel);

    List<Region> listSubRegion(int regionID);

    List<Region> listAllRegion();
}
