package com.medcaptain.file.mapper;

import com.medcaptain.file.entity.mysql.OptLog;
import com.medcaptain.file.entity.mysql.OptLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface OptLogMapper {
    int countByExample(OptLogExample example);

    int deleteByExample(OptLogExample example);

    int deleteByPrimaryKey(Integer optlogId);

    int insert(OptLog record);

    int insertSelective(OptLog record);

    List<OptLog> selectByExampleWithBLOBs(OptLogExample example);

    List<OptLog> selectByExample(OptLogExample example);

    OptLog selectByPrimaryKey(Integer optlogId);

    int updateByExampleSelective(@Param("record") OptLog record, @Param("example") OptLogExample example);

    int updateByExampleWithBLOBs(@Param("record") OptLog record, @Param("example") OptLogExample example);

    int updateByExample(@Param("record") OptLog record, @Param("example") OptLogExample example);

    int updateByPrimaryKeySelective(OptLog record);

    int updateByPrimaryKeyWithBLOBs(OptLog record);

    int updateByPrimaryKey(OptLog record);
}