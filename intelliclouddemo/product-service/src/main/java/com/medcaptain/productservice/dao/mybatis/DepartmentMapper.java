package com.medcaptain.productservice.dao.mybatis;

import com.medcaptain.productservice.entity.mybatis.Department;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DepartmentMapper {
    int deleteByPrimaryKey(Integer departmentId);

    int insert(Department record);

    int insertSelective(Department record);

    Department selectByPrimaryKey(Integer departmentId);

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);

    /**
     * 查询某一医院下，某个设备属于哪些科室
     *
     * @param deviceTripleId 设备id
     * @param organizationId 医院id
     * @return
     */
    List<Department> selectByDeviceTripleId(@Param(value = "deviceTripleId") Integer deviceTripleId, @Param(value = "organizationId") Integer organizationId);

    /**
     * 获取机构下全部科室
     *
     * @param organizationId 机构id
     * @return
     */
    List<Integer> getOrganizationList(@Param(value = "organizationId") Integer organizationId);

}