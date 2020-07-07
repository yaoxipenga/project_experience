package com.medcaptain.utils;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.List;

import static java.sql.Types.*;
import static java.sql.Types.NULL;

/**
 * 关系型数据库操作工具类
 *
 * @author bingwen.ai
 */
public class SqlUtil {
    private static Logger logger = LoggerFactory.getLogger(SqlUtil.class);

    private static final String QUOTATION_MARK = "\"";

    public static List commonSelect(Connection connection, String sql) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int fieldCount = resultSetMetaData.getColumnCount();
        StringBuilder stringBuilder = new StringBuilder("[");
        while (resultSet.next()) {
            stringBuilder.append("{");
            for (int i = 1; i <= fieldCount; i++) {
                String columnLabel = resultSetMetaData.getColumnLabel(i);
                int dataType = resultSetMetaData.getColumnType(i);
                String fieldValue = resultSet.getString(i);
                if (i > 1) {
                    stringBuilder.append(",");
                }
                stringBuilder.append(QUOTATION_MARK);
                stringBuilder.append(columnLabel);
                stringBuilder.append(QUOTATION_MARK + ":");
                if (needQuote(dataType)) {
                    stringBuilder.append(QUOTATION_MARK);
                    stringBuilder.append(fieldValue);
                    stringBuilder.append(QUOTATION_MARK);
                } else {
                    stringBuilder.append(fieldValue);
                }
            }
            stringBuilder.append("},");
        }
        stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
        stringBuilder.append("]");
        resultSet.close();
        statement.close();
        connection.close();
        return JSON.parseArray(stringBuilder.toString());
    }

    private static boolean needQuote(int dataType) {
        switch (dataType) {
            case BIT:
            case TINYINT:
            case SMALLINT:
            case INTEGER:
            case BIGINT:
            case FLOAT:
            case REAL:
            case DOUBLE:
            case NUMERIC:
            case DECIMAL:
            case NULL: {
                return false;
            }
            default: {
                return true;
            }
        }
    }
}
