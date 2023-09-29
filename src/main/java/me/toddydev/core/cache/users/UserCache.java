package me.toddydev.core.cache.users;

import me.toddydev.core.player.User;
import org.apache.commons.collections4.map.HashedMap;

import java.util.UUID;

public class UserCache {

    private HashedMap<UUID, User> users = new HashedMap<>();

    public void add(User user) {
        users.put(user.getUniqueId(), user);
    }

    public void remove(User user) {
        users.remove(user.getUniqueId());
    }

    public User find(UUID uuid) {
        return users.get(uuid);
    }

    public User findByName(String name) {
        return users.values().stream().filter(u -> u.getName().equals(name)).findFirst().orElse(null);
    }
}
