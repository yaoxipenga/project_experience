package com.medcaptain.productservice.dao.mybatis;

import com.medcaptain.productservice.entity.mybatis.DeviceDepartmentRelated;
import com.medcaptain.productservice.entity.mybatis.DeviceDepartmentRelatedKey;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

@Component
public interface DeviceDepartmentRelatedMapper {
    int deleteByPrimaryKey(DeviceDepartmentRelatedKey key);

    int insert(DeviceDepartmentRelated record);

    int insertSelective(DeviceDepartmentRelated record);

    DeviceDepartmentRelated selectByPrimaryKey(DeviceDepartmentRelatedKey key);

    int updateByPrimaryKeySelective(DeviceDepartmentRelated record);

    int updateByPrimaryKey(DeviceDepartmentRelated record);

    /**
     * 删除 当前 部门 下的全部设备 的关联
     *
     * @return
     */
    int deleteBydepartmentId(@Param("departmentId") Integer departmentId);

    /**
     * 删除 当前 设备 下的全部部门 的关联
     *
     * @return
     */
    int deleteBydeviceTripleId(@Param("deviceTripleId") Integer deviceTripleId);

    /**
     * @param deviceTripleId 设备三元组id
     * @param departmentId   部门id
     * @return
     */
    int deleteByDeviceTripleIdAndDepartmentId(@Param(value = "deviceTripleId") Integer deviceTripleId, @Param(value = "departmentId") Integer departmentId);


    /**
     * 根据设备设备三元组和部门id查询数据 （包含已删除）
     *
     * @param deviceTripleId 设备三元组
     * @param departmentId   部门id
     * @return
     */
    DeviceDepartmentRelated selectByDeviceTripleIdAndDepartmentId(@Param(value = "deviceTripleId") Integer deviceTripleId, @Param(value = "departmentId") Integer departmentId);


}