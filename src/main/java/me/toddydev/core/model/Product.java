package me.toddydev.core.model;

import lombok.Getter;
import lombok.Setter;
import me.toddydev.core.model.rewards.Reward;
import me.toddydev.core.player.order.gateways.Gateway;

@Getter @Setter
public class Product {

    private String id;

    private Gateway gateway;

    private Reward reward;
}
