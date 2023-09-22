package me.toddydev.bukkit;

import me.saiintbrisson.bukkit.command.BukkitFrame;
import me.toddydev.bukkit.command.PaymentCommand;
import me.toddydev.core.database.PooledConnection;
import org.bukkit.plugin.java.JavaPlugin;

public class PaymentsPlugin extends JavaPlugin {

    private PooledConnection pooledConnection;

    @Override
    public void onLoad() {
        saveResource("database.properties", false);
        pooledConnection = new PooledConnection(getDataFolder().getAbsolutePath(), "database.properties");
    }

    @Override
    public void onEnable() {
        BukkitFrame bukkitFrame = new BukkitFrame(this);
        bukkitFrame.registerCommands(new PaymentCommand());
    }

    @Override
    public void onDisable() {
        pooledConnection.close();
    }
}
