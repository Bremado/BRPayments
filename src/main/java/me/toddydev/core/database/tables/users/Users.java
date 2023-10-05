package me.toddydev.core.database.tables.users;

import me.toddydev.core.Core;
import me.toddydev.core.player.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class Users {

    public void create() {
        try {
            PreparedStatement ps = Core.getDatabase().getConnection().prepareStatement(
                    "CREATE TABLE IF NOT EXISTS users(uniqueId VARCHAR(36) PRIMARY KEY, name VARCHAR(16), totalOrders INT, totalPaid DOUBLE, totalRefunded DOUBLE, balance DOUBLE);"
            );
            ps.execute();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean create(User user) {
        try {
            PreparedStatement ps = Core.getDatabase().getConnection().prepareStatement(
                    "INSERT INTO users(uniqueId, name, totalOrders, totalPaid, totalRefunded, balance) VALUES(?, ?, ?, ?, ?, ?);"
            );
            ps.setString(1, user.getUniqueId().toString());
            ps.setString(2, user.getName());
            ps.setInt(3, user.getTotalOrders());
            ps.setDouble(4, user.getTotalPaid());
            ps.setDouble(5, user.getTotalRefunded());
            ps.setDouble(6, user.getBalance());
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public User find(UUID uniqueId) {
        try {
            PreparedStatement ps = Core.getDatabase().getConnection().prepareStatement(
                    "SELECT * FROM users WHERE uniqueId = ?;"
            );
            ps.setString(1, uniqueId.toString());
            ps.execute();
            while (ps.getResultSet().next()) {
                return new User(
                        UUID.fromString(ps.getResultSet().getString("uniqueId")),
                        ps.getResultSet().getString("name"),
                        ps.getResultSet().getInt("totalOrders"),
                        ps.getResultSet().getDouble("totalPaid"),
                        ps.getResultSet().getDouble("totalRefunded"),
                        ps.getResultSet().getDouble("balance"),
                        null
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User find(String name) {
        try {
            PreparedStatement ps = Core.getDatabase().getConnection().prepareStatement(
                    "SELECT * FROM users WHERE name = ?;"
            );
            ps.setString(1, name);
            ps.execute();
            while (ps.getResultSet().next()) {
                return new User(
                        UUID.fromString(ps.getResultSet().getString("uniqueId")),
                        ps.getResultSet().getString("name"),
                        ps.getResultSet().getInt("totalOrders"),
                        ps.getResultSet().getDouble("totalPaid"),
                        ps.getResultSet().getDouble("totalRefunded"),
                        ps.getResultSet().getDouble("balance"),
                        null
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(User user) {
        try {
            PreparedStatement ps = Core.getDatabase().getConnection().prepareStatement(
                    "UPDATE users SET name = ?, totalOrders = ?, totalPaid = ?, totalRefunded = ?, balance = ? WHERE uniqueId = ?;"
            );
            ps.setString(1, user.getName());
            ps.setInt(2, user.getTotalOrders());
            ps.setDouble(3, user.getTotalPaid());
            ps.setDouble(4, user.getTotalRefunded());
            ps.setDouble(5, user.getBalance());
            ps.setString(6, user.getUniqueId().toString());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}