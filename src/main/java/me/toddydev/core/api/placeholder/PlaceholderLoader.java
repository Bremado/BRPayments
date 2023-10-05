package me.toddydev.core.api.placeholder;

import lombok.Getter;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
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

    public static List<String> setPlaceholders(Player player, List<String> list) {
        if (!enabled) {
            return list;
        }

        return PlaceholderAPI.setPlaceholders(player, list);
    }
}