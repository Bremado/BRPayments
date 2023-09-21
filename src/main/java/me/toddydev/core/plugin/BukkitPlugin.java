package me.toddydev.core.plugin;

import org.bukkit.plugin.java.JavaPlugin;

public abstract class BukkitPlugin extends JavaPlugin {

    public abstract void load();
    public abstract void enable();
    public abstract void disable();

    public static JavaPlugin getInstance() {
        return getPlugin(BukkitPlugin.class);
    }

    @Override
    public void onLoad() {
        load();
    }

    @Override
    public void onEnable() {
        enable();
    }

    @Override
    public void onDisable() {
        disable();
    }
}
