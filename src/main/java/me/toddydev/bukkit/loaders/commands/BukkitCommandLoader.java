package me.toddydev.bukkit.loaders.commands;

import me.toddydev.bukkit.commands.BukkitCommand;
import me.toddydev.core.utils.classes.ClassGetter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public class BukkitCommandLoader {

    public static void load(JavaPlugin instance) {
        for (Class<?> commandClass : ClassGetter.getClassesForPackage(instance, "me.toddydev.bukkit.commands.register")) {
            if (BukkitCommand.class.isAssignableFrom(commandClass)) {
                try {
                    BukkitCommand commands = (BukkitCommand) commandClass.newInstance();
                    register(commands.getName(), commands);
                } catch (Exception e) {}
            }
        }
    }

    private static void register(String fallback, Command command) {
        try {
            Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
            commandMap.register(fallback, command);
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
    }

}
