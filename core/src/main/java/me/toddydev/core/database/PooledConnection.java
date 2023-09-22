package me.toddydev.core.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;

import java.io.File;
import java.sql.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public class PooledConnection {

    private final HikariDataSource hikariDataSource;

    public PooledConnection(String folder, String file) {
        hikariDataSource = new HikariDataSource(new HikariConfig(folder + File.separator + file));
    }

    public void statement(String query) {
        try (Connection connection = hikariDataSource.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void prepareStatement(String query, Object... parameters) {
        try (Connection connection = hikariDataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (int i = 0; i < parameters.length; i++) preparedStatement.setObject(i + 1, parameters[i]);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void prepareStatement(String query, List<Object[]> list) {
        try (Connection connection = hikariDataSource.getConnection()) {
            for (Object[] parameters : list) {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                for (int i = 0; i < parameters.length; i++) preparedStatement.setObject(i + 1, parameters[i]);

                preparedStatement.execute();
                preparedStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public QueryResponse selectAny(String query, Object... parameters) {
        try (Connection connection = hikariDataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (int i = 0; i < parameters.length; i++) preparedStatement.setObject(i + 1, parameters[i]);

            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next() ? QueryResponse.of(resultSet) : null;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Set<QueryResponse> select(String query, Object... parameters) {
        try (Connection connection = hikariDataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (int i = 0; i < parameters.length; i++) preparedStatement.setObject(i + 1, parameters[i]);

            ResultSet resultSet = preparedStatement.executeQuery();

            Set<QueryResponse> set = new HashSet<>();
            while (resultSet.next()) set.add(QueryResponse.of(resultSet));

            return set;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Collections.emptySet();
    }

    public void close() {
        hikariDataSource.close();
    }
}
