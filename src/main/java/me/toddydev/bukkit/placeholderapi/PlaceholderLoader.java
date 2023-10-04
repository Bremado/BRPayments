package me.toddydev.bukkit.placeholderapi;

import lombok.Getter;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.logging.Logger;

public class PlaceholderLoader {

    @Getter
    private static boolean enabled = false;

    public static void load() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            return;
        }

        Logger.getLogger("BRPayments").info("PlaceholderAPI found! Enabling placeholders...");
        enabled = true;
    }

    public static String setPlaceholders(Player player, String string) {
        if (!enabled) {
            return string;
        }

        return PlaceholderAPI.setPlaceholders(player, string);
    }
}
