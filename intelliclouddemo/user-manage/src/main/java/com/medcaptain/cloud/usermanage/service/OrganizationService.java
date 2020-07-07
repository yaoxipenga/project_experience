package com.medcaptain.cloud.usermanage.service;

import com.github.pagehelper.PageInfo;
import com.medcaptain.cloud.usermanage.entity.Organization;

/**
 * 机构信息服务
 *
 * @author bingwen.ai
 */
public interface OrganizationService {
    PageInfo<Organization> listOrganizations(int pageIndex, int pageSize, int organizationType, String provinceName,
                                             int regionCode, String organizationName, String orderBy);

    /**
     * 新增机构信息，并初始化管理员角色及前端权限
     *
     * @param organizationName  机构名称
     * @param organizationLevel 医院登记
     * @param organizationType  机构类型：0=医院；1=厂商；2=平台
     * @param regionCode        区域编号
     * @param createUserID      当前用户编号
     * @param remark            备注信息
     * @param managerEmail      机构管理员邮箱地址
     * @param departmentList    科室模板编号数组
     * @return 新增成功 = true；新增失败 =false
     * @throws IllegalArgumentException
     */
    boolean addOrganization(String organizationName, int organizationLevel, int organizationType,
                            int regionCode, int createUserID, String remark, String managerEmail, int[] departmentList)
            throws IllegalArgumentException;

    int updateOrganization(Organization organization);

    int deleteOrganization(int organizationID);

    boolean exist(String organizationName);


    boolean exist(int organizationID);

    Organization getOrganization(String organizationName);

    Organization getOrganization(int organizationID);

    boolean inUse(int organizationId);
}
