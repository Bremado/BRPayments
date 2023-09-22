package me.toddydev.core.controller;

import me.toddydev.core.database.PooledConnection;
import me.toddydev.core.database.QueryResponse;
import me.toddydev.core.model.User;
import me.toddydev.core.util.GsonUtil;

import java.util.UUID;

public class UserController {

    private static final String QUERY_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS `users` (`uuid` VARCHAR(36) PRIMARY KEY, `name` VARCHAR(16), `orders` TEXT DEFAULT `[]`);";
    private static final String QUERY_UPDATE_INSERT = "INSERT INTO `users` (`uuid`, `name`, `orders`) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE `orders` = VALUE(`orders`);";

    private static final String QUERY_SELECT = "SELECT * FROM `users` WHERE `uuid` = ?;";

    private final PooledConnection pooledConnection;

    public UserController(PooledConnection pooledConnection) {
        pooledConnection.statement(QUERY_CREATE_TABLE);

        this.pooledConnection = pooledConnection;
    }

    public void insertUpdate(User user) {
        pooledConnection.prepareStatement(QUERY_UPDATE_INSERT, user.getId().toString(), user.getName(), user.getOrders());
    }

    public User select(UUID uuid) {
        QueryResponse queryResponse = pooledConnection.selectAny(QUERY_SELECT, uuid.toString());
        if (queryResponse == null) return null;

        return new User(queryResponse.get("uuid"), queryResponse.get("name"), GsonUtil.fromJson(queryResponse.get("orders")));
    }

}
