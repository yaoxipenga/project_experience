package com.medcaptain.productservice.dao.mybatis;

import com.medcaptain.productservice.entity.mybatis.Shadowdevice;

public interface ShadowdeviceMapper {
    int deleteByPrimaryKey(Integer deviceTripleId);

    int insert(Shadowdevice record);

    int insertSelective(Shadowdevice record);

    Shadowdevice selectByPrimaryKey(Integer deviceTripleId);

    int updateByPrimaryKeySelective(Shadowdevice record);

    int updateByPrimaryKeyWithBLOBs(Shadowdevice record);

    int updateByPrimaryKey(Shadowdevice record);
}