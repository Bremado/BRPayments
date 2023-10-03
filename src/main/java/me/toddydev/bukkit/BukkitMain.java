package me.toddydev.bukkit;

import com.henryfabio.minecraft.inventoryapi.manager.InventoryManager;
import lombok.Getter;
import me.toddydev.bukkit.loaders.categories.CategoryLoader;
import me.toddydev.bukkit.loaders.commands.BukkitCommandLoader;
import me.toddydev.bukkit.loaders.gateways.GatewayLoader;
import me.toddydev.bukkit.loaders.listeners.ListenerLoader;
import me.toddydev.bukkit.loaders.products.ProductLoader;
import me.toddydev.bukkit.task.PayTask;
import me.toddydev.core.Core;
import me.toddydev.core.database.credentials.DatabaseCredentials;
import me.toddydev.core.database.tables.Tables;
import me.toddydev.core.plugin.BukkitPlugin;
import me.toddydev.discord.Discord;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class BukkitMain extends BukkitPlugin {

    @Getter
    private static YamlConfiguration messagesConfig;

    @Override
    public void load() {
        saveDefaultConfig();
        loadConfig();

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
        loadConfig();
        Tables.getUsers().create();
        Tables.getOrders().create();

        GatewayLoader.load(this);
        CategoryLoader.load(this);
        ProductLoader.load(this);

        ListenerLoader.load(this);

        InventoryManager.enable(this);

        BukkitCommandLoader.load(this);

        Core.setDiscord(new Discord(this));

        new PayTask().runTaskTimerAsynchronously(this, 0, 20*60);
    }

    private void loadConfig() {
        File f = new File(getDataFolder(), "messages.yml");

        if (!f.exists()) {
            saveResource("messages.yml", false);
        }

        messagesConfig = YamlConfiguration.loadConfiguration(f);
    }

    @Override
    public void disable() {
        Core.getDatabase().stop();
    }
}
