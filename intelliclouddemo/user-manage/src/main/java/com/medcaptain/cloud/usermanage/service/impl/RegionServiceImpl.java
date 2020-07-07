package com.medcaptain.cloud.usermanage.service.impl;

import com.medcaptain.cloud.usermanage.entity.Region;
import com.medcaptain.cloud.usermanage.entity.RegionExample;
import com.medcaptain.cloud.usermanage.mapper.RegionMapper;
import com.medcaptain.cloud.usermanage.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 区域信息服务实现
 *
 * @author bingwen.ai
 */
@Service
public class RegionServiceImpl implements RegionService {
    @Autowired
    RegionMapper regionMapper;

    @Override
    public List<Region> listRegion(int regionLevel) {
        RegionExample example = new RegionExample();
        example.createCriteria().andRegionLevelEqualTo((byte) regionLevel);
        return regionMapper.selectByExample(example);
    }

    @Override
    public List<Region> listSubRegion(int regionID) {
        RegionExample example = new RegionExample();
        example.createCriteria().andParentIdEqualTo(regionID);
        return regionMapper.selectByExample(example);
    }

    @Override
    public List<Region> listAllRegion() {
        RegionExample example = new RegionExample();
        example.setOrderByClause(" parent_id,region_id asc");
        return regionMapper.selectByExample(example);
    }
}
