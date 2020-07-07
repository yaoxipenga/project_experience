package com.medcaptain.file.mapper;

import com.medcaptain.file.entity.mysql.OptType;
import com.medcaptain.file.entity.mysql.OptTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OptTypeMapper {
    int countByExample(OptTypeExample example);

    int deleteByExample(OptTypeExample example);

    int deleteByPrimaryKey(Integer optType);

    int insert(OptType record);

    int insertSelective(OptType record);

    List<OptType> selectByExample(OptTypeExample example);

    OptType selectByPrimaryKey(Integer optType);

    int updateByExampleSelective(@Param("record") OptType record, @Param("example") OptTypeExample example);

    int updateByExample(@Param("record") OptType record, @Param("example") OptTypeExample example);

    int updateByPrimaryKeySelective(OptType record);

    int updateByPrimaryKey(OptType record);
}