package com.medcaptain.file.typehandler;

import com.medcaptain.enums.UpgradeStatus;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedJdbcTypes(JdbcType.INTEGER)
@MappedTypes(value = UpgradeStatus.class)
public class DeviceUpgradeStatusHandler extends BaseTypeHandler<UpgradeStatus> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, UpgradeStatus upgradeStatus, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, upgradeStatus.getCode());
    }

    @Override
    public UpgradeStatus getNullableResult(ResultSet resultSet, String s) throws SQLException {
        int status = resultSet.getInt(s);
        if (status != 0 && status != 1 && status != 2 && status != 3){
            return null;
        }
        return UpgradeStatus.getEnumById(status);
    }

    @Override
    public UpgradeStatus getNullableResult(ResultSet resultSet, int i) throws SQLException {
        int status = resultSet.getInt(i);
        if (status != 0 && status != 1 && status != 2 && status != 3){
            return null;
        }
        return UpgradeStatus.getEnumById(status);
    }

    @Override
    public UpgradeStatus getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        int status = callableStatement.getInt(i);
        if (status != 0 && status != 1 && status != 2 && status != 3){
            return null;
        }
        return UpgradeStatus.getEnumById(status);
    }
}
