package com.witchshop.sharedlib.typehandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.io.IOException;
import java.sql.*;

public abstract class JsonTypeHandler<T> extends BaseTypeHandler<T> {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final TypeReference<T> typeReference;

    protected JsonTypeHandler(TypeReference<T> typeReference) {
        this.typeReference = typeReference;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType)
            throws SQLException {
        try {
            String json = objectMapper.writeValueAsString(parameter);
            ps.setObject(i, json, Types.OTHER); // Для Postgres JSONB
        } catch (JsonProcessingException e) {
            throw new SQLException("Error converting object to JSON string.", e);
        }
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        T result = parseJson(rs.getString(columnName));
        return result == null ? getEmptyValue() : result;
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        T result = parseJson(rs.getString(columnIndex));
        return result == null ? getEmptyValue() : result;
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        T result = parseJson(cs.getString(columnIndex));
        return result == null ? getEmptyValue() : result;
    }

    protected T getEmptyValue() {
        return null;
    }

    private T parseJson(String json) throws SQLException {
        try {
            return objectMapper.readValue(json, typeReference);
        } catch (IOException e) {
            throw new SQLException("Error parsing JSON string.", e);
        }
    }
}
