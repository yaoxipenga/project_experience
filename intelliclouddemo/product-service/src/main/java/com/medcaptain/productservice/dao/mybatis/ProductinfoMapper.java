package com.medcaptain.productservice.dao.mybatis;

import com.medcaptain.productservice.entity.mybatis.Productinfo;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public interface ProductinfoMapper {
    int selectByProductModel(@Param("productModel") String productModel,
                             @Param("organizationId") Integer organizationId);

    int selectByProductName(@Param("productName") String productName,
                            @Param("organizationId") Integer organizationId);

    int selectByProductKey(@Param("productKey") String productKey,
                            @Param("organizationId") Integer organizationId);

    int updateProductInformation(String productKey, String productName, String productModel, String productDescribe);

    int getProductCount(@Param("productType") String productType, @Param("productName") String productName,
                        @Param("organizationId") Integer organizationId, @Param("userId") Integer userId,
                        @Param("userPermission") Integer userPermission);

    Map getProductCompleteInfo(String productKey);

    int deleteByProductKey(String productKey);

    List<Map<String, Object>> getProductList(Map map);

    int createProduct(Map map);

    int deleteByPrimaryKey(String productKey);

    int insert(Productinfo record);

    int insertSelective(Productinfo record);

    Productinfo selectByPrimaryKey(String productKey);

    int updateByPrimaryKeySelective(Productinfo record);

    int updateByPrimaryKey(Productinfo record);

    /**
     * 查询当前厂家全部产品 key
     *
     * @author yang
     */
    List<String> getOrganizationProductInfoList(@Param(value = "organizationId") Integer organizationId);

    /**
     * 指定时间的全部产品，包括已删除产品
     *
     * @author yang
     */
    List<Productinfo> getProductTime(
            @Param(value = "timeStart") Date timeStart,
            @Param(value = "timeEnd") Date timeEnd,
            @Param(value = "page") Integer page,
            @Param(value = "perPage") Integer perPage
    );


    List<Map> selectByRegionCount(@Param(value = "parentId") Integer parentId);
}