package com.medcaptain.cloud.usermanage.mapper;

import com.medcaptain.cloud.usermanage.entity.UserInfo;
import com.medcaptain.cloud.usermanage.entity.UserInfoExample;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 用户信息数据库操作映射
 *
 * @author bingwen.ai
 */
@Component
public interface UserInfoMapper {
    int countByExample(UserInfoExample example);

    int deleteByExample(UserInfoExample example);

    int deleteByPrimaryKey(Integer userId);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    List<UserInfo> selectByExample(UserInfoExample example);

    List<UserInfo> listUsers(Map<String, String> params);

    UserInfo selectByPrimaryKey(Integer userId);

    int updateByExampleSelective(@Param("record") UserInfo record, @Param("example") UserInfoExample example);

    int updateByExample(@Param("record") UserInfo record, @Param("example") UserInfoExample example);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);

    int countByUserNameAndOrganizationID(Map<String, String> params);

    List<UserInfo> listUsersHasPermission(Map<String, String> params);
}
