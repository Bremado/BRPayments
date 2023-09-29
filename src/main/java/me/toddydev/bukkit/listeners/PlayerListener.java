package me.toddydev.bukkit.listeners;

import me.toddydev.core.cache.Caching;
import me.toddydev.core.database.tables.Tables;
import me.toddydev.core.player.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(AsyncPlayerPreLoginEvent event) {
        User user = Tables.getUsers().find(event.getUniqueId());

        if (user == null) {
            user = new User(event.getUniqueId(), event.getName());
            Tables.getUsers().create(user);
        }

        Caching.getUserCache().add(user);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Caching.getUserCache().remove(Caching.getUserCache().find(event.getPlayer().getUniqueId()));
    }
}
