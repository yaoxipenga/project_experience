package com.medcaptain.cloud.usermanage.controller;

import com.alibaba.fastjson.JSONObject;
import com.medcaptain.annotation.AuthorizationFree;
import com.medcaptain.annotation.Log;
import com.medcaptain.cloud.usermanage.entity.Region;
import com.medcaptain.cloud.usermanage.service.RegionService;
import com.medcaptain.dto.RestResult;
import com.medcaptain.enums.HttpResponseCode;
import com.medcaptain.logging.ExceptionLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 地理区域信息控制器
 *
 * @author bingwen.ai
 */
@RestController
public class RegionController {
    private Logger logger = LoggerFactory.getLogger(RegionController.class);

    @Autowired
    RegionService regionService;

    @Log
    @AuthorizationFree
    @GetMapping(value = "/regions")
    public RestResult listRegions() {
        try {
            List<Region> regionList = regionService.listAllRegion();
            return RestResult.getInstance(HttpResponseCode.SUCCESS, formatRegions(regionList));
        } catch (Exception ex) {
            ExceptionLog exceptionLog = new ExceptionLog(ex, "获取地理区域信息");
            logger.error(exceptionLog.toString());
            return RestResult.getInstance(HttpResponseCode.SERVER_INTERNAL_ERROR, null);
        }
    }

    /**
     * 列出父节点下的所有下一级区域
     *
     * @param parentRegionId 父节点区域编号
     * @return 下一级区域信息列表
     */
    @AuthorizationFree
    @GetMapping(value = "/regions/{parentRegionId}")
    public RestResult listSubRegions(@PathVariable(name = "parentRegionId") int parentRegionId) {
        List<Region> regionList = regionService.listSubRegion(parentRegionId);
        return RestResult.getInstance(HttpResponseCode.SUCCESS, regionList);
    }

    private List<JSONObject> formatRegions(List<Region> regionList) {
        List<Region> countryRegions = filterRegion(regionList, 0, 0);
        List<JSONObject> regions = new ArrayList<>();
        for (Region countryRegion : countryRegions) {
            JSONObject country = new JSONObject();
            country.put("value", countryRegion.getRegionId());
            country.put("label", countryRegion.getRegionName());
            List<Region> districtRegions = filterRegion(regionList, countryRegion.getRegionLevel() + 1,
                    countryRegion.getRegionId());
            List<JSONObject> districtList = new ArrayList<>();
            for (Region districtRegion : districtRegions) {
                JSONObject district = new JSONObject();
                district.put("value", districtRegion.getRegionId());
                district.put("label", districtRegion.getRegionName());
                List<Region> provinceRegions = filterRegion(regionList, districtRegion.getRegionLevel() + 1,
                        districtRegion.getRegionId());
                List<JSONObject> provinceList = new ArrayList<>();
                for (Region provinceRegion : provinceRegions) {
                    JSONObject province = new JSONObject();
                    province.put("value", Integer.toString(provinceRegion.getRegionId()));
                    province.put("label", provinceRegion.getRegionName());
                    provinceList.add(province);
                }
                district.put("children", provinceList);
                districtList.add(district);
            }
            country.put("children", districtList);
            regions.add(country);
        }
        return regions;
    }

    private List<Region> filterRegion(List<Region> regionList, int regionLevel, int parentRegionID) {
        List<Region> regions = new ArrayList<>();
        for (Region region : regionList) {
            if (region.getRegionLevel() == (byte) regionLevel && region.getParentId() == parentRegionID) {
                regions.add(region);
            }
        }
        return regions;
    }
}
