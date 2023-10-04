package me.toddydev.core.model.product;

import lombok.*;
import me.toddydev.core.model.order.gateway.Gateway;
import me.toddydev.core.model.product.actions.Action;
import me.toddydev.core.model.product.categories.Category;
import me.toddydev.core.model.product.icon.Icon;
import me.toddydev.core.model.product.rewards.Reward;

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
