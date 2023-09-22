package me.toddydev.core.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import me.toddydev.core.controller.UserController;
import me.toddydev.core.model.User;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class UserCache {

    private final LoadingCache<UUID, User> users;

    public UserCache(UserController userController) {
        users = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.MINUTES).build(new CacheLoader<UUID, User>() {
            @Override
            public User load(UUID key) {
                return userController.select(key);
            }
        });
    }

    public User get(UUID uuid) {
        return users.getUnchecked(uuid);
    }

}
