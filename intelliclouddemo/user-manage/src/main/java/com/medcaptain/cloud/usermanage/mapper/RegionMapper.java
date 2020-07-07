package com.medcaptain.cloud.usermanage.mapper;

import com.medcaptain.cloud.usermanage.entity.Region;
import com.medcaptain.cloud.usermanage.entity.RegionExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 区域信息数据库操作映射
 *
 * @author bingwen.ai
 */

@Component
public interface RegionMapper {
    int countByExample(RegionExample example);

    int deleteByExample(RegionExample example);

    int deleteByPrimaryKey(Integer regionId);

    int insert(Region record);

    int insertSelective(Region record);

    List<Region> selectByExample(RegionExample example);

    Region selectByPrimaryKey(Integer regionId);

    int updateByExampleSelective(@Param("record") Region record, @Param("example") RegionExample example);

    int updateByExample(@Param("record") Region record, @Param("example") RegionExample example);

    int updateByPrimaryKeySelective(Region record);

    int updateByPrimaryKey(Region record);
}
