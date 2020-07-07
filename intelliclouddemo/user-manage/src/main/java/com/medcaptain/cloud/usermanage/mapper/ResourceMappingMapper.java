package com.medcaptain.cloud.usermanage.mapper;

import com.medcaptain.cloud.usermanage.entity.ResourceMapping;
import com.medcaptain.cloud.usermanage.entity.ResourceMappingExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 前后端资源映射 数据库操作映射
 *
 * @author bingwen.ai
 */

@Component
public interface ResourceMappingMapper {
    int countByExample(ResourceMappingExample example);

    int deleteByExample(ResourceMappingExample example);

    int deleteByPrimaryKey(Integer resourceMappingId);

    int insert(ResourceMapping record);

    int insertSelective(ResourceMapping record);

    List<ResourceMapping> selectByExample(ResourceMappingExample example);

    ResourceMapping selectByPrimaryKey(Integer resourceMappingId);

    int updateByExampleSelective(@Param("record") ResourceMapping record, @Param("example") ResourceMappingExample example);

    int updateByExample(@Param("record") ResourceMapping record, @Param("example") ResourceMappingExample example);

    int updateByPrimaryKeySelective(ResourceMapping record);

    int updateByPrimaryKey(ResourceMapping record);

    List<ResourceMapping> selectByFrontendResourceID(int frontendResourceID);
}
