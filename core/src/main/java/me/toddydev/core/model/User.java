package me.toddydev.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter @Setter
@AllArgsConstructor
public class User {

    private final UUID id;
    private final String name;
    private Set<Order> orders = new HashSet<>();

    public User(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

}
