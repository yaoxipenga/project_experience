package com.medcaptain.productservice.typehandler;

import com.medcaptain.enums.UpgradeStatus;
import com.medcaptain.productservice.enums.DeviceRunningStatusEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedJdbcTypes(JdbcType.INTEGER)
@MappedTypes(value = DeviceRunningStatusEnum.class)
public class DiviceRunningStatusHander extends BaseTypeHandler<DeviceRunningStatusEnum> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, DeviceRunningStatusEnum deviceRunningStatusEnum, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, deviceRunningStatusEnum.getCode());
    }

    @Override
    public DeviceRunningStatusEnum getNullableResult(ResultSet resultSet, String s) throws SQLException {
        int status = resultSet.getInt(s);
        if (status != 0 && status != 1 && status != 2 && status != 3){
            return null;
        }
        return DeviceRunningStatusEnum.getEnumById(status);
    }

    @Override
    public DeviceRunningStatusEnum getNullableResult(ResultSet resultSet, int i) throws SQLException {
        int status = resultSet.getInt(i);
        if (status != 0 && status != 1 && status != 2 && status != 3){
            return null;
        }
        return DeviceRunningStatusEnum.getEnumById(status);
    }

    @Override
    public DeviceRunningStatusEnum getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        int status = callableStatement.getInt(i);
        if (status != 0 && status != 1 && status != 2 && status != 3){
            return null;
        }
        return DeviceRunningStatusEnum.getEnumById(status);
    }
}
