package me.toddydev.discord.commands.loader;

import me.toddydev.core.utils.ClassGetter;
import me.toddydev.discord.commands.AbstractCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandLoader {

    public static void load(JavaPlugin instance, String packageName) {
        for (Class<?> commandClass : ClassGetter.getClassesForPackage(instance, packageName)) {
            if (AbstractCommand.class.isAssignableFrom(commandClass)) {
                try {
                    AbstractCommand c = (AbstractCommand) commandClass.newInstance();
                    c.register();
                    Bukkit.getConsoleSender().sendMessage("[BRDiscord] Loaded command " + commandClass.getName() + ".");
                } catch (Exception e) {
                    Bukkit.getConsoleSender().sendMessage("[BRDiscord] Failed to load command " + commandClass.getName() + ".");
                }
            }
        }
    }

}
