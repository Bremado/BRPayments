package me.toddydev.core.model;

import lombok.*;
import me.toddydev.core.model.rewards.Reward;
import me.toddydev.core.player.order.gateways.Gateway;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private String id;

    private Gateway gateway;

    private Reward reward;
}
