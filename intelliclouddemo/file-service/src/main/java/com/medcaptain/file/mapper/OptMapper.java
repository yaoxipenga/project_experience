package com.medcaptain.file.mapper;

import com.medcaptain.file.entity.dto.OptInfoEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface OptMapper {
    List<OptInfoEntity> getOptLogList(int start, int size, int userId);
}
