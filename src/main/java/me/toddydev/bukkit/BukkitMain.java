package me.toddydev.bukkit;

import me.toddydev.core.Core;
import me.toddydev.core.database.credentials.DatabaseCredentials;
import me.toddydev.core.database.tables.Tables;
import me.toddydev.core.plugin.BukkitPlugin;
import me.toddydev.discord.Discord;

public class BukkitMain extends BukkitPlugin {

    @Override
    public void load() {
        saveDefaultConfig();

        Core.getDatabase().start(
                new DatabaseCredentials(
                        getConfig().getString("database.host"),
                        getConfig().getString("database.port"),
                        getConfig().getString("database.username"),
                        getConfig().getString("database.password"),
                        getConfig().getString("database.database")
                )
        );
    }

    @Override
    public void enable() {
        Tables.getUsers().create();
        Tables.getOrders().create();

        Core.setDiscord(new Discord(this));
    }

    @Override
    public void disable() {
        Core.getDatabase().stop();
    }
}
