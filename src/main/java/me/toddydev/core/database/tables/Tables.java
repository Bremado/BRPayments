package me.toddydev.core.database.tables;

import lombok.Getter;
import me.toddydev.core.database.tables.orders.Orders;
import me.toddydev.core.database.tables.users.Users;

public class Tables {

    @Getter
    private static Users users = new Users();

    @Getter
    private static Orders orders = new Orders();
}
