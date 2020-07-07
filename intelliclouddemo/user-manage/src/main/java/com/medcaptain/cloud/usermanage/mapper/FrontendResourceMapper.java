package com.medcaptain.cloud.usermanage.mapper;

import com.medcaptain.cloud.usermanage.entity.FrontendResource;
import com.medcaptain.cloud.usermanage.entity.FrontendResourceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 前端资源数据库操作映射
 *
 * @author bingwen.ai
 */

@Component
public interface FrontendResourceMapper {
    int countByExample(FrontendResourceExample example);

    int deleteByExample(FrontendResourceExample example);

    int deleteByPrimaryKey(Integer frontendResourceId);

    int insert(FrontendResource record);

    int insertSelective(FrontendResource record);

    List<FrontendResource> selectByExample(FrontendResourceExample example);

    FrontendResource selectByPrimaryKey(Integer frontendResourceId);

    int updateByExampleSelective(@Param("record") FrontendResource record,
                                 @Param("example") FrontendResourceExample example);

    int updateByExample(@Param("record") FrontendResource record,
                        @Param("example") FrontendResourceExample example);

    int updateByPrimaryKeySelective(FrontendResource record);

    int updateByPrimaryKey(FrontendResource record);
}