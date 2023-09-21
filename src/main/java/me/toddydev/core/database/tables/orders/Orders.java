package me.toddydev.core.database.tables.orders;

import me.toddydev.core.Core;

import java.sql.PreparedStatement;

public class Orders {

    public void create() {
        try {
            PreparedStatement ps = Core.getDatabase().getConnection().prepareStatement(
                    "CREATE TABLE IF NOT EXISTS orders(payerId VARCHAR(36) PRIMARY KEY, referenceId VARCHAR(255), gateway VARCHAR(255), status VARCHAR(255), cost DOUBLE, FOREIGN KEY (payerId) REFERENCES users(uniqueId));"
            );
            ps.execute();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
