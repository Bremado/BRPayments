package me.toddydev.core.database;

import com.google.common.collect.ImmutableMap;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;

public class QueryResponse {

    private final Map<String, Object> response;

    QueryResponse(Map<String, Object> response) {
        this.response = response;
    }

    public <T> T get(String key) {
        return (T) response.get(key);
    }

    public <T> T getOrDefault(String key, T defaultValue) {
        return (T) response.getOrDefault(key, defaultValue);
    }

    public static QueryResponse of(ResultSet resultSet) throws SQLException {
        ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();

        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int columnCount = resultSetMetaData.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            String columnName = resultSetMetaData.getColumnName(i);
            Object result = resultSet.getObject(columnName);
            if (result == null) continue;

            builder.put(columnName, result);
        }

        return new QueryResponse(builder.build());
    }

}
