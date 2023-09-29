package me.toddydev.core.model;

import lombok.*;
import me.toddydev.core.model.actions.Action;
import me.toddydev.core.model.categories.Category;
import me.toddydev.core.model.icon.Icon;
import me.toddydev.core.model.rewards.Reward;
import me.toddydev.core.player.order.gateways.Gateway;

import java.util.List;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private String id;
    private String name;

    private Icon icon;
    private Category category;

    private Reward rewards;

    private List<Action> actions;
    private List<Gateway> gateways;

    private double price;

}
