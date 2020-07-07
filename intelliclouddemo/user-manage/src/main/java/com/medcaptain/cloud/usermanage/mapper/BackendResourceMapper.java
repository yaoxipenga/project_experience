package com.medcaptain.cloud.usermanage.mapper;

import com.medcaptain.cloud.usermanage.entity.BackendResource;
import com.medcaptain.cloud.usermanage.entity.BackendResourceExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 后端资源数据库操作映射
 *
 * @author bingwen.ai
 */

@Component
public interface BackendResourceMapper {
    int countByExample(BackendResourceExample example);

    int deleteByExample(BackendResourceExample example);

    int deleteByPrimaryKey(Integer backendResourceId);

    int insert(BackendResource record);

    int insertSelective(BackendResource record);

    List<BackendResource> selectByExample(BackendResourceExample example);

    BackendResource selectByPrimaryKey(Integer backendResourceId);

    int updateByExampleSelective(@Param("record") BackendResource record, @Param("example") BackendResourceExample example);

    int updateByExample(@Param("record") BackendResource record, @Param("example") BackendResourceExample example);

    int updateByPrimaryKeySelective(BackendResource record);

    int updateByPrimaryKey(BackendResource record);
}
