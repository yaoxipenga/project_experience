package com.medcaptain.cloud.usermanage.service;

import com.github.pagehelper.PageInfo;
import com.medcaptain.cloud.usermanage.entity.Department;

/**
 * 部门信息服务
 *
 * @author bingwen.ai
 */
public interface DepartmentService {
    PageInfo<Department> listDepartments(int pageIndex, int pageSize, int organizationID);

    Department getDepartment(int departmentID);

    Department getDepartment(String departmentName, int organizationID);

    boolean addDepartment(String departmentName, int hospitalID, String[] roleList);

    int updateDepartment(Department department);

    int deleteDepartment(int departmentID);

    boolean exist(int departmentID);

    boolean exist(String departmentName, int organizationID);

    boolean inUse(int departmentId);

    boolean hasDepartment(int organizationID);
}
