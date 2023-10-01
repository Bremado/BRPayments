package me.toddydev.core.player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private UUID uniqueId;
    private String name;

    private int totalOrders;
    private int totalPaid;
    private int totalRefunded;

    private double balance;

    public User(UUID uniqueId, String name) {
        this.uniqueId = uniqueId;
        this.name = name;

        this.totalOrders = 0;
        this.totalPaid = 0;
        this.totalRefunded = 0;

        this.balance = 0;
    }
}
